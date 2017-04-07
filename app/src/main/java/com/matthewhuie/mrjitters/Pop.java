package com.matthewhuie.mrjitters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.matthewhuie.mrjitters.Beans.Restaurant;
import com.matthewhuie.mrjitters.PlacePickerActivity;
import com.matthewhuie.mrjitters.R;
import com.matthewhuie.mrjitters.Services.RestaurantService;

import java.util.List;

/**
 * Created by Iraetus on 17/03/2017.
 */
public class Pop extends Activity {
    Button button;
    Button selectButton, bookmarkButton;

    private Restaurant restaurant;

    public final static String VENUE_ID = "com.venue.ID";
    public final static String VENUE_NAME = "com.venue.name";
    public final static String VENUE_LAT = "com.venue.lat";
    public final static String VENUE_LONG = "com.venue.long";

    @Override
    protected void onCreate(Bundle savedInstanceState){


        super.onCreate(savedInstanceState);

        setContentView(R.layout.info);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.6));

        TextView textview;
        TextView info;


        Intent intent = getIntent();
        int restaurantId = Integer.parseInt(intent.getStringExtra(PlacePickerActivity.RESTAURANT_ID));
        restaurant = RestaurantService.getRestaurant(this, restaurantId);

        textview = (TextView)findViewById(R.id.Title);
        info = (TextView)findViewById(R.id.Info);

        textview.setText(restaurant.getrName() + "");
        info.setText(restaurant.getrAddress() + "");

        button = (Button)findViewById(R.id.info_spin_again);
        this.button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

        bookmarkButton = (Button) findViewById(R.id.info_bookmark);
        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                List<Restaurant> restaurantList = RestaurantService.getAllRestaurants(getApplicationContext());

                Restaurant temp = restaurant;
                temp.setBookmarked(true);

                if(restaurantList.contains(restaurant)){
                    RestaurantService.updateRestaurant(getApplicationContext(), temp);
                }else{
                    RestaurantService.createRestaurant(getApplicationContext(), temp);
                }

                finish();
            }
        });

        selectButton = (Button) findViewById(R.id.info_select);
        selectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               /* List<Restaurant> restaurantList = RestaurantService.getAllRestaurants(getApplicationContext());

                Restaurant temp = restaurant;
                temp.setSelected(true);

                if(restaurantList.contains(restaurant)){
                    RestaurantService.updateRestaurant(getApplicationContext(), temp);
                }else{
                    RestaurantService.createRestaurant(getApplicationContext(), temp);
                }*/

                Restaurant temp = restaurant;
                temp.setSelected(true);

                RestaurantService.updateRestaurant(getApplicationContext(), temp);



                Intent intent = new Intent(Pop.this, MapsActivity.class);
                String venueID = temp.getVenueId();
                String venueName = temp.getrName();
                double venueLatitude = temp.getrLat();
                double venueLongitude = temp.getrLong();

                intent.putExtra(VENUE_ID, venueID);
                intent.putExtra(VENUE_NAME, venueName);
                intent.putExtra(VENUE_LAT, venueLatitude);
                intent.putExtra(VENUE_LONG, venueLongitude);

                startActivity(intent);
            }
        });
    }

}
