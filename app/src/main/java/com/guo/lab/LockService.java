package com.guo.lab;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import java.util.Date;

/**
 * Created by Administrator on 2017/4/5.
 */

public class LockService extends Service {

    private PowerManager.WakeLock lock;
    private CirculationLogger logger;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        lock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "lock");
        lock.acquire();
        logger = new CirculationLogger("lockservice.txt");
        logger.circulationLog( " circulation");
        logger.insertLog(" onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        logger.insertLog( " onLowMemory");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (lock.isHeld()) lock.release();
        logger.insertLog( " onDestroy");
    }
}
