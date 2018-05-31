package com.onwordiesquire.android.getyourreviews

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.onwordiesquire.android.getyourreviews.data.DataRepository
import com.onwordiesquire.android.getyourreviews.data.DataSourceResponse
import com.onwordiesquire.android.getyourreviews.ui.reviewList.ReviewListFragment
import com.onwordiesquire.android.getyourreviews.utils.MyLogger
import com.onwordiesquire.android.getyourreviews.utils.replaceFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), MyLogger {
    private val dataRepository: DataRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(ReviewListFragment.newInstance(), R.id.fragmentContainer)
    }

}
