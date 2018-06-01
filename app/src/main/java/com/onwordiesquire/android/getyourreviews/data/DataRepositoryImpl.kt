package com.onwordiesquire.android.getyourreviews.data

import com.onwordiesquire.android.getyourreviews.data.datasource.AppDatabase
import com.onwordiesquire.android.getyourreviews.data.datasource.Review
import com.onwordiesquire.android.getyourreviews.data.datasource.ReviewsApi
import com.onwordiesquire.android.getyourreviews.data.response.ReviewDto
import com.onwordiesquire.android.getyourreviews.data.response.ReviewPageDto
import com.onwordiesquire.android.getyourreviews.ui.inputReview.ReviewSubmission
import com.onwordiesquire.android.getyourreviews.utils.orDefault
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Response


class DataRepositoryImpl(private val remoteDataSource: ReviewsApi,
                         private val localDataSource: AppDatabase) : DataRepository {

    override fun createReview(reviewSubmission: ReviewSubmission) =
            Completable.fromAction {
                reviewSubmission.apply {
                    localDataSource.reviewDao().insertAll(Review(
                            title = title,
                            message = message,
                            author = reviewerName,
                            date = date,
                            rating = rating
                    ))
                }
            }

    /**
     * The aim here is to combine the local datasource(database) stream and the remote data source stream,
     * emitting results from both.
     * To support offline operation each call to the remote datasource results in the data being persisted
     * to the database.
     */
    override fun fetchReviews(location: String, tour: String, pageNo: Int, count: Int, rating: Int,
                              type: String, sortBy: String, sortDirection: SortDirection): Single<DataSourceResponse> {
        return remoteDataSource.getReviews(
                location = location,
                tour = tour,
                pageNo = pageNo,
                count = count,
                rating = rating,
                type = type,
                sortBy = sortBy,
                direction = sortDirection.value)
                .doOnSuccess(persistResponseInDatabase())
                .flatMap { emitResultsFromDatabase() }
                .onErrorResumeNext { emitResultsFromDatabase() }
    }

    private fun emitResultsFromDatabase(): Single<DataSourceResponse>? {
        return localDataSource.reviewDao().getAll().map {
            it.map {
                ReviewDto(it.review_id, it.rating,
                        it.title, it.message,
                        it.author, it.date)
            }.run {
                if (this.isNotEmpty()) {
                    DataSourceResponse.Success(payload = ReviewPageDto(data = this))
                } else {
                    DataSourceResponse.Failure()
                }
            }
        }
    }

    private fun persistResponseInDatabase(): (Response<ReviewPageDto>) -> Unit {
        return { response ->
            with(response.body()) {
                this?.data?.let {
                    it.map {
                        mapApiResponseToDbEntity(it)
                    }.filter {
                        it.isNotDefault()
                    }.run {
                        clearDbAndStoreNewResults(this)
                    }
                }
            }
        }
    }

    private fun clearDbAndStoreNewResults(list: List<Review>) {
        if (list.isNotEmpty()) {
            localDataSource.reviewDao().deleteAll()
            localDataSource.reviewDao().insertAll(*list.toTypedArray())
        }
    }

    private fun mapApiResponseToDbEntity(it: ReviewDto): Review {
        return Review(rating = it.rating.orDefault(),
                title = it.title.orDefault(),
                message = it.message.orDefault(),
                date = it.date.orDefault(),
                author = it.author.orDefault(),
                review_id = it.review_id.orDefault(0))
    }
}

fun Review.isDefault() =
        rating == 0.0 && title.isEmpty() && message.isEmpty() && date.isEmpty() && author.isEmpty()

fun Review.isNotDefault() = !isDefault()