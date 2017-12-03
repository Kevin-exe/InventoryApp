package edu.dtcc.inventoryapp;

/**
 * Created by Kevin on 11/27/17.
 */

public class FolderData {
    private String ID;
    private String name;
    private String parent;
    private String oldName;
    private String newName;

    public FolderData() {}

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String folderName) {
        this.name = folderName;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String folderParent) {
        this.parent = folderParent;
    }
}
