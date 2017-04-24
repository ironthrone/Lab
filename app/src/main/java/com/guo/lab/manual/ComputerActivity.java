package com.guo.lab.manual;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.utils.ToastUtils;
import com.guo.lab.R;

public class ComputerActivity extends AppCompatActivity implements View.OnClickListener {


        private IComputer iComputer;
    private ServiceConnection connection = new ServiceConnection() {


        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iComputer = Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer);
        bindService(new Intent(this, ComputerService.class), connection, Service.BIND_AUTO_CREATE);
        findViewById(R.id.add).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unbindService(connection);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (iComputer != null) {
            try {
                //阻塞方法
                int result = iComputer.add(10, 20);
                ToastUtils.showShortToastSafe("result: " + result);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
