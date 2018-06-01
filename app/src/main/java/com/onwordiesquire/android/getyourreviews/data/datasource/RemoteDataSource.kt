package com.onwordiesquire.android.getyourreviews.data.datasource

import com.onwordiesquire.android.getyourreviews.data.response.ReviewPageDto
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewsApi {
    @GET("{location}/{tour}/reviews.json")
    fun getReviews(@Path("location") location: String,
                   @Path("tour") tour: String,
                   @Query("count") count: Int?,
                   @Query("page") pageNo: Int?,
                   @Query("rating") rating: Int?,
                   @Query("type") type: String?,
                   @Query("sortBy") sortBy: String?,
                   @Query("direction") direction: String?,
                   @Header("User-Agent") header: String = "Get Your Guide"): Single<Response<ReviewPageDto>>
}

const val BASE_URL = "https://www.getyourguide.com/"