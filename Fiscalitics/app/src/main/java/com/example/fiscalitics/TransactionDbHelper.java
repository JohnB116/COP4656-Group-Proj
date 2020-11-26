package com.example.fiscalitics;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TransactionDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "transactionList.db";

    private static final int DATABASE_VERSION = 1;

    public TransactionDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        final String SQL_CREATE_TRANSACTION_TABLE = "CREATE TABLE " +
                TransactionMain.TransactionEntry.TABLE_NAME + " (" +
                TransactionMain.TransactionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TransactionMain.TransactionEntry.COLUMN_VALUE + " TEXT NOT NULL," +
                TransactionMain.TransactionEntry.COLUMN_TYPE + " TEXT NOT NULL, " +
                TransactionMain.TransactionEntry.COLUMN_DATE + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_TRANSACTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TransactionMain.TransactionEntry.TABLE_NAME);
    }

}
