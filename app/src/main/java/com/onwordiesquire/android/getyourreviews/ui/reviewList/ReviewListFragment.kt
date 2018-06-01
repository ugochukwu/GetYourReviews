package com.onwordiesquire.android.getyourreviews.ui.reviewList


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.onwordiesquire.android.getyourreviews.R
import com.onwordiesquire.android.getyourreviews.data.response.ReviewPageDto
import com.onwordiesquire.android.getyourreviews.ui.ModelState
import com.onwordiesquire.android.getyourreviews.utils.hide
import com.onwordiesquire.android.getyourreviews.utils.show
import kotlinx.android.synthetic.main.fragment_review_list.*
import org.koin.android.architecture.ext.viewModel

class ReviewListFragment : Fragment() {

    private lateinit var reviewsAdapter: ReviewListAdapter
    private val viewModel by viewModel<ReviewsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_review_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListView()
        setupSelectionOptions()
        setupResultSizeOptions()
        setupSearchButton()
        observeUiEvents()
    }

    private fun setupSearchButton() {
        searchButton.setOnClickListener { viewModel.onSearchClick() }
    }

    private fun observeUiEvents() {
        viewModel.subscribeToUiEvents().observe(this, Observer { uiModel ->
            uiModel?.let {
                with(it) {
                    when (state) {
                        is ModelState.Loading -> renderLoadingState()
                        is ModelState.Success -> renderSuccessState(state.data)
                        is ModelState.Empty -> renderFeedbackMessage(state.message)
                        is ModelState.Error -> renderFeedbackMessage(state.message)
                    }
                }
            }
        })
    }

    private fun renderFeedbackMessage(message: String) {
        progressBar.hide()
        Toast.makeText(context, message, LENGTH_LONG).show()
    }

    private fun renderSuccessState(data: ReviewPageDto) {
        reviewsAdapter.submitList(data.data)
        progressBar.hide()
        reviewsRecyclerView.show()
    }

    private fun renderLoadingState() {
        reviewsRecyclerView.hide()
        progressBar.show()
    }

    private fun setupListView() {
        reviewsAdapter = ReviewListAdapter()
        reviewsRecyclerView.apply {
            adapter = reviewsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupSelectionOptions() {
        val onSortOptionSelectedListener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedOption = resources.getStringArray(R.array.sort_options)[position]
                viewModel.onSortOptionSelected(selectedOption)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //No operation required here
            }
        }

        sortSpinner.onItemSelectedListener = onSortOptionSelectedListener
    }

    private fun setupResultSizeOptions() {
        val onResultSizeOptionSelected: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                resources.getStringArray(R.array.result_size_options)[position].apply {
                    viewModel.onResultSizeOptionSelected(this)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //No operation required here
            }
        }
        resultSizeSpinner.onItemSelectedListener = onResultSizeOptionSelected
    }

    companion object {
        @JvmStatic
        fun newInstance() = ReviewListFragment()
    }
}
