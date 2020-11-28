package com.example.fiscalitics;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

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


        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MyPieChart.class);
                startActivity(i);
                finish();
            }
        });

        btnEntertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Category.this, MyPieChart.class);
                startActivity(i);
                finish();
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
