package com.guo.lab.widget;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.guo.lab.R;

public class DragScrollActivity extends AppCompatActivity {

    private View scroll;
    private String[] strs = new String[]{"我最怜君中宵舞",
            "道 男儿到死心如铁",
            "看试手",
            "补天裂"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_scroll);
        LinearLayout linear = (LinearLayout) findViewById(R.id.linear);
        for (int i = 0; i < 20; i++) {
            TextView textView = new TextView(this);
            textView.setText(i + "  男儿到死心如铁");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.topMargin = 20;
            params.bottomMargin = 20;
            textView.setLayoutParams(params);

            linear.addView(textView);
        }
        scroll = findViewById(R.id.scroll);
        scroll.setOnTouchListener(new View.OnTouchListener() {
            private int startScrollY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        startScrollY = v.getScrollY();
                        break;
                    case MotionEvent.ACTION_UP:
                        int scrollY = v.getScrollY();
                        int screenHeight = ScreenUtils.getScreenHeight();
                        int scrollDirection = scrollY - startScrollY;
                        int scrollAmount = Math.abs(scrollY - startScrollY);
                        LogUtils.d("scrollAmount: " + scrollAmount);
                        int ss = SizeUtils.dp2px(100);
                        if (scrollDirection > 0) {
                            if (scrollAmount > ss) {
                                v.scrollTo(0,screenHeight);
//                                ObjectAnimator.ofInt(v, "scrollY", screenHeight)
//                                        .setDuration(300)
//                                        .start();

                            } else {
                                v.scrollTo(0,0);
//                                ObjectAnimator.ofInt(v, "scrollY", 0)
//                                        .setDuration(300)
//                                        .start();
                            }
                        } else {
                            if (scrollAmount > ss) {

                                v.scrollTo(0,0);
//                                ObjectAnimator.ofInt(v, "scrollY", 0)
//                                        .setDuration(300)
//                                        .start();
                            } else {
                                v.scrollTo(0,screenHeight);
//                                ObjectAnimator.ofInt(v, "scrollY", screenHeight)
//                                        .setDuration(300)
//                                        .start();
                            }
                        }
                        break;
                }

                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.click:

//                linear.scrollTo();
                break;
        }
    }
}
