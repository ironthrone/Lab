package com.guo.lab.progress;

import android.app.Activity;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/9.
 */

public class ProgressDialogSubscriber extends Subscriber {

    private final ProgressDialogHandler handler;

    public ProgressDialogSubscriber(Activity activity) {
        handler = new ProgressDialogHandler(activity);
    }

    @Override
    public void onStart() {
        super.onStart();
        handler.show();
    }

    @Override
    public void onCompleted() {
        handler.hide();
    }

    @Override
    public void onError(Throwable e) {
        handler.hide();
    }

    @Override
    public void onNext(Object o) {
        handler.hide();
    }
}
