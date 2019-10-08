package com.jcub.networkscanner;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jcub.networkscanner.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PingActivity extends AppCompatActivity {

    TextView result;
    private ProgressDialog ringProgressDialog;

    EditText url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping);
        result = findViewById(R.id.pingResult);

        url = findViewById(R.id.url);
        Button btnPing = findViewById(R.id.btnPing);
        btnPing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String host = url.getText().toString();
                new ping().execute(host);
            }
        });
    }

    private class ping extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String str = "";
            try {
                if (urls[0].equals("")){
                    urls[0] = "www.google.com";
                }
                Process process = Runtime.getRuntime().exec(
                        "/system/bin/ping -c 8 " + urls[0]);
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        process.getInputStream()));
                int i;
                char[] buffer = new char[4096];
                StringBuilder output = new StringBuilder();
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
            ringProgressDialog = ProgressDialog.show(PingActivity.this, "Please wait ...", "Ping in progress...", true);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String resultdata) {
            //Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
            ringProgressDialog.dismiss();
            result.setText(resultdata);

        }
    }
}