package edu.dtcc.inventoryapp;

/**
 * Created by Kevin on 11/27/17.
 */

class FolderData {
    private String ID;
    private String name;
    private String parent;
    private String oldName;
    private String newName;

    FolderData() {}

    String getOldName() {
        return oldName;
    }

    void setOldName(String oldName) {
        this.oldName = oldName;
    }

    String getNewName() {
        return newName;
    }

    void setNewName(String newName) {
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

    //Name cannot be blank
    public void setName(String folderName){
        if (folderName.equals(""))
            throw new NoSuchFieldError();
        else
            this.name = folderName;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String folderParent) {
        this.parent = folderParent;
    }
}
