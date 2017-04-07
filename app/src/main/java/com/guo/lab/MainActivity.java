package com.guo.lab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.blankj.utilcode.utils.FileUtils;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements BDLocationListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private LocationClient locationClient;
    private File directLocationFile;
    private File asyncLocationFile;
    private NetworkAndGpsReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startService(new Intent(this, FrameLocationService.class));




        locationClient = LocationMaster.getInstance(this).getLocationClient();
        locationClient.registerLocationListener(this);
        directLocationFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "directLocation.txt");
        FileUtils.createOrExistsFile(directLocationFile);
        asyncLocationFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "asyncLocation.txt");
        FileUtils.createOrExistsFile(asyncLocationFile);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                logLocation(directLocationFile, locationClient.getLastKnownLocation());
            }
        }, 0, 10000);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
        receiver = new NetworkAndGpsReceiver();
        registerReceiver(receiver, intentFilter);
    }

    private void logLocation(File file, BDLocation location) {
        if (!file.exists() || location == null) return;
        FileUtils.writeFileFromString(file, String.format(Locale.CHINA, "time:%s;latitude:%f;longitude:%f\n", Calendar.getInstance().getTime().toString(), location.getLatitude(),
                location.getLongitude()), true);

    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        logLocation(asyncLocationFile, bdLocation);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    public class NetworkAndGpsReceiver extends BroadcastReceiver {

        private String gpsStatus = "";
        private String networkStatus = "";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(LocationManager.PROVIDERS_CHANGED_ACTION)) {
                gpsStatus = LocationMaster.getInstance(context).isGpsOpend() ? "opened" : "closed";
                Log.d(TAG, gpsStatus);

                String status = String.format(Locale.CHINA, "gps : %s\nnetwork : %s\n\n", gpsStatus, networkStatus);
                logState(status);
            } else if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                networkStatus = networkInfo != null ? "connect" : "disconnect";
                Log.d(TAG, networkStatus);


                String status = String.format(Locale.CHINA, "gps : %s\nnetwork : %s\n\n", gpsStatus, networkStatus);
                logState(status);
            }
        }

        private void logState(String status) {
            FileUtils.writeFileFromString(directLocationFile, status, true);
            FileUtils.writeFileFromString(asyncLocationFile, status, true);
        }
    }
}


