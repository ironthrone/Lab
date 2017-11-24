package com.guo.lab.storage;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.guo.lab.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ironthrone on 2017/10/17 0017.
 */

public class StorageTasks {
    private static final String TAG = StorageTasks.class.getSimpleName();
    private Context context;

    public StorageTasks(Context context) {
        this.context = context;
    }



    private void useStatFs() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        int blockSize = statFs.getBlockSize();
        int blockCount = statFs.getBlockCount();
        int availableBlockCount = statFs.getAvailableBlocks();
        int freeBlockCount = statFs.getFreeBlocks();
        Log.d(TAG, "blockSize: " + blockSize + "\nblockCount: " + blockCount
                + "availableBlockCount: " + availableBlockCount + "\nfreeBlockCount: " + freeBlockCount);
    }

    public List<FileInfo> scanFile() {
        Uri externalUri = MediaStore.Files.getContentUri("external");
        String[] projection = new String[]{
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.TITLE,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.PARENT
        };

        List<FileInfo> fileInfos = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        Cursor cursor = context.getContentResolver().query(externalUri, projection, null, null, null);
            String externalCachePathRegex = Environment.getExternalStorageDirectory().getPath().replace("/", "\\/")
                    + "\\/Android\\/data\\/.+\\/cache\\/.+";
        if (cursor != null) {
            while (cursor.moveToNext()) {
                long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE));
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE));
                String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME));
                if (!TextUtils.isEmpty(path)) {
                    if (path.endsWith("log") || path.endsWith("tmp") || path.matches(externalCachePathRegex)) {
                        FileInfo fileInfo = new FileInfo();
                        fileInfo.size = size;
                        fileInfo.path = path;
                        fileInfo.title = title;
                        fileInfo.displayName = displayName;
                        fileInfos.add(fileInfo);
                    }
                }
            }
        }

        long sum = 0;
        StringBuilder sb = new StringBuilder();

        for (FileInfo fileInfo :
                fileInfos) {
            sum += fileInfo.size;
            sb.append(fileInfo.path + "\n");
        }
        long consumptionTime = System.currentTimeMillis() - startTime;
        sb.append("====================\n");
        sb.append("count: " + fileInfos.size() + "\n");
        sb.append("size: " + sum + "\n");
        sb.append("consumptionTime: " + consumptionTime);

        Log.d(TAG, sb.toString());
        return fileInfos;

    }
    //delete cache in settings app (version 23)
//        final PackageManager pm = getPackageManager();
//        final UserManager um = getSystemService(UserManager.class);
//
//        for (int userId : um.getProfileIdsWithDisabled(getUserId())) {
//            final List<PackageInfo> infos = pm.getInstalledPackagesAsUser(0, userId);
//
//            final ClearCacheObserver observer = new ClearCacheObserver(
//                    target, infos.size());
//            for (PackageInfo info : infos) {
//                pm.deleteApplicationCacheFilesAsUser(info.packageName, userId,
//                        observer);
//            }
//        }
}
