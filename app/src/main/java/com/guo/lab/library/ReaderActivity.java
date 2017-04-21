package com.guo.lab.library;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.guo.lab.R;
import com.guo.rong.ILibrary;
import com.guo.rong.IOnNewBookArriveListener;

import java.util.List;

public class ReaderActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ReaderActivity.class.getSimpleName();

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            library = ILibrary.Stub.asInterface(service);
            try {
                service.linkToDeath(deathRecipient, 0);
                library.registerOnNewBookArriveListener(onNewBookArriveListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
//            bindService()
            Log.d(TAG, "onServiceDisconnected "+Thread.currentThread().toString());
        }
    };

    IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {

            Log.d(TAG, "binderdied "+Thread.currentThread().toString());
            if (library != null) {
                library.asBinder().unlinkToDeath(deathRecipient, 0);
            }

            bindService(new Intent(ReaderActivity.this, LibraryService.class), connection, Service.BIND_AUTO_CREATE);
        }
    };


    private IOnNewBookArriveListener onNewBookArriveListener = new IOnNewBookArriveListener.Stub() {
        @Override
        public void onNewBookArrive(Book book) throws RemoteException {

            Log.d(TAG, book.toString());

            // 运行在主线程

            Log.d(TAG, "onNewBookArrive  "+Thread.currentThread().toString());
            Log.d(TAG, Thread.currentThread() == Looper.getMainLooper().getThread() ? "main" : "background");
            // 根据Pid获取进程名称，运行在当前进程
            int pid = android.os.Process.myPid();
            ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
                if (processInfo.pid == pid) {
                    Log.d(TAG, processInfo.processName);
                }
            }
        }

    };

    private ILibrary library;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        Intent bindIntent = new Intent(this, LibraryService.class);
        bindService(bindIntent, connection, BIND_AUTO_CREATE);
        findViewById(R.id.search).setOnClickListener(this);
        findViewById(R.id.all)
                .setOnClickListener(this);
        findViewById(R.id.add)
                .setOnClickListener(this);
        findViewById(R.id.unregister)
                .setOnClickListener(this);
        findViewById(R.id.register)
                .setOnClickListener(this);
        findViewById(R.id.stop)
                .setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stop:
                unbindService(connection);
                stopService(new Intent(this, LibraryService.class));
                break;
            case R.id.register:
                try {
                    library.registerOnNewBookArriveListener(onNewBookArriveListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.unregister:
                try {
                    library.unregisterOnNewBookArriveListener(onNewBookArriveListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.search:
                try {
                    Book book = library.searchBook("Android");
                    Log.d(TAG, book != null ? book.toString() : "miss");
                    if (book != null) {
                        book.setAuthor("World");
                        book = library.searchBook("Android");

                        Log.d(TAG, book != null ? book.toString() : "miss");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.all:
                try {
                    List<Book> books = library.getBookList();
                    Log.d(TAG, books.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.add:
                try {
                    library.addBook(new Book("Stock", "Guo", 44));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
