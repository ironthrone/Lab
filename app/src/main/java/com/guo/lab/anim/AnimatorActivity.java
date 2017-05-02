package com.guo.lab.anim;

import android.animation.Animator;
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
    private View square_1;
    private View square_2;
    private View square_3;
    private View square_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        imageView = (ImageView) findViewById(R.id.image);
        findViewById(R.id.play)
                .setOnClickListener(this);
        findViewById(R.id.animator_set)
                .setOnClickListener(this);
        findViewById(R.id.delay_sequence)
                .setOnClickListener(this);

        square_1 = findViewById(R.id.square_1);
        square_2 = findViewById(R.id.square_2);
        square_3 = findViewById(R.id.square_3);
        square_4 = findViewById(R.id.square_4);

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
            case R.id.animator_set:
                ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 1, 1.4f, 0.8f, 1.4f)
                        .setDuration(500);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 1, 1.4f, 0.8f, 1.4f)
                        .setDuration(500);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 1, 0)
                        .setDuration(300);
                ObjectAnimator rotate = ObjectAnimator.ofFloat(imageView, "rotation", 0, 180)
                        .setDuration(300);
                AnimatorSet animatorSet2 = new AnimatorSet();
                //搭配动画的次序
                animatorSet2.play(scaleX)
                        .with(scaleY)
                        .before(rotate);

                animatorSet2.play(rotate)
                        .before(alpha);
                animatorSet2.start();
                break;
            case R.id.play:
                animatorSet.start();
                break;
            case R.id.delay_sequence:
                //每隔800毫秒播放一次动画
                ObjectAnimator scale1 = ObjectAnimator.ofFloat(square_1, "scaleX", 1, 1.4f, 0.8f)
                        .setDuration(500);
                scale1.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        ToastUtils.showShortToastSafe(System.currentTimeMillis() + "");

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

                ObjectAnimator scale2 = scale1.clone();
                scale2.setTarget(square_2);
                AnimatorSet scale2Delay = new AnimatorSet();
                scale2Delay.play(scale2)
                        .after(800);

                ObjectAnimator scale3 = scale1.clone();
                scale3.setTarget(square_3);
                AnimatorSet scale3Delay = new AnimatorSet();
                scale3Delay.play(scale3)
                        .after(1600);
                ObjectAnimator scale4 = scale1.clone();
                scale4.setTarget(square_4);
                AnimatorSet scale4Delay = new AnimatorSet();
                scale4Delay.play(scale4)
                        .after(2400);

                AnimatorSet animatorSet = new AnimatorSet();
                //一起播放Animator，AnimatorSet也可以
                animatorSet.playTogether(scale1,scale2Delay,scale3Delay,scale4Delay);
                animatorSet.start();
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
