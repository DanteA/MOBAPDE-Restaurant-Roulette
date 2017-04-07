package com.matthewhuie.mrjitters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Iraetus on 23/03/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_RESTAURANTS = "RestaurantTable";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_INFO = "Info";
    public static final String COLUMN_TAG = "Tag";
    public static final String COLUMN_RATING = "Rating";
    public static final String COLUMN_PRICERANGE = "PriceRange";

    private static final String DATABASE_NAME = "Resto.db";
    private static final int DATABASE_VERSION = 1;

    //DATABASE CREATION SQL STATEMENT

    private static final String DATABASE_CREATE = "create table "
            + TABLE_RESTAURANTS + "( " +
            COLUMN_ID  + " integer primary key autoincrement, " +
            COLUMN_NAME + " text not null, " +
            COLUMN_INFO + " text not null, " +
            COLUMN_TAG + " text not null," +
            COLUMN_RATING + " float not null," +
            COLUMN_PRICERANGE + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS);
        onCreate(db);
    }

}

