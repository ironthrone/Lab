package com.guo.lab;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.blankj.utilcode.utils.ServiceUtils;


/**
 * 双进程相互拉起，在用户清理任务列表的操作后无效
 */
public class KeepLivingRemoteService extends Service {
    public KeepLivingRemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final CirculationLogger logger = new CirculationLogger("remoteService.txt");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
//                    SystemClock.sleep(2000);
                    if (!ServiceUtils.isServiceRunning(getApplicationContext(), "com.guo.lab.HandlerService")) {
                        startService(new Intent(getApplicationContext(), KeepLivingLocalService.class));
                        logger.insertLog("从远程拉起服务");
                    }
                }
            }
        }).start();
    }
}
