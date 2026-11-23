package com.example.core.utils

import android.os.SystemClock
import android.view.View

fun Int?.orZero() = this ?: 0
fun Long?.orZero() = this ?: 0L
fun Double?.orZero() = this ?: 0.0
fun Boolean?.orFalse() = this ?: false

fun String?.toShortMonth(): String = DateHelper.getMonth(this.orEmpty())


fun View.clickWithDebounce(debounceTime: Long = 500L, action: (View) -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action(v)
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}