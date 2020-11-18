package com.example.fiscalitics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements MyListFragment.MyListFragmentListener {

    boolean animate = false;
    float x1, x2, y1, y2;

    /* using a TAG for logging. thought it would be useful for debugging ~_~ */
    private static final String TAG = MainActivity.class.getCanonicalName();
    private int id = 0;      //Add this to sharedpreferences eventually
    private int count = 0;   //count up items in storage
    private float total = 0; //add up all money spent
    private String data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.enterl, R.anim.exitl);

        final EditText etView = new EditText(this);
        final SharedPreferences sp = getSharedPreferences("TLog", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        editor.putInt("count", count);
        editor.apply();

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

                final MyListFragment listfrag = new MyListFragment();
                final FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                trans.add(R.id.listFragmentContainer,listfrag,TAG);
                trans.commit();

                //simple button the user can press to submit their entry and exit the dialog.
                builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        if (!etView.getText().toString().equals("")) {
                            //getting the data entered into the view
                            data = etView.getText().toString();
                        }
                        //can't let them enter null!
                        else{return;}
                        Intent theIntent = new Intent();

                        //Here is some code I added in to make the data easier
                        //to store with SharedPreferences. --- Love, John0
                        id++;
                        Transaction myTransaction = new Transaction(Float.parseFloat(data),
                                Integer.toString(id), MainActivity.this);
                        myTransaction.Commit();

                        Log.v(TAG, "Transaction stored, value:" +
                                myTransaction.getValue() + " ... ID:" + myTransaction.getId());

                        listfrag.listItems.add("Transaction " + id + ": $" + data);
                        listfrag.adapter.notifyDataSetChanged();

                        theIntent.putExtra("data",data);        //putting the data passed from the dialog view into the intent.
                        etView.setText(null);                   //erasing the data from the view
                        Toast.makeText(getApplicationContext(), //debugging and whatnot
                                "Purchase or Transaction of $ " + data + " added"
                                , Toast.LENGTH_SHORT).show();

                        //Store count to SharedPreferences
                        count++;
                        editor.putInt("count", count);
                        editor.apply();

                        //store total to sharedpreferences
                        total += Float.parseFloat(data);
                        editor.putFloat("total", total);
                        editor.apply();

                    }
                });
                AlertDialog a = builder.create();
                a.show();
            }
        });

    }

    //Launch a new activity when the user swipes right(Probably will be a summary page)
    public boolean onTouchEvent(MotionEvent touchEvent){
        //Get swipe data
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x2 < x1){
                    //Launch new page
                    Intent i = new Intent(MainActivity.this, Summary.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }

    //need to implement this listener function to receive the item otherwise the app breaks. bummer, i know.
    @Override
    public void onListItemSelected(String input) {
        //when the list item is selected, we will do something with it.
        //but what do i know im just a kid.
    }
}
