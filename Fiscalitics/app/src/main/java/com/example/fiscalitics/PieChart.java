package com.example.fiscalitics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class PieChart extends AppCompatActivity {


    PieChart pieChart;
    PieData pieData;
    PieDataSet pieDataSet;
    ArrayList pieEntries;
    ArrayList PieEntryLabels;
    HashMap<String,Float> hashMap=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        //Add a back button and a new title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Expenditure Chart");

        com.github.mikephil.charting.charts.PieChart pieChart = findViewById(R.id.piechart);

        loadData(); //Getiing data from the database and adding them to HashMap


        getEntries(); // Add PieEntries to The pieEntries ArrayList

        pieDataSet = new PieDataSet(pieEntries, "");  //Ceating Pie DataSet
        pieData = new PieData(pieDataSet); // Creating PieData Object With PieDataSetEntries
        pieChart.setData(pieData);
        pieChart.setEntryLabelColor(Color.BLACK); //Changing Color Of Entry to Black  Like  300$ Text Color To Black
        pieChart.setEntryLabelTextSize(10f);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setSliceSpace(2f);  // Slice Space Space between two slices
        pieChart.setHoleRadius(0f); // hole radius which is in mid of the pie chart you can try to change it

        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(10f);

        pieDataSet.setSliceSpace(5f);
        pieDataSet.setValueFormatter(new ValueFormatter() { //This is value formatter by default we will have values like 20 or 30 but we wants to show a Dollar sign with that values so we are using this
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.02f", value) +" $ ";
            }
        });
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

    private void loadData()
    {

        Cursor cursor = getContentResolver().query(TransactionMain.TransactionEntry.CONTENT_URI, null, null, null, null);
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do
            {
                String TYPE=cursor.getString(2);
                String Value=cursor.getString(1);
                Value= Value.replace("$","");
                if(hashMap.get(TYPE)==null) // check if hasmap didnt have this key
                {
                    Float value = Float.parseFloat(Value.trim()); // parse value to float from string
                    hashMap.put(TYPE,value); // add this value to hashmap with key  e.g key=Groceries Value = 200
                }
                else
                {
                    //  else if hasmap already has that key which means hashmap has already stored with same key  then we wants to get that
                    // value and the current value with that
                    // e.g HashMap has already  item With key = grocery and value =200
                    // then now we have another item with key=grocery and value=400
                    // now what we will do is add 200 and 400 and update value of hasmap from 200 to 600
                    // this is what we are doing below
                    Float val=hashMap.get(TYPE);
                    Float value = Float.parseFloat(Value.trim());
                    val=value+val;
                    hashMap.put(TYPE,val);

                }
               /// System.out.println("DATA : 5 = "+cursor.getString(5) + ": 0 = " + cursor.getString(0)+" 1 = "+cursor.getString(1) + ": 2 " + cursor.getString(2 )+ ": 3 " + cursor.getString(3 ));
            }
            while (cursor.moveToNext());

        }

    }

    private void getEntries() {
        pieEntries = new ArrayList<>();
        for(String key:hashMap.keySet()) // loop to get all keys from hasmap
        {
            pieEntries.add(new PieEntry(hashMap.get(key), key));

        }

    }

}
