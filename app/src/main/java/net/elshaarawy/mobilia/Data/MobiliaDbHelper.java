package net.elshaarawy.mobilia.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class MobiliaDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mobilia.db";

    public MobiliaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO Create Databases using SQLTableCreator
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO Handle Database Upgrade

        this.onCreate(db);
    }

    private interface SQLTableCreator{
        //TODO declare SQLite Queries to Create tables
    }
}
