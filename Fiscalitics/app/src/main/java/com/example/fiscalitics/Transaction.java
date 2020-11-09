package com.example.fiscalitics;

import android.app.Activity;
import android.content.SharedPreferences;

enum TYPE {} //TBA

public class Transaction {

    private int Value;
    private int ID;
    private TYPE Type;
    public Activity activity;

    public Transaction(int v, int i, Activity a){
        this.Value = v;
        this.ID = i;
        this.activity = a;
    }

    public boolean Commit(){
        SharedPreferences TLog = activity.getApplicationContext().getSharedPreferences("TLog", 0);
        SharedPreferences.Editor Edit = TLog.edit();
        //Edit.putInt("")
        return true;
    }
}
