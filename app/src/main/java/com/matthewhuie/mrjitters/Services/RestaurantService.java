package com.matthewhuie.mrjitters.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.matthewhuie.mrjitters.Beans.Restaurant;

import com.matthewhuie.mrjitters.Beans.Tag;
import com.matthewhuie.mrjitters.DB.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronnie Nieva on 05/04/2017.
 */

public class RestaurantService {

    private static ContentValues toContentValue(Restaurant restaurant){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Restaurant.RESTAURANT_COL_NAME, restaurant.getrName());
        contentValues.put(Restaurant.RESTAURANT_COL_ADDRESS, restaurant.getrAddress());
        contentValues.put(Restaurant.RESTAURANT_COL_LATITUDE, restaurant.getrLat());
        contentValues.put(Restaurant.RESTAURANT_COL_LONGITUDE, restaurant.getrLong());
        contentValues.put(Restaurant.RESTAURANT_COL_RATING, restaurant.getRating());
        contentValues.put(Restaurant.RESTAURANT_COL_ICON, restaurant.getIcon());
        contentValues.put(Restaurant.RESTAURANT_COL_SELECTED, restaurant.isSelected());
        contentValues.put(Restaurant.RESTAURANT_COL_BOOKMARK, restaurant.isBookmarked());
        contentValues.put(Restaurant.RESTAURANT_COL_VENUE_ID, restaurant.getVenueId());
        return contentValues;
    }

    private static ContentValues toContentValue(Tag tag, long result){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Restaurant.RESTAURANT_TAG_COL_RESTAURANTID, result);
        contentValues.put(Restaurant.RESTAURANT_TAG_COL_TAGID, tag.getId());

        return contentValues;
    }

    private static Restaurant toRestaurant(Cursor cursor){
        Restaurant restaurant = new Restaurant();
        restaurant.setId(cursor.getInt(cursor.getColumnIndex(Restaurant.RESTAURANT_COL_ID)));
        restaurant.setVenueId(cursor.getString(cursor.getColumnIndex(Restaurant.RESTAURANT_COL_VENUE_ID)));
        restaurant.setrName(cursor.getString(cursor.getColumnIndex(Restaurant.RESTAURANT_COL_NAME)));
        restaurant.setrAddress(cursor.getString(cursor.getColumnIndex(Restaurant.RESTAURANT_COL_ADDRESS)));
        restaurant.setrLat(cursor.getDouble(cursor.getColumnIndex(Restaurant.RESTAURANT_COL_LATITUDE)));
        restaurant.setrLong(cursor.getDouble(cursor.getColumnIndex(Restaurant.RESTAURANT_COL_LONGITUDE)));
        restaurant.setRating(cursor.getFloat(cursor.getColumnIndex(Restaurant.RESTAURANT_COL_RATING)));
        restaurant.setIcon(cursor.getInt(cursor.getColumnIndex(Restaurant.RESTAURANT_COL_ICON)));
        restaurant.setSelected(cursor.getInt(cursor.getColumnIndex(Restaurant.RESTAURANT_COL_SELECTED)) == 1); //if 1 - selected ? 0 - never selected
        restaurant.setBookmarked(cursor.getInt(cursor.getColumnIndex(Restaurant.RESTAURANT_COL_BOOKMARK)) == 1); //if 1 - bookmarked ? 0 - not bookmarked

        return restaurant;
    }

    public static long createRestaurant(Context context, Restaurant restaurant){

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();

        ContentValues contentValues = toContentValue(restaurant);

        long result = db.insert(Restaurant.TABLE_RESTAURANT, null, contentValues);

        if(restaurant.getTags() != null) {
            List<Tag> tags = restaurant.getTags();
            for (Tag tag : tags) {
                contentValues = toContentValue(tag, result);
                /*
                contentValues = new ContentValues();
                contentValues.put(Restaurant.RESTAURANT_TAG_COL_RESTAURANTID, result);
                contentValues.put(Restaurant.RESTAURANT_TAG_COL_TAGID, tag.getId());
                */
                result = db.insert(Restaurant.TABLE_RESTAURANT_TAG, null, contentValues);
            }
        }

        db.close();
        return result;
    }


    public static int updateRestaurant(Context context, Restaurant restaurant){

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();

        String selection = Restaurant.RESTAURANT_COL_ID+ " = ?";
        String[] selectionArgs = {restaurant.getId() + ""};

        // update all attributes
        ContentValues contentValues = toContentValue(restaurant);

        int result = db.update(Restaurant.TABLE_RESTAURANT, contentValues, selection, selectionArgs);

        db.close();

        return result;
    }

    public static List<Restaurant> getAllRestaurants(Context context) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        // sort by restaurant name
        String orderBy = Restaurant.RESTAURANT_COL_NAME;

        Cursor cursor = db.query(Restaurant.TABLE_RESTAURANT, null, null, null, null, null, orderBy);
        List<Restaurant> restaurantList = new ArrayList<Restaurant>();
        while(cursor.moveToNext()){
            restaurantList.add(toRestaurant(cursor));
        }

        cursor.close();
        db.close();

        return restaurantList;
    }

    public static List<Restaurant> getAllBookmarks(Context context){
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        // 1 : bookmarked --- 0 : not bookmarked
        String selection = Restaurant.RESTAURANT_COL_BOOKMARK + " = ?";
        String[] selectionArgs = {"1"};

        // sort by date and time
        String orderBy = Restaurant.RESTAURANT_COL_NAME;

        Cursor cursor =  db.query(Restaurant.TABLE_RESTAURANT, null, selection, selectionArgs, null, null, orderBy);
        List<Restaurant> restaurantList = new ArrayList<Restaurant>();
        while(cursor.moveToNext()){

            restaurantList.add(toRestaurant(cursor));
        }

        cursor.close();
        db.close();

        return restaurantList;
    }

    public static List<Restaurant> getAllSelected(Context context){

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        // 1 : bookmarked --- 0 : not bookmarked
        String selection = Restaurant.RESTAURANT_COL_SELECTED + " = ?";
        String[] selectionArgs = {"1"};

        // sort by date and time
        String orderBy = Restaurant.RESTAURANT_COL_NAME;

        Cursor cursor =  db.query(Restaurant.TABLE_RESTAURANT, null, selection, selectionArgs, null, null, orderBy);
        List<Restaurant> restaurantList = new ArrayList<Restaurant>();
        while(cursor.moveToNext()){
            restaurantList.add(toRestaurant(cursor));
        }

        cursor.close();
        db.close();

        return restaurantList;

    }

    public static Restaurant getRestaurant(Context context, int id){
        Restaurant restaurant = null;

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        String selection = Restaurant.TABLE_RESTAURANT + "." + Restaurant.RESTAURANT_COL_ID + " = ?";
        String[] selectionArgs = {id + ""};

        Cursor cursor = db.query(
                Restaurant.TABLE_RESTAURANT, null, selection, selectionArgs, null, null, null);
        if(cursor.moveToFirst()){
            restaurant = toRestaurant(cursor);
        }

        cursor.close();

        if(restaurant != null){
            if(restaurant.getTags() == null){


                /* QUERY method */
                String table = Restaurant.TABLE_RESTAURANT + " NATURAL JOIN " + Restaurant.TABLE_RESTAURANT_TAG + " NATURAL JOIN " +
                        Tag.TABLE_TAG;

                String columns = "DISTINCT *";

                selection = Restaurant.TABLE_RESTAURANT + "." + Restaurant.RESTAURANT_COL_ID + " = ? AND " +
                        Restaurant.TABLE_RESTAURANT + "." + Restaurant.RESTAURANT_COL_ID + " = " +
                        Restaurant.TABLE_RESTAURANT_TAG + "." + Restaurant.RESTAURANT_TAG_COL_RESTAURANTID + " AND " +
                        Tag.TABLE_TAG + "." + Tag.TAG_COL_ID + " = " +
                        Restaurant.TABLE_RESTAURANT_TAG + "." + Restaurant.RESTAURANT_TAG_COL_TAGID;

                cursor = db.query(true, table, null, selection, selectionArgs, null, null, null, null);


                /* RAW QUERY METHOD
                String SELECT_QUERY =
                        "SELECT DISTINCT * " +
                        "FROM " + Restaurant.TABLE_RESTAURANT + " NATURAL JOIN " + Restaurant.TABLE_RESTAURANT_TAG +
                                " NATURAL JOIN " + Tag.TABLE_TAG + " " +
                        "WHERE " + Restaurant.TABLE_RESTAURANT + "." + Restaurant.RESTAURANT_COL_ID + " = " + id + " AND " +
                                Restaurant.TABLE_RESTAURANT + "." + Restaurant.RESTAURANT_COL_ID + " = " +
                                Restaurant.TABLE_RESTAURANT_TAG + "." + Restaurant.RESTAURANT_TAG_COL_RESTAURANTID  + " AND " +
                                Tag.TABLE_TAG + "." + Tag.TAG_COL_ID + " = " +
                                Restaurant.TABLE_RESTAURANT_TAG + "." + Restaurant.RESTAURANT_TAG_COL_TAGID;

                cursor = db.rawQuery(SELECT_QUERY, null);
                */

                List<Tag> tagList = new ArrayList<>();
                Tag tag = null;
                while(cursor.moveToNext()){
                    tag = new Tag();
                    tag.setId(cursor.getInt(cursor.getColumnIndex(Tag.TAG_COL_ID)));
                    tag.setName(cursor.getString(cursor.getColumnIndex(Tag.TAG_COL_NAME)));
                }
                cursor.close();

            }
        }

        db.close();

        return restaurant;
    }


}
