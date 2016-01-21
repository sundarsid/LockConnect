package com.example.sundar.lockconnect;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class AddLock extends AppCompatActivity {

    ArrayList<String> lockArray;
    EditText lockip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lock);

        lockip = (EditText)findViewById(R.id.lock_ip);
        lockArray = new ArrayList<String>();

        SharedPreferences sp = this.getSharedPreferences("lock_list",this.MODE_PRIVATE);
        lockArray.clear();
        int size = sp.getInt("Status_size", 0);

        for(int i=0;i<size;i++)
        {
            lockArray.add(sp.getString("Status_" + i, null));
        }



    }

    public void button_Add(View v){

        String ip;
        ip=lockip.getText().toString().trim();
        lockArray.add(ip);
        saveArray();
    }

    public boolean saveArray() {
        SharedPreferences sp = this.getSharedPreferences("lock_list",this.MODE_PRIVATE);
        SharedPreferences.Editor mEdit1 = sp.edit();
        mEdit1.putInt("Status_size", lockArray.size());

        for (int i = 0; i < lockArray.size(); i++) {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, lockArray.get(i));
        }

        return mEdit1.commit();
    }
}