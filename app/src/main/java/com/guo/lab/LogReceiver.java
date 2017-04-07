package com.guo.lab;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.content.ContextCompat;

public class LogReceiver extends BroadcastReceiver {


    public LogReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        CirculationLogger logger = AlarmService.logger;
        logger.insertLog( "Alarm onReceive");
//        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//        PowerManager.WakeLock lock = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP|PowerManager.SCREEN_DIM_WAKE_LOCK, "light");
//        lock.acquire();
//        lock.release();

//        doALog(context);
//        context.startService(new Intent(context, AlarmService.class));
    }

    public static void doALog(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent logPending = PendingIntent.getBroadcast(context, 0, new Intent(context, LogReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + 5000,logPending);
    }
}
