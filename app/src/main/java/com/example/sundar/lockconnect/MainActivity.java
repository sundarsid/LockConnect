package com.example.sundar.lockconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    String ipAddress;
    EditText ipAdd;
    ArrayList<String> lockArray;
    private ArrayAdapter<String> lockListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("Hi", "Creating");
        ipAdd = (EditText)findViewById(R.id.editText);

    }

    @Override
    public void onResume(){
        super.onResume();
        lockArray = new ArrayList<String>();

        SharedPreferences sp = this.getSharedPreferences("lock_list",this.MODE_PRIVATE);
        lockArray.clear();
        int size = sp.getInt("Status_size", 0);

        for(int i=0;i<size;i++)
        {
            lockArray.add(sp.getString("Status_" + i, null));
        }


        lockListAdapter = new ArrayAdapter<String>(this, R.layout.lock_list, R.id.lock_list_textView, lockArray);
        ListView listView = (ListView) this.findViewById(R.id.lock_list);
        listView.setAdapter(lockListAdapter);
        Log.e("Hi","Resuming");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            startActivity(new Intent(this, AddLock.class));
            return true;
        }
        if (id == R.id.action_configure) {
            startActivity(new Intent(this, Configure.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setIP(View v) {

        ipAddress=ipAdd.getText().toString().trim();
    }

    public void button_on(View v) {
        URI url;
        try {
            url = new URI("http://"+ipAddress+"/?pin=13");
            sendData msendData = new sendData();
            msendData.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void button_off(View v) {
        URI url;
        try {
            url = new URI("http://192.168.4.1/?pin=13");
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

                int timeoutConnection = 3000;
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

                int timeoutSocket = 5000;
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

                HttpClient httpclient = new DefaultHttpClient(httpParameters);

                HttpGet getRequest = new HttpGet();
                getRequest.setURI(url);
                HttpResponse response = httpclient.execute(getRequest);
                Log.v("Hi",url.toString());

                InputStream content = null;
                content = response.getEntity().getContent();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        content
                ));
                serverResponse = in.readLine();

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

            return null;
        }


        }
