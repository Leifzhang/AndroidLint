package com.kronos.sample

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.sankuai.waimai.router.Router
import com.sankuai.waimai.router.annotation.RouterPage
import com.sankuai.waimai.router.common.DefaultUriRequest
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*


@RouterPage(path = ["/test/a"])
class MainActivity : AppCompatActivity() {
    @NonNull
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("", "")
        Router.startPageUri(this, "")
        Router.getService(A::class.java, "")
        DefaultUriRequest(this, "").start()
        Glide.with(this)
        val image = BitmapFactory.decodeFile("")
        Observable.just("").subscribe()
        val text = "我想说"
        tv1.text = String.format("%s", text)
        Event("  123")
        Event("111")
        Thread({

        })
    }
}
