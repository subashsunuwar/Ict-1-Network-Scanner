package com.example.subas.networkscanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button networkButton;
    Button btnClientlist,btnPing,btnTraceroute,btnNetworkInfo;
    private static final int RESULT_SETTINGS = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        networkButton = findViewById(R.id.btnnetworkInfo); //connects with network button
        networkButton.setOnClickListener(new View.OnClickListener() {//click event trigger
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),dhcpInfo.class);//directs to network activity
                startActivity(intent);// go to this intent
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
}
