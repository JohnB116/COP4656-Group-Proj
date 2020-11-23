package com.example.fiscalitics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import java.util.Calendar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements MyListFragment.MyListFragmentListener {

    boolean animate = false;
    float x1, x2, y1, y2;

    /* using a TAG for logging. thought it would be useful for debugging ~_~ */
    private static final String TAG = MainActivity.class.getCanonicalName();
    private int id = 0;      //Add this to sharedpreferences eventually
    private int count;   //count up items in storage
    private float total = 0; //add up all money spent
    private float average = 0.0f;

    private String data = null;

    //is it ok if i make this a gwobal vawiable ? UwU

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.enterl, R.anim.exitl);

        //this edit text is used for entering the amount of $$CASHMONEY$$ you spent *$wag*
        final EditText etView = new EditText(this);
        //radio buttons for selecting what category your spending falls under.
        final RadioGroup radioGroup = new RadioGroup(this);
        final RadioButton btnEntertainment = new RadioButton(this);
        final RadioButton btnFood = new RadioButton(this);
        final RadioButton btnTravel = new RadioButton(this);
        btnEntertainment.setText("Entertainment");
        btnFood.setText("Food");
        btnTravel.setText("Travel");

        final SharedPreferences sp = getSharedPreferences("TLog", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        //Get total number of transactions
        if(sp.getInt("count", -1) == -1) {
            editor.putInt("count", 0);
            editor.apply();
        }
        else{ count = sp.getInt("count", -1); }

        //Get total spent
        if(sp.getFloat("total", -1.0f) == -1.0f){}
        else{ total = sp.getFloat("total", -1.0f); }

        //Get list of transactions
        final Set<String> theSet = new HashSet<>();
        if(sp.getStringSet("theSet", null) == null){
            //do nothing
        }
        else{
            theSet.addAll(sp.getStringSet("theSet", null));
        }

        if(sp.getFloat("average", -1.0f) == -1.0f){
            //
        }
        else{
            average = sp.getFloat("total", -1.0f)/sp.getInt("count", 1);
            editor.putFloat("average", average);
            editor.apply();
        }

        final MyListFragment listfrag = new MyListFragment();
        final FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.add(R.id.listFragmentContainer,listfrag,TAG);
        trans.commit();

        //This works for refreshing the list on restart,
        //I don't know why, but we'll work with it
        listfrag.adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                listfrag.listItems);
        //refresh listview on activity restart
        if(theSet.size() > 0) {
            listfrag.listItems.addAll(theSet);
            listfrag.adapter.notifyDataSetChanged();
        }

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
                builder.setView(etView);//setting the view in the dialog so the user can enter data.

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

                        //Add data to the list and display it
                        listfrag.listItems.add("$" + data + " " + Calendar.getInstance().getTime());
                        listfrag.adapter.notifyDataSetChanged();

                        theIntent.putExtra("data",data);        //putting the data passed from the dialog view into the intent.
                        etView.setText(null);                   //erasing the data from the view
                        Toast.makeText(getApplicationContext(), //debugging and whatnot
                                "Purchase or Transaction of $ " + data + " added"
                                , Toast.LENGTH_SHORT).show();

                        //Store count to SharedPreferences
                        count++;
                        editor.putInt("count", count);
                        //store total to sharedpreferences
                        total += Float.parseFloat(data);
                        editor.putFloat("total", total);
                        //add the transaction to the list and commit
                        theSet.add("$" + data + " " + Calendar.getInstance().getTime());
                        editor.putStringSet("theSet", theSet);
                        //Store average to sharedpreferences
                        average = sp.getFloat("total", 0.0f)/sp.getInt("count", 1);
                        editor.putFloat("average", average);
                        editor.apply();

                        //Launch dialog to enter Transaction information
                        final AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                        builder2.setTitle("Purchase Type").setMessage("What kind of purchase was this?");
                        //this is used to remove the view that is created otherwise the app doesn't work.
                        if(btnFood.getParent() != null) {
                            ((ViewGroup)btnFood.getParent()).removeView(btnFood);
                        }
                        radioGroup.addView(btnFood);
                        if(btnEntertainment.getParent() != null) {
                            ((ViewGroup)btnEntertainment.getParent()).removeView(btnEntertainment);
                        }
                        radioGroup.addView(btnEntertainment);
                        if(btnTravel.getParent() != null) {
                            ((ViewGroup)btnTravel.getParent()).removeView(btnTravel);
                        }
                        radioGroup.addView(btnTravel);
                        builder2.setView(radioGroup);
                        if(radioGroup.getParent() != null) {
                            ((ViewGroup)radioGroup.getParent()).removeView(radioGroup);
                        }
                        builder2.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        AlertDialog b = builder2.create();
                        b.show();

                    }
                });
                AlertDialog a = builder.create();
                a.show();

            }
        });

    }

    //Launch the Summary activity when the user swipes right
    public boolean onTouchEvent(MotionEvent touchEvent) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_exit_id:
                finish();
                System.exit(0);
                break;
        }
        return true;
    }

    //since shared prefs. cannot be global, i gotta make a stupid function to handle clearing the data
    public int wipe(SharedPreferences sp) {
        if (sp.edit().clear().commit()) return 0;
        else return -1;
    }
}
