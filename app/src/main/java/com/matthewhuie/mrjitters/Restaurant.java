package com.matthewhuie.mrjitters;

/**
 * Created by CCS on 12/02/2017.
 */

public class Restaurant {

    private long id;
    private String name;
    private String info;
    private float rating;
    private PriceRange priceRange;
    private String tag;
    private boolean isBookmarked;

   /* public Restaurant(long id, String label, String info) {
        this.setId(id);
        this.setName(label);
        this.setInfo(info);
    }*/

    public String getName() {
        return name;
    }

    public String getInfo() {return info;}

    public void setInfo(String info) {this.info = info;}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public PriceRange getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(PriceRange priceRange) {
        this.priceRange = priceRange;
    }


}
