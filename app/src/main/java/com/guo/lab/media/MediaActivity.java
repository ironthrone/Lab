package com.guo.lab.media;

import android.content.Context;
import android.media.AudioManager;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.guo.lab.R;

import java.io.File;

public class MediaActivity extends AppCompatActivity {

    private AudioManager audioManager;
    private StorageManager storageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

//        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//
//        audioManager.setStreamVolume(AudioManager.STREAM_RING,0,0);
//        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,0,0);

        storageManager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
//        storageManager.getStorageVolumes();
//        storageManager.getPrimaryStorageVolume();
//        storageManager.getStorageVolume(new File());
        storageManager.getMountedObbPath("");
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.vibrate:
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                break;
            case R.id.silence:
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                audioManager.setStreamVolume(AudioManager.STREAM_RING,0,0);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                audioManager.setStreamVolume(AudioManager.STREAM_ALARM,0,0);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.audio_ring_mode_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.silence:
                try {

                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                } catch (Exception e) {

                }
                break;
            case R.id.vibrate:
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                break;
            case R.id.normal:
                try {

                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                } catch (Exception e) {

                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
