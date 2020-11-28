package com.example.fiscalitics;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class MyPieChart extends AppCompatActivity {

    float x1, x2, y1, y2;

    private PieChart chart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_piechart);

        Log.v("TAG","hummina");

        //Animate lateral slide-in
        overridePendingTransition(R.anim.exit, R.anim.enterl);

        chart = findViewById(R.id.pieChart);
        chart.setCenterText("boogers");

    }

    //
    public boolean onTouchEvent(MotionEvent touchEvent) {
        //Get swipe data
        switch(touchEvent.getAction()) {
            case MotionEvent.ACTION_UP:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_DOWN:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(y2 < y1) {
                    //Launch new page
                    Intent i = new Intent(this, Category.class);
                    i.putExtra("direction", "l");
                    startActivity(i);
                    finish();
                }
                break;
        }
        return false;
    }
}
