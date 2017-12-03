package edu.dtcc.inventoryapp;

import android.content.Context;

/**
 * Created by Kevin on 12/3/17.
 */

class DatabaseDeleter extends TestZone {

    public DatabaseDeleter(Context context){
        super(context);
    }

    //todo create loop to delete heirarchies
    public void deleteFolder(String folderName){
        db = inventoryData.getWritableDatabase();

        selection = Inventory.Folders.FOLDER_NAME_COLUMN + " LIKE ?";
        String[] args = {folderName};

        db.delete(Inventory.Folders.TABLE_NAME, selection, args);

        selection = Inventory.Folders.FOLDER_PARENT_COLUMN + " LIKE ?";

        db.delete(Inventory.Folders.TABLE_NAME, selection, args);

        db.close();
    }

    // method that deletes a file from a folder
    public void deleteFile(String fileName){
        db = inventoryData.getWritableDatabase();

        selection = Inventory.Files.FILE_NAME_COLUMN + " LIKE ?";
        String[] args = {fileName};

        db.delete(Inventory.Files.TABLE_NAME, selection, args);

        selection = Inventory.FileData.FILE_NAME_COLUMN + "LIKE ?";

        db.delete(Inventory.FileData.TABLE_NAME, selection, args);

        db.close();
    }
}
