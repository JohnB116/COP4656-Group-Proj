package com.example.fiscalitics;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;


public class Category extends AppCompatActivity {

    private static final String TAG = Category.class.getCanonicalName();

    float x1, x2, y1, y2;

    HashMap<String,Float> hashMap=new HashMap<>();
    ArrayList barEntries;

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

        Bundle bundle = new Bundle();
        loadData();
        getEntries();

        bundle.putSerializable("hashmap", hashMap);
        bundle.putSerializable("barentries",barEntries);

        BarChartFragment barChartFragment = new BarChartFragment();
        barChartFragment.setArguments(bundle);

        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.add(R.id.barChartFragmentContainer,barChartFragment,TAG);
        trans.commit();
    }

    private void loadData() {
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
        barEntries = new ArrayList<>();
        for(String key:hashMap.keySet()) // loop to get all keys from hasmap
            {
            barEntries.add(new BarEntry( 0.1f, hashMap.get(key)));
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
