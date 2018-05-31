package com.onwordiesquire.android.getyourreviews.data

import com.onwordiesquire.android.getyourreviews.data.datasource.ReviewsApi
import io.reactivex.Single

class DataRepositoryImpl(private val remoteDataSource: ReviewsApi) : DataRepository {
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
                direction = sortDirection.value).map { response ->
            with(response) {
                when {
                    isSuccessful -> DataSourceResponse.Success(payload = body())
                    else -> DataSourceResponse.Failure(code())
                }
            }
        }
    }
}