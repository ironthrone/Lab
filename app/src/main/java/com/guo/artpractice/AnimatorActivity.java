package com.guo.artpractice;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

public class AnimatorActivity extends AppCompatActivity {

    private ValueAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        final ImageView imageView = (ImageView) findViewById(R.id.image);
        animator = ValueAnimator.ofInt(100, 200)
                .setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                fraction = new AccelerateInterpolator().getInterpolation(fraction);

                imageView.setAlpha(fraction);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        animator.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        animator.end();
    }
}
