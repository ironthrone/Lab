package com.guo.lab.navigation;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.guo.lab.R;
import com.guo.lab.anim.AnimatorActivity;
import com.guo.lab.anim.AnimatorActivity2;

import rx.annotations.Beta;


public class AlphaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha);

        Intent openBetaIntent = new Intent(getApplicationContext(), GamaActivity.class);
        openBetaIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(GamaActivity.class);
        taskStackBuilder.addNextIntent(openBetaIntent);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(100, 0);



        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("hello")
                .setContentText("world")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(10, notification);
        startActivity(new Intent(this, BetaActivity.class));

        launchMultiActivitiesThroughPendingIntent();

//        Intent intent1 = new Intent(this, AnimatorActivity2.class);
//        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Intent[] intents = new Intent[]{
//                intent1,
//                new Intent(this, AnimatorActivity.class)
//        };
//        startActivity(new Intent(this, AnimatorActivity2.class));
//        startActivities(intents);
    }

    @NonNull
    private void launchMultiActivitiesThroughPendingIntent() {
        PendingIntent pendingIntent;Intent firstIntent = new Intent(getApplicationContext(), BetaActivity.class);
        firstIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        firstIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        Intent secondIntent = new Intent(getApplicationContext(), GamaActivity.class);
        pendingIntent = PendingIntent.getActivities(this, 100,
                new Intent[]{firstIntent, secondIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }


}
