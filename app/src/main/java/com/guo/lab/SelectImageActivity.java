package com.guo.lab;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectImageActivity extends AppCompatActivity {

    private static final int SELECT_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);

        //打开相册选取图片，使用Uri来定位资源。也支持选择视频
        findViewById(R.id.select_image)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(intent, SELECT_IMAGE);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE && data != null) {
            Uri uri = data.getData();
            String[] columns = {MediaStore.Audio.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, columns, null, null, null);
            if (cursor != null) {
                try {
                    cursor.moveToFirst();
                    String path = cursor.getString(cursor.getColumnIndex(columns[0]));
                    System.out.println(path);
                } finally {
                    cursor.close();
                }
            }
        }
    }
}
