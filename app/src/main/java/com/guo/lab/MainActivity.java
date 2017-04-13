package com.guo.lab;

import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(MainActivity.this, ForegroundService.class));
        startService(new Intent(MainActivity.this, HandlerService.class));
        startService(new Intent(MainActivity.this, KeepLivingRemoteService.class));
        startService(new Intent(MainActivity.this, KeepLivingLocalService.class));

        startService(new Intent(MainActivity.this, AlarmService.class));
//        startService(new Intent(MainActivity.this, LockService.class));
        sendBroadcast(new Intent(this,WakefulReceiver.class));
        findViewById(R.id.start_service)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        startService(new Intent(MainActivity.this,ForegroundService.class));
                    }
                });

    }
}
