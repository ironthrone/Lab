package com.guo.lab.anim;

import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.utils.ToastUtils;
import com.guo.lab.R;

public class SpringAnimationActivity extends AppCompatActivity implements View.OnClickListener, DynamicAnimation.OnAnimationUpdateListener, DynamicAnimation.OnAnimationEndListener {


    private SpringAnimation xSpring;
    private SpringAnimation hookSpring;
    private View hook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spring_animation);
        View button = findViewById(R.id.spring);
        hook = findViewById(R.id.hook);
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        //自定义SpringForce
        SpringAnimation ySpring = new SpringAnimation(v, SpringAnimation.TRANSLATION_Y);
        SpringForce springForce = new SpringForce().setStiffness(SpringForce.STIFFNESS_VERY_LOW)
                .setDampingRatio(0.05f)
                .setFinalPosition(0);
        ySpring.setSpring(springForce);
        ySpring.setStartVelocity(1000);
        ySpring.addUpdateListener(this);
        ySpring.addEndListener(this);


        xSpring = new SpringAnimation(v, DynamicAnimation.TRANSLATION_X, 0);
        xSpring.getSpring().setDampingRatio(0.05f)
                .setStiffness(SpringForce.STIFFNESS_VERY_LOW);
        xSpring.setStartVelocity(2000);


        SpringAnimation rotateXSpring = new SpringAnimation(v, DynamicAnimation.ROTATION, 0);
        rotateXSpring.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW)
                .setDampingRatio(0.05f);
        rotateXSpring.setStartValue(200);

        //钩子动画，跟随view做上下动作
        hookSpring = new SpringAnimation(hook, DynamicAnimation.TRANSLATION_Y, 0);

        //开始x和y方向的动画
        xSpring.start();
        ySpring.start();
        rotateXSpring.start();


    }

    @Override
    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {


//        hookSpring.animateToFinalPosition(value);
        //也可以直接设置hook的属性
        hook.setTranslationY(value);
        hook.invalidate();
        ToastUtils.showShortToastSafe(String.format("value: %f,velocity: %f", value, velocity));
    }

    @Override
    public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
        ToastUtils.showShortToastSafe(String.format("value: %f,velocity: %f", value, velocity));
    }
}
