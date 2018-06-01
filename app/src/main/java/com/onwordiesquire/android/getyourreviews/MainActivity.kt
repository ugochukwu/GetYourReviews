package com.onwordiesquire.android.getyourreviews

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.onwordiesquire.android.getyourreviews.ui.inputReview.InputFragment
import com.onwordiesquire.android.getyourreviews.ui.reviewList.ReviewListFragment
import com.onwordiesquire.android.getyourreviews.utils.replaceFragment

class MainActivity : AppCompatActivity(), OnNavigationEventListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(ReviewListFragment.newInstance(), R.id.fragmentContainer)
    }

    override fun navigateToEntryForm() {
        replaceFragment(
                fragment = InputFragment.newInstance(),
                containerId = R.id.fragmentContainer,
                addToBackStack = true)
    }

    override fun pop() {
        supportFragmentManager.popBackStack()
    }
}

interface OnNavigationEventListener {
    fun navigateToEntryForm()
    fun pop()
}
