package com.guo.artpractice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;


import com.guo.rong.ILibrary;
import com.guo.rong.IOnNewBookArriveListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class LibraryService extends Service {
    private static final String TAG = LibraryService.class.getSimpleName();

    public LibraryService() {
    }
    private ILibrary library = new ILibraryImp();

    @Override
    public IBinder onBind(Intent intent) {
        return library.asBinder();
    }

}
