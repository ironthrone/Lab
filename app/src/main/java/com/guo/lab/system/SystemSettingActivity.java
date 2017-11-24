package com.guo.lab.system;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;
import com.guo.lab.R;
import com.guo.lab.storage.CleanCacheAccessibilityService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SystemSettingActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = SystemSettingActivity.class.getSimpleName();
    private TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_setting);
        findViewById(R.id.disconnect)
                .setOnClickListener(this);
        findViewById(R.id.connect)
                .setOnClickListener(this);

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

//        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 255);



    }

    /**
     * ITelephony#setDataEnabled(),enableDataConnectivity(),disableDataConnectivity()都需要 MODIFY_PHONE_STATE,
     * 这个权限已经是系统权限，通过反射调用这些方法会抛出SecurityException，因此暂时打开关闭数据流量功能是不能做到了
     *
     * @param enable
     */
    public void setMobileDataEnable(boolean enable) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            Method methodGetITelephony = TelephonyManager.class.getDeclaredMethod("getITelephony");
            methodGetITelephony.setAccessible(true);
            ITelephony iTelephony = (ITelephony) methodGetITelephony.invoke(telephonyManager);
            Method methodSetDataEnable = telephonyManager.getClass().getDeclaredMethod("setDataEnabled", boolean.class);
            methodSetDataEnable.setAccessible(true);
            methodSetDataEnable.invoke(telephonyManager, enable);

            if (iTelephony != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                    iTelephony.setDataEnabled(SubscriptionManager.getDefaultSubscriptionId(), enable);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {

                    Method methodGetDefaultDataSubId = SubscriptionManager.class.getDeclaredMethod("getDefaultDataSubId");
                    methodGetDefaultDataSubId.setAccessible(true);
                    int defaultDataSubId = (int) methodGetDefaultDataSubId.invoke(null);
                    iTelephony.setDataEnabled(defaultDataSubId, enable);

                } else {
                    Method methodSetDataEnabled = iTelephony.getClass().getDeclaredMethod("setDataEnabled", boolean.class);
                    methodSetDataEnabled.setAccessible(true);
                    methodSetDataEnabled.invoke(iTelephony, enable);
                }
            } else {
                Log.d(TAG, "iTelephony is null");
            }
        } catch (Exception e) {
            // Oops! Something went wrong, so we throw the exception here.
            e.printStackTrace();
        }
    }

    private static boolean isMobileDataEnabledFromLollipop(Context context) {
        boolean state = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            state = Settings.Global.getInt(context.getContentResolver(), "mobile_data", 0) == 1;
        }
        return state;
    }

    private void showDataState() {
        Toast.makeText(this, telephonyManager.getDataState() == TelephonyManager.DATA_CONNECTED ? "connect" : "disconnect", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connect:
                //not work, requset permission “MODIFY_PHONE_STATE”,but this permission can only be granted to system app
                setMobileDataEnable(true);

                break;
            case R.id.disconnect:
//                Settings.Global.putInt(getContentResolver(), "mobile_data", 1);
                setMobileDataEnable(false);
                break;
        }
        showDataState();
    }
}
