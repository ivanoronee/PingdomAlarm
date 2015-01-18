package com.frontline.pingdomalarm.services;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class InstantMessenger extends AccessibilityService {

    public boolean isInit = false;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i("notfication", "entered onAccessibilityEvent with: ");
        if (event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            //Do something, eg getting packagename
            final String packagename = String.valueOf(event.getPackageName());
            Log.i("notfication", "notification: "+event.getText().toString());
        }
    }

    @Override
    protected void onServiceConnected() {
        Log.i("ivan", "notification onservice sdconnected method called");
        if (isInit) {
            return;
        }
        Log.i("ivan", "notification proceeding");

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
}