package edu.dtcc.inventoryapp;

import android.provider.BaseColumns;

/**
 * Created by Kevin on 11/27/17.
 */

public final class Inventory {
    public static final String DATABASE_NAME = "INVENTORY";

    //do not make public.
    private Inventory(){}

    public static class Folders implements BaseColumns{
        public static final String TABLE_NAME = "FOLDERS";

        public static final String FOLDER_NAME_COLUMN = "FOLDER_NAME";
        public static final String FOLDER_PARENT_COLUMN = "FOLDER_PARENT";
    }

    public static class Files implements BaseColumns{
        public static final String TABLE_NAME = "FILES";

        public static final String FILE_NAME_COLUMN = "FILE_NAME";
        public static final String FILE_PARENT_COLUMN = "FILE_PARENT";
    }
    public static class FileData implements BaseColumns{
        public static final String TABLE_NAME = "FILE_DATA";

        public static final String FILE_NAME_COLUMN = "FILE_NAME";
        public static final String DESCRIPTION_COLUMN = "DESCRIPTION";
        public static final String VALUE_COLUMN = "VALUE";
        public static final String LOCATION_COLUMN = "LOCATION";
    }

    //-----CREATE TABLES-----//
    public static final String SQL_CREATE_FOLDERS =
        "CREATE TABLE " + Folders.TABLE_NAME + " (" +
        Folders._ID + " INTEGER PRIMARY KEY," +
        Folders.FOLDER_NAME_COLUMN + " TEXT," +
        Folders.FOLDER_PARENT_COLUMN + " TEXT)";


    public static final String SQL_CREATE_FILES =
        "CREATE TABLE " + Files.TABLE_NAME + " (" +
        Files._ID + " INTEGER PRIMARY KEY," +
        Files.FILE_NAME_COLUMN + " TEXT," +
        Files.FILE_PARENT_COLUMN + " TEXT)";


    public static final String SQL_CREATE_FILE_DATA =
        "CREATE TABLE " + FileData.TABLE_NAME + " (" +
        FileData._ID + " INTEGER PRIMARY KEY," +
        FileData.FILE_NAME_COLUMN + " TEXT," +
        FileData.DESCRIPTION_COLUMN + " TEXT," +
        FileData.VALUE_COLUMN + " TEXT," +
        FileData.LOCATION_COLUMN + " TEXT)";

    //-----DROP TABLES-----//
    public static final String SQL_DELETE_FOLDERS =
        "DROP TABLE IF EXISTS " + Folders.TABLE_NAME;

    public static final String SQL_DELETE_FILES =
        "DROP TABLE IF EXISTS " + Files.TABLE_NAME;

    public static final String SQL_DELETE_FILE_DATA =
        "DROP TABLE IF EXISTS " + FileData.TABLE_NAME;




}
