package com.onwordiesquire.android.getyourreviews.ui

import com.onwordiesquire.android.getyourreviews.data.response.ReviewPageDto

data class UiModel(val state: ModelState)

sealed class ModelState {
    class Loading : ModelState()
    class Success(val data: ReviewPageDto) : ModelState()
    class Empty(val message: String) : ModelState()
    class Error(val message: String) : ModelState()
}