package com.example.fiscalitics;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        int i = 1; //Arbitrary ID
        String num = Integer.toString(i);

        Transaction t = new Transaction(14.47f, num, this);
        t.Commit();
        Log.v("Log", "transaction stored");
        SharedPreferences sp = getSharedPreferences("TLog", Activity.MODE_PRIVATE);
        float the_value = sp.getFloat(num, 0.0f);
        Log.v("Log", "The value stored is" + Float.toString(the_value));

        */ //Just an example of adding a transaction to sharedpreferences with the class
           // I made, delete this at your liking




    }
}