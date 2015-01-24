package com.frontline.pingdomalarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.frontline.pingdomalarm.domain.AlarmTrigger;
import com.frontline.pingdomalarm.util.AlarmTriggerManager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final ListView alarmTriggerList = (ListView) findViewById(R.id.alarmTriggers);
        final List<AlarmTrigger> alarmTriggers = AlarmTriggerManager.getAllAlarmTriggers();
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, alarmTriggers);
        alarmTriggerList.setAdapter(adapter);

        alarmTriggerList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                final AlarmTrigger item = (AlarmTrigger) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                adapter.remove(item);
                                alarmTriggers.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
                return true;
            }

        });
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
                    AlarmTriggerManager.createAlarmTrigger(triggerText);
                }
            });

            builder.setNegativeButton("Cancel",null);

            builder.create().show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class StableArrayAdapter extends ArrayAdapter<AlarmTrigger> {

        List<AlarmTrigger> items = new ArrayList<AlarmTrigger>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<AlarmTrigger> objects) {

            super(context, textViewResourceId, objects);
            items = objects;
        }

        @Override
        public long getItemId(int position) {
            //TODO remove this ugly hack of the ifs
            if (position == items.size() && position != 0){
                position--;
            }
            if (position == 0){
                return 0L;
            }
            return items.get(position).getId();
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
