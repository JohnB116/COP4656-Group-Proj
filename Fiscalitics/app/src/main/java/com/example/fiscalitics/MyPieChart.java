package com.example.fiscalitics;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MotionEvent;

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
    float x1, x2, y1, y2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_piechart);

        //Animate lateral slide-in
        overridePendingTransition(R.anim.enterl, R.anim.exitl);

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

    //
    public boolean onTouchEvent(MotionEvent touchEvent){
        //Get swipe data
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_UP:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_DOWN:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(y2 < y1){
                    //Launch new page
                    Intent i = new Intent(MyPieChart.this, Category.class);
                    i.putExtra("direction", "l");
                    startActivity(i);
                    finish();
                }
                break;
        }
        return false;
    }
}
