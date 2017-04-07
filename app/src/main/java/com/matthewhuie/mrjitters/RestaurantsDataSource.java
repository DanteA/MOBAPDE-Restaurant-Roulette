package com.matthewhuie.mrjitters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Iraetus on 23/03/2017.
 */

public class RestaurantsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_INFO, DatabaseHelper.COLUMN_TAG,
                                    DatabaseHelper.COLUMN_RATING, DatabaseHelper.COLUMN_PRICERANGE};

    public RestaurantsDataSource(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Restaurant createRestaurant(String name, String info, String tag, float rating, PriceRange priceRange){
        //Add to database
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_INFO, info);
        values.put(DatabaseHelper.COLUMN_TAG, tag);
        values.put(DatabaseHelper.COLUMN_RATING, rating);
        values.put(DatabaseHelper.COLUMN_PRICERANGE, PriceRange.toString(priceRange));

        long insertId = database.insert(DatabaseHelper.TABLE_RESTAURANTS, null, values);


        //Get data then return
        Cursor cursor = database.query(DatabaseHelper.TABLE_RESTAURANTS,
                allColumns, DatabaseHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Restaurant newRestaurant = cursorToRestaurant(cursor);
        cursor.close();
        return newRestaurant;
    }

    public void deleteRestaurant(Restaurant restaurant){
        long id = restaurant.getId();
        System.out.println("Restaurant deleted with id: " + id);
        database.delete(DatabaseHelper.TABLE_RESTAURANTS,
                DatabaseHelper.COLUMN_ID
                + " = " + id, null);
    }

    public ArrayList<Restaurant> getAllRestaurants(){
        ArrayList<Restaurant> restaurantList = new ArrayList<Restaurant>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_RESTAURANTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Restaurant restaurant = cursorToRestaurant(cursor);
            restaurantList.add(restaurant);
            cursor.moveToNext();
        }

        cursor.close();
        return restaurantList;
    }

    private Restaurant cursorToRestaurant(Cursor cursor){
        Restaurant restaurant = new Restaurant();
        restaurant.setId(cursor.getLong(0));
        restaurant.setName(cursor.getString(1));
        restaurant.setInfo(cursor.getString(2));
        restaurant.setTag(cursor.getString(3));
        restaurant.setRating(cursor.getFloat(4));
        restaurant.setPriceRange(PriceRange.fromString(cursor.getString(5)));
        return restaurant;
    }
}

