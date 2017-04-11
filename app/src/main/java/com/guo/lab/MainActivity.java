package com.guo.lab;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.guo.lab.remoteview.ToastReceiver;


public class MainActivity extends AppCompatActivity  {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.horizontal_scroll)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this,HorizontalPagerActivity.class));
                    }
                });
        findViewById(R.id.layout_animation)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this,LayoutAnimationActivity.class));
                    }
                });
        findViewById(R.id.property_animator)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this,AnimatorActivity.class));
                    }
                });

        findViewById(R.id.recycler)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this,RecyclerActivity.class));
                    }
                });
        findViewById(R.id.remote_view)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
                        remoteViews.setTextViewText(R.id.title,"What have done");
                        Intent intent = new Intent(getApplicationContext(), ToastReceiver.class);
                        intent.putExtra("extra", "hello");
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        remoteViews.setOnClickPendingIntent(R.id.play,pendingIntent);
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContent(remoteViews);

                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(1,builder.build());


                        //发送另外一个通知，测试PendingIntent的标记位的作用
                        //在PendingIntent匹配的情况下,标记位会影响匹配的PendingIntent的内容
                        Intent intent2 = new Intent(getApplicationContext(), ToastReceiver.class);
                        intent2.putExtra("extra", "world");
                        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext(), 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                        RemoteViews remoteViews2 = new RemoteViews(getPackageName(), R.layout.custom_notification);
                        remoteViews2.setTextViewText(R.id.title,"What have done");
                        remoteViews2.setOnClickPendingIntent(R.id.play,pendingIntent2);
                        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContent(remoteViews2);

                        notificationManager.notify(2,builder2.build());
                    }
                });

    }
}
