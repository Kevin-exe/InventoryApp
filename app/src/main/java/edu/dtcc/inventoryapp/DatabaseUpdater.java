package edu.dtcc.inventoryapp;

import android.content.ContentValues;
import android.content.Context;

/**
 * Created by Kevin on 12/3/17.
 */

class DatabaseUpdater extends TestZone {

    public DatabaseUpdater(Context context){
        super(context);
    }

    public void updateFolderName(FolderData folderData) {
        db = inventoryData.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Inventory.Folders.FOLDER_NAME_COLUMN, folderData.getNewName());
        selection = Inventory.Folders.FOLDER_NAME_COLUMN + " = ?";
        String[] args = {folderData.getOldName()};

        db.update(Inventory.Folders.TABLE_NAME, values, selection, args);

        values = new ContentValues();
        values.put(Inventory.Folders.FOLDER_PARENT_COLUMN, folderData.getNewName());
        selection = Inventory.Folders.FOLDER_PARENT_COLUMN + " = ?";

        db.update(Inventory.Folders.TABLE_NAME, values, selection, args);

        db.close();
    }

    public void updateFile(FileData fileData) {
        db = inventoryData.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Inventory.FileData.FILE_NAME_COLUMN, fileData.getName());
        values.put(Inventory.FileData.DESCRIPTION_COLUMN, fileData.getDescription());
        values.put(Inventory.FileData.VALUE_COLUMN, fileData.getValue());
        values.put(Inventory.FileData.LOCATION_COLUMN, fileData.getLocation());
        selection = Inventory.FileData._ID + " = ?";

        String[] args = {fileData.getID()};

        db.update(Inventory.FileData.TABLE_NAME, values, selection, args);

        values = new ContentValues();
        values.put(Inventory.Files.FILE_NAME_COLUMN, fileData.getName());
        selection = Inventory.Files._ID + " = ?";

        db.update(Inventory.Files.TABLE_NAME, values, selection, args);

        db.close();
    }
}
