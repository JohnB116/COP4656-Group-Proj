package com.example.fiscalitics;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
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


        com.github.mikephil.charting.charts.PieChart pieChart = findViewById(R.id.piechart);

        loadData();
        getEntries();
        pieDataSet = new PieDataSet(pieEntries, "");
        pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(10f);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setSliceSpace(2f);
        pieChart.setHoleRadius(0f);

        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(10f);

        pieDataSet.setSliceSpace(5f);
        pieDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return value+" $ ";
            }
        });
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
                if(hashMap.get(TYPE)==null)
                {
                    Float value = Float.parseFloat(Value.trim());
                    hashMap.put(TYPE,value);
                }
                else
                {
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
        for(String key:hashMap.keySet())
        {
            pieEntries.add(new PieEntry(hashMap.get(key), key));

        }

    }
}