package com.guo.lab.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Handler;
import android.os.Message;

import com.blankj.utilcode.util.LogUtils;

import java.util.List;

public class RepeatListener extends AnimatorListenerAdapter {
    private int repeatCount;
    private int currentCount;
    private Animator animator;
    private Handler handler;
    private static final int REPEAT_ANIMATOR = 100;

    public RepeatListener(Animator animator, int repeatCount) {
        this.repeatCount = repeatCount;
        this.animator = animator;
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case REPEAT_ANIMATOR:
                        RepeatListener.this.animator.start();
                        break;
                }
                return false;
            }
        });


        List<Animator.AnimatorListener> listeners = animator.getListeners();
        if (listeners == null) return;
        for (Animator.AnimatorListener listener :
                listeners) {
            if (listener instanceof RepeatListener && listener != this) {
                animator.removeListener(listener);
            }
        }
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        currentCount++;
        if (currentCount <= repeatCount) {
            handler.sendEmptyMessage(REPEAT_ANIMATOR);
            LogUtils.d(animation + "currentCount: " + currentCount);
        } else {
            currentCount = 0;
            animation.removeListener(this);
        }
    }
}