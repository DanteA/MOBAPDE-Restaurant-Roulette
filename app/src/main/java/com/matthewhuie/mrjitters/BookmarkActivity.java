package com.matthewhuie.mrjitters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.matthewhuie.mrjitters.Adapters.BookmarkAdapter;
import com.matthewhuie.mrjitters.Beans.Restaurant;
import com.matthewhuie.mrjitters.R;
import com.matthewhuie.mrjitters.Services.RestaurantService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronnie Nieva on 24/03/2017.
 */

public class BookmarkActivity extends AppCompatActivity {
    private List<Restaurant> bookmarkList = new ArrayList<>();
    private RecyclerView bookmarkRecyclerView;
    private BookmarkAdapter bookmarkAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);
        setTitle("Bookmarked Restaurants");

        bookmarkList = RestaurantService.getAllBookmarks(this);
        bookmarkRecyclerView = (RecyclerView) findViewById(R.id.bookmark_restaurant_recycler);
        bookmarkAdapter = new BookmarkAdapter(bookmarkList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        bookmarkRecyclerView.setLayoutManager(mLayoutManager);
        bookmarkRecyclerView.setItemAnimator(new DefaultItemAnimator());
        bookmarkRecyclerView.setAdapter(bookmarkAdapter);

    }

    private void listBookmarks(){
        /*
        Bookmark bookmark;
        bookmark = new Bookmark("Jollibee", "Cor., Taft. Ave, Malate, Manila", 4.2f, R.drawable.jollibee);
        bookmarkList.add(bookmark);

        bookmark = new Bookmark("Manang's", "One Archers Place, Taft Ave, Malate, Manila", 4.2f, R.drawable.manangs_chicken);
        bookmarkList.add(bookmark);
        */
    }

}
