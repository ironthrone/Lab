package com.guo.lab;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import java.util.Date;

public class AlarmService extends Service {
    public AlarmService() {
    }

    public static CirculationLogger logger;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        logger = new CirculationLogger("alarmservice.txt");
        logger.insertLog( " onCreate");

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent logPending = PendingIntent.getBroadcast(this, 0, new Intent(this, LogReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + 5000,logPending);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,0,60000,logPending);
//        LogReceiver.doALog(this);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        logger.insertLog( "onStartCommand");
        return START_STICKY;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        logger.insertLog( " adbonLowMemory");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        logger.insertLog(" onDestroy");
    }
}
