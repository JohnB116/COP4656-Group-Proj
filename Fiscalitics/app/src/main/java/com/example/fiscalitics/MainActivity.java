package com.example.fiscalitics;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    boolean animate = false;

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

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate = ViewAnimation.animateFab(fab, !animate);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Money").setMessage("So, you like spending money, do ya?");
                AlertDialog a = builder.create();
                a.show();

            }
        });


    }
}