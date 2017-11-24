package com.guo.lab.permission;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.guo.lab.R;
import com.guo.lab.Rom;
import com.guo.lab.Toolbox;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public class PermissionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = PermissionActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        findViewById(R.id.request).setOnClickListener(this);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {

            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int cameraResult = appOpsManager.checkOpNoThrow(
                    "android:camera", Process.myUid(), getPackageName());
            int storageResult = appOpsManager.checkOpNoThrow(
                    "android:read_external_storage", Process.myUid(), getPackageName());

            Log.d(TAG, opResultDesc(cameraResult));
            Log.d(TAG, opResultDesc(storageResult));
        }
        File file = new File(Environment.getExternalStorageDirectory(), "hello.txt");
        try {
            file.delete();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AppOpsManager aom = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (aom.checkOpNoThrow(AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW, Process.myUid(), getPackageName())!= AppOpsManager.MODE_ALLOWED) {
//                requestPermissions(new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW},100);
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        }
    }

    public static boolean needRequestDrawOverlay(Context context) {
        boolean need;
        AppOpsManager aom = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);

        if (Rom.isMiui() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            need = aom.checkOpNoThrow(AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW, Process.myUid(), context.getPackageName()) == AppOpsManager.MODE_ALLOWED;
        } else {
            need = Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1 || Settings.canDrawOverlays(context);
        }
        return need;
    }

    private String opResultDesc(int resultCode) {
        String opDesc;
        switch (resultCode) {
            case AppOpsManager.MODE_ALLOWED:
                opDesc = "Allow";
                break;
            case AppOpsManager.MODE_IGNORED:
                opDesc = "Ignored";
                break;
            case AppOpsManager.MODE_ERRORED:
                opDesc = "Errored";
                break;
            case AppOpsManager.MODE_DEFAULT:
                opDesc = "Default";
                break;
            default:
                opDesc = "NotKnow";
        }
        return opDesc;
    }


    @Override
    public void onClick(View v) {
        if (checkPermission(Manifest.permission.CAMERA, Process.myPid(), Process.myUid()) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
