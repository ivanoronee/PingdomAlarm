package com.frontline.pingdomalarm.services;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Notification;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.RemoteViews;

import com.frontline.pingdomalarm.domain.AlarmTrigger;
import com.frontline.pingdomalarm.intents.AlarmTonePlayer;
import com.frontline.pingdomalarm.util.AlarmTriggerManager;
import com.frontline.pingdomalarm.util.StringConstants;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InstantMessenger extends AccessibilityService {
    private final Logger log = Logger.getLogger(InstantMessenger.class);

    public boolean isInit = false;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            if (event.getText().toString().contains(StringConstants.ALARM_NOTIFICATION_HEADER)) {
                return;
            }
            Notification notification = (Notification) event.getParcelableData();
            List<String> notificationText = getText(notification);
            if (notificationText == null){
                log.info("notification list was null");
                return;
            }
            List<AlarmTrigger> alarmTriggers = AlarmTriggerManager.getAllAlarmTriggers();
            if (notificationText == null){

            }
            for (String string: notificationText)
            {
                log.info("looping through text: "+string);
                for (AlarmTrigger alarmTrigger: alarmTriggers){
                    if (string.contains(alarmTrigger.getMatchText())) {
                        log.info("notification read: "+getText(notification));
                        AlarmTonePlayer.soundAlarm(this);
                        return;
                    }
                }
            }

        }
    }

    @Override
    protected void onServiceConnected() {
        if (isInit) {
            return;
        }
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
        setServiceInfo(info);
        isInit = true;
    }

    @Override
    public void onInterrupt() {
        isInit = false;
    }

    public static List<String> getText(Notification notification) {
        // We have to extract the information from the view
        if (notification == null){
            Log.e("null", "notification was null");
            return null;
        }
        RemoteViews views = notification.bigContentView;
        if (views == null) views = notification.contentView;
        if (views == null) return null;

        // Use reflection to examine the m_actions member of the given RemoteViews object.
        // It's not pretty, but it works.
        List<String> text = new ArrayList<String>();
        try {
            Field field = views.getClass().getDeclaredField("mActions");
            field.setAccessible(true);

            @SuppressWarnings("unchecked")
            ArrayList<Parcelable> actions = (ArrayList<Parcelable>) field.get(views);

            // Find the setText() and setTime() reflection actions
            for (Parcelable p : actions) {
                Parcel parcel = Parcel.obtain();
                p.writeToParcel(parcel, 0);
                parcel.setDataPosition(0);

                // The tag tells which type of action it is (2 is ReflectionAction, from the source)
                int tag = parcel.readInt();
                if (tag != 2) continue;

                // View ID
                parcel.readInt();

                String methodName = parcel.readString();
                if (methodName == null) continue;

                    // Save strings
                else if (methodName.equals("setText")) {
                    // Parameter type (10 = Character Sequence)
                    parcel.readInt();

                    // Store the actual string
                    String t = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel).toString().trim();
                    text.add(t);
                }

                // Save times. Comment this section out if the notification time isn't important
                else if (methodName.equals("setTime")) {
                    // Parameter type (5 = Long)
                    parcel.readInt();

                    String t = new SimpleDateFormat("h:mm a").format(new Date(parcel.readLong()));
                    text.add(t);
                }

                parcel.recycle();
            }
        } catch (Exception e) {
            Log.e("NotificationClassifier", e.toString());
        }
        return text;
    }
}