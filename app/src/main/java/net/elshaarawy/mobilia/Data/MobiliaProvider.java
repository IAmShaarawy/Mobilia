package net.elshaarawy.mobilia.Data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static net.elshaarawy.mobilia.Data.MobiliaContract.*;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class MobiliaProvider extends ContentProvider {

    private MobiliaDbHelper mDbHelper;
    private static UriMatcher sMatcher;

    @Override
    public boolean onCreate() {
        mDbHelper = new MobiliaDbHelper(getContext());
        sMatcher = MobiliaMatcher();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase database = mDbHelper.getReadableDatabase();
        final SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        Cursor cursor;

        switch (sMatcher.match(uri)) {
            case MatchingCodes.CATEGORIES:
                cursor = database.query(CategoriesColumns.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs, null, null,
                        sortOrder);
                break;
            case MatchingCodes.FURNITURE:
                qb.setTables(JoinedTables.FurnitureTable);
                cursor = qb.query(
                        database,
                        projection,
                        selection,
                        selectionArgs, null, null,
                        sortOrder);
                break;
            case MatchingCodes.SHOPS:
                qb.setTables(JoinedTables.ShopsTable);
                cursor = qb.query(
                        database,
                        projection,
                        selection,
                        selectionArgs, null, null,
                        sortOrder);
                break;
            case MatchingCodes.OFFERS:
                qb.setTables(JoinedTables.OffersTable);
                cursor = qb.query(
                        database,
                        projection,
                        selection,
                        selectionArgs, null, null,
                        sortOrder);
                break;
            case MatchingCodes.SHOPS_OFFERS:
                qb.setTables(JoinedTables.ShopsOffersTable);
                cursor = qb.query(
                        database,
                        projection,
                        selection,
                        selectionArgs, null, null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        } else throw new SQLiteException("Unsupported Operation");
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sMatcher.match(uri)) {

            case MatchingCodes.CATEGORIES:
                return MimeTypes.CATEGORIES_TYPE;

            case MatchingCodes.FURNITURE:
                return MimeTypes.FURNITURE_TYPE;

            case MatchingCodes.SHOPS:
                return MimeTypes.SHOPS_TYPE;

            case MatchingCodes.OFFERS:
                return MimeTypes.OFFERS_TYPE;

            case MatchingCodes.SHOPS_OFFERS:
                return MimeTypes.SHOPS_OFFERS_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (selection == null) {
            throw new IllegalArgumentException("You shouldn't pass null, you will delete all the table entries if you want that pass empty string \"\"");
        }

        final SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int effected;
        switch (sMatcher.match(uri)) {
            case MatchingCodes.CATEGORIES:
                effected = database.delete(CategoriesColumns.TABLE_NAME, selection, selectionArgs);
                break;

            case MatchingCodes.FURNITURE:
                effected = database.delete(FurnitureColumns.TABLE_NAME, selection, selectionArgs);
                break;

            case MatchingCodes.SHOPS:
                effected = database.delete(ShopsColumns.TABLE_NAME, selection, selectionArgs);
                break;

            case MatchingCodes.OFFERS:
                effected = database.delete(OffersColumns.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        if (effected == 1) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        database.close();
        return effected;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase database = mDbHelper.getWritableDatabase();
        ContentResolver resolver = getContext().getContentResolver();
        switch (sMatcher.match(uri)) {
            case MatchingCodes.CATEGORIES:
                return multiInsertion(database, CategoriesColumns.TABLE_NAME, values, resolver, uri);

            case MatchingCodes.FURNITURE:
                return multiInsertion(database, FurnitureColumns.TABLE_NAME, values, resolver, uri);

            case MatchingCodes.SHOPS:
                return multiInsertion(database, ShopsColumns.TABLE_NAME, values, resolver, uri);

            case MatchingCodes.OFFERS:
                return multiInsertion(database, OffersColumns.TABLE_NAME, values, resolver, uri);

            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

    }

    @Override
    public void shutdown() {
        super.shutdown();
        mDbHelper.close();
    }

    private static int multiInsertion(SQLiteDatabase db, String tableName, ContentValues[] values, ContentResolver resolver, Uri _uri) {
        int insertedRows = 0;
        db.beginTransaction();
        try {
            for (ContentValues value : values) {
                Long _id = db.insert(tableName, null, value);
                if (_id != -1)
                    insertedRows++;
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        if (insertedRows > 0) {
            resolver.notifyChange(_uri, null);
        }
        return insertedRows;
    }

    private UriMatcher MobiliaMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, Paths.CATEGORIES, MatchingCodes.CATEGORIES);
        uriMatcher.addURI(AUTHORITY, Paths.FURNITURE, MatchingCodes.FURNITURE);
        uriMatcher.addURI(AUTHORITY, Paths.SHOPS, MatchingCodes.SHOPS);
        uriMatcher.addURI(AUTHORITY, Paths.OFFERS, MatchingCodes.OFFERS);
        uriMatcher.addURI(AUTHORITY, Paths.SHOPS_OFFERS, MatchingCodes.SHOPS_OFFERS);

        return uriMatcher;
    }

    private interface JoinedTables {
        String FurnitureTable =
                CategoriesColumns.TABLE_NAME + " JOIN "
                        + FurnitureColumns.TABLE_NAME
                        + " ON ( "
                        + CategoriesColumns.TABLE_NAME + "."
                        + CategoriesColumns._ID
                        + " = "
                        + FurnitureColumns.TABLE_NAME + "."
                        + FurnitureColumns.CATEGORY_ID
                        + " )";

        String ShopsTable =
                CategoriesColumns.TABLE_NAME + " JOIN "
                        + ShopsColumns.TABLE_NAME
                        + " ON ( "
                        + CategoriesColumns.TABLE_NAME + "."
                        + CategoriesColumns._ID
                        + " = "
                        + ShopsColumns.TABLE_NAME + "."
                        + ShopsColumns.CATEGORY_ID
                        + " )";

        String OffersTable =
                CategoriesColumns.TABLE_NAME + " JOIN "
                        + OffersColumns.TABLE_NAME
                        + " ON ( "
                        + CategoriesColumns.TABLE_NAME + "."
                        + CategoriesColumns._ID
                        + " = "
                        + OffersColumns.TABLE_NAME + "."
                        + OffersColumns.CATEGORY_ID
                        + " )";
        String ShopsOffersTable =
                ShopsColumns.TABLE_NAME + " JOIN "
                        + OffersColumns.TABLE_NAME
                        + " ON ( "
                        + ShopsColumns.TABLE_NAME + "."
                        + ShopsColumns._ID
                        + " = "
                        + OffersColumns.TABLE_NAME + "."
                        + OffersColumns.SHOP_ID
                        + " )";
    }

}
