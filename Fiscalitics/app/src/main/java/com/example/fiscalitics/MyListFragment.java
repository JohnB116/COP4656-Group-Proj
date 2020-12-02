package com.example.fiscalitics;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;


public class MyListFragment extends Fragment {

    private static final String TAG = "ListFragment";

    //***These three will be private once the refreshing is worked out***
    public MyListFragmentListener listener;
    public ListView list;
    public ArrayAdapter<String> adapter;
    public ArrayList<String> listItems = new ArrayList<String>();

    public MyListFragment() {
        // Required empty public constructor
    }

    public interface MyListFragmentListener {
        void onListItemSelected(String input);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,listItems);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        list = (ListView) view.findViewById(R.id.listView1);
        adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1,listItems);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                Object obj = list.getItemAtPosition(i);
                final String str = (String) obj;
                listener.onListItemSelected(str);

                //Launch dialog to enter Transaction information
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("About this transaction");

                TransactionDbHelper db = new TransactionDbHelper(getActivity());
                final SQLiteDatabase s = db.getWritableDatabase(); //Read info from database and show it to the user upon request
                String selectQuery = "SELECT * FROM transactionList WHERE _ID = " + (i + 1);

                //Put together data from the entry to show
                Cursor cursor = s.rawQuery(selectQuery, null);
                cursor.moveToFirst();
                builder.setMessage("ID: " + cursor.getInt(cursor.getColumnIndex(TransactionMain.TransactionEntry._ID))
                + "\n\n" + "Money Spent: " + cursor.getString(cursor.getColumnIndex(TransactionMain.TransactionEntry.COLUMN_VALUE))
                + "\n\n" + "Type: " + cursor.getString(cursor.getColumnIndex(TransactionMain.TransactionEntry.COLUMN_TYPE))
                + "\n\n" + "Date/Time: " + cursor.getString(cursor.getColumnIndex(TransactionMain.TransactionEntry.COLUMN_DAY))
                + " " + cursor.getString(cursor.getColumnIndex(TransactionMain.TransactionEntry.COLUMN_DATE))
                + " " + cursor.getString(cursor.getColumnIndex(TransactionMain.TransactionEntry.COLUMN_TIME)));

                AlertDialog a = builder.create();
                a.show();

            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyListFragmentListener) {
            listener = (MyListFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}