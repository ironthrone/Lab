package com.guo.lab.accessibility;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.guo.lab.R;
import com.guo.lab.view.SomeViewAttrsActivity;

public class WindowActivity extends AppCompatActivity {

    private HomeWatcher homeWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            if (!Settings.canDrawOverlays(getApplicationContext())) {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//                startActivity(intent);
//            } else {
//
//                startService(new Intent(this, WindowService.class));
//            }
//        } else {
//
//            startService(new Intent(this, WindowService.class));
//        }

//        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_FULLSCREEN;
//        } else {
//            params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//        }
//        params.format = PixelFormat.TRANSLUCENT;
//        params.type = WindowManager.LayoutParams.TYPE_TOAST;


        View view = new LinearLayout(this);
//        view = LayoutInflater.from(this)
//                .inflate(R.layout.level_0, null);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        view.setBackgroundColor(0xbbff0000);
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_FULLSCREEN;
        } else {
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        }

        layoutParams.gravity = Gravity.TOP;
        layoutParams.alpha = 0.8f;
        layoutParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        layoutParams.format = PixelFormat.TRANSPARENT;

        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        Toast.makeText(this, "afasf", Toast.LENGTH_SHORT).show();
        windowManager.addView(view, layoutParams);
//            startService(new Intent(this, WindowService.class));

//        homeWatcher = new HomeWatcher(getApplicationContext());
//        homeWatcher.setOnHomePressedListener(new HomeWatcher.OnHomePressedListener() {
//            @Override
//            public void onHomeLongPressed() {
//
//            }
//
//            @Override
//            public void onHomePressed() {
//
//            }
//        });
//        homeWatcher.startWatch();

//        startActivity(new Intent(this, SomeViewAttrsActivity.class));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homeWatcher.stopWatch();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

//    @Override
//    public void onBackPressed() {
//
//    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        LogUtils.d("onKeyUp");
//        return true;
//
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        LogUtils.d("onKeyDown");
//        if (keyCode == KeyEvent.KEYCODE_HOME) {
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
