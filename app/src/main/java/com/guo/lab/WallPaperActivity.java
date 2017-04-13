package com.guo.lab;

import android.content.Context;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class WallPaperActivity extends AppCompatActivity {

    public PowerManager.WakeLock wakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FLAG_DISMISS_KEYGUARD使键盘保活被清除，点击电源键直接进入系统
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        setContentView(R.layout.activity_foreground);
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "foreground");
        wakeLock.acquire();

        CirculationLogger logger = new CirculationLogger("foreground.txt");
        logger.circulationLog("foreground");
        LabApplication.keepFroundActivity = this;
    }

    @Override
    public void onBackPressed() {
    }
}
