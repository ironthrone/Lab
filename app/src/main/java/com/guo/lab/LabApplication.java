package com.guo.lab;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.facebook.stetho.Stetho;

/**
 * Created by Administrator on 2017/3/22.
 */

public class LabApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        Stetho.initializeWithDefaults(this);
        //default is a static member, so all thread which started by this vm can use this handler
        //non-default handler will only be used by target thread
//        Thread.currentThread().setUncaughtExceptionHandler(null);
//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread t, Throwable e) {
//            }
//        });
    }
}
