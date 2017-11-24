package com.guo.lab.anim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.guo.lab.R;

public class InvalidateTestActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewGroup viewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invalidate_test);
        viewGroup = (ViewGroup) findViewById(R.id.container);
        findViewById(R.id.start)
                .setOnClickListener(this);
        findViewById(R.id.end)
                .setOnClickListener(this);
        findViewById(R.id.init)
                .setOnClickListener(this);
    }

    private void initViewGroup(int count) {
        viewGroup.removeAllViews();
        for (int i = 0; i < count; i++) {
            BroadcastView2 homeRingView = new BroadcastView2(this);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(400, 400);
            homeRingView.setLayoutParams(layoutParams);
            viewGroup.addView(homeRingView);
        }
    }

    @Override
    public void onClick(View v) {
                int childCount = viewGroup.getChildCount();
        switch (v.getId()) {
            case R.id.init:
                int count = Integer.parseInt(((EditText) findViewById(R.id.input_count)).getText().toString().trim());
                initViewGroup(count);
                break;
            case R.id.start:
                for (int i = 0; i < childCount; i++) {
                    ((BroadcastView2) viewGroup.getChildAt(i)).start();
                }
                break;
            case R.id.end:
                for (int i = 0; i < childCount; i++) {
                    ((BroadcastView2) viewGroup.getChildAt(i)).end();
                }
                break;
        }
    }
}
