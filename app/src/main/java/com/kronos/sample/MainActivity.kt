package com.kronos.sample

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import io.reactivex.Observable
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.util.zip.ZipEntry


class MainActivity : AppCompatActivity() {
    @NonNull
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //     Router.startPageUri(this, "")
        //   Router.getService(A::class.java, "")
        Thread {

        }
        AlertDialog.Builder(this).show()
//        DefaultUriRequest(this, "").start()
        Log.i("", "")
        Glide.with(this)
        val image = BitmapFactory.decodeFile("")
        Observable.just("").subscribe()
        val text = "我想说"
        findViewById<TextView>(R.id.tv1).text = String.format("%s", text)
        Event("  123")
        Event("111")
        val path = Environment.getExternalStorageDirectory()
        val otherPath = Environment.getDownloadCacheDirectory()
        val test = Environment.DIRECTORY_PICTURES
        //File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_ALARMS)
        val value = "Pictures"
        File(Environment.DIRECTORY_PICTURES, "").delete()
        if (this is AppCompatActivity) {

        }
        findViewById<TextView>(R.id.tv1).viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                return true
            }
        })
        val entry = ZipEntry("zip")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val manager = getSystemService(TelephonyManager::class.java)
            val did = manager.deviceId
            val androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
            //     String newDid =PrivacyUtils.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        }
    }

}
