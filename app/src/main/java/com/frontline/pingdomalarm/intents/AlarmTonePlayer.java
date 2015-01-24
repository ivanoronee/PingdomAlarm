package com.frontline.pingdomalarm.intents;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
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


    public static void soundAlarm(Context context) {
        Intent intent = new Intent(context, AlarmTonePlayer.class);
        intent.setAction(StringConstants.ACTION_SOUNND_ALARM);
        context.startService(intent);
    }

    public static void stopAlarm(){
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }
    }

    public AlarmTonePlayer() {
        super("AlarmTonePlayer");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (StringConstants.ACTION_SOUNND_ALARM.equals(action)) {;
                handleSoundAlarm();
            }
        }
    }

    private void handleSoundAlarm() {
        Log.i("alarm player", "starting playback");
        mediaPlayer = MediaPlayer.create(this, R.raw.pingdom_alarm);

        mediaPlayer.start();

        String songName = "server down";
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0,
                new Intent(getApplicationContext(), AlarmActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification();
        notification.tickerText = "pingdom alert!!";
        notification.icon = R.drawable.ic_launcher;
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        notification.setLatestEventInfo(getApplicationContext(), "PingdomAlarm",
                "Playing: " + songName, pi);

        startForeground(ALARM_NOTIFICATION_ID, notification);
    }
}
