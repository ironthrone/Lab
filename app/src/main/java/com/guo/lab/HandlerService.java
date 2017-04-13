package com.guo.lab;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.IntDef;

public class HandlerService extends Service {
    private static final int TRACK_LOG = 1000;
    private CirculationLogger logger;
    private PowerManager.WakeLock wakeLock;

    public HandlerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == TRACK_LOG) {
                logger.insertLog("handler");
                Message message = handler.obtainMessage(TRACK_LOG);
                handler.sendMessageDelayed(message, 5000);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "weak");
        wakeLock.acquire();

        logger = new CirculationLogger("handlerService.txt");
        Message message = handler.obtainMessage(TRACK_LOG);
        handler.sendMessage(message);
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(wakeLock.isHeld()) wakeLock.release();
    }
}
