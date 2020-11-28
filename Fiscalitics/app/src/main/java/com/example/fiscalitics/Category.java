package com.example.fiscalitics;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class Category extends AppCompatActivity {

    private static final String TAG = Category.class.getCanonicalName();

    float x1, x2, y1, y2;

    PieChart pieChart;
    PieData pieData;
    List<PieEntry> pieEntryList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //Animate lateral slide-in
        overridePendingTransition(R.anim.enterl, R.anim.exitl);

        Button btnFood = (Button) findViewById(R.id.categoryFood);
        Button btnEntertainment = (Button) findViewById(R.id.categoryEntertainment);
        Button btnTravel = (Button) findViewById(R.id.categoryTravel);
        Button btnCar = (Button) findViewById(R.id.categoryCar);
        Button btnMisc = (Button) findViewById(R.id.categoryMisc);

        final PieChartFragment piechartfrag = new PieChartFragment();
        final FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

        pieChart = findViewById(R.id.pieChart);
        //pieChart.setUsePercentValues(true);
        pieEntryList.add(new PieEntry(10,"India"));
        pieEntryList.add(new PieEntry(5,"US"));
        pieEntryList.add(new PieEntry(7,"UK"));
        pieEntryList.add(new PieEntry(3,"NZ"));
        PieDataSet pieDataSet = new PieDataSet(pieEntryList,"country");
        pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

        trans.add(R.id.pieChartFragmentContainer,piechartfrag,TAG);

        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!trans.isEmpty()) trans.remove(piechartfrag);
                trans.commit();
            }
        });

        btnEntertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!trans.isEmpty()) trans.remove(piechartfrag);
//                trans.commit();
            }
        });

        btnTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!trans.isEmpty()) trans.remove(piechartfrag);
                trans.commit();
            }
        });

        btnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!trans.isEmpty()) trans.remove(piechartfrag);
//                trans.commit();
            }
        });

        btnMisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!trans.isEmpty()) trans.remove(piechartfrag);
//                trans.commit();
            }
        });


    }

    public void initPieChart() {

    }

    //Launch a main activity when the user swipes right
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
                if(x2 < x1){
                    //Launch new page
                    Intent i = new Intent(Category.this, MainActivity.class);
                    i.putExtra("direction", "l");
                    startActivity(i);
                    finish();
                }
                break;
        }
        return false;
    }
}
