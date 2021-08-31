package com.kronos.sample

import android.app.Application
import com.kronos.sample.uitls.DynamicEntity
import com.kronos.sample.uitls.EpicHook

/**
 *
 *  @Author LiABao
 *  @Since 2021/8/2
 *
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        EpicHook.start(DynamicEntity("java.io.File.delete", "别删除啊", null))
    }
}