package edu.dtcc.inventoryapp;

import java.util.ArrayList;

/**
 * Created by Kevin on 12/2/17.
 */

//Class deals with the general contents of a folder
class FolderContents {

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> types = new ArrayList<>();

    FolderContents(){}

    FolderContents(ArrayList<String> names, ArrayList<String> types) {
        this.names.addAll(names);
        this.types.addAll(types);
    }
    ArrayList<String> getNames() {
        return names;
    }

    ArrayList<String> getTypes() {
        return types;
    }
}
