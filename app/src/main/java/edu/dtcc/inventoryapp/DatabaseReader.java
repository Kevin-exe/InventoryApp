package edu.dtcc.inventoryapp;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by Kevin on 12/3/17.
 */

public class DatabaseReader extends TestZone {
    ArrayList<String> buffer = new ArrayList<>();

    public DatabaseReader(Context context){
        super(context);
    }

    public FolderContent getFolderContent(String parentDirectory) {
        int folderQuantity;
        int fileQuantity;
        Cursor cursor;
        FolderContent folderContent;
        db = inventoryData.getReadableDatabase();

        ArrayList<String> namesContainedInFolder = new ArrayList<>();
        ArrayList<String> theTypeOfEachName = new ArrayList<>();

        //Collecting folder data
        selection = createSQLselectStatement(
                Inventory.Folders.FOLDER_NAME_COLUMN,
                Inventory.Folders.TABLE_NAME,
                Inventory.Folders.FOLDER_PARENT_COLUMN,
                parentDirectory,
                Inventory.Folders.FOLDER_NAME_COLUMN
        );
        cursor = db.rawQuery(selection, null);

        collectCursorData(cursor, namesContainedInFolder);
        folderQuantity = namesContainedInFolder.size();
        fillContentType("Folder", folderQuantity, theTypeOfEachName);

        //Collecting file data
        selection = createSQLselectStatement(
                Inventory.Files.FILE_NAME_COLUMN,
                Inventory.Files.TABLE_NAME,
                Inventory.Files.FILE_PARENT_COLUMN,
                parentDirectory,
                Inventory.Files.FILE_NAME_COLUMN
        );
        cursor = db.rawQuery(selection, null);

        collectCursorData(cursor, namesContainedInFolder);
        fileQuantity = namesContainedInFolder.size() - folderQuantity;
        fillContentType("File", fileQuantity, theTypeOfEachName);

        folderContent = new FolderContent(namesContainedInFolder, theTypeOfEachName);
        db.close();
        cursor.close();

        return folderContent;
    }

    //method used for collecting single-column returns
    private void collectCursorData(Cursor cursor, ArrayList<String> cursorData) {
        if (cursor.moveToFirst()) {
            do {
                cursorData.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
    }
    private void fillContentType(String type, int size, ArrayList<String> contentType){
        for(int x = 0; x < size; x++){
            contentType.add(type);
        }
    }
    private String createSQLselectStatement(String column, String table, String whereColumn, String argument, String orderBy) {
        return String.format("SELECT %1s FROM %2s WHERE %3s = '%4s' ORDER BY %5s", column, table, whereColumn, argument, orderBy);
    }

    public FileData getFileContentFromDB(String fileName) {
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
                Inventory.Folders.TABLE_NAME,
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

    public String findRootDirectory(String childDirectory){
        db = inventoryData.getReadableDatabase();

        selection = createSQLselectStatement(
                Inventory.Folders.FOLDER_PARENT_COLUMN,
                Inventory.Folders.TABLE_NAME,
                Inventory.Folders.FOLDER_NAME_COLUMN,
                childDirectory,
                Inventory.Folders.FOLDER_NAME_COLUMN);

        Cursor cursor = db.rawQuery(selection, null);

        collectCursorData(cursor, buffer);

        return buffer.get(0);

    }
}
