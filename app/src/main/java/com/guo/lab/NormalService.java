package com.guo.lab;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.app.NotificationCompat;

import com.blankj.utilcode.utils.LogUtils;

import java.util.Date;

public class NormalService extends Service {

    private CirculationLogger logger;
    private KeepLiveReceiver keepLiveReceiver;

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

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        keepLiveReceiver = new KeepLiveReceiver();
        registerReceiver(keepLiveReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        LogUtils.d(new Date() + "onStartCommand");
        Notification foreground  = new NotificationCompat.Builder(this)
                .setContentTitle("I am working")
                .setContentText("")
                .setSmallIcon(R.mipmap.ic_launcher).build();
        startForeground(1,foreground);

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
        unregisterReceiver(keepLiveReceiver);
        logger.insertLog("on destroy");
    }
}
