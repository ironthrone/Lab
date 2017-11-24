package com.guo.lab.widget;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.guo.lab.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.ViewDragHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SlidingPanelActivity extends AppCompatActivity implements View.OnClickListener {

    private View contentContainer;
    private View text;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private View panel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_panel);
        contentContainer = findViewById(R.id.content_container);
        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        panel = findViewById(R.id.panel);
        panel.setOnClickListener(this);

        text = findViewById(R.id.text);
        text.setOnClickListener(this);
        contentContainer.post(new Runnable() {
            @Override
            public void run() {
                Rect textRect = new Rect();
                text.getDrawingRect(textRect);
                textRect.bottom += 200;
                Rect bound = new Rect(0, 0, contentContainer.getWidth(), contentContainer.getHeight());
                contentContainer.setTouchDelegate(new TouchDelegate(textRect, text));


            }
        });


        View fullScreenDrag = findViewById(R.id.full_screen_drag);
        fullScreenDrag.setOnTouchListener(new View.OnTouchListener() {

            private float deltaY;
            private float startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        startY = event.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        deltaY = event.getY() - startY;
                        startY = event.getY();

                        slidePanelTo(-deltaY);

                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });
//        slidingUpPanelLayout.setDragView(fullScreenDrag);
//        fullScreenDrag.setOnClickListener(null);

    }


    private void slidePanelTo(float distance) {

        try {
            Field slideRangeField = SlidingUpPanelLayout.class.getDeclaredField("mSlideRange");
            slideRangeField.setAccessible(true);
            float slideRange = slideRangeField.getFloat(slidingUpPanelLayout);
            float slideRatio = distance / slideRange;
            Method smoothSlideTo = SlidingUpPanelLayout.class.getDeclaredMethod("smoothSlideTo", float.class, int.class);
            smoothSlideTo.setAccessible(true);
            smoothSlideTo.invoke(slidingUpPanelLayout, slideRatio, 0);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private int a = 100;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text:
                ToastUtils.showShortSafe("text is clicked");

//                slidePanelTo(a);
//                a += 100;
                break;
            case R.id.panel:
                ToastUtils.showShortSafe("sliding panel is clicked");
                break;
        }
    }
}
