package com.frontline.pingdomalarm.services;

import android.content.Context;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.frontline.pingdomalarm.domain.AlarmTrigger;
import com.frontline.pingdomalarm.intents.AlarmTonePlayer;
import com.frontline.pingdomalarm.util.AlarmTriggerManager;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ivan on 4/24/15.
 */
public class NotificationListenerService extends android.service.notification.NotificationListenerService{
    private final Logger log = Logger.getLogger(NotificationListenerService.class);

    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        log.info("notification recieved!");
        String ticker = sbn.getNotification().tickerText.toString();

        Bundle extras = sbn.getNotification().extras;

        String title = extras.getString("android.title");
        String text = extras.getCharSequence("android.text").toString();

        List<AlarmTrigger> alarmTriggers = AlarmTriggerManager.getAllAlarmTriggers();


        for (String notificationText: Arrays.asList(new String[]{ticker, title, text}))
        {
            Log.i("looping", "looping through"+notificationText);
            for (AlarmTrigger alarmTrigger: alarmTriggers){
                if (notificationText.contains(alarmTrigger.getMatchText())) {
                    log.info("notification read: "+ notificationText);
                    if (!AlarmTonePlayer.isAlarmOn()){
                        AlarmTonePlayer.soundAlarm(this);
                    }
                    return;
                }
            }
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sb) {
        super.onCreate();
        context = getApplicationContext();
    }
 }
