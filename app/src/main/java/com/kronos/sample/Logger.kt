package com.kronos.sample

import android.annotation.SuppressLint
import android.util.Log

object Logger {
    fun i(logInfo: String) {
        Log.i("Logger", logInfo)
    }
}