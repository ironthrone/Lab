package com.guo.artpractice;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.guo.art.ILibrary;

import java.util.Arrays;
import java.util.List;

public class ReaderActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ReaderActivity.class.getSimpleName();
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            library = ILibrary.Stub.asInterface(service);
            try {
                service.linkToDeath(deathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
//            bindService()
        }
    };
    IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            bindService(new Intent(ReaderActivity.this, LibraryService.class), connection, Service.BIND_AUTO_CREATE);
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
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search:
                try {
                    boolean hit = library.searchBook("tianlong");
                    Log.d(TAG, hit ? "hit" : "miss");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.all:
                try {
                    List<String> books = library.getBookList();
                    Log.d(TAG, books.toString());
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
