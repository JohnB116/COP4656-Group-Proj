package com.example.fiscalitics;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Summary extends AppCompatActivity {

    float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sp = getSharedPreferences("TLog", Activity.MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        //Animate lateral slide-in
        overridePendingTransition(R.anim.enter, R.anim.exit);

        //Retrieve statistics from SharedPreferences
        TextView total = (TextView) findViewById(R.id.total);
        String disp = "Total Amount Spent: $" + String.format("%.2f", sp.getFloat("total", 0.0f));
        TextView avg = (TextView) findViewById(R.id.avg);
        String disp2 = "Average Transaction Value: $" + String.format("%.2f", sp.getFloat("average", 0.0f));
        total.setText(disp);
        avg.setText(disp2);

        //Set up calendar that displays activity on chosen date
        CalendarView c = (CalendarView) findViewById(R.id.calendarView);
        c.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int date) {

                String the_month = null, the_day = null;

                if(month+1 < 10){
                    the_month = "0" + String.valueOf(month+1);
                }
                else{
                    the_month = String.valueOf(month+1);
                }
                if(date < 10){
                    the_day = "0" + String.valueOf(date);
                }
                else{
                    the_day = String.valueOf(date);
                }
                String theDate = "'"+(the_month + "/" + the_day +
                        "/" + String.valueOf(year)).trim() + "'";
                TransactionDbHelper db = new TransactionDbHelper(getApplicationContext());
                SQLiteDatabase s = db.getReadableDatabase();

                AlertDialog.Builder builder = new AlertDialog.Builder(Summary.this);
                builder.setTitle("Transactions from " + theDate);

                //Read info from database and show it to the user upon request
                String selectQuery = "SELECT * FROM transactionList WHERE Date = "  + theDate;

                //Put together data from the entry to show
                Cursor cursor = s.rawQuery(selectQuery, null);
                cursor.moveToFirst();
                StringBuilder list = new StringBuilder();

                //Concatenate all transactions found for the selected date
                if(cursor.getCount() > 0) {
                    for (int i = 0; i < cursor.getCount(); i++) {
                        list.append(cursor.getString(cursor.getColumnIndex(TransactionMain.TransactionEntry.COLUMN_TIME))
                        + ": " + cursor.getString(cursor.getColumnIndex(TransactionMain.TransactionEntry.COLUMN_VALUE))
                        + " " + cursor.getString(cursor.getColumnIndex(TransactionMain.TransactionEntry.COLUMN_TYPE))).append("\n");
                        cursor.moveToNext();
                    }
                    builder.setMessage(list.toString());
                }
                //No transactions found
                else{
                    builder.setMessage("No activity logged on this date");
                }

                AlertDialog a = builder.create();
                a.show();
            }
        });


    }

    //Launch main activity when the user swipes left
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
                if(x1 < x2){
                    //Launch new page
                    Intent i = new Intent(Summary.this, MainActivity.class);
                    i.putExtra("direction", "r");
                    startActivity(i);
                    finish();
                }
                break;
        }
        return false;
    }
}