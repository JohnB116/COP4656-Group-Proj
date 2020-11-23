package com.example.fiscalitics;

import android.net.Uri;
import android.provider.BaseColumns;

public class TransactionMain {

    public static final String CONTENT_AUTHORITY = "com.example.fiscalitics";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class TransactionEntry implements BaseColumns{

        public static final String TABLE_NAME = "transactionList";
        public static final String COLUMN_VALUE = "Value";
        //public static final String COLUMN_TYPE = "Type";
        public static final String COLUMN_DATE = "Date";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME).build();

        public static Uri buildTransactionUriWithId(long id){
            return CONTENT_URI.buildUpon().appendPath(Long.toString(id)).build();
        }

    }



}
