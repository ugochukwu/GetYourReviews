package com.onwordiesquire.android.getyourreviews.data

import com.onwordiesquire.android.getyourreviews.data.response.ReviewPageDto
import com.onwordiesquire.android.getyourreviews.ui.inputReview.ReviewSubmission
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

    fun createReview(reviewSubmission: ReviewSubmission)
}

enum class SortDirection(val value: String) {
    DESC("DESC"),
    ASC("ASC")
}

sealed class DataSourceResponse {
    data class Success(val payload: ReviewPageDto?) : DataSourceResponse()
    class Failure : DataSourceResponse()
}