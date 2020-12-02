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

    HashMap<String,Float> hashMap=new HashMap<>();

    ArrayList barEntries;

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

        loadData();
        getEntries();

        Bundle bundle = new Bundle();
        bundle.putSerializable("hashmap",hashMap);
        bundle.putSerializable("barEntries",barEntries);

        BarChartFragment barChartFragment = new BarChartFragment();
        barChartFragment.setArguments(bundle);

        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.add(R.id.barChartFragmentContainer,barChartFragment,TAG);
        trans.commit();

        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        btnEntertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        btnTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        btnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        btnEdu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        btnMisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


    }

    private void loadData() {
        Cursor cursor = getContentResolver().query(TransactionMain.TransactionEntry.CONTENT_URI, null, null, null, null);
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                String TYPE=cursor.getString(2);
                String Value=cursor.getString(1);
                Value= Value.replace("$","");
                if(hashMap.get(TYPE)==null) {
                    Float value = Float.parseFloat(Value.trim());
                    hashMap.put(TYPE,value);
                }
                else {
                    Float val=hashMap.get(TYPE);
                    Float value = Float.parseFloat(Value.trim());
                    val=value+val;
                    hashMap.put(TYPE,val);

                }
            }
            while (cursor.moveToNext());
        }
    }

    private void getEntries() {
        barEntries = new ArrayList<>();
        for(String key:hashMap.keySet()) {
            barEntries.add(new PieEntry(hashMap.get(key), key));
        }
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
