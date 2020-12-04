package com.example.fiscalitics;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;


public class Category extends AppCompatActivity {

    private static final String TAG = Category.class.getCanonicalName();

    float x1, x2, y1, y2;

    HashMap<String,Float> hashMap=new HashMap<>();
    ArrayList barEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //Animate lateral slide-in
        overridePendingTransition(R.anim.enterl, R.anim.exitl);

        Button btnFood = (Button) findViewById(R.id.categoryFood);
        Button btnEntertainment = (Button) findViewById(R.id.categoryEntertainment);
        Button btnHome = (Button) findViewById(R.id.categoryHome);
        Button btnCar = (Button) findViewById(R.id.categoryCar);
        Button btnEdu = (Button) findViewById(R.id.categoryEdu);

        Bundle bundle = new Bundle();

        //Get database table
        TransactionDbHelper db = new TransactionDbHelper(this);
        final SQLiteDatabase s = db.getReadableDatabase(); //Read info from database and show it to the user upon request
        String selectQuery = "SELECT * FROM transactionList";
        final Cursor cursor = s.rawQuery(selectQuery, null);

        //For each onClickListener, display
        //the category's total, average, and
        //last date accessed from
        //the SQLite table
        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor.moveToFirst();
                float total = 0;
                int count = 0;
                String date = "";

                if(cursor.getCount() == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Category.this);
                    builder.setTitle("No transactions found");
                    builder.setMessage("Make a transaction to get started");
                    AlertDialog a = builder.create();
                    a.show();
                }
                else {
                    do {
                        if ((cursor.getString(cursor.getColumnIndex(TransactionMain.TransactionEntry.COLUMN_TYPE))
                                .trim().equals("Grocery/Food"))) {
                            String val = cursor.getString(cursor.getColumnIndex(TransactionMain.TransactionEntry.COLUMN_VALUE));
                            String num = val.replace("$", "");
                            total += Float.parseFloat(num);
                            count++;
                        }

                        if (cursor.isLast()) {
                            date = cursor.getString(cursor.getColumnIndex(TransactionMain.TransactionEntry.COLUMN_DATE));
                        }
                    } while (cursor.moveToNext());

                    AlertDialog.Builder builder = new AlertDialog.Builder(Category.this);
                    builder.setTitle("Grocery/Food");
                    builder.setMessage(String.format("Total: %.2f \nAverage: %.2f \nLast Transaction: %s", total, total / count,
                            date));
                    AlertDialog a = builder.create();
                    a.show();
                }

            }
        });
        btnEntertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor.moveToFirst();
                float total = 0;
                int count = 0;
                String date = "";

                if(cursor.getCount() == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Category.this);
                    builder.setTitle("No transactions found");
                    builder.setMessage("Make a transaction to get started");
                    AlertDialog a = builder.create();
                    a.show();
                }
                else {
                    do {
                        if ((cursor.getString(cursor.getColumnIndex(TransactionMain.TransactionEntry.COLUMN_TYPE))
                                .trim().equals("Social/Drinks/Entertainment"))) {
                            String val = cursor.getString(cursor.getColumnIndex(TransactionMain.TransactionEntry.COLUMN_VALUE));
                            String num = val.replace("$", "");
                            total += Float.parseFloat(num);
                            count++;
                        }

                        if (cursor.isLast()) {
                            date = cursor.getString(cursor.getColumnIndex(TransactionMain.TransactionEntry.COLUMN_DATE));
                        }
                    } while (cursor.moveToNext());

                    AlertDialog.Builder builder = new AlertDialog.Builder(Category.this);
                    builder.setTitle("Social/Drinks/Entertainment");
                    builder.setMessage(String.format("Total: %.2f \nAverage: %.2f\nLast Transaction: %s", total, total / count,
                            date));
                    AlertDialog a = builder.create();
                    a.show();
                }
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor.moveToFirst();
                float total = 0;
                int count = 0;
                String date = "";

                if(cursor.getCount() == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Category.this);
                    builder.setTitle("No transactions found");
                    builder.setMessage("Make a transaction to get started");
                    AlertDialog a = builder.create();
                    a.show();
                }

                else {
                    do {
                        if ((cursor.getString(cursor.getColumnIndex(TransactionMain.TransactionEntry.COLUMN_TYPE))
                                .trim().equals("Home/Living/Rent"))) {
                            String val = cursor.getString(cursor.getColumnIndex(TransactionMain.TransactionEntry.COLUMN_VALUE));
                            String num = val.replace("$", "");
                            total += Float.parseFloat(num);
                            count++;
                        }

                        if (cursor.isLast()) {
                            date = cursor.getString(cursor.getColumnIndex(TransactionMain.TransactionEntry.COLUMN_DATE));
                        }
                    } while (cursor.moveToNext());

                    AlertDialog.Builder builder = new AlertDialog.Builder(Category.this);
                    builder.setTitle("Home/Living/Rent");
                    builder.setMessage(String.format("Total: %.2f \nAverage: %.2f \nLast Transaction: %s", total, total / count,
                            date));
                    AlertDialog a = builder.create();
                    a.show();
                }

            }
        });
        btnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor.moveToFirst();
                float total = 0;
                int count = 0;
                String date = "";

                if(cursor.getCount() == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Category.this);
                    builder.setTitle("No transactions found");
                    builder.setMessage("Make a transaction to get started");
                    AlertDialog a = builder.create();
                    a.show();
                }

                else {
                    do {
                        if ((cursor.getString(cursor.getColumnIndex(TransactionMain.TransactionEntry.COLUMN_TYPE))
                                .trim().equals("Gas/Automotive"))) {
                            String val = cursor.getString(cursor.getColumnIndex(TransactionMain.TransactionEntry.COLUMN_VALUE));
                            String num = val.replace("$", "");
                            total += Float.parseFloat(num);
                            count++;
                        }

                        if (cursor.isLast()) {
                            date = cursor.getString(cursor.getColumnIndex(TransactionMain.TransactionEntry.COLUMN_DATE));
                        }
                    } while (cursor.moveToNext());

                    AlertDialog.Builder builder = new AlertDialog.Builder(Category.this);
                    builder.setTitle("Gas/Automotive");
                    builder.setMessage(String.format("Total: %.2f \nAverage: %.2f \nLast Transaction: %s", total, total / count,
                            date));
                    AlertDialog a = builder.create();
                    a.show();
                }
            }
        });
        btnEdu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor.moveToFirst();
                float total = 0;
                int count = 0;
                String date = "";

                if(cursor.getCount() == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Category.this);
                    builder.setTitle("No transactions found");
                    builder.setMessage("Make a transaction to get started");
                    AlertDialog a = builder.create();
                    a.show();
                }

                else {
                    do {
                        if ((cursor.getString(cursor.getColumnIndex(TransactionMain.TransactionEntry.COLUMN_TYPE))
                                .trim().equals("School or Study Supplies"))) {
                            String val = cursor.getString(cursor.getColumnIndex(TransactionMain.TransactionEntry.COLUMN_VALUE));
                            String num = val.replace("$", "");
                            total += Float.parseFloat(num);
                            count++;
                        }

                        if (cursor.isLast()) {
                            date = cursor.getString(cursor.getColumnIndex(TransactionMain.TransactionEntry.COLUMN_DATE));
                        }
                    } while (cursor.moveToNext());

                    AlertDialog.Builder builder = new AlertDialog.Builder(Category.this);
                    builder.setTitle("School or Study Supplies");
                    builder.setMessage(String.format("Total: %.2f \nAverage: %.2f \nLast Transaction: %s", total, total / count,
                            date));
                    AlertDialog a = builder.create();
                    a.show();

                }
            }
        });
    }


    //Launch a main activity when the user swipes right
    public boolean onTouchEvent(MotionEvent touchEvent) {
        //Get swipe data
        switch(touchEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x2 < x1) {
                    //Launch new page
                    Intent i = new Intent(this, MainActivity.class);
                    i.putExtra("direction", "l");
                    startActivity(i);
                    finish();
                }
                break;
        }
        return false;
    }
}
