package edu.dtcc.inventoryapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Kevin on 12/4/17.
 */

class FileMenu {
    private Activity activity;
    private Context context;
    private FileData fileData;
    private String parent;
    private DatabaseAdder dbAdder;
    private DatabaseReader dbReader;

    FileMenu(Context context, Activity activity){
        this.activity = activity;
        this.context = context;

    }
    void createNewFile(String parent){
        dbAdder = new DatabaseAdder(context);
        this.parent = parent;
        collectNewFileData();
        createFileInDB();
    }

    private void collectNewFileData(){
        EditText fileName = (EditText) activity.findViewById(R.id.name_input);
        EditText location = (EditText) activity.findViewById(R.id.location_input);
        EditText value = (EditText) activity.findViewById(R.id.value_input);
        EditText description = (EditText) activity.findViewById(R.id.description_input);

        fileData = new FileData(getString(fileName), parent, getString(description), getString(value), getString(location));
    }

    private String getString(EditText editText) {
        return editText.getText().toString();
    }

    private void createFileInDB(){
        dbAdder.createNewFile(fileData);
    }

    void displayFileData(String fileName){
        TextView name = (TextView) activity.findViewById(R.id.file_display);
        TextView location = (TextView) activity.findViewById(R.id.location_display);
        TextView value = (TextView) activity.findViewById(R.id.value_display);
        TextView description = (TextView) activity.findViewById(R.id.description_display);

        getFileData(fileName);

        name.setText(fileData.getName());
        location.setText(fileData.getLocation());
        value.setText(fileData.getValue());
        description.setText(fileData.getDescription());
    }

    private void getFileData(String fileName){
        dbReader = new DatabaseReader(context);
        fileData = dbReader.getFileContentFromDB(fileName);
    }


}
