package edu.dtcc.inventoryapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;

import java.util.ArrayList;


public class TestZone {
    private Activity activity;
    private DatabaseHandler inventoryData;
    private SQLiteDatabase db;
    private ContentValues values;
    private int counter;
    private String selection;
    private String[] args;
    private FolderData folderData;
    private FileData fileData;

    public TestZone(Context context, Activity activity){
        try {
            this.activity = activity;
            inventoryData = new DatabaseHandler(context);
        } catch (Exception e) {
            System.out.println("TestZone Error: " + e.getMessage());
        }
    }
    public void deleteDB(){
        db = inventoryData.getWritableDatabase();
        db.execSQL(Inventory.SQL_DELETE_FOLDERS);
        db.execSQL(Inventory.SQL_CREATE_FOLDERS);

        db.close();
    }

    public void createNewFolder(){
        db = inventoryData.getWritableDatabase();
        folderData = new FolderData();

        //remove getValues later
        getValues(folderData);
        values = new ContentValues();
        values.put(Inventory.Folders.FOLDER_NAME_COLUMN, folderData.getFolderName());
        values.put(Inventory.Folders.FOLDER_PARENT_COLUMN, folderData.getFolderParent());

        db.insert(Inventory.Folders.TABLE_NAME, null, values);
        db.close();
    }
    private void getValues(FolderData folderData){
        EditText folderName = (EditText) activity.findViewById(R.id.folder_name);
        EditText folderParent = (EditText) activity.findViewById(R.id.folder_parent);

        folderData.setFolderName(folderName.getText().toString());
        folderData.setFolderParent(folderParent.getText().toString());
    }

    public void createNewFile() {
        db = inventoryData.getWritableDatabase();
        fileData = new FileData();

        values = new ContentValues();
        values.put(Inventory.Files.FILE_NAME_COLUMN, fileData.getFileName());
        values.put(Inventory.Files.FILE_PARENT_COLUMN, fileData.getFileParent());

        db.insert(Inventory.Files.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(Inventory.FileData.FILE_NAME_COLUMN, fileData.getFileName());
        values.put(Inventory.FileData.DESCRIPTION_COLUMN, fileData.getDescription());
        values.put(Inventory.FileData.VALUE_COLUMN, fileData.getValue());
        values.put(Inventory.FileData.LOCATION_COLUMN, fileData.getLocation());

        db.insert(Inventory.FileData.TABLE_NAME, null, values);

        db.close();
    }

    public ArrayList<ArrayList<String>> getFolderContent(String parentDirectory) {
        int folderQuantity;
        int fileQuantity;
        Cursor cursor;
        db = inventoryData.getReadableDatabase();

        ArrayList<ArrayList<String>> folderContent = new ArrayList<>();
        folderContent.add(new ArrayList<String>());
        folderContent.add(new ArrayList<String>());

        selection = createContentSQLselect(
                Inventory.Folders.FOLDER_NAME_COLUMN,
                Inventory.Folders.TABLE_NAME,
                Inventory.Folders.FOLDER_PARENT_COLUMN,
                parentDirectory
        );
        cursor = db.rawQuery(selection, null);

        collectCursorData(cursor, folderContent.get(0));
        folderQuantity = folderContent.get(0).size();
        fillContentType("Folder", folderQuantity, folderContent.get(1));

        //Disabled until Interface for file Creation is created
        /*selection = createContentSQLselect(
                Inventory.Files.FILE_NAME_COLUMN,
                Inventory.Files.TABLE_NAME,
                Inventory.Files.FILE_PARENT_COLUMN,
                parentDirectory
        );
        cursor = db.rawQuery(selection, null);

        collectCursorData(cursor, folderContent.get(0));
        fileQuantity = folderContent.get(0).size() - folderQuantity;
        fillContentType("File", fileQuantity, folderContent.get(1));*/

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
        private String createContentSQLselect(String column, String table, String whereColumn, String argument) {
            return String.format("SELECT %1s FROM %2s WHERE %3s = '%4s'", column, table, whereColumn, argument);
        }

    public void getFileContentFromDB(String fileName) {
        db = inventoryData.getReadableDatabase();

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
            fileData.setFileName(cursor.getString(0));
            fileData.setDescription(cursor.getString(1));
            fileData.setValue(cursor.getString(2));
            fileData.setLocation(cursor.getString(3));
        }

        cursor.close();
        db.close();
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

    public void deleteFile(String fileName){
        db = inventoryData.getWritableDatabase();

        selection = Inventory.Files.FILE_NAME_COLUMN + " LIKE ?";
        String[] args = {fileName};

        db.delete(Inventory.Files.TABLE_NAME, selection, args);

        selection = Inventory.FileData.FILE_NAME_COLUMN + "LIKE ?";

        db.delete(Inventory.FileData.TABLE_NAME, selection, args);

        db.close();
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
