package com.guo.lab;

import android.app.Application;

import com.blankj.utilcode.utils.Utils;

/**
 * Created by Administrator on 2017/3/22.
 */

public class LabApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
