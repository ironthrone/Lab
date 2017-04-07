package com.guo.lab;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.blankj.utilcode.utils.LogUtils;

import java.util.Date;

public class WakefulService extends Service {
    private CirculationLogger logger;
    private Intent intent;

    public WakefulService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.d(new Date() + " onCreate");
        logger = new CirculationLogger("wakefulservice.txt");
        logger.circulationLog("circulation");
        logger.insertLog("onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.d(new Date() + "onStartCommand");
        this.intent = intent;
        return START_STICKY;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        logger.insertLog("on low memory");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        logger.insertLog("on destroy");
        if (intent != null) {
            WakefulReceiver.completeWakefulIntent(intent);
        }
    }
}
