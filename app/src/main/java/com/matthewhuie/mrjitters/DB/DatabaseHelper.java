package com.matthewhuie.mrjitters.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.matthewhuie.mrjitters.Beans.Restaurant;
import com.matthewhuie.mrjitters.Beans.Tag;

/**
 * Created by Iraetus on 23/03/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper _instance;

    private static final String DATABASE_NAME = "Resto.db";
    private static final int DATABASE_VERSION = 1;


    private static final String CREATE_RESTAURANT_TABLE = "create table "
            + Restaurant.TABLE_RESTAURANT + "( " +
            Restaurant.RESTAURANT_COL_ID  + " integer primary key autoincrement, " +
            Restaurant.RESTAURANT_COL_VENUE_ID + " text not null, " +
            Restaurant.RESTAURANT_COL_NAME + " text not null, " +
            Restaurant.RESTAURANT_COL_ADDRESS + " text not null, " +
            Restaurant.RESTAURANT_COL_LATITUDE + " double not null, " +
            Restaurant.RESTAURANT_COL_LONGITUDE + " double not null, " +
            Restaurant.RESTAURANT_COL_ICON + " integer not null, " +
            Restaurant.RESTAURANT_COL_RATING + " float not null, " +
            Restaurant.RESTAURANT_COL_SELECTED + " integer not null, " +
            Restaurant.RESTAURANT_COL_BOOKMARK + " integer not null);";

    private static final String CREATE_RESTAURANT_TAG_TABLE = "create table "
            + Restaurant.TABLE_RESTAURANT_TAG + "( " +
            Restaurant.RESTAURANT_TAG_COL_ID  + " integer primary key autoincrement, " +
            Restaurant.RESTAURANT_TAG_COL_RESTAURANTID + " integer not null, " +
            Restaurant.RESTAURANT_TAG_COL_TAGID + " integer not null);";

    private static final String CREATE_TAG_TABLE = "create table "
            + Tag.TABLE_TAG + "( " +
            Tag.TAG_COL_ID  + " integer primary key autoincrement, " +
            Tag.TAG_COL_NAME + " text not null)";

    private static final String SQL_DELETE_RESTAURANT_TABLE =
            "DROP TABLE IF EXISTS " + Restaurant.TABLE_RESTAURANT;

    private static final String SQL_DELETE_TAG_TABLE =
            "DROP TABLE IF EXISTS " + Tag.TABLE_TAG;

    private static final String SQL_DELETE_RESTAURANT_TAG_TABLE =
            "DROP TABLE IF EXISTS " + Restaurant.TABLE_RESTAURANT_TAG;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if(_instance == null) {
            _instance = new DatabaseHelper(context);
        }
        return _instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_RESTAURANT_TABLE);
        db.execSQL(CREATE_TAG_TABLE);
        db.execSQL(CREATE_RESTAURANT_TAG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS);*/

        db.execSQL(SQL_DELETE_RESTAURANT_TABLE);
        db.execSQL(SQL_DELETE_TAG_TABLE);
        db.execSQL(SQL_DELETE_RESTAURANT_TAG_TABLE);

        onCreate(db);
    }

}

