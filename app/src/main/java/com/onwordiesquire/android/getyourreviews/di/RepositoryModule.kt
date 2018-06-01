package com.onwordiesquire.android.getyourreviews.di

import android.arch.persistence.room.Room
import android.content.Context
import com.onwordiesquire.android.getyourreviews.data.DataRepository
import com.onwordiesquire.android.getyourreviews.data.DataRepositoryImpl
import com.onwordiesquire.android.getyourreviews.data.datasource.AppDatabase
import com.onwordiesquire.android.getyourreviews.data.datasource.ReviewsApi
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit

val repositoryModule = applicationContext {
    bean {
        provideRepository(
                createApi(get()),
                provideDatabase(androidApplication())
        ) as DataRepository
    }
}

inline fun <reified T> createApi(retrofit: Retrofit) = retrofit.create(T::class.java)

fun provideRepository(reviewsApi: ReviewsApi, appDatabase: AppDatabase) = DataRepositoryImpl(reviewsApi, appDatabase)

fun provideDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "room.db")
                .build()
