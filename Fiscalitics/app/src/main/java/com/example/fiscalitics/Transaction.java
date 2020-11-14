package com.example.fiscalitics;

import android.app.Activity;
import android.content.SharedPreferences;

enum TYPE {} //TBA

public class Transaction {

    private float Value;
    private String ID;
    private TYPE Type; //TBA
    public Activity activity;

    //Parameterized Constructor
    public Transaction(float v, String i, Activity a) {
        this.Value = v;
        this.ID = i;
        this.activity = a;
    }

    public float getValue(){
        return this.Value;
    }

    public String getId(){
        return this.ID;
    }

    //Use this to add a transaction to storage
    public void Commit() {
        SharedPreferences TLog = activity.getApplicationContext().getSharedPreferences("TLog", 0);
        SharedPreferences.Editor Edit = TLog.edit();
        Edit.putFloat(this.ID, this.Value);
        Edit.apply();
    }

}
