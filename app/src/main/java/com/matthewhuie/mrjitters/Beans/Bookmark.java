package com.matthewhuie.mrjitters.Beans;

/**
 * Created by Ronnie Nieva on 24/03/2017.
 */

public class Bookmark {

    private String restaurantName;
    private String restaurantAddress;
    private float restaurantRating;
    private int restaurantIcon;

    public Bookmark(){}

    public Bookmark(String restaurantName, String restaurantAddress, float restaurantRating, int restaurantIcon){
        this.setRestaurantName(restaurantName);
        this.restaurantAddress = restaurantAddress;
        this.restaurantRating = restaurantRating;
        this.restaurantIcon = restaurantIcon;
    }


    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public float getRestaurantRating() {
        return restaurantRating;
    }

    public void setRestaurantRating(float restaurantRating) {
        this.restaurantRating = restaurantRating;
    }

    public int getRestaurantIcon() {
        return restaurantIcon;
    }

    public void setRestaurantIcon(int restaurantIcon) {
        this.restaurantIcon = restaurantIcon;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
