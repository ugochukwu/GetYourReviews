package com.onwordiesquire.android.getyourreviews.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun ViewGroup.inflate(layoutRes: Int): View =
        LayoutInflater.from(context).inflate(layoutRes, this, false)

fun TextView?.showContent(content: String?) {
    this?.let {
        if (content.isNullOrEmpty()) {
            hide()
        } else {
            text = content
            show()
        }
    }
}