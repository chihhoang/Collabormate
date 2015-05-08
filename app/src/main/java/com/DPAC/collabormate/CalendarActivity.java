package com.DPAC.collabormate;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class CalendarActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ExtendedCalendarView calendar = (ExtendedCalendarView) findViewById(R.id.calendar);
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
        int startDayYear = 0;
        int startDayMonth = 0;
        int startDayDay = 0;
        int startTimeHour = 0;
        int startTimeMin = 0;

        int endDayYear = 0;
        int endDayMonth = 0;
        int endDayDay = 0;
        int endTimeHour = 0;
        int endTimeMin = 0;

        ContentValues values = new ContentValues();
        values.put(CalendarProvider.COLOR, Event.COLOR_RED);
        values.put(CalendarProvider.DESCRIPTION, "Some Description");
        values.put(CalendarProvider.LOCATION, "Some location");
                values.put(CalendarProvider.EVENT, "Event name");

                        Calendar cal = Calendar.getInstance();

        cal.set(startDayYear, startDayMonth, startDayDay, startTimeHour, startTimeMin);
        values.put(CalendarProvider.START, cal.getTimeInMillis());
        byte[] julianDay = new byte[0];
        values.put(CalendarProvider.START_DAY, julianDay);
        TimeZone tz = TimeZone.getDefault();

        cal.set(endDayYear, endDayMonth, endDayDay, endTimeHour, endTimeMin);
        int endDayJulian = Time.getJulianDay(cal.getTimeInMillis(), TimeUnit.MILLISECONDS.toSeconds(tz.getOffset(cal.getTimeInMillis())));

        values.put(CalendarProvider.END, cal.getTimeInMillis());
        values.put(CalendarProvider.END_DAY, endDayJulian);

        Uri uri = getContentResolver().insert(CalendarProvider.CONTENT_URI, values);
    }

}
