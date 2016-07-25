package com.tc.echosense.cache.entity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tc on 4/22/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "echosense_db";
    public static final String WIFI_TABLE = "wifi_table";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createRecordsTable(db);
   }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createRecordsTable(SQLiteDatabase db)
    {
        String query =
                "CREATE TABLE "+ WIFI_TABLE +"("
                +"id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"ssid TEXT"
                + ")";
        db.execSQL(query);
    }

}
