package com.matthewhuie.mrjitters.Adapters;

/**
 * Created by CCS on 12/02/2017.
 */


import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.matthewhuie.mrjitters.R;
import com.matthewhuie.mrjitters.Beans.Restaurant;

import java.util.List;


public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    //All methods in this adapter are required for a bare minimum recyclerview adapter

    private List<Restaurant> restaurantSourceList;

    // Constructor of the class
    public RestaurantAdapter(List<Restaurant> restaurantSourceList) {
        this.restaurantSourceList = restaurantSourceList;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return restaurantSourceList == null ? 0 : restaurantSourceList.size();
    }


    // specify the row layout file and click for each row
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new ViewHolder(view);

    }

    // load data in each row element
    @Override
    public void onBindViewHolder(ViewHolder holder, int listPosition) {
        Restaurant restaurant = restaurantSourceList.get(listPosition);
        if(listPosition % 2 == 0){
            holder.itemView.setBackgroundColor(Color.argb(100,255, 238, 181));
        }
        else holder.itemView.setBackgroundColor(Color.argb(100,255, 202, 105));

        holder.label.setText(restaurant.getrName());
    }

    // Static inner class to initialize the views of rows
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView label;
        Button button;


        public ViewHolder(View itemView) {
            super(itemView);


            label = (TextView) itemView.findViewById(R.id.fruit_label);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + label.getText());
            Toast.makeText(view.getContext(), label.getText(), Toast.LENGTH_SHORT).show();

        }


    }
}

