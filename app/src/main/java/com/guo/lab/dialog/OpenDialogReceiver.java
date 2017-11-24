package com.guo.lab.dialog;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.WindowManager;

import com.blankj.utilcode.util.ToastUtils;

/**
 * Created by ironthrone on 2017/10/19 0019.
 */

public class OpenDialogReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Dialog dialog = new Dialog(context.getApplicationContext());
        dialog.setTitle("Hello");
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        //on Android 7.1,if a toast is shown ,we show a toast type window, app crash
        try {
        ToastUtils.showShortSafe("Block you , dialog which comes from receiver");
            dialog.show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }
}
