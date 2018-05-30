package com.onwordiesquire.android.getyourreviews.data.response

data class ReviewPageDto(
        val status: Boolean?,
        val total_reviews_comments: Int?,
        val data: List<ReviewDto>?
)

data class ReviewDto(
        val review_id: Int?,
        val rating: String?,
        val title: String?,
        val message: String?,
        val author: String?,
        val foreignLanguage: Boolean?,
        val date: String?,
        val date_unformatted: String?,
        val languageCode: String?,
        val traveler_type: String?,
        val reviewerName: String?,
        val reviewerCountry: String?
)