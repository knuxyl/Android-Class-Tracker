package edu.wgu.jameswatsonabm2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends android.content.BroadcastReceiver {
    static int notificationId;
    String channel = "notifications";

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel(context, channel);
        String date = intent.getStringExtra("date");
        String type = intent.getStringExtra("title");
        String title = type + " " + date;
        Notification notification = new NotificationCompat.Builder(context, channel)
                .setSmallIcon(R.drawable.ic_wgu)
                .setContentText(intent.getStringExtra("message"))
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId++, notification);
    }

    public void createNotificationChannel(Context context, String id) {
        NotificationChannel channel = new NotificationChannel(id, "Notifications", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Notifications for courses and assessments");
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}