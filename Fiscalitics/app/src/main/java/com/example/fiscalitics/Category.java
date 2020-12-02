package com.example.fiscalitics;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;

public class Category extends AppCompatActivity {

    private static final String TAG = Category.class.getCanonicalName();

    float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //Animate lateral slide-in
        overridePendingTransition(R.anim.enterl, R.anim.exitl);

        Button btnFood = (Button) findViewById(R.id.categoryFood);
        Button btnEntertainment = (Button) findViewById(R.id.categoryEntertainment);
        Button btnTravel = (Button) findViewById(R.id.categoryHome);
        Button btnCar = (Button) findViewById(R.id.categoryCar);
        Button btnEdu = (Button) findViewById(R.id.categoryEdu);
        Button btnMisc = (Button) findViewById(R.id.categoryMisc);

        Bundle bundle = new Bundle();

        BarChartFragment barChartFragment = new BarChartFragment();
        barChartFragment.setArguments(bundle);

        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.add(R.id.barChartFragmentContainer,barChartFragment,TAG);
        trans.commit();
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
