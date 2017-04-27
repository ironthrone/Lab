package com.guo.lab.anim;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.guo.lab.R;

/**
 * 步骤：
 * 1.在XML中为ViewGroup设置属性 "animateLayoutChanges=true"
 * 2.optional,设置自定义的LayoutTransition
 */
public class LayoutTransitionActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewGroup linearContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_transition);

        findViewById(R.id.add)
                .setOnClickListener(this);
        linearContainer = (ViewGroup) findViewById(R.id.linear_container);

        //自定义布局过渡
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.setAnimator(LayoutTransition.APPEARING,ObjectAnimator.ofFloat(null, "scaleX", 0, 1).setDuration(100));
        layoutTransition.setAnimator(LayoutTransition.DISAPPEARING,ObjectAnimator.ofFloat(null, "scaleX", 1, 0).setDuration(100));
        linearContainer.setLayoutTransition(layoutTransition);
    }

    @Override
    public void onClick(View v) {
        Button button = new Button(this);
        int childCount = linearContainer.getChildCount();
        button.setText("" + (childCount+1));
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(layoutParams);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearContainer.removeView(v);
            }
        });
        linearContainer.addView(button);
    }
}
