package com.onwordiesquire.android.getyourreviews.data.response

data class ReviewPageDto(
        val data: List<ReviewDto>?
)

data class ReviewDto(
        val review_id: Int?,
        val rating: Double?,
        val title: String?,
        val message: String?,
        val author: String?,
        val date: String?
)