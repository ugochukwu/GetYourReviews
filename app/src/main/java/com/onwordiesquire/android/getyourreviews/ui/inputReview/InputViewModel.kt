package com.onwordiesquire.android.getyourreviews.ui.inputReview

import com.onwordiesquire.android.getyourreviews.data.DataRepository
import com.onwordiesquire.android.getyourreviews.ui.base.BaseViewModel
import com.onwordiesquire.android.getyourreviews.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class InputViewModel(private val dataRepository: DataRepository) : BaseViewModel() {

    val navigationEventLiveData: SingleLiveEvent<Unit> = SingleLiveEvent()

    fun onSubmitData(reviewSubmission: ReviewSubmission) {
        val disposable = dataRepository.createReview(reviewSubmission)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnComplete({
                    navigationEventLiveData.call()
                })
                .subscribe()
        compositeDisposable.add(disposable)
    }

}

data class ReviewSubmission(val title: String,
                            val message: String,
                            val reviewerName: String,
                            val rating: Double = 0.0,
                            val date: String = Date(System.currentTimeMillis()).formatted())

fun Date.formatted(): String {
    return SimpleDateFormat("MMM dd, YYYY", Locale.getDefault()).run {
        format(this@formatted)
    }
}
