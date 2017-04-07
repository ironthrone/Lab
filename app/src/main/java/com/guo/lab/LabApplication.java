package com.guo.lab;

import android.app.Activity;
import android.app.Application;

import com.blankj.utilcode.utils.Utils;

/**
 * Created by Administrator on 2017/3/22.
 */

public class LabApplication extends Application {
    public static Activity keepFroundActivity;
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
