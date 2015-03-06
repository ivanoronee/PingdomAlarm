package com.frontline.pingdomalarm;

import android.content.Context;

import org.apache.log4j.Level;

import java.io.File;

import de.mindpipe.android.logging.log4j.LogConfigurator;

/**
 * Created by frontline on 2/26/15.
 */
public class ConfigureLog4J {
    public static void configure(Context context) {
        String LOG_FILE_NAME = context.getFilesDir()+ File.separator + "pingdom_alarm.log";
        final LogConfigurator logConfigurator = new LogConfigurator();
        logConfigurator.setFileName(LOG_FILE_NAME);
        logConfigurator.setRootLevel(Level.DEBUG);
        // Set log level of a specific logger
        logConfigurator.setLevel("org.apache", Level.ERROR);
        logConfigurator.configure();
    }
}
