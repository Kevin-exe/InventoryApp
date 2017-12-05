package edu.dtcc.inventoryapp;

/**
 * Created by Kevin on 11/27/17.
 */

public class FileData {
    private String ID;
    private String name;
    private String parent;
    private String description;
    private String value;
    private String location;

    public FileData() {}

    public FileData(String name, String parent, String description, String value, String location) {
        this.name = name;
        this.parent = parent;
        this.description = description;
        this.value = value;
        this.location = location;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
