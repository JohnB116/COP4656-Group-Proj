package com.example.fiscalitics;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

public class Summary extends AppCompatActivity {

    float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sp = getSharedPreferences("TLog", Activity.MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        //Animate lateral slide-in
        overridePendingTransition(R.anim.enter, R.anim.exit);

        TextView total = (TextView) findViewById(R.id.total);
        String disp = "Total spending: $" + String.valueOf(sp.getFloat("total", 0.0f));

        total.setText(disp);

    }

    //Launch a main activity when the user swipes left
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
                if(x1 < x2){
                    //Launch new page
                    Intent i = new Intent(Summary.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                break;
        }
        return false;
    }
}