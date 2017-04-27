package com.guo.lab.anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.blankj.utilcode.utils.ToastUtils;
import com.guo.lab.R;

public class AnimatorActivity extends AppCompatActivity implements View.OnClickListener {

    private ValueAnimator alpha;
    private ImageView imageView;
    private AnimatorSet animatorSet;
    private ValueAnimator translate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        imageView = (ImageView) findViewById(R.id.image);
        findViewById(R.id.play)
                .setOnClickListener(this);
        findViewById(R.id.post_a_animation)
                .setOnClickListener(this);

        constructAnimator();
    }

    private void constructAnimator() {
        alpha = ValueAnimator.ofInt(100, 200)
                .setDuration(1000);
        alpha.setRepeatCount(2);
        alpha.setRepeatMode(ValueAnimator.REVERSE);
        alpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                fraction = new AccelerateInterpolator().getInterpolation(fraction);

                imageView.setAlpha(fraction);

            }
        });


        //ValueAnimator
        translate = ValueAnimator.ofFloat(0, 100)
                .setDuration(2000);
        translate.setTarget(imageView);
        translate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                imageView.setX(animatedValue);
            }
        });

        //ObjectAnimator比起ValueAnimator多了Target和属性的设定
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 0, 1)
                .setDuration(2000);


        //需要多个属性一起动画的时候可以采用PropertyValuesHolder，这样创建出一个包含多个属性的动画
        // 当然也可以为每个属性创建一个Animator，然后一起播放
        PropertyValuesHolder scaleXHolder = PropertyValuesHolder.ofFloat("scaleX", 0, 1, 0, 1);
        PropertyValuesHolder scaleYHolder = PropertyValuesHolder.ofFloat("scaleY", 1, 0, 1);
        ObjectAnimator scale = ObjectAnimator.ofPropertyValuesHolder(imageView, scaleXHolder, scaleYHolder)
                .setDuration(2000);
        //动画组
        animatorSet = new AnimatorSet();
        //play返回一个AnimatorSet.Builder对象，它是一个工具类，它的before,with,after方法用来安排各个Animator的执行顺序
        animatorSet.play(translate)
                .before(scaleX)
                .with(scale)
                .after(alpha);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_a_animation:
                post();
                break;
            case R.id.play:
                animatorSet.start();
                break;
        }
    }

    @TargetApi(16)
    private void post() {
        imageView.postOnAnimation(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showShortToastSafe("post");
            }
        });

    }
}
