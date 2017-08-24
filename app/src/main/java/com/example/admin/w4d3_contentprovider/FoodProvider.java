package com.example.admin.w4d3_contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by Admin on 8/23/2017.
 */



public class FoodProvider extends ContentProvider {
    static final String PROVIDER_NAME = "com.example.admin.w4d3_contentprovider.FoodProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/foods";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String _ID = "_id";
    static final String FOOD_NAME = "Name";
    static final String CALORIES = "calories";

    private static HashMap<String, String> FOOD_PROJECTION_MAP;

    static final int FOODS = 1;
    static final int FOOD_ID = 2;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "foods", FOODS);
        uriMatcher.addURI(PROVIDER_NAME, "foods/#", FOOD_ID);
    }

    /**
     * Database specific constant declarations
     */

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "Foods";
    static final String FOOD_TABLE_NAME = "foods";
    static final int DATABASE_VERSION = 1;
    static final String FAT = "fat";
    static final String PROTEIN = "protein";
    static final String SODIUM = "sodium";
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + FOOD_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FOOD_NAME + " TEXT NOT NULL, " +
                    CALORIES + " TEXT NOT NULL, " +
                    FAT + " TEXT NOT NULL, " +
                    PROTEIN + " TEXT NOT NULL, " +
                    SODIUM + " TEXT NOT NULL" +
                    ");";

    /**
     * Helper class that actually creates and manages
     * the provider's underlying data repository.
     */

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + FOOD_TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */

        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        long rowID = db.insert(FOOD_TABLE_NAME, "", values);

        /**
         * If record is added successfully
         */
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }

        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(FOOD_TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case FOODS:
                qb.setProjectionMap(FOOD_PROJECTION_MAP);
                break;

            case FOOD_ID:
                qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                break;

            default:
        }

        /*if (sortOrder == null || sortOrder == ""){

            sortOrder = CALORIES;
        }*/

        Cursor c = qb.query(db,	projection,	selection,
                selectionArgs,null, null, sortOrder);
        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case FOODS:
                count = db.delete(FOOD_TABLE_NAME, selection, selectionArgs);
                break;

            case FOOD_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete(FOOD_TABLE_NAME, _ID +  " = " + id +
                                (!TextUtils.isEmpty(selection) ? " AND (" 
                                        + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case FOODS:
                count = db.update(FOOD_TABLE_NAME, values, selection, selectionArgs);
                break;

            case FOOD_ID:
                count = db.update(FOOD_TABLE_NAME, values,
                        _ID + " = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? 
                                        " AND (" +selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

}