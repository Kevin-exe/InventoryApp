package edu.dtcc.inventoryapp;

import java.util.ArrayList;

/**
 * Created by Kevin on 11/27/17.
 */

public class FileData {
    private String fileID;
    private String fileName;
    private String description;
    private String value;
    private String location;

    public FileData(String fileName, String description, String value, String location) {
        this.fileName = fileName;
        this.description = description;
        this.value = value;
        this.location = location;
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
    public String getdescription() {
        return description;
    }
    public void setdescription(String description) {
        this.description = description;
    }
    public String getvalue() {
        return value;
    }
    public void setvalue(String value) {
        this.value = value;
    }
    public String getlocation() {
        return location;
    }
    public void setlocation(String location) {
        this.location = location;
    }

}
