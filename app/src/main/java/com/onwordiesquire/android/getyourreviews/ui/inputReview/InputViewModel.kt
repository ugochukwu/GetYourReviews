package com.onwordiesquire.android.getyourreviews.ui.inputReview

import com.onwordiesquire.android.getyourreviews.data.DataRepository
import com.onwordiesquire.android.getyourreviews.ui.base.BaseViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class InputViewModel(private val dataRepository: DataRepository) : BaseViewModel() {

    fun onSubmitData(reviewSubmission: ReviewSubmission) {
        dataRepository.createReview(reviewSubmission)
    }

}

data class ReviewSubmission(val title: String,
                            val message: String,
                            val reviewerName: String,
                            val rating: Int,
                            val date: String = Date(System.currentTimeMillis()).formatted())

fun Date.formatted(): String {
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault()).run {
        format(this)
    }
}
