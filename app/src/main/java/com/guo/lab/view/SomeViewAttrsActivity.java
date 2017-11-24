package com.guo.lab.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.guo.lab.R;

/**
 * illustrate effect of clipToPadding
 */
public class SomeViewAttrsActivity extends AppCompatActivity {

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_some_view_attrs);
        view = findViewById(R.id.view);
        findViewById(R.id.button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.animate()
                                .scaleX(3)
                                .scaleY(3)
                                .setDuration(2000)
                                .start();
                    }
                });
        findViewById(R.id.content)
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LOW_PROFILE);
    }
}
