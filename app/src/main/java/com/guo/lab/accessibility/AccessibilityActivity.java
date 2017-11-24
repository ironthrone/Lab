package com.guo.lab.accessibility;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;

import java.lang.reflect.Constructor;


public abstract class AccessibilityActivity<T extends OverlayWindowController> extends AppCompatActivity implements OverlayWindowController.OnCloseCallback {

    public T getOverlayWindowController() {
        return overlayWindowController;
    }

    private T overlayWindowController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OverlayWindowControllerClass overlayWindowControllerClass = getClass().getAnnotation(OverlayWindowControllerClass.class);
        if (overlayWindowControllerClass == null) {
            throw new IllegalArgumentException("You should provider a class");
        }
        Class clazz = overlayWindowControllerClass.value();
        try {
            Constructor constructor = clazz.getConstructor(Context.class);
            overlayWindowController = (T) constructor.newInstance(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (overlayWindowController == null) {
            LogUtils.d("OverlayWindowController instantiate fail");
            finish();
        }
        overlayWindowController.setOnCloseCallback(this);
    }



    @Override
    public void onBackPressed() {
        if (overlayWindowController.backButtonCanPass() && canFinish()) {
            finish();
        } else {
            super.onBackPressed();
        }
    }


    protected boolean canFinish() {
        return true;
    }

    @Override
    public void onClose() {

    }
}
