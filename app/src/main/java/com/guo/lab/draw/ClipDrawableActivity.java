package com.guo.lab.draw;

import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ImageView;

import com.guo.lab.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ironthrone on 2017/7/10 0010.
 */

public class ClipDrawableActivity extends AppCompatActivity {

    private ClipDrawable clipDrawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_drawable);


        //TODO clipDrawable
        ImageView imageView = (ImageView) findViewById(R.id.clip);

//        clipDrawable = new ClipDrawable(imageView.getDrawable(), Gravity.LEFT, ClipDrawable.HORIZONTAL);
        clipDrawable = ((ClipDrawable) imageView.getDrawable());
        clipDrawable.setLevel(22);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Observable
                .interval(0, 100, TimeUnit.MILLISECONDS)
                .onBackpressureDrop()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        clipDrawable.setLevel(aLong.intValue() * 100 %10000);
                    }
                })

                .subscribe();

    }
}
