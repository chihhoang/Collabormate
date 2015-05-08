package com.DPAC.collabormate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.parse.*;


public class MainActivity extends ActionBarActivity {
    Button btn_Login = null;
    Button btn_Signup = null;
    Button btn_Forget = null;
    private EditText mUserNameEditText;
    private EditText mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.initialize(this, "9Zwa3ybvYWhz5MLHXyuhofJRVUVgU848ln5aUeyW",
                "HMz4vwC3GmyyZPzsFuh5eBWVUkhS5m0OJpkKilKb");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        cd = new ConnectionDetector(getApplicationContext());

        btn_Login = (Button) findViewById(R.id.btn_login);
        btn_Signup = (Button) findViewById(R.id.btn_signup);
        btn_Forget = (Button) findViewById(R.id.btn_forget);
        mUserNameEditText = (EditText) findViewById(R.id.usernameEdit)
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return(super.onCreateOptionsMenu(menu));
    }

    @Override
    public void onResume() {
        super.onResume();
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
}