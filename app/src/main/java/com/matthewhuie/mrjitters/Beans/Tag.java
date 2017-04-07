package com.matthewhuie.mrjitters.Beans;

/**
 * Created by Ronnie Nieva on 29/03/2017.
 */

public class Tag {

    public static final String TABLE_TAG = "tag";
    public static final String TAG_COL_ID = "_id";
    public static final String TAG_COL_NAME = "name";

    private int id;
    private String name;
    private boolean selected;

    /*
    public Tag(String name, boolean selected){
        this.setName(name);
        this.setSelected(selected);
    }*/


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
