package com.example.fiscalitics;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MyListFragment extends Fragment {

    private MyListFragmentListener listener;

    private ListView list;
    private ArrayAdapter<String> adapter;
    private ArrayList listItems = new ArrayList();

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

        listItems.add("ruba dub");

        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,listItems);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        list = (ListView) view.findViewById(R.id.listView1);
        adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1,listItems);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object obj = list.getItemAtPosition(i);
                String str = (String) obj;
                listener.onListItemSelected(str);
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