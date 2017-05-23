package net.elshaarawy.mobilia.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static net.elshaarawy.mobilia.Data.MobiliaContract.CategoriesColumns;
import static net.elshaarawy.mobilia.Data.MobiliaContract.FurnitureColumns;
import static net.elshaarawy.mobilia.Data.MobiliaContract.ShopsColumns;
import static net.elshaarawy.mobilia.Data.MobiliaContract.OffersColumns;

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
        db.execSQL(SQLTableCreator.CREATE_CATEGORIES);
        db.execSQL(SQLTableCreator.CREATE_FURNITURE);
        db.execSQL(SQLTableCreator.CREATE_SHOPS);
        db.execSQL(SQLTableCreator.CREATE_OFFERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FurnitureColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ShopsColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + OffersColumns.TABLE_NAME);

        this.onCreate(db);
    }

    private interface SQLTableCreator {
        String CREATE_CATEGORIES = "CREATE TABLE" + CategoriesColumns.TABLE_NAME + " ( " +
                CategoriesColumns._ID + " INTEGER PRIMARY KEY , " +
                CategoriesColumns.TITLE + " TEXT NOT NULL ); ";

        String CREATE_FURNITURE = "CREATE TABLE" + FurnitureColumns.TABLE_NAME + " ( " +
                FurnitureColumns._ID + " INTEGER PRIMARY KEY , " +
                FurnitureColumns.TITLE + " TEXT NOT NULL , " +
                FurnitureColumns.CATEGORY_ID + " INTEGER NOT NULL , " +
                FurnitureColumns.SHOP_ID + " INTEGER NOT NULL , " +
                FurnitureColumns.IMAGE + " TEXT NOT NULL , " +
                FurnitureColumns.BODY + " TEXT NOT NULL ); ";

        String CREATE_SHOPS = "CREATE TABLE" + ShopsColumns.TABLE_NAME + " ( " +
                ShopsColumns._ID + " INTEGER PRIMARY KEY , " +
                ShopsColumns.TITLE + " TEXT NOT NULL , " +
                ShopsColumns.CATEGORY_ID + " INTEGER NOT NULL , " +
                ShopsColumns.IMAGE + " TEXT NOT NULL , " +
                ShopsColumns.PHONE + " TEXT NOT NULL , " +
                ShopsColumns.WEBSITE + " TEXT NOT NULL , " +
                ShopsColumns.ABOUT + " TEXT NOT NULL ); ";

        String CREATE_OFFERS = "CREATE TABLE" + OffersColumns.TABLE_NAME + " ( " +
                OffersColumns._ID + " INTEGER PRIMARY KEY , " +
                OffersColumns.TITLE + " TEXT NOT NULL , " +
                OffersColumns.CATEGORY_ID + " INTEGER NOT NULL , " +
                OffersColumns.SHOP_ID + " INTEGER NOT NULL , " +
                OffersColumns.IMAGE + " TEXT NOT NULL , " +
                OffersColumns.BODY + " TEXT NOT NULL ); ";
    }
}
