package com.guo.lab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class KeepLiveReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_SCREEN_OFF)) {

            context.startActivity(new Intent(context,KeepForegroundActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (action.equals(Intent.ACTION_SCREEN_ON)){
            if (LabApplication.keepFroundActivity != null) {
                LabApplication.keepFroundActivity.finish();
            }
        }
    }
}
