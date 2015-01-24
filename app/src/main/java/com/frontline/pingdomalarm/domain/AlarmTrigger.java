package com.frontline.pingdomalarm.domain;

import android.content.Context;

import com.orm.SugarRecord;

/**
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
}
