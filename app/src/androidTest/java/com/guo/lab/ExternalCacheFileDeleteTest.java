package com.guo.lab;

import java.io.File;

/**
 * Created by ironthrone on 2017/10/17 0017.
 */

public class ExternalCacheFileDeleteTest extends FileDeleteTest {
    protected String getFilePath() {
        return new File(context.getExternalCacheDir(), "deleteFile.txt").getAbsolutePath();
    }

    @Override
    public void deleteWithMediaStore() {
        super.deleteWithMediaStore();
    }
}
