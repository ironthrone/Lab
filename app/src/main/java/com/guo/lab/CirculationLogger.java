package com.guo.lab;

import android.content.Context;

import com.blankj.utilcode.utils.FileUtils;
import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.SDCardUtils;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/4/5.
 */

public class CirculationLogger implements Serializable {
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
    private File logFile;
    private boolean circulated;

    public CirculationLogger(String fileStr) {
        logFile = new File(SDCardUtils.getSDCardPath() + "/Lab/" + fileStr);
        LogUtils.d("logfile:" + logFile.getAbsolutePath());
        boolean result = FileUtils.createFileByDeleteOldFile(logFile);
        if (!result) throw new IllegalArgumentException("File path not legal");
    }

    private boolean checkLogFileIsExist() {
        boolean exit = FileUtils.createOrExistsFile(logFile);
        return exit;
    }

    public void circulationLog(final String str) {
        if (circulated) {
            circulated = true;
            return;
        }
        if (checkLogFileIsExist()) {

            scheduler.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    insertLog(str);
                }
            }, 0, 5, TimeUnit.SECONDS);
        }
    }

    public void insertLog(String string) {
        if (checkLogFileIsExist()) {
            FileUtils.writeFileFromString(logFile, new Date() + string + "\n", true);
        }
    }
}
