package edu.dtcc.inventoryapp;

import java.util.ArrayList;

/**
 * Created by Kevin on 12/2/17.
 */

public class FolderContent {

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> types = new ArrayList<>();

    public FolderContent(){}

    public FolderContent(ArrayList<String> names, ArrayList<String> types) {
        this.names.addAll(names);
        this.types.addAll(types);
    }
    public ArrayList<String> getNames() {
        return names;
    }

    public void setNames(ArrayList<String> names) {
        this.names.addAll(names);
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<String> types) {
        this.types.addAll(types);
    }
}
