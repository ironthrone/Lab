package com.guo.lab.accessibility;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class WindowService extends Service {
    public WindowService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
//        View view = new LinearLayout(this);
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//        view.setBackgroundColor(0xbbff0000);
//        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
//        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
//        layoutParams.flags =
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
//                        WindowManager.LayoutParams.FLAG_FULLSCREEN
//                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
//        layoutParams.alpha = 0.5f;
//
//        layoutParams.format = PixelFormat.TRANSPARENT;
//
//        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        windowManager.addView(view, layoutParams);
        return START_STICKY;

    }
}
