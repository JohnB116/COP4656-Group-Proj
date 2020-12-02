package com.example.fiscalitics;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BarChartFragment extends Fragment {

    BarChart chart;
    BarData barData;
    BarDataSet barDataSet;
    HashMap<String,Float> hashMap = new HashMap<>();
    ArrayList barEntries;

    public BarChartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bar_chart, container, false);

        chart = view.findViewById(R.id.barChart);


        hashMap = (HashMap<String, Float>) getArguments().getSerializable("hashmap");
        barEntries = (ArrayList) getArguments().getSerializable("barentries");

        barDataSet = new BarDataSet(barEntries, "gabagool");  //Ceating Pie DataSet
        barData = new BarData(barDataSet); // Creating PieData Object With PieDataSetEntries
        chart.setData(barData);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(10f);

        barDataSet.setValueFormatter(new ValueFormatter() { //This is value formatter by default we will have values like 20 or 30 but we wants to show a Dollar sign with that values so we are using this
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.02f", value) +" $ ";
            }
        });

//        List<BarEntry> entries = new ArrayList<>();
//        entries.add(new BarEntry(0f, 10f));
//        entries.add(new BarEntry(1f, 20f));
//        entries.add(new BarEntry(2f, 30f));
//        entries.add(new BarEntry(3f, 40f));
//        entries.add(new BarEntry(4f, 50f));
//        entries.add(new BarEntry(5f, 60f));
//
//        BarDataSet set = new BarDataSet(entries, "Expenditure");
//
//        BarData data = new BarData(set);
//        data.setBarWidth(0.9f); // set custom bar width
//        chart.setData(data);
//        chart.setFitBars(true); // make the x-axis fit exactly all bars
//        chart.invalidate(); // refresh

        return view;
    }
}