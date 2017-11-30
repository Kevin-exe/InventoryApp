package edu.dtcc.inventoryapp;

import java.util.ArrayList;

/**
 * Created by Kevin on 11/27/17.
 */

public class Folders {
    static public String folderID;
    static public String folderName;
    static public String folderParent;

    public Folders() {}

    public Folders(String folderID, String folderName, String folderParent) {
        this.folderID = folderID;
        this.folderName = folderName;
        this.folderParent = folderParent;
    }

    public String getfolderID() {
        return folderID;
    }
    public void setfolderID(String folderID) {
        Folders.folderID = folderID;
    }
    public String getfolderName() {
        return folderName;
    }
    public void setfolderName(String folderName) {
        Folders.folderName = folderName;
    }
    public String getfolderParent() {
        return folderParent;
    }
    public void setfolderParent(String folderParent) {
        Folders.folderParent = folderParent;
    }



}
