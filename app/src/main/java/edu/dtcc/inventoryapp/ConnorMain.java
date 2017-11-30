package edu.dtcc.inventoryapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity
{
    // ArrayList of strings to hold the list items
    ArrayList<String> list = new ArrayList<>();

    // custom list adapter that will handle the list
    CustomListAdapter adapter;

    // create listView object
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize custom adapter
        adapter = new CustomListAdapter(list, getApplicationContext());

        // initialize the list and set the adapter
        listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        // onClick listener for the listView (not the edit buttons)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //long viewId = view.getId();
                //int position = (Integer)view.getTag();
                int position = adapter.getItemPosition();
                int count = adapter.getItemCount();

                Toast.makeText(getApplicationContext(), position + " out of " + count, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // button handler
    public void buttonHandler(View button)
    {
        switch(button.getTag().toString())
        {
            case "Add":
                adapter.addItems();
                break;
            case "Delete":
                adapter.deleteItems(0);
                break;
        }
    }
}
