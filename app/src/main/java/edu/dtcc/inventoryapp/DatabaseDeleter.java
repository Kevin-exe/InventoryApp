package edu.dtcc.inventoryapp;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Kevin on 12/3/17.
 */

class DatabaseDeleter extends TestZone {
    DatabaseReader dbReader;
    Context context;
    FolderContent folderContent;
    ArrayList<String> foldersToDelete = new ArrayList<>();
    ArrayList<String> filesToDelete = new ArrayList<>();
    ArrayList<String> contentBuffer = new ArrayList<>();
    ArrayList<String> typesBuffer = new ArrayList<>();

    public DatabaseDeleter(Context context){
        super(context);
        this.context = context;
    }

    public void deleteFolder(String folderName){
        db = inventoryData.getWritableDatabase();
        dbReader = new DatabaseReader(context);
        folderContent = new FolderContent();

        int counter = 1;

        selection = Inventory.Folders.FOLDER_NAME_COLUMN + " LIKE ?";
        String fileSelection = Inventory.Files.FILE_NAME_COLUMN + "LIKE ?";
        String fileDataSelection = Inventory.FileData.FILE_NAME_COLUMN + "LIKE ?";
        /*String[] args = {folderName};

        db.delete(Inventory.Folders.TABLE_NAME, selection, args);

        selection = Inventory.Folders.FOLDER_PARENT_COLUMN + " LIKE ?";

        db.delete(Inventory.Folders.TABLE_NAME, selection, args);*/

        foldersToDelete.add(folderName);

        getContentAndTypes(folderName);
        for (int x = 0; x < contentBuffer.size(); x++) {
            filterFolderAndFileContent(x);
        }


        try {
            while (true) {
                folderName = foldersToDelete.get(counter);

                getContentAndTypes(folderName);
                for (int x = 0; x < contentBuffer.size(); x++) {
                    filterFolderAndFileContent(x);
                }
                counter++;
            }
        } catch (ArrayIndexOutOfBoundsException e){
            String[] foldersToDeleteArray = foldersToDelete.toArray(new String[foldersToDelete.size()]);
            String[] filesToDeleteArray = filesToDelete.toArray(new String[filesToDelete.size()]);
            db.delete(Inventory.Folders.TABLE_NAME, selection, foldersToDeleteArray);
            db.delete(Inventory.Files.TABLE_NAME, fileSelection, filesToDeleteArray);
            db.delete(Inventory.FileData.TABLE_NAME, fileDataSelection, filesToDeleteArray);
        }

        db.close();
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
