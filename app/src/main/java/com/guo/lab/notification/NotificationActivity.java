package com.guo.lab.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.guo.lab.R;
import com.guo.lab.remoteview.ToastReceiver;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    private NotificationManager notificationManager;
    private Notification notification1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        findViewById(R.id.show)
                .setOnClickListener(this);
        findViewById(R.id.cancel)
                .setOnClickListener(this);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
        remoteViews.setTextViewText(R.id.title, "one");
        Intent intent = new Intent(getApplicationContext(), ToastReceiver.class);
        intent.putExtra("extra", "hello");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.play, pendingIntent);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                //这个方法在API16生效，并且显示的通知是折叠的
//                .setCustomContentView(remoteViews)
//                .setContentTitle("lab title")
//                .setContentText("lab text")
                .setContent(remoteViews)
                .setPriority(Integer.MAX_VALUE)
                .setAutoCancel(false);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notification1 = builder.build();
        notificationManager.notify(1, notification1);


        //发送另外一个通知，测试PendingIntent的标记位的作用
        //在PendingIntent匹配的情况下,标记位会影响匹配的PendingIntent的内容
        Intent intent2 = new Intent(getApplicationContext(), ToastReceiver.class);
        intent2.putExtra("extra", "world");
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext(), 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
        remoteViews.setTextViewText(R.id.title, "two");
        remoteViews.setOnClickPendingIntent(R.id.play, pendingIntent2);

        RemoteViews bigRemoteView = new RemoteViews(getPackageName(), R.layout.custom_big_notification);

        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCustomContentView(remoteViews)
                .setCustomBigContentView(bigRemoteView)
                .setAutoCancel(false);

        Notification notification = builder2.build();
        notificationManager.notify(2, notification);

//
//  notificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
//                .setContentTitle("Content Title")
//                .setSmallIcon(R.drawable.ic_launcher)
//                .setContentText("Content Text")
//                .setContent(notificationView)
//                .setPriority(Integer.MAX_VALUE);
//        mBuilder.setAutoCancel(false);
//        Intent notificationIntent = new Intent(context, MainActivity.class);
//        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent contentIntent = PendingIntent.getActivity(context, notifyId, notificationIntent, FLAG_UPDATE_CURRENT);
//        mBuilder.setContentIntent(contentIntent);
//
//        notification = mBuilder.build();
//        notification.bigContentView = notificationView;
//        notification.when = System.currentTimeMillis();
//
//        notificationView.setTextViewText(R.id.tvMode, mode);
//        notificationView.setTextViewText(R.id.tvTime, time);
//        notificationView.setTextViewText(R.id.tvLevel, "" + level + "%");
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notification.flags = Notification.FLAG_ONGOING_EVENT;
//        manager.notify(notifyId, notification);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show:
                //when; on going flag
                notification1.when = System.currentTimeMillis();
                notification1.flags = Notification.FLAG_ONGOING_EVENT;
                notificationManager.notify(1, notification1);
                break;
            case R.id.cancel:
                notificationManager.cancel(200);
                break;
        }
    }
}
