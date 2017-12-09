package edu.dtcc.inventoryapp;

import android.provider.BaseColumns;

/**
 * Created by Kevin on 11/27/17.
 */

//This is the class that has the db tables
final class Inventory {
    public static final String DATABASE_NAME = "INVENTORY";

    //do not make public.
    private Inventory(){}

    static class Folders implements BaseColumns{
        static final String TABLE_NAME = "FOLDERS";

        static final String FOLDER_NAME_COLUMN = "FOLDER_NAME";
        static final String FOLDER_PARENT_COLUMN = "FOLDER_PARENT";
    }

    static class Files implements BaseColumns{
        static final String TABLE_NAME = "FILES";

        static final String FILE_NAME_COLUMN = "FILE_NAME";
        static final String FILE_PARENT_COLUMN = "FILE_PARENT";
    }
    static class FileData implements BaseColumns{
        static final String TABLE_NAME = "FILE_DATA";

        static final String FILE_NAME_COLUMN = "FILE_NAME";
        static final String DESCRIPTION_COLUMN = "DESCRIPTION";
        static final String VALUE_COLUMN = "VALUE";
        static final String LOCATION_COLUMN = "LOCATION";
    }

    //-----CREATE TABLES-----//
    static final String SQL_CREATE_FOLDERS =
        "CREATE TABLE " + Folders.TABLE_NAME + " (" +
        Folders._ID + " INTEGER PRIMARY KEY," +
        Folders.FOLDER_NAME_COLUMN + " VARCHAR," +
        Folders.FOLDER_PARENT_COLUMN + " VARCHAR)";


    static final String SQL_CREATE_FILES =
        "CREATE TABLE " + Files.TABLE_NAME + " (" +
        Files._ID + " INTEGER PRIMARY KEY," +
        Files.FILE_NAME_COLUMN + " TEXT," +
        Files.FILE_PARENT_COLUMN + " TEXT)";


    static final String SQL_CREATE_FILE_DATA =
        "CREATE TABLE " + FileData.TABLE_NAME + " (" +
        FileData._ID + " INTEGER PRIMARY KEY," +
        FileData.FILE_NAME_COLUMN + " TEXT," +
        FileData.DESCRIPTION_COLUMN + " TEXT," +
        FileData.VALUE_COLUMN + " TEXT," +
        FileData.LOCATION_COLUMN + " TEXT)";

    //-----DROP TABLES-----//
    static final String SQL_DELETE_FOLDERS =
        "DROP TABLE IF EXISTS " + Folders.TABLE_NAME;

    static final String SQL_DELETE_FILES =
        "DROP TABLE IF EXISTS " + Files.TABLE_NAME;

    static final String SQL_DELETE_FILE_DATA =
        "DROP TABLE IF EXISTS " + FileData.TABLE_NAME;




}
