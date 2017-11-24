package com.guo.lab;

import android.os.Environment;

import java.io.File;

/**
 * Created by ironthrone on 2017/10/17 0017.
 */

public class ExternalFileDeleteTest extends FileDeleteTest {
    protected String getFilePath() {
        return new File(Environment.getExternalStorageDirectory(), "deleteTest.txt").getAbsolutePath();
    }
}
