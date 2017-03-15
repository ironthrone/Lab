package com.guo.artpractice.library;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


import com.guo.rong.ILibrary;

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
