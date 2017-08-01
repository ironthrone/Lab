package com.guo.lab.anim;

import android.animation.LayoutTransition;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.guo.lab.R;

/**
 * 1.定义动画。 两种方式： xml文件res/anim/,根标签layoutanimation；代码
 * 2.设置动画到ViewGroup中，可以通过布局文件或者代码
 */
public class LayoutAnimationActivity extends AppCompatActivity {

    private LinearLayout animated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_animation);
        animated = ((LinearLayout) findViewById(R.id.animated_linear));
        View childOne = animated.getChildAt(1);
        childOne.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                v.animate()
                        .translationY(-200)
                        .setDuration(1000)
                        .start();
            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });

        LayoutTransition layoutTransition = new LayoutTransition();
    }

    public void click(View view) {
        animated.scheduleLayoutAnimation();
    }
}
