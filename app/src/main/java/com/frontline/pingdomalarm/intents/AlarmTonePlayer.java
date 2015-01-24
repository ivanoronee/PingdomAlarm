package com.frontline.pingdomalarm.intents;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.frontline.pingdomalarm.AlarmActivity;
import com.frontline.pingdomalarm.R;
import com.frontline.pingdomalarm.util.StringConstants;


public class AlarmTonePlayer extends IntentService {
    public static final int ALARM_NOTIFICATION_ID = 1;
    private static MediaPlayer mediaPlayer;


    public AlarmTonePlayer() {
        super("AlarmTonePlayer");
    }

    public static void soundAlarm(Context context) {
        Intent intent = new Intent(context, AlarmTonePlayer.class);
        intent.setAction(StringConstants.ACTION_SOUNND_ALARM);
        context.startService(intent);
    }

    public static void stopAlarm() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (StringConstants.ACTION_SOUNND_ALARM.equals(action)) {
                ;
                handleSoundAlarm();
            }
        }
    }

    private void handleSoundAlarm() {
        mediaPlayer = MediaPlayer.create(this, R.raw.pingdom_alarm);
        mediaPlayer.start();
        createAlarmRunningNotification();
    }

    private void createAlarmRunningNotification() {
        Notification.Builder mBuilder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(StringConstants.ALARM_NOTIFICATION_HEADER)
                .setContentText("You gotta check the cloud!!");

        Intent resultIntent = new Intent(this, AlarmActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(AlarmActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(ALARM_NOTIFICATION_ID, mBuilder.build());
    }
}
