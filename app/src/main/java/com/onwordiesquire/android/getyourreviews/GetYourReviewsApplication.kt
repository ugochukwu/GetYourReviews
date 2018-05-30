package com.onwordiesquire.android.getyourreviews

import android.app.Application
import com.onwordiesquire.android.getyourreviews.data.DataRepository
import com.onwordiesquire.android.getyourreviews.di.networkModule
import com.onwordiesquire.android.getyourreviews.di.repositoryModule
import com.onwordiesquire.android.getyourreviews.utils.MyLogger
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.startKoin

class GetYourReviewsApplication : Application(), MyLogger {

    private val dataRepository: DataRepository by inject()

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(networkModule, repositoryModule))
    }
}