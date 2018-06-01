package com.onwordiesquire.android.getyourreviews.ui.reviewList

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.onwordiesquire.android.getyourreviews.data.DataRepository
import com.onwordiesquire.android.getyourreviews.data.DataSourceResponse
import com.onwordiesquire.android.getyourreviews.data.SortDirection
import com.onwordiesquire.android.getyourreviews.ui.ModelState
import com.onwordiesquire.android.getyourreviews.ui.UiModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ReviewsViewModel(private val dataRepository: DataRepository) : ViewModel() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
        get() = if (field.isDisposed) CompositeDisposable() else field
    private var uiModelLiveData: MutableLiveData<UiModel> = MutableLiveData()
    private var selectedSortOption: SortDirection = SortDirection.DESC
    private var resultSize: Int = DEFAULT_NO_OF_ITEMS

    fun subscribeToUiEvents() = uiModelLiveData

    private fun loadData(noOfItems: Int = DEFAULT_NO_OF_ITEMS, sortDirection: SortDirection = SortDirection.DESC) {
        val disposable = dataRepository.fetchReviews(location = LOCATION, tour = TOUR, count = noOfItems, sortDirection = sortDirection)
                .map({ mapToUiState(it) })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toFlowable()
                .startWith(UiModel(state = ModelState.Loading()))
                .subscribe(
                        { uiModelLiveData.value = it },
                        { uiModelLiveData.value = UiModel(state = ModelState.Error(ERROR_MSG)) }
                )
        compositeDisposable.add(disposable)
    }

    private fun mapToUiState(dataSourceResponse: DataSourceResponse): UiModel {
        return when (dataSourceResponse) {
            is DataSourceResponse.Success -> dataSourceResponse.payload?.let { UiModel(state = ModelState.Success(data = it)) }
                    ?: UiModel(state = ModelState.Error(ERROR_MSG))
            else -> UiModel(state = ModelState.Error(ERROR_MSG))
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun onSortOptionSelected(selectedOption: String?) {
        selectedOption?.let {
            selectedSortOption = SortDirection.valueOf(it)
        }
    }

    fun onResultSizeOptionSelected(resultSizeOption: String?) {
        resultSizeOption?.let {
            if (it.isNotEmpty()) {
                resultSize = when {
                    it.equals(other = "all", ignoreCase = true) -> ALL_ITEMS
                    else -> it.toInt()
                }
            }
        }
    }

    fun onSearchClick() {
        loadData(noOfItems = resultSize, sortDirection = selectedSortOption)
    }
}

const val ALL_ITEMS = 0
const val LOCATION = "berlin-l17"
const val TOUR = "tempelhof-2-hour-airport-history-tour-berlin-airlift-more-t23776"
const val DEFAULT_NO_OF_ITEMS = 20
const val ERROR_MSG = "Sorry something's gone wrong"