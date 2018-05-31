package com.onwordiesquire.android.getyourreviews.data

import com.onwordiesquire.android.getyourreviews.data.response.ReviewPageDto
import io.reactivex.Single

interface DataRepository {

    fun fetchReviews(location: String,
                     tour: String,
                     pageNo: Int = 0,
                     count: Int = 0,
                     rating: Int = 0,
                     type: String = "",
                     sortBy: String = "",
                     sortDirection: SortDirection = SortDirection.DESC): Single<DataSourceResponse>
}

enum class SortDirection(val value: String) {
    DESC("DESC"),
    ASC("ASC")
}

sealed class DataSourceResponse {
    data class Success(val payload: ReviewPageDto?) : DataSourceResponse()
    data class Failure(val code: Int) : DataSourceResponse()
}