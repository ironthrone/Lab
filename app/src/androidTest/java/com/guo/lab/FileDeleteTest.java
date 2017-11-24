package com.guo.lab;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;


import com.blankj.utilcode.util.FileUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.junit.Assert.assertTrue;

/**
 * Created by ironthrone on 2017/10/17 0017.
 * 测试文件两个维度: 本应用/其他应用
 *          Environment.getExternalStorageDirectory()/Environment.getExternalStorageCacheDirectory()
 */

@RunWith(AndroidJUnit4.class)
public class FileDeleteTest {

    protected Context context = InstrumentationRegistry.getTargetContext();
    private File targetFile;


    String testFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/stats_furion.txt";

    @Before
    public void createFile() {

        targetFile = new File(testFile);
        boolean created = FileUtils.createFileByDeleteOldFile(targetFile);
        assertTrue(" fails to create file ", created);
    }

    @Test
    public void deleteWithFileDelete() {
        boolean deleted = targetFile.delete();
        assertTrue("file exist", !targetFile.exists());
        assertTrue("delete fails by File#delete()", deleted);
    }

    @Test
    public void deleteWithMediaStore() {
        Uri externalUri = MediaStore.Files.getContentUri("external");
        String where = MediaStore.Files.FileColumns.DATA + "=?";
        String[] args = new String[]{targetFile.getAbsolutePath()};
        Cursor cursor = context.getContentResolver().query(externalUri, null, where, args, null);
        assertTrue("file not added into MediaStore", cursor.getCount() > 0);
        cursor.close();
        int result = context.getContentResolver().delete(externalUri, where, args);
        assertTrue("fail to delete from content provider", result == 1);
        assertTrue("fail to delete from file system", !targetFile.exists());
    }


}
