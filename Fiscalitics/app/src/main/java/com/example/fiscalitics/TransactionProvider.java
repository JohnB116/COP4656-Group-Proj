package com.example.fiscalitics;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TransactionProvider extends ContentProvider {

    private TransactionDbHelper mHelper;

    public static final int CODE_TRANSACTION = 100;
    public static final int CODE_TRANSACTION_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    @Override
    public boolean onCreate(){
        mHelper = new TransactionDbHelper(getContext());
        return mHelper != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder){
        Cursor cursor;

        switch (sUriMatcher.match(uri)){

            case CODE_TRANSACTION_WITH_ID:
                String _ID = uri.getLastPathSegment();
                String[] SelectionArguments = new String[]{_ID};
                cursor = mHelper.getReadableDatabase().query(
                        TransactionMain.TransactionEntry.TABLE_NAME,
                        projection,
                        TransactionMain.TransactionEntry._ID + " = ? ",
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case CODE_TRANSACTION:
                cursor = mHelper.getReadableDatabase().query(
                        TransactionMain.TransactionEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)){
            case CODE_TRANSACTION:
                long _id = db.insert(TransactionMain.TransactionEntry.TABLE_NAME, null, values);
                if (_id != 1) {

                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return TransactionMain.TransactionEntry.buildTransactionUriWithId(_id);
            default:
                return null;
        }

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }

    public static UriMatcher buildUriMatcher(){
        final UriMatcher match = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = TransactionMain.CONTENT_AUTHORITY;
        match.addURI(authority, TransactionMain.TransactionEntry.TABLE_NAME, CODE_TRANSACTION);
        match.addURI(authority, TransactionMain.TransactionEntry.TABLE_NAME + "/#",
                CODE_TRANSACTION_WITH_ID);
        return match;
    }

}
