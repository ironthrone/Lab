package com.guo.lab;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;


public class FrameLocationService extends Service {
    private static final String TAG = FrameLocationService.class.getSimpleName();
    private CirculationLogger logger;

    public FrameLocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        logger = new CirculationLogger("frameLocationService.txt");
        logger.insertLog(" onCreate");
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                logger.insertLog(location.toString());
                Log.d(TAG, "onLocationChanged");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                logger.insertLog("enable:" + provider);
                Log.d(TAG, "onProviderEnabled");
            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        logger.insertLog("onStartCommand");
        startForeground(1,new Notification());
        return START_STICKY;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        logger.insertLog(" adbonLowMemory");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        logger.insertLog(" onDestroy");
    }
}
