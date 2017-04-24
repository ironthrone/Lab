package com.guo.lab.manual;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.guo.rong.ILibrary;

public abstract class Stub extends Binder implements IComputer {


    public Stub() {
        this.attachInterface(this, DESCRIPTOR);
    }

    public static IComputer asInterface(IBinder obj) {
        if (obj == null) {
            return null;
        }

        IInterface iInterface = obj.queryLocalInterface(DESCRIPTOR);
        if (iInterface != null && iInterface instanceof ILibrary) {
            return (IComputer) iInterface;
        }
        return new Proxy(obj);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case TRANSACTION_add:
                //非必须，和写入token对应
                data.enforceInterface(DESCRIPTOR);
                int a = data.readInt();
                int b = data.readInt();
                //写入异常，非必须
                reply.writeNoException();
                reply.writeInt(add(a,b));
            return true;
        }
        return super.onTransact(code, data, reply, flags);

    }

    private static class Proxy implements IComputer {
        private IBinder binder;

        public Proxy(IBinder binder) {
            this.binder = binder;
        }

        //这个方法也不是必须的
        public String getInterfaceDescriptor() {
            return DESCRIPTOR;
        }

        @Override
        public int add(int a, int b) throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            try {
                //写入接口的token
                data.writeInterfaceToken(DESCRIPTOR);
                data.writeInt(a);
                data.writeInt(b);
                binder.transact(TRANSACTION_add, data, reply, 0);
                //这和写异常对应
                reply.readException();
                return reply.readInt();
            } finally {
                data.recycle();
                reply.recycle();
            }
        }

        @Override
        public IBinder asBinder() {
            return binder;
        }
    }
}