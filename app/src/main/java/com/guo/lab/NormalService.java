package com.guo.lab;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;

import com.blankj.utilcode.utils.LogUtils;

import java.util.Date;

public class NormalService extends Service {

    private CirculationLogger logger;

    public NormalService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.d(new Date() + " onCreate");
        logger = new CirculationLogger("normalservice.txt");
        logger.circulationLog("circulation");
        logger.insertLog("onCreate");
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        LogUtils.d(new Date() + "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
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
    }
}
