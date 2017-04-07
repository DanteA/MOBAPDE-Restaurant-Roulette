/**
 * Filename: PlacePickerActivity.java
 * Author: Matthew Huie
 *
 * PlacePickerActivity represents the location view of the app.  This Activity will obtain the user's
 * current location via Google APIs.  Also, this activity will make appropriate calls to the
 * Foursquare API using Retrofit 2, and parsing the JSON responses using GSON.  The top
 * TextView will display the user's current location, and the bottom RecyclerView will display nearby
 * coffee shops.
 */

package com.matthewhuie.mrjitters;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.matthewhuie.mrjitters.Adapters.RestaurantAdapter;
import com.matthewhuie.mrjitters.Beans.Restaurant;
import com.matthewhuie.mrjitters.Beans.RestaurantSource;
import com.matthewhuie.mrjitters.Services.RestaurantService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlacePickerActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    // The client object for connecting to the Google API
    private GoogleApiClient mGoogleApiClient;

    // The TextView for displaying the current location
    private TextView snapToPlace;

    // The RecyclerView and associated objects for displaying the nearby coffee spots
    private LinearLayoutManager placePickerManager;
    private RecyclerView.Adapter placePickerAdapter;

    // The base URL for the Foursquare API
    private String foursquareBaseURL = "https://api.foursquare.com/v2/";

    // The client ID and client secret for authenticating with the Foursquare API
    private String foursquareClientID;
    private String foursquareClientSecret;

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
        setContentView(R.layout.activity_spinner); // ORIGINAL IS activity_place_picker.xml
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // The visible TextView and RecyclerView objects
        snapToPlace = (TextView)findViewById(R.id.Main_Location);

       // placePicker.addItemDecoration(new DividerItemDecoration(placePicker.getContext(), placePickerManager.getOrientation()));

        // Creates a connection to the Google API for location services
        mGoogleApiClient = new GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build();

        // Gets the stored Foursquare API client ID and client secret from XML
//        TODO: get the shits from api.xml instead of manually putting them
        foursquareClientID = "LCMZPVR5JO4GCBPE4WJ4E0PBVI5MGBSVP0PXBNCSQG0KUWFJ";
        foursquareClientSecret = "NTYS3AFWQJ33MJ1HGUUASR3X4THEEUPXWPAG0B0FJOKRG4OJ";
//        foursquareClientID = getResources().getString(R.string.foursquare_client_id);
//        foursquareClientSecret = getResources().getString(R.string.foursquare_client_secret);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.spineffect);

        this.spinButton = (Button)findViewById(R.id.spin);

        this.spinButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Random rand = new Random();
                final int n;

                n = rand.nextInt((10) + listRestaurantSourceSPIN.size()-10);

                mp.start();
                spinRecyclerView.smoothScrollToPosition(n+1);


                // Used to delay the
                // up after "spinning"
                new CountDownTimer(4000, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }
                    public void onFinish() {
                        Intent intent = new Intent(PlacePickerActivity.this, Pop.class);
                        String id = listRestaurantSourceSPIN.get(n).getId() + "";
                        intent.putExtra(RESTAURANT_ID, id);

                        startActivity(intent);
                    }
                }.start();

            };
        });



        spinRecyclerView = (RecyclerView)findViewById(R.id.spin_restaurant_list);

        // Sets the dimensions, LayoutManager, and dividers for the RecyclerView
        spinRecyclerView.setHasFixedSize(true);
        placePickerManager = new LinearLayoutManager(this);
        spinRecyclerView.setLayoutManager(placePickerManager);

        spinRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //spinRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        spinRecyclerView.setNestedScrollingEnabled(false);
        spinRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        spinButton = (Button)findViewById(R.id.spin);
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

        if (id == R.id.action_previous_restaurants) {

            startActivity(new Intent(this, PreviousRestaurantActivity.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle connectionHint) {

        // Checks for location permissions at runtime (required for API >= 23)
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            // Makes a Google API request for the user's last known location
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (mLastLocation != null) {

                // The user's current latitude, longitude, and location accuracy
                String userLL = mLastLocation.getLatitude() + "," +  mLastLocation.getLongitude();
                double userLLAcc = mLastLocation.getAccuracy();

                // Builds Retrofit and FoursquareService objects for calling the Foursquare API and parsing with GSON
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(foursquareBaseURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                FoursquareService foursquare = retrofit.create(FoursquareService.class);

                // Calls the Foursquare API to snap the user's location to a Foursquare venue
                Call<FoursquareJSON> stpCall = foursquare.snapToPlace(
                        foursquareClientID,
                        foursquareClientSecret,
                        userLL,
                        userLLAcc);
                stpCall.enqueue(new Callback<FoursquareJSON>() {
                    @Override
                    public void onResponse(Call<FoursquareJSON> call, Response<FoursquareJSON> response) {

                        // Gets the venue object from the JSON response
//                        TODO: find out if the venue object is only used to know where the user currently is
//                        TODO: "frs" is used to refer to both a List<FoursquareVenue> AND later, a List<FoursquareResults> HOW DOES THIS WORK?
                        FoursquareJSON fjson = response.body();
                        FoursquareResponse fr = fjson.response;
                        List<FoursquareVenue> frs = fr.venues;
                        FoursquareVenue fv = frs.get(0);

                        // Notifies the user of their current location
                        snapToPlace.setText(fv.name);
                    }

                    @Override
                    public void onFailure(Call<FoursquareJSON> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Mr. Nouger can't connect to Foursquare's servers!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });

                // Calls the Foursquare API to explore nearby coffee spots
                Call<FoursquareJSON> coffeeCall = foursquare.searchCoffee(
                        foursquareClientID,
                        foursquareClientSecret,
                        userLL,
                        userLLAcc);
                coffeeCall.enqueue(new Callback<FoursquareJSON>() {
                    @Override
                    public void onResponse(Call<FoursquareJSON> call, Response<FoursquareJSON> response) {

                        // Gets the venue object from the JSON response
                        FoursquareJSON fjson = response.body();
                        FoursquareResponse fr = fjson.response;
                        FoursquareGroup fg = fr.group;
                        List<FoursquareResults> frs = fg.results;

                        addToRestaurantDB(frs);

                        // Displays the results in the RecyclerView
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

                        restaurantAdapter = new RestaurantAdapter(listRestaurantSourceSPIN);
                       // placePickerAdapter = new PlacePickerAdapter(getApplicationContext(), frs);
                        spinRecyclerView.setAdapter(restaurantAdapter);
                    }

                    @Override
                    public void onFailure(Call<FoursquareJSON> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Mr. Nouger can't connect to Foursquare's servers!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Mr. Nouger can't determine your current location!", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    public void addToRestaurantDB(List<FoursquareResults> results){

        listRestaurantSource = RestaurantService.getAllRestaurants(this);

        if(listRestaurantSource.size() != 0) {
            return;
        }

        // Iterate through results
        // Convert each element in results into a Restaurant object
        // Call RestaurantService.createRestaurant() to add the newly made Restaurant object to the DB
        // TODO: You might want to add a checker somewhere to make sure you never convert an empty list.

        Restaurant r;
        int count=0;

        for(FoursquareResults fr : results){
            count++;
            r = new Restaurant();
            r.setVenueId(fr.venue.id);
            r.setrName(fr.venue.name);
            if(fr.venue.location.address == null)
            {
                r.setrAddress("Unspecified Address");
            }
            else
            r.setrAddress(fr.venue.location.address);
            r.setrLat(fr.venue.location.lat);
            r.setrLong(fr.venue.location.lng);
            r.setRating((float) fr.venue.rating);
            r.setIcon(R.drawable.jollibee);
            r.setSelected(false);
            r.setBookmarked(false);
            restaurantAdapter.notifyDataSetChanged();
            //RestaurantService.createRestaurant(this, r);
            if(count == 9)
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Reconnects to the Google API
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Disconnects from the Google API
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "Mr. Nouger can't connect to Google's servers!", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void onFilterClick(View view){

        startActivity(new Intent(this, FilterActivity.class));

    }
}