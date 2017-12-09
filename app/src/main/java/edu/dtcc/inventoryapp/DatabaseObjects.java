package edu.dtcc.inventoryapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.INotificationSideChannel;
import android.widget.EditText;

import java.util.ArrayList;

//This class also a "testing" class, where db test methods are present
class DatabaseObjects {
    private Activity activity;
    DatabaseHandler inventoryData;
    SQLiteDatabase db;
    ContentValues values;
    String selection;
    FolderData folderData;
    FileData fileData;

    DatabaseObjects(Context context){
        inventoryData = new DatabaseHandler(context);
    }

    DatabaseObjects(Context context, Activity activity){
        try {
            this.activity = activity;
            inventoryData = new DatabaseHandler(context);
        } catch (Exception e) {
            System.out.println("DatabaseObjects Error: " + e.getMessage());
        }
    }

    //TESTING method that deletes and re-creates the Folders table
    void deleteEverything(){
        db = inventoryData.getWritableDatabase();
        db.execSQL(Inventory.SQL_DELETE_FOLDERS);
        db.execSQL(Inventory.SQL_DELETE_FILE_DATA);
        db.execSQL(Inventory.SQL_DELETE_FILES);

        db.execSQL(Inventory.SQL_CREATE_FOLDERS);
        db.execSQL(Inventory.SQL_CREATE_FILES);
        db.execSQL(Inventory.SQL_CREATE_FILE_DATA);

        db.close();
    }


    //TESTING
    void selectAll(){
        db = inventoryData.getReadableDatabase();
        ArrayList<String> names = new ArrayList<>();

        String selectAllQuery = String.format("SELECT %1s FROM %2S", Inventory.Folders.FOLDER_NAME_COLUMN, Inventory.Folders.TABLE_NAME);

        Cursor cursor = db.rawQuery(selectAllQuery, null);

        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(0));
            } while (cursor.moveToNext());

            for (String Name : names) {
                System.out.println("TESTING Selecting all: " + Name);
            }
        }
        cursor.close();
        db.close();

    }
    //TESTING
    void getFromDB(){
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

        System.out.println("Argument --" + arg + "--");

        if (cursor.moveToFirst()) {
            do {
                System.out.println("Acquiring cursor data");
                rowDetails.add(cursor.getString(0));
                rowDetails.add(cursor.getString(1));
                rowDetails.add(cursor.getString(2));
            } while (cursor.moveToNext());

            for (String Name : rowDetails) {
                System.out.println("Test select: " + Name);
            }
        }

        cursor.close();
        db.close();


    }
}
