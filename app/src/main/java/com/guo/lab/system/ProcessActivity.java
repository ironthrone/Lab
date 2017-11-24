package com.guo.lab.system;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.guo.lab.R;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class ProcessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);

        ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
        String currentApp = tasks.get(0).processName;
        PackageManager packageManager = getPackageManager();
        List<String> packageNames = new ArrayList<>();
        for (ActivityManager.RunningAppProcessInfo task : tasks) {
            final String packageName = task.processName;
            try {
                if (!packageName.equals(getPackageName()) && !packageName.contains("system")) {
                    Drawable icon = packageManager.getApplicationIcon(packageName);
                    packageNames.add(packageName);
                }
            } catch (PackageManager.NameNotFoundException e) {
                // e.printStackTrace();
            }
        }


        List<ActivityManager.RunningServiceInfo> runningServiceInfos =
                ((ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo serviceInfo : runningServiceInfos) {
            String packageName = serviceInfo.service.getPackageName();
            try {
                if ((getPackageManager().getApplicationInfo(packageName, 0).flags & 1) == 1) {
                } else {
                    if (!serviceInfo.process.equals(getPackageName()) && !packageNames.contains(packageName)) {
                        Drawable icon = packageManager.getApplicationIcon(packageName);
                        packageNames.add(packageName);
                    }

                }
            } catch (PackageManager.NameNotFoundException e) {
            }
        }

    }
}
