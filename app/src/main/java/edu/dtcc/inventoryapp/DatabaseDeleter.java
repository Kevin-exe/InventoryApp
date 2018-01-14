package edu.dtcc.inventoryapp;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Kevin on 12/3/17.
 */

class DatabaseDeleter extends DatabaseObjects {
    private boolean filesInFolder = true;
    private DatabaseReader dbReader;
    private Context context;
    private FolderContents folderContents;
    private StringBuilder folders = new StringBuilder();
    private StringBuilder files = new StringBuilder();
    private ArrayList<String> foldersToDelete = new ArrayList<>(1);
    private ArrayList<String> filesToDelete = new ArrayList<>(1);
    private ArrayList<String> contentBuffer = new ArrayList<>(1);
    private ArrayList<String> typesBuffer = new ArrayList<>(1);

    DatabaseDeleter(Context context){
        super(context);
        this.context = context;
    }

    void deleteFolder(String folderName){
        db = inventoryData.getWritableDatabase();
        dbReader = new DatabaseReader(context);
        folderContents = new FolderContents();
        String folderSelection;
        String fileSelection;
        String fileDataSelection;

        foldersToDelete.add(folderName);

        findAllChildren(folderName);

        createSQLstrings();

        //Folder Deletion
        folderSelection = createINstatement(Inventory.Folders.TABLE_NAME, Inventory.Folders.FOLDER_NAME_COLUMN, folders);
        db.execSQL(folderSelection);

        //File Deletion
        if (filesInFolder){
            fileSelection = createINstatement(Inventory.Files.TABLE_NAME, Inventory.Files.FILE_NAME_COLUMN, files);
            fileDataSelection = createINstatement(Inventory.FileData.TABLE_NAME, Inventory.FileData.FILE_NAME_COLUMN, files);
            db.execSQL(fileSelection);
            db.execSQL(fileDataSelection);
        }

        db.close();
    }

    //The sub-methods of the deleteFolder method. (8 total)
    private void findAllChildren(String parentFolder){
        int index = 0;
        String nextFolder;
        int totalNumberOfFoldersToDelete = foldersToDelete.size();

        getAndFilterChildren(parentFolder);

        while (totalNumberOfFoldersToDelete > index)
        {
            nextFolder = foldersToDelete.get(index);
            getAndFilterChildren(nextFolder);

            totalNumberOfFoldersToDelete = foldersToDelete.size();
            index++;
        }
    }

    private void getAndFilterChildren(String currentParent){
        getContentAndTypes(currentParent);
        for (int x = 0; x < contentBuffer.size(); x++) {
            filterFolderAndFileContent(x);
        }
    }

    private String createINstatement(String table, String column, StringBuilder dataSetToDelete){
        return String.format("DELETE FROM %1s WHERE %2s IN (%3s);", table, column, dataSetToDelete.toString());
    }

    private void getContentAndTypes(String folderName){
        folderContents = dbReader.getFolderContent(folderName);
        contentBuffer = (ArrayList) folderContents.getNames().clone();
        typesBuffer = (ArrayList) folderContents.getTypes().clone();
    }

    private void filterFolderAndFileContent(int index){
        if (typesBuffer.get(index).equals("Folder"))
            foldersToDelete.add(contentBuffer.get(index));
        else
            filesToDelete.add(contentBuffer.get(index));
    }

    private void createSQLstrings(){

        for (String folder : foldersToDelete)
            appendSQLsyntax(folders, folder);
        appendEndingSyntax(folders);

        if(!filesToDelete.isEmpty()) {
            for (String file : filesToDelete)
                appendSQLsyntax(files, file);
            appendEndingSyntax(files);
        } else
            filesInFolder = false;
    }

    private void appendSQLsyntax(StringBuilder stringBuilder, String item) {
        stringBuilder.append("'");
        stringBuilder.append(item);
        stringBuilder.append("', ");
    }

    private void appendEndingSyntax(StringBuilder stringBuilder){
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
    }

    // method that deletes a file from Files and FileData tables
    void deleteFile(String fileName){
        db = inventoryData.getWritableDatabase();

        selection = Inventory.Files.FILE_NAME_COLUMN + " = ?";
        String[] args = {fileName};

        db.delete(Inventory.Files.TABLE_NAME, selection, args);

        selection = Inventory.FileData.FILE_NAME_COLUMN + " = ?";

        db.delete(Inventory.FileData.TABLE_NAME, selection, args);

        db.close();
    }
}
