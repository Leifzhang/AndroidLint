package com.kronos.sample

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView

/**
 * @Author LiABao
 * @Since 2020/10/15
 */
class CustomView(context: Context) : FrameLayout(context) {
    init {
        LayoutInflater.from(context).inflate(R.layout.activity_main, this)
        val view = findViewById<ImageView>(R.id.tv1)
    }
}