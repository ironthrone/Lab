package com.guo.lab;

import android.os.Environment;

/**
 * Created by ironthrone on 2017/10/17 0017.
 */

public class OtherAppExternalCacheFileDeleteTest extends FileDeleteTest {
    protected String getFilePath() {
        return  Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/cn.ticktick.task/cache/delete";
    }

    @Override
    public void deleteWithMediaStore() {
        super.deleteWithMediaStore();
    }
}
