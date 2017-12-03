package edu.dtcc.inventoryapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.INotificationSideChannel;
import android.widget.EditText;

import java.util.ArrayList;


public class TestZone {
    private Activity activity;
    DatabaseHandler inventoryData;
    SQLiteDatabase db;
    ContentValues values;
    String selection;
    FolderData folderData;
    FileData fileData;

    public TestZone(Context context){
        inventoryData = new DatabaseHandler(context);
    }

    public TestZone(Context context, Activity activity){
        try {
            this.activity = activity;
            inventoryData = new DatabaseHandler(context);
        } catch (Exception e) {
            System.out.println("TestZone Error: " + e.getMessage());
        }
    }

    // method that deletes and re-creates the Folders table
    public void deleteFoldersTable(){
        db = inventoryData.getWritableDatabase();
        db.execSQL(Inventory.SQL_DELETE_FOLDERS);
        db.execSQL(Inventory.SQL_CREATE_FOLDERS);

        db.close();
    }


    //Testing. Method collects folder name & parent from db_testing
    private void getValues(FolderData folderData){
        EditText folderName = (EditText) activity.findViewById(R.id.folder_name);
        EditText folderParent = (EditText) activity.findViewById(R.id.folder_parent);

        folderData.setName(folderName.getText().toString());
        folderData.setParent(folderParent.getText().toString());
    }

    //Testing
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
        db.close();

    }
    //Testing
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
                null
        );

        System.out.println("Argument =" + arg + "|");

        cursor.moveToFirst();
        System.out.println("preCursor: "+ cursor.getString(0));
        System.out.println("preCursor: "+ cursor.getString(1));
        System.out.println("preCursor: "+ cursor.getString(2));
        if (cursor.moveToFirst()) {
            do {
                rowDetails.add(cursor.getString(0));
                System.out.println("Acquiring cursor data: " + cursor.getString(0));
            } while (cursor.moveToNext());

            for (String Name : rowDetails) {
                System.out.println("Test select: " + Name);
            }
        }

        /*try {
            FolderData.folderID = (rowDetails.get(0));
            FolderData.folderName = (rowDetails.get(1));
            FolderData.folderParent = (rowDetails.get(2));

            System.out.println("ID: " + FolderData.folderID);
            System.out.println("Name: " + FolderData.folderName);
            System.out.println("Parent: " + FolderData.folderParent);
        } catch (Exception e){
            System.out.println("getCursor error: " + e.getMessage());
        }*/


        cursor.close();
        db.close();


    }
}
