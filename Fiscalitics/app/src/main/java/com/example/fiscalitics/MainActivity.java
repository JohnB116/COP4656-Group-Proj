package com.example.fiscalitics;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
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
    private int count = 0;   //count up items in storage
    private float total = 0.0f; //add up all money spent
    private float average = 0.0f;
    private String data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String direction = getIntent().getStringExtra("direction");

        if(direction != null) {
            if (direction.equals("l")) {
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
            else{
                overridePendingTransition(R.anim.enterl, R.anim.exitl);
            }
        }

        //this edit text is used for entering the amount of $$CASHMONEY$$ you spent *$wag*
        final EditText etView = new EditText(this);
        //radio buttons for selecting what category your spending falls under.
        final RadioGroup radioGroup = new RadioGroup(this);
        final RadioButton btnEntertainment = new RadioButton(this);
        final RadioButton btnFood = new RadioButton(this);
        final RadioButton btnHome = new RadioButton(this);
        final RadioButton btnGas = new RadioButton(this);
        final RadioButton btnEdu = new RadioButton(this);
        btnEntertainment.setText("Social/Drinks/Entertainment");
        btnFood.setText("Grocery/Food");
        btnHome.setText("Home/Living/Rent");
        btnGas.setText("Gas/Automotive");
        btnEdu.setText("School or Study supplies");

        final SharedPreferences sp = getSharedPreferences("TLog", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        //Get total number of transactions
        if(sp.getInt("count", -1) == -1) { }
        else{ count = sp.getInt("count", -1); }

        //Get total spent
        if(sp.getFloat("total", -1.0f) == -1.0f){}
        else{ total = sp.getFloat("total", -1.0f); }

        //Get average transaction val
        if(sp.getFloat("average", -1.0f) == -1.0f){ }
        else{ average = sp.getFloat("total", -1.0f)/sp.getInt("count", 1); }

        final MyListFragment listfrag = new MyListFragment();
        final FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.add(R.id.listFragmentContainer,listfrag,TAG);
        trans.commit();

        //On restart of application or activity, this will refresh the ListView
        listfrag.adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                listfrag.listItems);
        Cursor cursor = getContentResolver()
                .query(TransactionMain.TransactionEntry.CONTENT_URI, null, null, null, null);
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                listfrag.listItems.add(cursor.getString(5) + ": " + cursor.getString(1));
            } while (cursor.moveToNext());
            listfrag.adapter.notifyDataSetChanged();
        }

        //Floating action button actions
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animate = ViewAnimation.animateFab(fab, !animate); //Indicate button clicked

                //Launch dialog to enter Transaction information
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("New transaction").setMessage("Enter a new transaction you made:");
                etView.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);

                //this is used to remove the edit text view that is created otherwise the app crashes.
                if(etView.getParent() != null) {
                    ((ViewGroup)etView.getParent()).removeView(etView);
                }
                builder.setView(etView);//setting the view in the dialog so the user can enter data.

                //simple button the user can press to submit their entry and exit the dialog.
                builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (!etView.getText().toString().equals("")) {
                            //getting the data entered into the view
                            data = etView.getText().toString();
                        }
                        //can't let them enter null!
                        else{return;}
                        Intent theIntent = new Intent();

                        theIntent.putExtra("data",data);        //putting the data passed from the dialog view into the intent.
                        etView.setText(null);                   //erasing the data from the view
                        Toast.makeText(getApplicationContext(), //debugging and whatnot
                                "Purchase or Transaction of $ " + data + " added"
                                , Toast.LENGTH_SHORT).show();
                        count++;

                        //Insert into the database via ContentProvider
                        final ContentValues values = new ContentValues();
                        final DateTimeFormatter date = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                        final DateTimeFormatter day = DateTimeFormatter.ofPattern("E");
                        final DateTimeFormatter time = DateTimeFormatter.ofPattern("h:mm a");
                        values.put(TransactionMain.TransactionEntry.COLUMN_VALUE, "$" + String.format("%.2f", Float.valueOf(data)));
                        values.put(TransactionMain.TransactionEntry.COLUMN_DATE, LocalDateTime.now().format(date));
                        values.put(TransactionMain.TransactionEntry.COLUMN_DAY, LocalDateTime.now().format(day));
                        values.put(TransactionMain.TransactionEntry.COLUMN_TIME, LocalDateTime.now().format(time));

                        //Store count to SharedPreferences
                        editor.putInt("count", count);
                        //store total to sharedpreferences
                        total += Float.parseFloat(data);
                        editor.putFloat("total", total);
                        //Store average to sharedpreferences
                        average = total/count;
                        editor.putFloat("average", average);
                        editor.apply();

                        //Launch dialog to enter Transaction information
                        final AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                        builder2.setTitle("Purchase Type").setMessage("What kind of purchase was this?");
                        //this is used to remove the view that is created otherwise the app doesn't work.
                        builder2.setCancelable(false);
                        if(btnFood.getParent() != null) {
                            ((ViewGroup)btnFood.getParent()).removeView(btnFood);
                        }
                        radioGroup.addView(btnFood);
                        if(btnEntertainment.getParent() != null) {
                            ((ViewGroup)btnEntertainment.getParent()).removeView(btnEntertainment);
                        }
                        radioGroup.addView(btnEntertainment);
                        if(btnEdu.getParent() != null) {
                            ((ViewGroup)btnEdu.getParent()).removeView(btnEdu);
                        }
                        radioGroup.addView(btnEdu);
                        if(btnGas.getParent() != null) {
                            ((ViewGroup)btnGas.getParent()).removeView(btnGas);
                        }
                        radioGroup.addView(btnGas);
                        if(btnHome.getParent() != null) {
                            ((ViewGroup)btnHome.getParent()).removeView(btnHome);
                        }
                        radioGroup.addView(btnHome);
                        builder2.setView(radioGroup);
                        if(radioGroup.getParent() != null) {
                            ((ViewGroup)radioGroup.getParent()).removeView(radioGroup);
                        }

                        //Check for what radiobutton is clicked once the 'add' button is clicked
                        builder2.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                    if(btnFood.isChecked()){
                                        values.put(TransactionMain.TransactionEntry.COLUMN_TYPE, "Grocery/Food");
                                    }
                                    else if(btnEntertainment.isChecked()){
                                        values.put(TransactionMain.TransactionEntry.COLUMN_TYPE, "Social/Drinks/Entertainment");
                                    }
                                    else if(btnHome.isChecked()){
                                        values.put(TransactionMain.TransactionEntry.COLUMN_TYPE, "Home/Living/Rent");
                                    }
                                    else if(btnGas.isChecked()){
                                        values.put(TransactionMain.TransactionEntry.COLUMN_TYPE, "Gas/Automotive");
                                    }
                                    else if(btnEdu.isChecked()){
                                        values.put(TransactionMain.TransactionEntry.COLUMN_TYPE, "School or Study Supplies");
                                    }
                                    else{
                                        values.put(TransactionMain.TransactionEntry.COLUMN_TYPE, "Unknown");
                                    }
                                    //Commit Content Provider entry
                                    getContentResolver().insert(TransactionMain.TransactionEntry.CONTENT_URI
                                    , values);
                                    //Doing the whole refreshing thing again
                                    Cursor cursor = getContentResolver()
                                            .query(TransactionMain.TransactionEntry.CONTENT_URI, null, null, null, null);
                                    if(cursor != null && cursor.getCount() > 0){
                                        cursor.moveToPosition(count - 1);
                                        do {
                                            listfrag.listItems.add(cursor.getString(5) + ": " + cursor.getString(1));
                                        } while (cursor.moveToNext());
                                        listfrag.adapter.notifyDataSetChanged();
                                    }
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
                else if (x2 > x1) {
                    Intent i = new Intent(MainActivity.this,Category.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }

    //need to implement this listener function to receive the item otherwise the app breaks. bummer, i know.
    @Override
    public void onListItemSelected(String input) {
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
