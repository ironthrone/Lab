package com.guo.lab.battery;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;


import com.guo.lab.Toolbox;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Created by ironthrone on 2017/6/19 0019.
 */

public class ExtractBatteryInfo {

    private File outFile;


    public ExtractBatteryInfo() {
        outFile = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            outFile = new File(Environment.getExternalStorageDirectory() + "/battery.info");
            try {
                if (!outFile.exists()) {
                    boolean result = outFile.createNewFile();
                    if (!result) {
                        throw new UnsupportedOperationException("Create file fail");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalAccessError("There is no sd card");
        }

    }


    public String extract(Context context) {

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent intent = context.registerReceiver(null, intentFilter);
        Bundle bundle = intent.getExtras();
        StringBuilder infoSB = new StringBuilder();
        for (String key :
                bundle.keySet()) {
            Object value = bundle.get(key);
            infoSB.append(String.format("%s: %s\n", key, value));
        }
        BatteryManager batteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int current = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
            infoSB.append(String.format("current in microampere: %d\n", current));
            //微安时
            int remainingQuantity = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
            infoSB.append(String.format("quantity in microampere-hour: %d\n", remainingQuantity));
            int currentAverage = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE);
            infoSB.append(String.format("average current:%d\n", currentAverage));
            infoSB.append(String.format("design battery capacity :%d\n", BatteryUtils.getDesignedBatteryCapacity(context)));
        }


        appendFile(infoSB,"uevent","/sys/class/power_supply/battery/uevent");
        appendFile(infoSB, "current_now", "/sys/class/power_supply/battery/current_now");
        appendFile(infoSB,"current_avg","/sys/class/power_supply/battery/current_avg");
        appendFile(infoSB,"charge_full","/sys/class/power_supply/battery/charge_full");

        return infoSB.toString();
    }

    private static void appendFile(StringBuilder sb, String title, String path) {
        String content = Toolbox.read(path);

        sb.append(title + "\n");

        if (TextUtils.isEmpty(content)) {
            sb.append("empty");
        } else {
            sb.append(content);
        }
        sb.append("\n\n");
    }


    public void extractAndWrite(Context context) {
        String info = extract(context);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile));
            bufferedWriter.write(info);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
