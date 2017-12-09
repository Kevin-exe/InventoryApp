package edu.dtcc.inventoryapp;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by Kevin on 12/3/17.
 */

class DatabaseReader extends DatabaseObjects {
    private ArrayList<String> buffer = new ArrayList<>();
    private ArrayList<String> theTypeOfEachName = new ArrayList<>();
    private String rootDirectory;

    DatabaseReader(Context context){
        super(context);
    }

    //Used by the dbDeleter and for creating the listView
    FolderContents getFolderContent(String parentDirectory) {
        Cursor cursor;
        FolderContents folderContents;
        String[] argument = {parentDirectory};
        db = inventoryData.getReadableDatabase();

        ArrayList<String> namesContainedInFolder = new ArrayList<>();


        //Collecting folder data
        selection = createSQLselectStatement(
                Inventory.Folders.FOLDER_NAME_COLUMN,
                Inventory.Folders.TABLE_NAME,
                Inventory.Folders.FOLDER_PARENT_COLUMN,
                Inventory.Folders.FOLDER_NAME_COLUMN
        );
        cursor = db.rawQuery(selection, argument);

        collectCursorData(cursor, namesContainedInFolder, "Folder");

        //Collecting file data
        selection = createSQLselectStatement(
                Inventory.Files.FILE_NAME_COLUMN,
                Inventory.Files.TABLE_NAME,
                Inventory.Files.FILE_PARENT_COLUMN,
                Inventory.Files.FILE_NAME_COLUMN
        );
        cursor = db.rawQuery(selection, argument);

        collectCursorData(cursor, namesContainedInFolder, "File");

        folderContents = new FolderContents(namesContainedInFolder, theTypeOfEachName);

        db.close();
        cursor.close();

        return folderContents;
    }

    //method used for collecting single-column returns
    //also appends the folder content type.
    private void collectCursorData(Cursor cursor, ArrayList<String> cursorData, String itemType) {
        if (cursor.moveToFirst()) {
            do {
                cursorData.add(cursor.getString(0));
                theTypeOfEachName.add(itemType);
            } while (cursor.moveToNext());
        }
    }

    private String createSQLselectStatement(String column, String table, String whereColumn, String orderBy) {
        return String.format("SELECT %s FROM %s WHERE %s = ? ORDER BY %s", column, table, whereColumn, orderBy);
    }

    //returns file data into the FileData class
    FileData getFileContentFromDB(String fileName) {
        db = inventoryData.getReadableDatabase();
        fileData = new FileData();

        String[] projection = {
                Inventory.FileData.FILE_NAME_COLUMN,
                Inventory.FileData.DESCRIPTION_COLUMN,
                Inventory.FileData.VALUE_COLUMN,
                Inventory.FileData.LOCATION_COLUMN
        };

        String selection = Inventory.FileData.FILE_NAME_COLUMN + " = ?";
        String[] selectionArgs = {fileName};

        Cursor cursor = db.query(
                Inventory.FileData.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            fileData.setName(cursor.getString(0));
            fileData.setDescription(cursor.getString(1));
            fileData.setValue(cursor.getString(2));
            fileData.setLocation(cursor.getString(3));
        }

        cursor.close();
        db.close();

        return fileData;
    }

    //Used for the back button.
    String findPreviousDirectory(String childDirectory){
        db = inventoryData.getReadableDatabase();
        String[] argument = {childDirectory};

        selection = createSQLselectStatement(
                Inventory.Folders.FOLDER_PARENT_COLUMN,
                Inventory.Folders.TABLE_NAME,
                Inventory.Folders.FOLDER_NAME_COLUMN,
                Inventory.Folders.FOLDER_NAME_COLUMN);

        Cursor cursor = db.rawQuery(selection, argument);

        if (cursor.moveToFirst())
            rootDirectory = cursor.getString(0);

        db.close();
        cursor.close();

        return rootDirectory;

    }
}
