package com.guo.lab;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.blankj.utilcode.utils.AppUtils;
import com.blankj.utilcode.utils.ServiceUtils;

public class KeepLivingLocalService extends Service {
    public KeepLivingLocalService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final CirculationLogger logger = new CirculationLogger("localService.txt");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
//                    SystemClock.sleep(2000);
                    if (!ServiceUtils.isServiceRunning(getApplicationContext(), "com.guo.lab.KeepLivingRemoteService")) {
                        startService(new Intent(getApplicationContext(), KeepLivingRemoteService.class));
                        logger.insertLog("拉起远程服务进程");
                    }
                }
            }
        }).start();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //行不通
        startService(new Intent(this, KeepLivingLocalService.class));
    }
}
