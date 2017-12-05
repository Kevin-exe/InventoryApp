package edu.dtcc.inventoryapp;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;

/**
 * Created by Kevin on 12/4/17.
 */

class NewFile {
    private Activity activity;
    private FileData fileData;
    private String parent;
    private DatabaseAdder dbAdder;

    public NewFile(Context context, Activity activity, String parent){
        this.activity = activity;
        this.parent = parent;
        dbAdder = new DatabaseAdder(context);
        collectNewFileData();
        createFile();
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

    private void createFile(){
        dbAdder.createNewFile(fileData);
    }


}
