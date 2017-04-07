package com.matthewhuie.mrjitters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ronnie Nieva on 24/03/2017.
 */

public class FilterActivity extends AppCompatActivity {

    Button tagsButton;

    public static ArrayList<String> selectedTagsList;
    TextView textTags;

    SeekBar seek_distance, seek_rating, seek_price;
    TextView text_distance, text_rating, text_price;

    private int maxDistance = 5, maxStars = 5, maxPrice = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        setTitle("Filter");

        // ---- tags ---- //
        textTags = (TextView)findViewById(R.id.filter_tags_text);
        selectedTagsList = new ArrayList<String>();
        tagsButton = (Button) findViewById(R.id.filter_tags_icon);
        tagsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                startActivity(new Intent(FilterActivity.this, TagListActivity.class));
            }
        });
        ///////////////////


        text_price = (TextView) findViewById(R.id.priceIndicator);
        seek_price = (SeekBar) findViewById(R.id.priceSeekBar);
        seek_price.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(progress == 0){
                    text_rating.setText("Any Price");
                    return;
                }

                int division = 100 / maxPrice;
                for(int i=1; i<=maxPrice; i++){
                    if(progress <= 1 * division) {
                        text_price.setText("PHP 100-200");
                        return;
                    }
                    else if(progress <= 2 * division){
                        text_price.setText("PHP 200-500");
                        return;
                    }
                    else if(progress <= 3 * division){
                        text_price.setText("PHP 500-1000");
                        return;
                    }
                    else
                        text_price.setText("PHP 1000+");
                    return;
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        text_rating = (TextView) findViewById(R.id.ratingIndicator);
        seek_rating = (SeekBar) findViewById(R.id.ratingSeekBar);
        seek_rating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(progress == 0){
                    text_rating.setText("Any Rating");
                    return;
                }
                int division = 100 / maxStars;
                for(int i=1; i<=maxStars; i++){
                    if(progress <= division * i){
                        text_rating.setText(i + " Stars");
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

        text_distance = (TextView) findViewById(R.id.distanceIndicator);
        seek_distance = (SeekBar) findViewById(R.id.distanceSeekBar);
        seek_distance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress == 0){
                    text_distance.setText("Any Distance");
                    return;
                }
                int division = 100 / maxDistance;
                for(int i=1; i<=maxDistance; i++){
                    if(progress <= division * i){
                        text_distance.setText(i + "");
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

        setSelectedTagList();

    }

    private void setSelectedTagList(){

        Log.d("FilterActivity", TagListActivity.tagList.size() + "");

        if(TagListActivity.tagList == null || TagListActivity.tagList.size() == 0){
            textTags.setText("Any Tags");
            return;
        }

        textTags.setText("");
        String comma =", ";
        for(int i = 0; i< TagListActivity.tagList.size(); i++){
            if(TagListActivity.tagList.get(i).isSelected()) {
                selectedTagsList.add(TagListActivity.tagList.get(i).getName());

                if(i == 0)
                    comma = "";
                else
                    comma = ", ";

                textTags.setText(textTags.getText() + comma + TagListActivity.tagList.get(i).getName());
            }
        }



    }

}
