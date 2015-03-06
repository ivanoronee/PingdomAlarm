package com.frontline.pingdomalarm;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

/**
 * Created by frontline on 3/5/15.
 */
@ReportsCrashes(formKey = "", // will not be used
        mailTo = "ivan@frontlinesms.com",
        customReportContent = { ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME,
                ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL, ReportField.CUSTOM_DATA,
                ReportField.STACK_TRACE, ReportField.APPLICATION_LOG},
        mode = ReportingInteractionMode.SILENT,
        applicationLogFile = "pingdom_alarm.log",
        applicationLogFileLines = 150)
public class PingDomAlarm  extends com.orm.SugarApp {
    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
        ConfigureLog4J.configure(this);
    }
}
