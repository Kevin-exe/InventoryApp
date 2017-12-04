package edu.dtcc.inventoryapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Kevin on 11/27/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "INVENTORY.db";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Inventory.SQL_CREATE_FOLDERS);
        db.execSQL(Inventory.SQL_CREATE_FILES);
        db.execSQL(Inventory.SQL_CREATE_FILE_DATA);
        System.out.println("DB onCreate executed");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Inventory.SQL_DELETE_FOLDERS);
        db.execSQL(Inventory.SQL_DELETE_FILES);
        db.execSQL(Inventory.SQL_DELETE_FILE_DATA);

        onCreate(db);
    }

}
