package com.guo.lab.manual;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

/**
 * Created by Administrator on 2017/4/24.
 */

public  interface IComputer extends IInterface {
    public static final String DESCRIPTOR = IComputer.class.getName();
    public static final int TRANSACTION_add = 1;
    public int add(int a, int b) throws RemoteException;



}
