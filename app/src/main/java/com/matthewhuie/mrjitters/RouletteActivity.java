package com.matthewhuie.mrjitters;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.matthewhuie.mrjitters.Adapters.RestaurantAdapter;
import com.matthewhuie.mrjitters.Beans.RestaurantSource;
import com.matthewhuie.mrjitters.Beans.Restaurant;
import com.matthewhuie.mrjitters.Services.RestaurantService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RouletteActivity extends AppCompatActivity {

    public static ArrayList<RestaurantSource> database = new ArrayList<>();

    List<Restaurant> listRestaurantSource = new ArrayList<Restaurant>();
    List<Restaurant> listRestaurantSourceSPIN = new ArrayList<Restaurant>();

    private static final int VERTICAL_ITEM_SPACE = 48;

    public final static String RESTAURANT_ID = "com.restaurant.ID";

    RestaurantAdapter restaurantAdapter;
    Bundle temp;
    RecyclerView spinRecyclerView;

    Button spinButton;

    ArrayList<String> tagList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        temp = savedInstanceState;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        listRestaurantSource = RestaurantService.getAllRestaurants(this);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.spineffect);

        this.spinButton = (Button)findViewById(R.id.spin);

        this.spinButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Random rand = new Random();
                final int n;

                n = rand.nextInt((10) + listRestaurantSourceSPIN.size()-10);
                System.out.println(n);

                mp.start();
                spinRecyclerView.smoothScrollToPosition(n+1);


                // Used to delay the pop up after "spinning"
                new CountDownTimer(4000, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }
                    public void onFinish() {
                        Intent intent = new Intent(RouletteActivity.this, Pop.class);
                        String id = listRestaurantSourceSPIN.get(n).getId() + "";
                        intent.putExtra(RESTAURANT_ID, id);

                        startActivity(intent);
                        /*new CountDownTimer(1000, 1000) {

                            public void onTick(long millisUntilFinished) {

                            }

                            public void onFinish() {
                                spinRecyclerView.scrollToPosition(0);
                            }
                        }.start();*/
                    }
                }.start();

            };
        });


        /**** POPULATE THE RESTAURANT LIST HERE ****/
        //loadRestaurants();

        int j = 0;
        /* MULTIPLIES THE LIST BY 20 TO GIVE THE ILLUSION OF SPINS*/
        if(!listRestaurantSource.isEmpty())
            for(int i = 0; i < 21; i++) {
                while (j < listRestaurantSource.size()) {
                    listRestaurantSourceSPIN.add(listRestaurantSource.get(j));
                    j++;
                }
                j = 0;
            }

        /**** POPULATE THE RESTAURANT LIST HERE ****/


        spinRecyclerView = (RecyclerView) findViewById(R.id.spin_restaurant_list);
        spinRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        restaurantAdapter = new RestaurantAdapter(listRestaurantSourceSPIN);

        spinRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //spinRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        spinRecyclerView.setAdapter(restaurantAdapter);
        spinRecyclerView.setNestedScrollingEnabled(false);

        spinRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        spinButton = (Button)findViewById(R.id.spin);

    }


    void loadRestaurants(){

        Restaurant r;

        r = new Restaurant();
        r.setrName("Rap's");
        r.setrAddress("879 Dagonoy St, Malate, Manila, Metro Manila");
        r.setRating(3.0f);
        r.setIcon(R.drawable.jollibee);
        r.setSelected(false);
        r.setBookmarked(false);
        RestaurantService.createRestaurant(this, r);

        r.setrName("Jollibee");
        r.setrAddress("Cor., Taft. Ave, Malate, Manila");
        r.setRating(3.0f);
        r.setIcon(R.drawable.jollibee);
        r.setSelected(false);
        r.setBookmarked(false);
        RestaurantService.createRestaurant(this, r);

        r.setrName("Manang's");
        r.setrAddress("One Archers Place, Taft Ave, Malate, Manila");
        r.setRating(2.0f);
        r.setIcon(R.drawable.jollibee);
        r.setSelected(false);
        r.setBookmarked(false);
        RestaurantService.createRestaurant(this, r);

        r.setrName("Toribox");
        r.setrAddress("810 Castro, Malate, Manila, Metro Manila");
        r.setRating(4.0f);
        r.setIcon(R.drawable.jollibee);
        r.setSelected(false);
        r.setBookmarked(false);
        RestaurantService.createRestaurant(this, r);

        r.setrName("KFC");
        r.setrAddress("Taft /ave, Ermita, Metro Manila, 1000 Metro Manila");
        r.setRating(5.0f);
        r.setIcon(R.drawable.jollibee);
        r.setSelected(false);
        r.setBookmarked(false);
        RestaurantService.createRestaurant(this, r);

        r.setrName("Goldilocks");
        r.setrAddress("Taft Ave, Malate Manila, Metro Manila");
        r.setRating(2.0f);
        r.setIcon(R.drawable.jollibee);
        r.setSelected(false);
        r.setBookmarked(false);
        RestaurantService.createRestaurant(this, r);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_bookmarks) {

            startActivity(new Intent(this, BookmarkActivity.class));

            return true;
        }

        if( id == R.id.action_previous_restaurants){

            startActivity(new Intent(this, PreviousRestaurantActivity.class));

            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void onFilterClick(View view){

        startActivity(new Intent(this, FilterActivity.class));

    }

}
