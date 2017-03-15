package com.guo.artpractice.binderpool;


import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.guo.artpractice.Book;
import com.guo.artpractice.ILibraryImp;
import com.guo.rong.IBinderPool;
import com.guo.rong.ILibrary;
import com.guo.rong.IOnNewBookArriveListener;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/3/15.
 */

public class BinderPool {
    public static final int TYPE_LIBRARY = 1;

    private IBinderPool binderPoolReal;
    private static BinderPool INSTANCE;
    private Context context;


    public BinderPool(Context context) {
        this.context = context.getApplicationContext();
        connectToService();
    }

    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private ServiceConnection mPoolConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binderPoolReal = BinderPoolReal.asInterface(service);
            //TODO DeathRecipient
            countDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void connectToService() {
        Intent intent = new Intent(context, BinderPoolService.class);
        context.bindService(intent, mPoolConnection, Service.BIND_AUTO_CREATE);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //后台线程调用
    public static BinderPool with(Context context) {
        if (INSTANCE == null) {
            synchronized (BinderPool.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BinderPool(context);
                }
            }
        }
        return INSTANCE;
    }


    public IBinder getBinder(int type) {
        IBinder binder = null;
        try {
            if (binderPoolReal != null) {
                binder = binderPoolReal.getBinder(type);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;

    }

    /**
     * 取消绑定，防止内存泄露
     */
    public void clear(){
        if (mPoolConnection != null) {
        context.unbindService(mPoolConnection);
            mPoolConnection = null;
        }
    }


    public static class BinderPoolReal extends IBinderPool.Stub {
        public ILibrary library = new ILibraryImp();

        @Override
        public IBinder getBinder(int type) throws RemoteException {
            IBinder iBinder;
            switch (type) {
                case TYPE_LIBRARY:
                    iBinder = library.asBinder();
                    break;
                default:
                    throw new UnsupportedOperationException("Not support this type");
            }
            return iBinder;
        }
    }
}
