package com.guo.lab.binderpool;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.guo.rong.IBinderPool;

public class BinderPoolService extends Service {
    public BinderPoolService() {
    }

    private IBinderPool iBinderPool = new BinderPoolManager.BinderPoolReal();

    @Override
    public IBinder onBind(Intent intent) {
        return iBinderPool.asBinder();
    }
}
