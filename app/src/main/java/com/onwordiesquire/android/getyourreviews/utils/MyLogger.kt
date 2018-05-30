package com.onwordiesquire.android.getyourreviews.utils

import android.util.Log
import com.onwordiesquire.android.getyourreviews.BuildConfig


interface MyLogger {

    private val TAG: String
        get() = javaClass.simpleName

    fun logD(message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message)
        }
    }

    fun logE(message: String, throwable: Throwable? = null) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, message, throwable)
        }
    }
}