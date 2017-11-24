package com.guo.lab.accessibility;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.blankj.utilcode.util.LogUtils;


/**
 * Created by ironthrone on 2017/10/10 0010.
 */

public abstract class OverlayWindowController implements HomeWatcher.OnHomePressedListener {
    public static final String ACTION_HOME_CLICK = "home_click";
    public static final String ACTION_INTERRUPT = "interrupt";


    protected Context context;
    private boolean backButtonCanPass;
    private HomeWatcher homeWatcher;
    protected   View rootView;
    private final WindowManager windowManager;


    public OverlayWindowController(final Context context) {

        this.context = context;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        homeWatcher = new HomeWatcher(context);
        homeWatcher.setOnHomePressedListener(this);

    }


    public void show(Bundle bundle) {
        rootView = onCreateView();
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_FULLSCREEN;
        } else {
            params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        }
        params.format = PixelFormat.TRANSLUCENT;
        // not overlay , it will make event action_outside
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_TOAST;
        }

        params.gravity = Gravity.TOP;

        params.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        if (rootView != null) {
//            rootView.setLayoutParams(null);
//            ((ViewGroup) rootView).setLayoutParams(null);
            try {
                windowManager.addView(rootView, params);
            } catch (Exception e) {
                return;
            }
        } else {
            LogUtils.d("view is null");
        }

        homeWatcher.startWatch();
        backButtonCanPass = false;
        onShow(bundle);
    }


    public abstract void onShow(Bundle bundle);

    protected void onClose() {

    }

    public abstract View onCreateView();

    @Override
    public void onHomeLongPressed() {
//        close();
    }

    @Override
    public void onHomePressed() {
        close();
    }

    public boolean backButtonCanPass() {
        return backButtonCanPass;
    }

    public void close() {
        try {
            if (rootView != null) {
                windowManager.removeView(rootView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        backButtonCanPass = true;
        homeWatcher.stopWatch();
        onClose();
        if (onCloseCallback != null) {
            onCloseCallback.onClose();
        }
    }

    private OnCloseCallback onCloseCallback;

    public void setOnCloseCallback(OnCloseCallback callback) {
        this.onCloseCallback = callback;
    }

    public interface OnCloseCallback {
        void onClose();
    }
}
