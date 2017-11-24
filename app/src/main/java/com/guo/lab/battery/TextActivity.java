package com.guo.lab.battery;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.guo.lab.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class TextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        // 读取和电池相关的文件，不需要root权限
        //相关的文件/sys/class/power_supply/battery/uevent
        final ExtractBatteryInfo extractBatteryInfo = new ExtractBatteryInfo();
        Observable.interval(100, 100, TimeUnit.MILLISECONDS)
                .onBackpressureDrop()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {

                        ((TextView) findViewById(R.id.text)).setText(extractBatteryInfo.extract(getApplicationContext()));
                    }
                })
                .subscribe();

        //viscous broadcast
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);

        Intent intent = registerReceiver(null, intentFilter);
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        String material = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
        ToastUtils.showShortSafe("level: "+ level + " technology: " + material);
    }
}
