package com.frontline.pingdomalarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.frontline.pingdomalarm.util.AlarmTriggerManager;

import java.lang.reflect.Method;


public class HomeActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_add_trigger){
            AlarmTriggerManager alarmTriggerManager = new AlarmTriggerManager();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add alarm trigger");
            builder.setMessage("Enter text that will be matched to trigger alarm");
            final EditText triggerTextInputfield = new EditText(this);
            builder.setView(triggerTextInputfield);
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String triggerText = triggerTextInputfield.getText().toString();
                    Log.i("saving new match text",triggerText);
                    AlarmTriggerManager.createAlarmTrigger(triggerText);
                }
            });

            builder.setNegativeButton("Cancel",null);

            builder.create().show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
