package com.guo.lab.anim;

import android.animation.LayoutTransition;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.guo.lab.R;

/**
 *
 * 1.定义动画。 两种方式： xml文件res/anim/,根标签layoutanimation；代码
 * 2.设置动画到ViewGroup中，可以通过布局文件或者代码
 *
 */
public class LayoutAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_animation);

        LayoutTransition layoutTransition = new LayoutTransition();
    }
}
