package com.guo.lab.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

/**
 * Created by ironthrone on 2017/8/24 0024.
 */

public class TheAccessibilityService extends AccessibilityService {
    private static final String TAG = TheAccessibilityService.class.getSimpleName();

    public static TheAccessibilityService instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
//        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
//        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED | AccessibilityEvent.TYPE_VIEW_FOCUSED | AccessibilityEvent.TYPES_ALL_MASK;
//        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
//        setServiceInfo(info);
        LogUtils.d(TAG, "accessibility connected");
    }

    public void startA() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        ToastUtils.showLongSafe(event.toString());
//        LogUtils.d(TAG, event.toString());
//        performGlobalAction(GLOBAL_ACTION_BACK);
        event.getPackageName();

    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        LogUtils.d(event.toString());
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            return super.onKeyEvent(event);
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyEvent(event);
    }
}
