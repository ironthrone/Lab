package com.guo.lab;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.platform.comapi.location.CoordinateType;
import com.blankj.utilcode.utils.LogUtils;

import java.util.List;

/**
 * 定位管理类
 * Created by Administrator on 2016/8/3.
 */
public class LocationMaster {
    private static final String TAG = LocationMaster.class.getSimpleName();
    private LocationClient mLocationClient;
    private static final int LOCATION_SPAN = 1000;

    private static LocationMaster INSTANCE;
    private final Context mContext;

    public static LocationMaster getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (LocationMaster.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocationMaster(context);
                }
            }
        }
        return INSTANCE;
    }

    private LocationMaster(Context context) {
        mContext = context.getApplicationContext();
        mLocationClient = new LocationClient(context);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType(CoordinateType.BD09LL);
        option.setIsNeedAddress(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setScanSpan(LOCATION_SPAN);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        Log.d(TAG, "Location started");
    }

    /**
     * 初始化定位客户端
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        getInstance(context);
    }


    public LocationClient getLocationClient() {
        return mLocationClient;
    }

    public BDLocation getLastKnownLocation() {
        BDLocation bdLocation = mLocationClient.getLastKnownLocation();
        if (bdLocation == null) {
            bdLocation = new BDLocation();
            LogUtils.d("location is null");
        }
        if (BuildConfig.DEBUG) {
            Log.d("Current CacheLocation", bdLocation.getLatitude()
                    + " " + bdLocation.getLongitude() + " locType:" + bdLocation.getLocType());
        }
        return bdLocation;
    }


//    /**
//     * 获取最近的位置，使用百度定位在某些机型上会出现定位失败，这个方法做了一层保证，在
//     * 百度定位获取位置信息失败的时候，使用安卓自身框架来获取位置，然后转换为BD09LL坐标
//     *
//     * @param context 上下文
//     * @return BDLocation
//     */
//    public BDLocation getFusedLastKnownLocation(Context context) {
//        BDLocation lastKnownLocation = mLocationClient.getLastKnownLocation();
//        if (lastKnownLocation == null) {
//            LogUtil.d("bdlocation from baidu library is null");
//            lastKnownLocation = new BDLocation();
//        }
//        if (lastKnownLocation.getLatitude() < 0.0001 || lastKnownLocation.getLongitude() < 0.0001 || lastKnownLocation.getLocType() == 167) {
//            LogUtil.d("baidu location is not useful");
//
//            Location frameLocation = getLastKnownLocationFromFramework(context);
//            if (frameLocation == null) {
//                LogUtil.d("frame location is null");
//                return lastKnownLocation;
//            }
//            LogUtil.d(String.format(Locale.getDefault(), "framework latitude:%f,longitude:%f ", frameLocation.getLatitude(), frameLocation.getLongitude()));
//
//            CoordinateConverter coordinateConverter = new CoordinateConverter();
//            coordinateConverter.from(CoordinateConverter.CoordType.COMMON);
//            coordinateConverter.coord(new LatLng(frameLocation.getLatitude(), frameLocation.getLongitude()));
//            LatLng convert = coordinateConverter.convert();
//            lastKnownLocation.setLatitude(convert.latitude);
//            lastKnownLocation.setLongitude(convert.longitude);
//
//            LogUtil.d(String.format(Locale.getDefault(), "converted latitude:%f,longitude:%f ", convert.latitude, convert.longitude));
//        } else {
//            LogUtil.d(String.format(Locale.getDefault(), "bd location latitude:%f,longitude:%f ", lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()));
//        }
//
//        return lastKnownLocation;
//    }


    public  boolean isGpsReady() {
        return hasGpsSensor() && isGpsOpend() && isLocationPermissionGranted();
    }

    /**
     * 是否具有GPS传感器
     *
     * @return
     */
    public  boolean hasGpsSensor() {
        PackageManager pm = mContext.getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
    }

    /**
     * GPS是否有权限
     *
     * @return
     */
    public  boolean isLocationPermissionGranted() {
        return ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * GPS 是否打开
     *
     * @return
     */
    public  boolean isGpsOpend() {
        android.location.LocationManager lm = (android.location.LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);

    }

    public static LocationManager getLocationManager(Context context) {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

    }

    public  Location getLastKnownLocationFromFramework() {
        if (!isLocationPermissionGranted()) {
            return null;
        }

        LocationManager locationManager = getLocationManager(mContext);
        List<String> providers = locationManager.getProviders(true);
        Log.d(TAG,"available location provider" + providers.toString());
        Location bestLocation = null;

        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    public static boolean isLocationProviderAvailable(Context context) {
        LocationManager locationManager = getLocationManager(context);
        List<String> enableProviders = locationManager.getProviders(true);
        return enableProviders.size() != 0;
    }

}
