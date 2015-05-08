package com.DPAC.collabormate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class CalendarActivity extends Activity {
    private TaskDataSource datasource;
    private String taskName = "";
    private ArrayAdapter<Task> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        datasource = new TaskDataSource(this);
        datasource.open();

        List<Task> values = datasource.getAllTasks();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        adapter = new ArrayAdapter<Task>(this,
                android.R.layout.simple_list_item_1, values);

        ListView lv = (ListView)findViewById(R.id.taskList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                //On click check marks task.
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);

        return(super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.about:
                // Use the Builder class for convenient dialog construction
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle(R.string.about)
                        .setMessage(R.string.dialog_about)
                        .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();    // User presses OK
                            }
                        })
                        .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();    // User presses Cancel
                            }
                        });
                // Create the AlertDialog object and return it
                AlertDialog dialog = builder.create();

                builder.show();
                return(true);

            case R.id.help:
                // Use the Builder class for convenient dialog construction
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setCancelable(true);
                builder1.setTitle(R.string.help)
                        .setMessage(R.string.dialog_help)
                        .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();    // User presses OK
                            }
                        })
                        .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();    // User presses Cancel
                            }
                        });
                // Create the AlertDialog object and return it
                AlertDialog dialog1 = builder1.create();

                builder1.show();
                return(true);

            case R.id.settings:
                startActivity(new Intent(this, Preferences.class));
                return(true);
        }

        return super.onOptionsItemSelected(item);
    }

    public void addTask(View view) {
        String person = "Steve";
        int startDayYear = 2015;
        int startDayMonth = 5;
        int startDayDay = 8;
        int startTimeHour = 10;
        int startTimeMin = 30;

        int endDayYear = 2015;
        int endDayMonth = 8;
        int endDayDay = 10;
        int endTimeHour = 10;
        int endTimeMin = 0;

        ContentValues values = new ContentValues();
        values.put(CalendarProvider.COLOR, Event.COLOR_RED);
        values.put(CalendarProvider.DESCRIPTION, "Some Description");
        values.put(CalendarProvider.LOCATION, "Some location");
                values.put(CalendarProvider.EVENT, "Event name");

                        Calendar cal = Calendar.getInstance();

        cal.set(startDayYear, startDayMonth, startDayDay, startTimeHour, startTimeMin);
        values.put(CalendarProvider.START, cal.getTimeInMillis());

        TimeZone tz = TimeZone.getDefault();
        int julianDay = Time.getJulianDay(cal.getTimeInMillis(), TimeUnit.MILLISECONDS.toSeconds(tz.getOffset(cal.getTimeInMillis())));

        values.put(CalendarProvider.START_DAY, julianDay);

        cal.set(endDayYear, endDayMonth, endDayDay, endTimeHour, endTimeMin);
        int endDayJulian = Time.getJulianDay(cal.getTimeInMillis(), TimeUnit.MILLISECONDS.toSeconds(tz.getOffset(cal.getTimeInMillis())));

        values.put(CalendarProvider.END, cal.getTimeInMillis());
        values.put(CalendarProvider.END_DAY, endDayJulian);

        Uri uri = getContentResolver().insert(CalendarProvider.CONTENT_URI, values);


        //Adds task to list at bottom
        @SuppressWarnings("unchecked")
        final AlertDialog.Builder[] builder = {new AlertDialog.Builder(this)};
        builder[0].setTitle("New Task:");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder[0].setView(input);

        // Add new course
        builder[0].setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                taskName = input.getText().toString();
                Task task = null;
                task = datasource.createTask(taskName);
                adapter.add(task);
                adapter.notifyDataSetChanged();
            }
        });
        //Cancel
        builder[0].setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder[0].show();
    }

}
