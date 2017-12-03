package edu.dtcc.inventoryapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;

/**
 * Created by Kevin on 12/3/17.
 */

class DatabaseAdder extends TestZone {

    public DatabaseAdder(Context context) {
        super(context);
    }

    // method that creates a new Folder in the DB
    public void createNewFolder() {
        db = inventoryData.getWritableDatabase();
        folderData = new FolderData();

        values = new ContentValues();
        values.put(Inventory.Folders.FOLDER_NAME_COLUMN, folderData.getName());
        values.put(Inventory.Folders.FOLDER_PARENT_COLUMN, folderData.getParent());

        db.insert(Inventory.Folders.TABLE_NAME, null, values);
        db.close();
    }

    // method that creates a file in the Files Table and the same file in the FileData table
    public void createNewFile(FileData fileData) {
        db = inventoryData.getWritableDatabase();

        values = new ContentValues();
        values.put(Inventory.Files.FILE_NAME_COLUMN, fileData.getName());
        values.put(Inventory.Files.FILE_PARENT_COLUMN, fileData.getParent());

        db.insert(Inventory.Files.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(Inventory.FileData.FILE_NAME_COLUMN, fileData.getName());
        values.put(Inventory.FileData.DESCRIPTION_COLUMN, fileData.getDescription());
        values.put(Inventory.FileData.VALUE_COLUMN, fileData.getValue());
        values.put(Inventory.FileData.LOCATION_COLUMN, fileData.getLocation());

        db.insert(Inventory.FileData.TABLE_NAME, null, values);

        db.close();
    }
}
