package com.guo.lab;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RemoteViews;

import com.blankj.utilcode.util.ToastUtils;
import com.guo.lab.accessibility.CleanActivity;
import com.guo.lab.accessibility.WindowActivity;
import com.guo.lab.anim.AActivity;
import com.guo.lab.anim.AnimatorActivity;
import com.guo.lab.anim.AnimatorActivity2;
import com.guo.lab.anim.InvalidateTestActivity;
import com.guo.lab.anim.LayoutAnimationActivity;
import com.guo.lab.anim.LayoutTransitionActivity;
import com.guo.lab.anim.SpringAnimationActivity;
import com.guo.lab.databinding.ActivityMainBinding;
import com.guo.lab.databinding.BindingActivity;
import com.guo.lab.dialog.DialogActivity;
import com.guo.lab.draw.CanvasActivity;
import com.guo.lab.lottie.LottieActivity;
import com.guo.lab.material.DrawerActivity;
import com.guo.lab.media.MediaActivity;
import com.guo.lab.navigation.AlphaActivity;
import com.guo.lab.notification.NotificationActivity;
import com.guo.lab.permission.PermissionActivity;
import com.guo.lab.persist.SqliteDatabaseActivity;
import com.guo.lab.recyclerview.RecyclerActivity;
import com.guo.lab.remoteview.ToastReceiver;
import com.guo.lab.storage.StorageActivity;
import com.guo.lab.system.ProcessActivity;
import com.guo.lab.system.SystemSettingActivity;
import com.guo.lab.transition.TransitionFromActivity;
import com.guo.lab.view.SomeViewAttrsActivity;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

//        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        ToastUtils.showShortToast("wifi   " + String.valueOf(wifiManager.isWifiEnabled()));


//        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
//        android.provider.Settings.System.putInt(getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS,
//                12);

        toTestActivity(WindowActivity.class);


//        new NewDialog(this)
//                .show();

    }

    private void toTestActivity(Class<? extends Activity> activityClass) {
        startActivity(new Intent(this, activityClass));
        finish();
    }

}
