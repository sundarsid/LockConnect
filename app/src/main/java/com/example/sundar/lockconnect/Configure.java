package com.example.sundar.lockconnect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.net.URI;

public class Configure extends AppCompatActivity {

    EditText ssid;
    EditText ssid_pass;
    String ssid_name;
    String ssidpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);

        ssid = (EditText) findViewById(R.id.ssid);
        ssid_pass = (EditText) findViewById(R.id.ssidpass);


    }

    public void sendConfig(View v) {

        ssid_name=ssid.getText().toString().trim();
        ssidpass=ssid_pass.getText().toString().trim();

        URI url;
        try {
            url = new URI("http://192.168.4.1/?pin=13&ID="+ssid_name+"&Pwd="+ssidpass+"&");
            sendData msendData = new sendData();
            msendData.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
