package com.example.fiscalitics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    boolean animate = false;

    /* using a TAG for logging. thought it would be useful for debugging ~_~ */
    private static final String TAG = MainActivity.class.getCanonicalName();
    private int id = 0; //Add this to sharedpreferences eventually
    private String data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etView = new EditText(this);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animate = ViewAnimation.animateFab(fab, !animate); //Indicate button clicked

                //Launch dialog to enter Transaction information
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("New transaction").setMessage("Enter a new transaction you made...");
                etView.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);

                //this is used to remove the edit text view that is created otherwise the app crashes.
                if(etView.getParent() != null) {
                    ((ViewGroup)etView.getParent()).removeView(etView);
                }
                //setting the view in the dialog so the user can enter data.
                builder.setView(etView);

                //simple button the user can press to submit their entry and exit the dialog.
                builder.setPositiveButton("+", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //getting the data entered into the view
                        if (etView.getText().toString() != null) data = etView.getText().toString();
                        else return; //can't let them enter null!
                        Intent theIntent = new Intent();

                        //Here is some code I added in to make the data easier
                        //to store with SharedPreferences. --- Love, John
                        id++;
                        Transaction myTransaction = new Transaction(Float.parseFloat(data),
                                Integer.toString(id), MainActivity.this);
                        myTransaction.Commit();

                        Log.v(TAG, "Transaction stored, value:" +
                                myTransaction.getValue() + " ... ID:" + myTransaction.getId());

                        theIntent.putExtra("data",data); //putting the data passed from the dialog view into the intent.
                        etView.setText(null);//erasing the data from the view
                        Toast.makeText(getApplicationContext(), //debugging and whatnot
                                "the data is " + data, Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog a = builder.create();
                a.show();
            }
        });

        MyListFragment listfrag = new MyListFragment();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.add(R.id.listFragmentContainer,listfrag,TAG);
        trans.commit();

    }
}

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