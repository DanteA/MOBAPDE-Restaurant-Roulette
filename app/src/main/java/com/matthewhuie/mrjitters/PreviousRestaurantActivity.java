package com.matthewhuie.mrjitters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.matthewhuie.mrjitters.Adapters.PreviousRestaurantAdapter;
import com.matthewhuie.mrjitters.Beans.Restaurant;
import com.matthewhuie.mrjitters.R;
import com.matthewhuie.mrjitters.Services.RestaurantService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronnie Nieva on 04/04/2017.
 */

public class PreviousRestaurantActivity extends AppCompatActivity {

    private List<Restaurant> prevRestaurantList = new ArrayList<>();
    private RecyclerView prevRestaurantRecyclerView;
    private PreviousRestaurantAdapter prevRestaurantAdapter;

    SeekBar seek_week;
    TextView text_week;

    private int maxWeek = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_restaurants);
        setTitle("Previously Selected Restaurants");

        text_week = (TextView) findViewById(R.id.prevWeekIndicator);
        seek_week = (SeekBar) findViewById(R.id.prevWeekSeekBar);
        seek_week.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (progress == 0) {
                    text_week.setText("Show All");
                    return;
                }

                int division = 100 / maxWeek;
                for (int i = 1; i <= maxWeek; i++) {
                    if (progress <= division * i) {
                        text_week.setText("Hide Last " + i + " Week/s");
                        return;
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        prevRestaurantRecyclerView = (RecyclerView) findViewById(R.id.previous_restaurant_recycler);

        prevRestaurantList = RestaurantService.getAllSelected(this);
        prevRestaurantAdapter = new PreviousRestaurantAdapter(prevRestaurantList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        prevRestaurantRecyclerView.setLayoutManager(mLayoutManager);
        prevRestaurantRecyclerView.setItemAnimator(new DefaultItemAnimator());
        prevRestaurantRecyclerView.setAdapter(prevRestaurantAdapter);

    }
}
