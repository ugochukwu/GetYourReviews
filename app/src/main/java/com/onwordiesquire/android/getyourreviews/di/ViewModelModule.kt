package com.onwordiesquire.android.getyourreviews.di

import com.onwordiesquire.android.getyourreviews.ui.reviewList.ReviewsViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext

var viewModelModule = applicationContext {
    viewModel { ReviewsViewModel(get()) }
}