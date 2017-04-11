package com.guo.lab.remoteview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.utils.ToastUtils;

public class ToastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ToastUtils.showShortToastSafe(intent.getStringExtra("extra"));
    }
}
