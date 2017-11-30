package edu.dtcc.inventoryapp;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter implements ListAdapter
{
    private ArrayList<String> list;

    private Context context;

    // counter variable for the number of list items
    private int itemCount;

    // constructor that accepts an ArrayList of strings and Context
    public CustomListAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {return list.size();}

    @Override
    public Object getItem(int pos) {return list.get(pos);}

    @Override
    public long getItemId(int pos) {
        // if the list items were to have an ID...
        //return list.get(pos).getId();

        return 0;
    }

    public int getItemCount() {return itemCount;}

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_folder, null);
        }

        // set the list item text
        TextView listItemText = (TextView) view.findViewById(R.id.folderText);
        listItemText.setText(list.get(position));

        // set the list item image
        ImageView listItemImage = (ImageView)view.findViewById(R.id.folderImage);
        listItemImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.folder));

        // onClick listener for edit button
        Button editButton = (Button) view.findViewById(R.id.folder_button);
        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Button is clicked", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });

        return view;
    }

    // method that adds list elements
    public void addItems()
    {
        list.add("Item " + ++itemCount);
        notifyDataSetChanged();
    }

    // method that deletes list elements
    public void deleteItems(int pos) {
        if (list.size() != 0)
        {
            list.remove(pos);
            itemCount--;
        }
        notifyDataSetChanged();
    }
}