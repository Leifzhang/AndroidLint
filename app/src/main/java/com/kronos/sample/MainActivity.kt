package com.kronos.sample

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.sankuai.waimai.router.Router
import com.sankuai.waimai.router.annotation.RouterPage
import com.sankuai.waimai.router.common.DefaultUriRequest
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.InputStream
import java.io.OutputStream


@RouterPage(path = ["/test/a"])
class MainActivity : AppCompatActivity() {
    @NonNull
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Router.startPageUri(this, "")
        Router.getService(A::class.java, "")
        Thread {

        }
        AlertDialog.Builder(this).show()
        DefaultUriRequest(this, "").start()
        Log.i("", "")
        Glide.with(this)
        val image = BitmapFactory.decodeFile("")
        Observable.just("").subscribe()
        val text = "我想说"
        tv1.text = String.format("%s", text)
        Event("  123")
        Event("111")
        val path = Environment.getExternalStorageDirectory()
        val otherPath = Environment.getDownloadCacheDirectory()
        val test = Environment.DIRECTORY_PICTURES
        //File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_ALARMS)
        val value = "Pictures"
        File(Environment.DIRECTORY_PICTURES, "").delete()
    }

}
