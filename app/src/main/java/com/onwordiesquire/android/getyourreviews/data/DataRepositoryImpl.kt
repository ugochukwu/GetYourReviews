package com.onwordiesquire.android.getyourreviews.data

import com.onwordiesquire.android.getyourreviews.data.datasource.AppDatabase
import com.onwordiesquire.android.getyourreviews.data.datasource.Review
import com.onwordiesquire.android.getyourreviews.data.datasource.ReviewsApi
import com.onwordiesquire.android.getyourreviews.data.response.ReviewDto
import com.onwordiesquire.android.getyourreviews.data.response.ReviewPageDto
import com.onwordiesquire.android.getyourreviews.ui.inputReview.ReviewSubmission
import com.onwordiesquire.android.getyourreviews.utils.orDefault
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response


class DataRepositoryImpl(private val remoteDataSource: ReviewsApi,
                         private val localDataSource: AppDatabase) : DataRepository {

    override fun createReview(reviewSubmission: ReviewSubmission) {
        return reviewSubmission.run {
            localDataSource.reviewDao().insertAll(Review(
                    title = title,
                    message = message,
                    author = reviewerName,
                    date = date,
                    rating = rating.toDouble()
            ))
        }
    }

    override fun fetchReviews(location: String, tour: String, pageNo: Int, count: Int, rating: Int,
                              type: String, sortBy: String, sortDirection: SortDirection): Flowable<DataSourceResponse> {
        val remoteStream = remoteDataSource.getReviews(
                location = location,
                tour = tour,
                pageNo = pageNo,
                count = count,
                rating = rating,
                type = type,
                sortBy = sortBy,
                direction = sortDirection.value)
                .doOnSuccess(persistResponseInDatabase())
                .map { response ->
                    with(response) {
                        when {
                            isSuccessful -> DataSourceResponse.Success(payload = body())
                            else -> DataSourceResponse.Failure(code())
                        }
                    }
                }

        val localStream = localDataSource.reviewDao().getAll().map {
            it.map {
                ReviewDto(it.review_id, it.rating,
                        it.title, it.message,
                        it.author, it.date)
            }.run { DataSourceResponse.Success(payload = ReviewPageDto(data = this)) }
        }

        return Single.merge(localStream, remoteStream)
    }

    private fun persistResponseInDatabase(): (Response<ReviewPageDto>) -> Unit {
        return { response ->
            with(response.body()) {
                this?.data?.let {
                    it.map {
                        Review(rating = it.rating.orDefault(),
                                title = it.title.orDefault(),
                                message = it.message.orDefault(),
                                date = it.date.orDefault(),
                                author = it.author.orDefault())
                    }.filter {
                        it.isNotDefault()
                    }.run {
                        localDataSource.reviewDao().deleteAll()
                        localDataSource.reviewDao().insertAll(*this.toTypedArray())
                    }
                }
            }
        }
    }
}

fun Review.isDefault() =
        rating == 0.0 && title.isEmpty() && message.isEmpty() && date.isEmpty() && author.isEmpty()

fun Review.isNotDefault() = !isDefault()