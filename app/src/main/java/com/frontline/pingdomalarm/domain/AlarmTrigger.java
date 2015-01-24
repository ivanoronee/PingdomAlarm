package com.frontline.pingdomalarm.domain;

import com.orm.SugarRecord;

/**
 * Domain object to represent a trigger for the alarm.
 *
 * Created by frontline on 1/24/15.
 */
public class AlarmTrigger extends SugarRecord<AlarmTrigger> {

    String matchText;

    /**
     * Added because required by SugarORM.
     */
    public AlarmTrigger() {
    }

    public AlarmTrigger(String matchText){
        this.matchText = matchText;
    }

    @Override
    public String toString(){
        return matchText;
    }
}
