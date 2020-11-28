package com.example.fiscalitics;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;
import java.util.List;

public class MyPieChart extends AppCompatActivity {

    PieChart pieChart;
    PieData pieData;
    List<PieEntry> pieEntryList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_piechart);

        pieChart = findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);
        pieEntryList.add(new PieEntry(10,"India"));
        pieEntryList.add(new PieEntry(5,"US"));
        pieEntryList.add(new PieEntry(7,"UK"));
        pieEntryList.add(new PieEntry(3,"NZ"));
        PieDataSet pieDataSet = new PieDataSet(pieEntryList,"country");
        pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
