package com.quwaysim.maathuraat.utils;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.quwaysim.maathuraat.R;
import com.quwaysim.maathuraat.ui.MaathuraatActivity;

import java.util.Calendar;


public class Notification extends BroadcastReceiver {

    private final String NOTIFICATION_CHANNEL_ID = "Al-Maathuraat";
    private NotificationManager mNotifyManager;
    private final Context mContext;
    private AlarmManager alarmManager;

    public Notification(Context context) {
        mContext = context;
    }

    private void createNotificationChannel() {

        mNotifyManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "Al-Maathuraat Notification";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setDescription("Notifications from Al-Maathuraat");
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(Color.RED);
            mNotifyManager.createNotificationChannel(channel);
        }
    }

    public void createNotification() {
        createNotificationChannel();
        PendingIntent pendingIntent =
                PendingIntent.getActivity(mContext, 24434,
                        new Intent(mContext, MaathuraatActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
//        Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher_foreground);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setContentTitle("Time for some Adhkaar")
                .setContentText("It'll only take some mins :)")
                .setSmallIcon(R.drawable.ic_logo)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        mNotifyManager.notify(17, notificationBuilder.build());
    }

    public void createAlarm() {


        alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(mContext, Notification.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notificationIntent.setClassName(mContext.getPackageName(), String.valueOf(Notification.class));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, notificationIntent, 0);


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 30);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                1000 * 60 * 20,
                pendingIntent);

        createToast("Alarm Set");
    }

    private void cancelAlarm() {
        if (alarmManager != null) {
//            alarmManager.cancel(AlarmManager.OnAlarmListener);
            createToast("Alarm Cancelled");
        }
    }

    private void createToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
