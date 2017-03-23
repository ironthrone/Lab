package com.guo.lab;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */

public class TheApplication extends Application {
    private static final String TAG = TheApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        PackageManager packageManager = getPackageManager();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList = activityManager.getRunningAppProcesses();
//        activityManager.proce
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcessInfoList) {

            Log.d(TAG, runningAppProcessInfo.processName);
        }
    }
}
