package com.example.fiscalitics;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    boolean animate = false;

    /* using a TAG for logging. thought it would be useful for debugging ~_~ */
    private static final String TAG = MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etView = new EditText(this);

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
                builder.setTitle("Money").setMessage("So, you like spending money, do ya? Well STOP IT");
                etView.setInputType(InputType.TYPE_CLASS_NUMBER);
                
                if(etView.getParent() != null) {
                    ((ViewGroup)etView.getParent()).removeView(etView);
                    Log.v(TAG,"removing the view");
                }
                builder.setView(etView);
                Log.v(TAG,"new view created");
                builder.setPositiveButton("+", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog a = builder.create();
                a.show();
            }
        });
    }
}