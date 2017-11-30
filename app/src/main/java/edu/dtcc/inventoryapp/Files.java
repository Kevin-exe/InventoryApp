package edu.dtcc.inventoryapp;

import java.util.ArrayList;

/**
 * Created by Kevin on 11/27/17.
 */

public class Files {
    private String fileID;
    private String fileName;
    private String fileParent;

    public Files(String fileName, String fileParent) {
        this.fileName = fileName;
        this.fileParent = fileParent;
    }

    public String getfileID() {
        return fileID;
    }
    public void setfileID(String fileID) {
        this.fileID = fileID;
    }
    public String getfileName() {
        return fileName;
    }
    public void setfileName(String fileName) {
        this.fileName = fileName;
    }
    public String getfileParent() {
        return fileParent;
    }
    public void setfileParent(String fileParent) {
        this.fileParent = fileParent;
    }



}
