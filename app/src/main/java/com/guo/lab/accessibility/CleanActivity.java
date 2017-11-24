package com.guo.lab.accessibility;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.guo.lab.R;

import java.util.concurrent.TimeUnit;

import rx.functions.Action0;
import rx.schedulers.Schedulers;


@OverlayWindowControllerClass(CleanCacheOverlayWindowController.class)
public class CleanActivity extends AccessibilityActivity<CleanCacheOverlayWindowController> {

    private FrameLayout container2;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean);
        findViewById(R.id.show)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View content = findViewById(R.id.content);
                        ((ViewGroup) content.getParent()).removeView(content);
//                        container2.addView(image);
                        getOverlayWindowController().setView(content);
                        getOverlayWindowController().show(null);
                    }
                });
        Schedulers.io().createWorker().schedulePeriodically(new Action0() {
            @Override
            public void call() {
                Activity top = ActivityUtils.getTopActivity();
                LogUtils.d(top == null ? "null" : top.toString());
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);

        image = (ImageView) findViewById(R.id.image);
        FrameLayout container1 = (FrameLayout) findViewById(R.id.container_1);
        container2 = (FrameLayout) findViewById(R.id.container_2);

        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(!checkBox.isChecked());
            }
        });

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }
}
