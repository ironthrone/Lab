package com.guo.lab;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.blankj.utilcode.utils.LogUtils;

import java.util.Date;

public class ForegroundService extends Service {

    private CirculationLogger logger;
    private KeepLiveReceiver keepLiveReceiver;

    public ForegroundService() {
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
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.d(new Date() + "onStartCommand");
        //api小于18时候直接弹出空的通知
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(1, new Notification());
        } else {
            Notification foreground = new NotificationCompat.Builder(this)
                    .setContentTitle("I am working")
                    .setContentText("")
                    .setSmallIcon(R.mipmap.ic_launcher).build();
            startForeground(1, foreground);
        //开启服务清除通知
        startService(new Intent(this, InnerService.class));
        }


        //返回值表明如果服务被杀死，这个服务可以被重启
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        logger.insertLog("on low memory");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        unregisterReceiver(keepLiveReceiver);
        logger.insertLog("on destroy");
    }

    // 用作清除通知
    public static class InnerService extends Service {

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Notification foreground = new NotificationCompat.Builder(this)
                    .setContentTitle("I am working")
                    .setContentText("")
                    .setSmallIcon(R.mipmap.ic_launcher).build();
            startForeground(1, foreground);
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }
    }
}
