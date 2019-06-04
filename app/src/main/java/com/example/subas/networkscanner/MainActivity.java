package com.example.subas.networkscanner;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
        Button btnPing,btnTraceroute,btnNetworkInfo;
    private String m_Text = "";
    private static final int RESULT_SETTINGS = 1;

    ProgressDialog ringProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnNetworkInfo = findViewById(R.id.btnnetworkInfo); //connects with network button
        btnNetworkInfo.setOnClickListener(new View.OnClickListener() {//click event trigger
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), dhcpInfo.class);//directs to network activity
                startActivity(intent);// go to this intent
            }
        });
        btnPing = (Button) findViewById(R.id.btnPing);
        btnTraceroute = (Button) findViewById(R.id.btnTracer);


//creating new object of view -- inner class
        btnPing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("my name is subash");
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Ping");
                System.out.println("now alert dialogue");

                // Set up the input
                final EditText input = new EditText(v.getContext());
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        //call ping function should be done by shishir
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
                builder.show();
            }
        });
        btnTraceroute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Traceroute");

                // Set up the input
                final EditText input = new EditText(v.getContext());
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        //this part to be done by shishir
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_settings:
                Intent i = new Intent(this, UserSettingActivity.class);
                startActivityForResult(i, RESULT_SETTINGS);
                break;

        }

        return true;
    }

    private class ping extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String str = "";
            try {
                Process process = Runtime.getRuntime().exec(
                        "/system/bin/ping -c 8 " + urls[0]);
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        process.getInputStream()));
                int i;
                char[] buffer = new char[4096];
                StringBuffer output = new StringBuffer();
                while ((i = reader.read(buffer)) > 0)
                    output.append(buffer, 0, i);
                reader.close();

                // body.append(output.toString()+"\n");
                str = output.toString();
                // Log.d(TAG, str);
            } catch (IOException e) {
                // body.append("Error\n");
                e.printStackTrace();
            }
            ringProgressDialog.dismiss();
            return str;
        }

        @Override
        protected void onPreExecute() {
            ringProgressDialog = ProgressDialog.show(MainActivity.this, "Please wait ...", "Ping in progress...", true);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
            ringProgressDialog.dismiss();
            Intent intent = new Intent(getBaseContext(), PingActivity.class);
            System.out.println("data present >>>>>>>>>>>>>>>>> "+ result);
            intent.putExtra("data", result);
            startActivity(intent);
            //finish();
        }
    }
}
