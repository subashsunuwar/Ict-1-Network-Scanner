package com.jcub.networkscanner;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jcub.networkscanner.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class TracerouteActivity extends AppCompatActivity {
    TextView tv;
    private ProgressDialog ringProgressDialog;
    private EditText url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traceroute);
        tv = findViewById(R.id.tracerouteResult);
        url = findViewById(R.id.url);
        Button btnTracer = findViewById(R.id.btnTracer);
        btnTracer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String host = url.getText().toString();
                new getData().execute(host);
            }
        });
    }

    private class getData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            try {
                if (urls[0].equals("")) {
                    urls[0] = "www.google.com";
                }
                URL url = new URL("http://" + showUserSettings() + ":8089/traceroute?ip=" + urls[0]);
                URLConnection tc = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        tc.getInputStream()));
                String line;
                StringBuilder data = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                    data.append(line).append("\n");
                }
                return data.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "NULL";
        }

        @Override
        protected void onPreExecute() {
            ringProgressDialog = ProgressDialog.show(TracerouteActivity.this, "Please wait ...", "Getting Data from Server...", true);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
            ringProgressDialog.dismiss();
            tv.setText(result);
        }
    }

    public String showUserSettings() {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        return sharedPrefs.getString("serverIpKey", "null");
    }
}
