package com.onwordiesquire.android.getyourreviews.di

import com.onwordiesquire.android.getyourreviews.data.DataRepository
import com.onwordiesquire.android.getyourreviews.data.DataRepositoryImpl
import com.onwordiesquire.android.getyourreviews.data.datasource.ReviewsApi
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit

val repositoryModule = applicationContext {
    bean { provideRepository(createApi(get())) as DataRepository }
}

inline fun <reified T> createApi(retrofit: Retrofit) = retrofit.create(T::class.java)

fun provideRepository(reviewsApi: ReviewsApi) = DataRepositoryImpl(reviewsApi)