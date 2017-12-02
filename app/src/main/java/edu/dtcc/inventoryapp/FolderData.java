package edu.dtcc.inventoryapp;

import java.util.ArrayList;

/**
 * Created by Kevin on 11/27/17.
 */

public class FolderData {
    private String folderID;
    private String folderName;
    private String folderParent;

    public FolderData() {}

    public String getFolderID() {
        return folderID;
    }

    public void setFolderID(String folderID) {
        this.folderID = folderID;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderParent() {
        return folderParent;
    }

    public void setFolderParent(String folderParent) {
        this.folderParent = folderParent;
    }
}
