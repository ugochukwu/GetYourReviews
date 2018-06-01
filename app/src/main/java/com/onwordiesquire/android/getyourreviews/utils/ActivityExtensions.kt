package com.onwordiesquire.android.getyourreviews.utils

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.replaceFragment(fragment: Fragment,
                                      @IdRes containerId: Int,
                                      tag: String = fragment.javaClass.simpleName,
                                      addToBackStack: Boolean = false) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.apply {
        replace(containerId, fragment, tag)
        if (addToBackStack) {
            addToBackStack(tag)
        }
        commit()
    }
}