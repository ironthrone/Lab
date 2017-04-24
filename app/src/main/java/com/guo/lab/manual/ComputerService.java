package com.guo.lab.manual;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class ComputerService extends Service {
    public ComputerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new Stub() {
            @Override
            public int add(int a, int b) throws RemoteException {
                return a+b;
            }
        };
    }
}
