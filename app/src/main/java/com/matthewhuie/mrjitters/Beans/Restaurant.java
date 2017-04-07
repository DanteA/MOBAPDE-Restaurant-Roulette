package com.matthewhuie.mrjitters.Beans;
import java.util.List;

/**
 * Created by Ronnie Nieva on 04/04/2017.
 */

public class Restaurant {
    public static final String TABLE_RESTAURANT = "restaurant";
    public static final String RESTAURANT_COL_ID = "_id";
    public static final String RESTAURANT_COL_VENUE_ID = "venue_id";
    public static final String RESTAURANT_COL_NAME = "name";
    public static final String RESTAURANT_COL_ADDRESS = "address";
    public static final String RESTAURANT_COL_LATITUDE = "latitude";
    public static final String RESTAURANT_COL_LONGITUDE = "longitude";
    public static final String RESTAURANT_COL_RATING = "rating";
    public static final String RESTAURANT_COL_ICON = "icon";
    public static final String RESTAURANT_COL_SELECTED = "is_selected";
    public static final String RESTAURANT_COL_BOOKMARK = "is_bookmarked";

    public static final String TABLE_RESTAURANT_TAG = "restaurant_tag";
    public static final String RESTAURANT_TAG_COL_ID = "_id";
    public static final String RESTAURANT_TAG_COL_RESTAURANTID = "restaurant_id";
    public static final String RESTAURANT_TAG_COL_TAGID = "tag_id";

    private int id;
    private String venueId;
    private String rName;
    private String rAddress;
    private double rLat;
    private double rLong;
    private float rating;
    private List<Tag> tags;
    private int icon;
    private boolean selected;
    private boolean bookmarked;

    /*
    public Restaurant(String rName, String rAddress, float rating, List<Tag> tags, int icon, boolean selected, boolean bookmarked){
        this.rName = rName;
        this.rAddress = rAddress;
        this.rating = rating;
        this.tags = tags;
        this.icon = icon;
        this.selected = selected;
        this.bookmarked = bookmarked;
    }
    */


    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public String getrAddress() {
        return rAddress;
    }

    public void setrAddress(String rAddress) {
        this.rAddress = rAddress;
    }

    public double getrLat() { return rLat; }

    public void setrLat(double rLat) { this.rLat = rLat; }

    public double getrLong() { return rLong; }

    public void setrLong(double rLong) { this.rLong = rLong; }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }
}
