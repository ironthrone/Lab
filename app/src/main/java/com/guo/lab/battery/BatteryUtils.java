package com.guo.lab.battery;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;

public class BatteryUtils {
    private static double VAL_TIME_REMAIN = 1;
    private static double VAL_TIME_CHARGE = 1.8;

    public static int getDesignedBatteryCapacity(Context context) {
        Object mPowerProfile_ = null;

        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";

        try {
            mPowerProfile_ = Class.forName(POWER_PROFILE_CLASS).getConstructor(Context.class).newInstance(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Double batteryCapacity = (Double) Class.forName(POWER_PROFILE_CLASS).getMethod("getAveragePower", java.lang.String.class).invoke(mPowerProfile_, "battery.capacity");
            return batteryCapacity.intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    public static long getActualBatteryCapacity(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {

                BatteryManager mBatteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
                //电池剩余容量，微安陪每小时
                Long chargeCounter = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
                //剩余电量百分比
                Long capacity = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
                if (chargeCounter > 0 && capacity != 0) {
                        return  (long) (( chargeCounter * 1.0f/ capacity) * 100) / 1000;
                } else {
                    return (long) getDesignedBatteryCapacity(context);
                }

            } catch (Exception e) {
                return (long) getDesignedBatteryCapacity(context);
            }
        } else {
            return getDesignedBatteryCapacity(context);
        }
    }




    public static double getTimeRemaining(Context context, int level) {
        long batteryCapacity = getActualBatteryCapacity(context);
        double tmp = ((VAL_TIME_REMAIN * batteryCapacity / 100 * level));
        return tmp;
    }

    public static double getChargeRemaining(Context context, int level) {
        double batteryCapacity = 60 * VAL_TIME_CHARGE;
        double tmp = batteryCapacity / 100 * (100 - level);
        return tmp;
    }

    // Note where is these number come from
    public static double getCallTimeRemaining(Context context, int level) {
        long batteryCapacity = getActualBatteryCapacity(context);
        double tmp = ((VAL_TIME_REMAIN * batteryCapacity / 100 * level));
        return tmp / 7.54792;
    }

    public static double getWiFiTimeRemaining(Context context, int level) {
        long batteryCapacity = getActualBatteryCapacity(context);
        double tmp = ((VAL_TIME_REMAIN * batteryCapacity / 100 * level));
        return tmp / 5.34792;
    }

    public static double getMoviesTimeRemaining(Context context, int level) {
        long batteryCapacity = getActualBatteryCapacity(context);
        double tmp = ((VAL_TIME_REMAIN * batteryCapacity / 100 * level));
        return tmp / 5.52292;
    }

    public static String displayTime(int hourOrMin) {
        if (hourOrMin < 10) {
            return ("0" + hourOrMin);
        } else {
            return ("" + hourOrMin);
        }
    }

    public static android.text.Spanned getDisplayAvailableTime(double mTimeRemaining) {
        return android.text.Html.fromHtml("<font color=\"#00dd68\">" + BatteryUtils.displayTime((int) mTimeRemaining / 60) + "</font> " +
                "<font color=\"#2d3039\">h</font> " +
                "<font color=\"#00dd68\">" + BatteryUtils.displayTime((int) (((mTimeRemaining / 60) - (int) (mTimeRemaining / 60)) * 60)) + "</font>" +
                "<font color=\"#2d3039\">m</font> "
        );
    }





    public static int getBatteryLevel(Context activity) {
        Intent batteryIntent = activity.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        // Error checking that probably isn't needed but I added just in case.
        if (level == -1 || scale == -1) {
            return (int) 50.0f;
        }

        return (int) (((float) level / (float) scale) * 100.0f);
    }

}
