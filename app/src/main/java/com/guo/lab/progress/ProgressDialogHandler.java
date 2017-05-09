package com.guo.lab.progress;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by Administrator on 2017/5/9.
 */

public class ProgressDialogHandler {
    private ProgressDialog dialog;
    private  DialogHandler handler;

    public ProgressDialogHandler(Context context) {
        handler = new DialogHandler();
        dialog = new ProgressDialog(context);
    }

    private static final int FLAG_SHOW = 100;
    private static final int FLAG_HIDE = 200;

    private class DialogHandler extends Handler{
        public DialogHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case FLAG_SHOW:
                    if (!dialog.isShowing()) {
                        dialog.show();
                    }
                    break;
                case FLAG_HIDE:
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    break;
            }
        }
    }

    public void show() {
        handler.obtainMessage(FLAG_SHOW).sendToTarget();

    }

    public void hide() {
        handler.obtainMessage(FLAG_HIDE).sendToTarget();

    }


}
