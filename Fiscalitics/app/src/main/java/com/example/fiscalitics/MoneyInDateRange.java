package com.example.fiscalitics;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MoneyInDateRange extends AppCompatActivity {


    String startingDate=null; //Variable to store date in string format
    String EndingDate=null;

    Date dateStart; //start date object
    Date dateEnd; //end date object

    TextView txtDays; //TexView for showing days
    TextView txtMoney;  //TexView for Money Spent
    TextView txtAvg;  //TexView for Avg money spent
    LinearLayout lytResult; // linear layout for results we hide layout if user input invalid values
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_in_date_range);

        //Add a back button and a new title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Expenditure in Date Range");

        txtDays=findViewById(R.id.txtDays);
        txtMoney=findViewById(R.id.txtMoney);
        lytResult=findViewById(R.id.lytResult);
        txtAvg=findViewById(R.id.txtAvg);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClickStartDate(final View v) //this function will run if user click on sart date button
    {
        DatePickerDialog datePickerDialog=new DatePickerDialog(this); //DatePicker Dialog
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy"); //Formatting Date to how we wants it

                startingDate = format.format(calendar.getTime()); //Assigning that value to our StartDate Variable

                ((EditText) v).setText(startingDate); // showing  that date value to on edittext

            }
        });
       // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClickEndDate(final View v) {
        DatePickerDialog datePickerDialog=new DatePickerDialog(this);
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy"); //Formatting Date to how we wants it

                EndingDate = format.format(calendar.getTime()); //Assigning that value to our StartDate Variable

                ((EditText) v).setText(EndingDate); // showing  that date value to on edittext

            }
        });
       // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();

    }

    public void onClickCalculate(View view) //this function will run when user click on button calcualte
    {

        lytResult.setVisibility(View.GONE); //we will hide the result layout

        if(startingDate!=null || EndingDate!=null) //if user didnt select start and end  date do nothing
        {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy"); //DateFormater we will use this to convert date string to date object

            try
            {
                dateStart=format.parse(startingDate);
                dateEnd=format.parse(EndingDate);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            // LOGIC to get total days between two dates
            long diff = dateEnd.getTime() - dateStart.getTime();
            long Days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            Days+=1; //Adding one to date because this logic gives us one day less then it should be

            if(diff<0)// if then ending date is before the start date then show error
            {
                Toast.makeText(MoneyInDateRange.this,"Select Valid Date | Make Sure Starting Date Should be Before Ending Date",Toast.LENGTH_LONG).show();
                return;
            }

            // Cursor to get data from database
            Cursor cursor = getContentResolver().query(TransactionMain.TransactionEntry.CONTENT_URI, null, TransactionMain.TransactionEntry.COLUMN_DATE + " BETWEEN ? AND ?",  new String[] {startingDate, EndingDate }, null);
            Float totalAmount=0f;
            if(cursor != null && cursor.getCount() > 0)
            {
                cursor.moveToFirst();
                do
                {
                    //getiing values from databse and adding them to totalamount variable
                    String Value=cursor.getString(1);
                    Value= Value.replace("$","");
                    totalAmount+= Float.parseFloat(Value.trim());
                                 }
                while (cursor.moveToNext());

            }

            float avgamount=totalAmount/cursor.getCount();
            lytResult.setVisibility(View.VISIBLE); //show result layout
            txtDays.setText(String.valueOf(Days));
            txtMoney.setText(String.format("%.2f$", totalAmount));
            txtAvg.setText(String.format("%.2f$", avgamount));


        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}