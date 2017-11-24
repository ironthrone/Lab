package com.guo.lab.storage;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.guo.lab.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class StorageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = StorageActivity.class.getSimpleName();
    private File targetFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

//

        findViewById(R.id.clean_cache)
                .setOnClickListener(this);
        findViewById(R.id.toast)
                .setOnClickListener(this);

        targetFile = new File(testFile);
//        boolean created = FileUtils.createFileByDeleteOldFile(targetFile);

        boolean deleted = targetFile.delete();
//
//

        //not work

        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(Environment.getExternalStorageDirectory())));
        Schedulers.io().createWorker().schedule(new Action0() {
            @Override
            public void call() {

                List<FileInfo> fileInfos = new StorageTasks(getApplicationContext()).scanFile();

                List<FileInfo> deleteFailedFile = new ArrayList<>();
                for (FileInfo fileInfo :
                        fileInfos) {
                    File file = new File(fileInfo.path);
                    boolean result;
                    if (file.exists()) {
                        result = file.delete();
                    } else {
                        result = true;
                    }
                    if (!result) {
                        deleteFailedFile.add(fileInfo);
                    }
                }

                LogUtils.d("fail file  count: " + deleteFailedFile.size());
                StringBuilder sb = new StringBuilder();
                for (FileInfo fileInfo :
                        deleteFailedFile) {
                    sb.append(fileInfo.toString());
                    sb.append("\n");
                }
                LogUtils.d(sb.toString());

                // refresh file info in MediaStore
                String[] paths = new String[fileInfos.size()];
                for (int i = 0; i < paths.length; i++) {
                    paths[i] = fileInfos.get(i).path;
                }
                final CountDownLatch refreshLatch = new CountDownLatch(paths.length);
                MediaScannerConnection.scanFile(getApplicationContext(), paths, null, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.d(TAG, "scan after media refesh");
                        refreshLatch.countDown();
                    }
                });
                try {
                    refreshLatch.await();
                    new StorageTasks(getApplicationContext()).scanFile();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    String testFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/title.png";

    private void scanFileToMediaStore() {
        MediaScannerConnection.scanFile(this, new String[]{Environment.getExternalStorageDirectory().getAbsolutePath()},
                null, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {

                        Log.d(TAG, path);
                        Log.d(TAG, uri == null ? null : uri.toString());
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clean_cache:
                sendBroadcast(new Intent(CleanCacheAccessibilityService.CLEAN_CACHE_ACTION));
                break;
            case R.id.toast:
                ToastUtils.showShortSafe("toast");
                break;
        }
    }
}
