package com.frontline.pingdomalarm.util;

import com.frontline.pingdomalarm.domain.AlarmTrigger;

import java.util.List;

/**
 * Created by frontline on 1/24/15.
 */
public class AlarmTriggerManager {

    public static void createAlarmTrigger(String name){
        AlarmTrigger trigger = new AlarmTrigger(name);
        trigger.save();
    }

    public static List<AlarmTrigger> getAllAlarmTriggers(){
        return AlarmTrigger.listAll(AlarmTrigger.class);
    }
}
