package edu.dtcc.inventoryapp;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Connor on 12/2/17.
 */

class CustomListAdapter extends BaseAdapter implements ListAdapter
{
    private ArrayList<String> list;

    private Context context;
    private Activity activity;
    private FolderContent folderContent;
    private DialogBox dialogBox;
    private int currentIndex;

    // counter variable for the number of list items
    private int itemCount;

    // constructor that accepts an ArrayList of strings and Context
    CustomListAdapter(ArrayList<String> list, FolderContent folderContent, Context context, Activity activity) {
        this.list = list;
        this.folderContent = folderContent;
        this.context = context;
        this.activity = activity;
        itemCount = 0;
    }
    int getCurrentIndex(){return currentIndex;}

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

    DialogBox getDialogBox()
    {
        return dialogBox;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View view = convertView;
        dialogBox = new DialogBox(activity);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_folder, null);
        }

        // set the list item text
        TextView listItemText = (TextView) view.findViewById(R.id.folderText);
        listItemText.setText(list.get(position));

        // set the list item image
        ImageView listItemImage = (ImageView)view.findViewById(R.id.folderImage);

        if (folderContent.getTypes().get(position).equals("Folder"))
            listItemImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.folder));
        else
            listItemImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.page));

        // onClick listener for edit button
        ImageView editButton = (ImageView) view.findViewById(R.id.edit_folder_button);
        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialogBox.newDialogBox(R.layout.editdialog);
                dialogBox.setDialogBox(dialogBox);
                currentIndex = position;
            }
        });

        return view;
    }
}