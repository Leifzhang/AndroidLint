package com.kronos.sample;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.github.moduth.blockcanary.BlockCanary;

public class TestApplication extends MyApp {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        BlockCanary.install(this, new AppContext()).start();
        Log.i("BlockCanary", "BlockCanary.install");
    }

    public static Context getAppContext() {
        return sContext;
    }
}
