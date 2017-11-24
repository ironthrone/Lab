package com.guo.lab.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.blankj.utilcode.util.ScreenUtils;
import com.guo.lab.R;

public class TransitionFromActivity extends AppCompatActivity implements View.OnClickListener {

    private View image;
    private ImageView share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_from);
        image = findViewById(R.id.image);
//        ObjectAnimator animator = ObjectAnimator.ofFloat(image, View.ROTATION, 0, 360)
//                .setDuration(1500);
//        animator.setRepeatCount(ValueAnimator.INFINITE);
//        animator.start();
        findViewById(R.id.switch_to)
                .setOnClickListener(this);
        findViewById(R.id.clear)
                .setOnClickListener(this);

        share = (ImageView) findViewById(R.id.share);

    }

    String transitionName = "wusong";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_to:
                int[] location = new int[2];
                image.getLocationOnScreen(location);
                float translationY = ScreenUtils.getScreenHeight() / 2f - location[1];
                TranslateAnimation translate = new TranslateAnimation(0, 0, 0, translationY);
                translate.setDuration(500);
                translate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
//                        binding.phoneBoostBottom.
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                translate.setFillAfter(true);
                int screenWidth = ScreenUtils.getScreenWidth();
                int screenHeight = ScreenUtils.getScreenHeight();
                int width = image.getWidth();
                float scaleX = screenWidth * 1.0f / width;
                float scaleY = (screenHeight + image.getHeight()) / (image.getHeight());
                ScaleAnimation scale = new ScaleAnimation(1, scaleX, 1, scaleY,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.ABSOLUTE, translationY + image.getHeight() / 2);
                scale.setDuration(3000);
                scale.setStartOffset(500);

                AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(translate);
                animationSet.addAnimation(scale);
                animationSet.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(TransitionFromActivity.this, share, transitionName);
                        startActivity(new Intent(TransitionFromActivity.this, TransitionToActivity.class), optionsCompat.toBundle());
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                animationSet.setFillAfter(true);
                image.startAnimation(animationSet);
                break;
            case R.id.clear:
                image.clearAnimation();
                break;
        }
    }
}
