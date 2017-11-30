package edu.dtcc.inventoryapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;


public class TestZone {
    private Activity activity;
    private DatabaseHandler inventoryData;
    private SQLiteDatabase db;
    private int counter;
    Folders folder;
    Files file;
    FileData fileData;

    public TestZone(Context context, Activity activity){
        try {
            this.activity = activity;
            inventoryData = new DatabaseHandler(context);
//            insertIntoDB();
//            getFromDB();
//            selectAll();
        } catch (Exception e) {
            System.out.println("TestZone Error: " + e.getMessage());
        }
    }
    public void deleteDB(){
        db.execSQL(Inventory.SQL_DELETE_FOLDERS);
    }

    public void createNewFolder(){
        db = inventoryData.getWritableDatabase();

        getValues();
        ContentValues values = new ContentValues();
        values.put(Inventory.Folders.FOLDER_NAME_COLUMN, folder.getfolderName());
        values.put(Inventory.Folders.FOLDER_PARENT_COLUMN, folder.getfolderParent());

        db.insert(Inventory.Folders.TABLE_NAME, null, values);
        db.close();
    }
    private void getValues(){
        EditText folderName = (EditText) activity.findViewById(R.id.folder_name);
        EditText folderParent = (EditText) activity.findViewById(R.id.folder_parent);

        folder.setfolderName(folderName.getText().toString());
        folder.setfolderParent(folderParent.getText().toString());
    }

    public void selectAll(){
        db = inventoryData.getReadableDatabase();
        ArrayList<String> names = new ArrayList<>();

        String selectAllQuery = String.format("SELECT %1s FROM %2S", Inventory.Folders.FOLDER_NAME_COLUMN, Inventory.Folders.TABLE_NAME);

        Cursor cursor = db.rawQuery(selectAllQuery, null);

        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(0));
            } while (cursor.moveToNext());

            for (String Name : names) {
                System.out.println(Name);
            }
        }

        cursor.close();

    }
    public void getFromDB(){
        EditText userSelection = (EditText) activity.findViewById(R.id.test_select);
        String arg = userSelection.getText().toString();
        db = inventoryData.getReadableDatabase();
        ArrayList<String> rowDetails = new ArrayList<>();

        String[] projection = {
                Inventory.Folders._ID,
                Inventory.Folders.FOLDER_NAME_COLUMN,
                Inventory.Folders.FOLDER_PARENT_COLUMN
        };

        String selection = Inventory.Folders.FOLDER_NAME_COLUMN + " = ?";
        String[] selectionArgs = {arg};

        String sortOrder = Inventory.Folders.FOLDER_NAME_COLUMN;

        Cursor cursor = db.query(
                Inventory.Folders.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );



        if (cursor.moveToFirst()) {
            do {
                rowDetails.add(cursor.getString(0));
            } while (cursor.moveToNext());

            for (String Name : rowDetails) {
                System.out.println(Name);
            }
        }

        /*try {
            folder.setfolderID(rowDetails.get(0));
            folder.setfolderName(rowDetails.get(1));
            folder.setfolderParent(rowDetails.get(2));

            System.out.println("ID: " + folder.getfolderID());
            System.out.println("Name: " + folder.getfolderName());
            System.out.println("Parent: " + folder.getfolderParent());
        } catch (Exception e){
            System.out.println("getCursor error: " + e.getMessage());
        }*/


        cursor.close();
        db.close();


    }
}
