package com.frontline.pingdomalarm.util;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.frontline.pingdomalarm.HomeActivity;

/**
 * Created by frontline on 1/18/15.
 */
public class NotificationsUtil {

    private static final String TAG = NotificationsUtil.class.getSimpleName();

    public static boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        final String service = "com.frontline.pingdomalarm/com.frontline.pingdomalarm.services.InstantMessenger";
        boolean accessibilityFound = false;
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, "Error finding setting: "
                    + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                TextUtils.SimpleStringSplitter splitter = mStringColonSplitter;
                splitter.setString(settingValue);
                while (splitter.hasNext()) {
                    String accessabilityService = splitter.next();
                    if (accessabilityService.equalsIgnoreCase(service)) {
                        return true;
                    }
                }
            }
        } else {
            Log.v(TAG, "***ACCESSIBILIY IS DISABLED***");
        }
        return accessibilityFound;
    }

    public static void openDeviceAccessibilitySettings(Context context){
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        context.startActivity(intent);
    }
}
