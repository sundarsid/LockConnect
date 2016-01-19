package com.example.sundar.lockconnect;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

public class MainActivity extends AppCompatActivity {
    EditText editText = (EditText)findViewById(R.id.editText);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void button_on(View v) {
        URI url;
        try {
            url = new URI("http://192.168.1.169/?pin=13");
            sendData msendData = new sendData();
            msendData.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void button_off(View v) {
        URI url;
        try {
            url = new URI("http://192.168.1.169/?pin=13");
            sendData msendData = new sendData();
            msendData.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    class sendData extends AsyncTask<URI, Void, Void>{

        @Override
        protected Void doInBackground(URI... params) {
            URI url = params[0];
            String serverResponse;
           // HttpURLConnection urlConnection = null;

            try {
                HttpParams httpParameters = new BasicHttpParams();
// Set the timeout in milliseconds until a connection is established.
// The default value is zero, that means the timeout is not used.
                int timeoutConnection = 3000;
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
// Set the default socket timeout (SO_TIMEOUT)
// in milliseconds which is the timeout for waiting for data.
                int timeoutSocket = 5000;
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

                HttpClient httpclient = new DefaultHttpClient(httpParameters);
                //URI website = new URI("http://"+ipAddress+":"+portNumber+"/?"+parameterName+"="+parameterValue);
                HttpGet getRequest = new HttpGet();
                getRequest.setURI(url);
                HttpResponse response = httpclient.execute(getRequest);
                Log.v("Hi",url.toString());
                // execute the request
                // get the ip address server's reply
                InputStream content = null;
                content = response.getEntity().getContent();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        content
                ));
                serverResponse = in.readLine();
                // Close the connection
                content.close();
            } catch (ClientProtocolException e) {
                // HTTP error
                serverResponse = e.getMessage();
                e.printStackTrace();
                Log.v("Hi", e.toString());
            } catch (IOException e) {
                // IO error
                serverResponse = e.getMessage();
                e.printStackTrace();
                Log.v("Hi", e.toString());
            }
            // return the server's reply/response text
            //return serverResponse;
            return null;
        }


        }
