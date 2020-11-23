package com.example.fiscalitics;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TransactionDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "transactions.db";

    private static final int DATABASE_VERSION = 1;

    public TransactionDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

}
