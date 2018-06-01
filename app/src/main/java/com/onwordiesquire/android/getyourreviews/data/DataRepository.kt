package com.onwordiesquire.android.getyourreviews.data

import com.onwordiesquire.android.getyourreviews.data.response.ReviewPageDto
import com.onwordiesquire.android.getyourreviews.ui.inputReview.ReviewSubmission
import io.reactivex.Flowable

interface DataRepository {

    fun fetchReviews(location: String,
                     tour: String,
                     pageNo: Int = 0,
                     count: Int = 0,
                     rating: Int = 0,
                     type: String = "",
                     sortBy: String = "",
                     sortDirection: SortDirection = SortDirection.DESC): Flowable<DataSourceResponse>

    fun createReview(reviewSubmission: ReviewSubmission)
}

enum class SortDirection(val value: String) {
    DESC("DESC"),
    ASC("ASC")
}

sealed class DataSourceResponse {
    data class Success(val payload: ReviewPageDto?) : DataSourceResponse()
    data class Failure(val code: Int) : DataSourceResponse()
}