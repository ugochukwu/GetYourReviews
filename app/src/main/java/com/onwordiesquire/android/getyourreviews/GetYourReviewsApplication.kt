package com.onwordiesquire.android.getyourreviews

import android.app.Application
import com.onwordiesquire.android.getyourreviews.di.networkModule
import com.onwordiesquire.android.getyourreviews.di.repositoryModule
import com.onwordiesquire.android.getyourreviews.di.viewModelModule
import org.koin.android.ext.android.startKoin

class GetYourReviewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(networkModule, repositoryModule, viewModelModule))
    }
}