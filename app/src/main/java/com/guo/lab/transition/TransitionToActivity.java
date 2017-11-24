package com.guo.lab.transition;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.guo.lab.R;

public class TransitionToActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_to);

        ImageView share = (ImageView) findViewById(R.id.share);
        ObjectAnimator animator = ObjectAnimator.ofFloat(share, View.ROTATION_X, 0, 360)
                .setDuration(1500);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
        setEnterSharedElementCallback(new SharedElementCallback() {
        });
    }

}
