package com.onwordiesquire.android.getyourreviews.ui.reviewList


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onwordiesquire.android.getyourreviews.R
import kotlinx.android.synthetic.main.fragment_review_list.*
import org.koin.android.architecture.ext.getViewModel

class ReviewListFragment : Fragment() {

    private lateinit var reviewsAdapter: ReviewListAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_review_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListView()
        val model = getViewModel<ReviewsViewModel>()
    }

    private fun setupListView() {
        reviewsAdapter = ReviewListAdapter()
        reviewsRecyclerView.apply {
            adapter = reviewsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ReviewListFragment()
    }
}
