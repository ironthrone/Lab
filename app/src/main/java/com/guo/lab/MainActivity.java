package com.guo.lab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.guo.lab.binderpool.BinderPoolClientActivity;
import com.guo.lab.library.ReaderActivity;
import com.guo.lab.socket.ConversationActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.read)
                .setOnClickListener(this);
        findViewById(R.id.binder_pool)
                .setOnClickListener(this);
        findViewById(R.id.socket)
                .setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.read:
                        startActivity(new Intent(MainActivity.this,ReaderActivity.class));
                break;
            case R.id.binder_pool:
                        startActivity(new Intent(MainActivity.this,BinderPoolClientActivity.class));
                break;
            case R.id.socket:

                        startActivity(new Intent(MainActivity.this,ConversationActivity.class));
                break;

        }
    }
}
