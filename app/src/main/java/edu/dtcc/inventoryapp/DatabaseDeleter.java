package edu.dtcc.inventoryapp;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Kevin on 12/3/17.
 */

public class DatabaseDeleter extends TestZone {
    private boolean filesInFolder = true;
    private DatabaseReader dbReader;
    private Context context;
    private FolderContent folderContent;
    private StringBuilder folders = new StringBuilder();
    private StringBuilder files = new StringBuilder();
    private ArrayList<String> foldersToDelete = new ArrayList<>();
    private ArrayList<String> filesToDelete = new ArrayList<>();
    private ArrayList<String> contentBuffer = new ArrayList<>();
    private ArrayList<String> typesBuffer = new ArrayList<>();

    public DatabaseDeleter(Context context){
        super(context);
        this.context = context;
    }

    public void deleteFolder(String folderName){
        db = inventoryData.getWritableDatabase();
        dbReader = new DatabaseReader(context);
        folderContent = new FolderContent();
        String folderSelection;
        String fileSelection;
        String fileDataSelection;

        int counter = 1;

        foldersToDelete.add(folderName);

        getContentAndTypes(folderName);
        for (int x = 0; x < contentBuffer.size(); x++) {
            filterFolderAndFileContent(x);
        }

        while (foldersToDelete.size() > counter) {
            folderName = foldersToDelete.get(counter);

            getContentAndTypes(folderName);
            for (int x = 0; x < contentBuffer.size(); x++) {
                filterFolderAndFileContent(x);
            }
            counter++;
        }
            createStrings();

            folderSelection = createINstatement(Inventory.Folders.TABLE_NAME, Inventory.Folders.FOLDER_NAME_COLUMN, folders);
            db.execSQL(folderSelection);

        if (filesInFolder){
            fileSelection = createINstatement(Inventory.Files.TABLE_NAME, Inventory.Files.FILE_NAME_COLUMN, files);
            fileDataSelection = createINstatement(Inventory.FileData.TABLE_NAME, Inventory.FileData.FILE_NAME_COLUMN, files);
            db.execSQL(fileSelection);
            db.execSQL(fileDataSelection);
        }

        db.close();
    }
    private String createINstatement(String table, String column, StringBuilder dataSetToDelete){
        return String.format("DELETE FROM %1s WHERE %2s IN (%3s);", table, column, dataSetToDelete.toString());
    }
        private void getContentAndTypes(String folderName){
            folderContent = dbReader.getFolderContent(folderName);
            contentBuffer = (ArrayList) folderContent.getNames().clone();
            typesBuffer = (ArrayList) folderContent.getTypes().clone();
        }

        private void filterFolderAndFileContent(int index){
            if (typesBuffer.get(index).equals("Folder"))
                foldersToDelete.add(contentBuffer.get(index));
            else
                filesToDelete.add(contentBuffer.get(index));
        }
    private void createStrings(){

        for (String folder : foldersToDelete)
            appendWithSyntax(folders, folder);
        appendEndingSyntax(folders);

        if(!filesToDelete.isEmpty()) {
            for (String file : filesToDelete)
                appendWithSyntax(files, file);
            appendEndingSyntax(files);
        } else
            filesInFolder = false;
    }
        private void appendWithSyntax(StringBuilder stringBuilder, String item) {
            stringBuilder.append("'");
            stringBuilder.append(item);
            stringBuilder.append("', ");
        }
        private void appendEndingSyntax(StringBuilder stringBuilder){
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }

    // method that deletes a file from Files and FileData tables
    public void deleteFile(String fileName){
        db = inventoryData.getWritableDatabase();

        selection = Inventory.Files.FILE_NAME_COLUMN + " = ?";
        String[] args = {fileName};

        db.delete(Inventory.Files.TABLE_NAME, selection, args);

        selection = Inventory.FileData.FILE_NAME_COLUMN + " = ?";

        db.delete(Inventory.FileData.TABLE_NAME, selection, args);

        db.close();
    }
}
