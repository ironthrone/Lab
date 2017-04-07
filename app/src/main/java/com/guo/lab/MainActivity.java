package com.guo.lab;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(MainActivity.this, NormalService.class));
        startService(new Intent(MainActivity.this, AlarmService.class));
        startService(new Intent(MainActivity.this, LockService.class));
        sendBroadcast(new Intent(this,WakefulReceiver.class));
        findViewById(R.id.start_service)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        startService(new Intent(MainActivity.this,NormalService.class));
                    }
                });

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, location.toString());
                Log.d(TAG, "onLocationChanged");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d(TAG, "onProviderEnabled");
            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        Log.d(TAG,locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).toString());

    }
}
