package com.matthewhuie.mrjitters.Adapters;

/**
 * Created by Ronnie Nieva on 24/03/2017.
 */


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.matthewhuie.mrjitters.R;
import com.matthewhuie.mrjitters.Beans.Restaurant;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>{

    private List<Restaurant> restaurantSourceList;

    public BookmarkAdapter(List<Restaurant> restaurantSourceList){
        this.restaurantSourceList = restaurantSourceList;
    }

    public class BookmarkViewHolder extends RecyclerView.ViewHolder {
        public View context;
        public TextView name, address, rate;
        public ImageView icon;

        public BookmarkViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.bookmark_name);
            address = (TextView) view.findViewById(R.id.bookmark_address);
            rate = (TextView) view.findViewById(R.id.bookmark_rate);
            //icon = (ImageView) view.findViewById(R.id.bookmark_icon);
            context = view;
        }
    }

    @Override
    public BookmarkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookmark_item, parent, false);

        return new BookmarkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookmarkAdapter.BookmarkViewHolder holder, int position) {
        Restaurant restaurantSource = restaurantSourceList.get(position);
        holder.name.setText(restaurantSource.getrName());
        holder.address.setText(restaurantSource.getrAddress());
        holder.rate.setText(restaurantSource.getRating() + "");
        // holder.icon.setImageDrawable(holder.context.getResources().getDrawable(bookmark.getRestaurantIcon()));
    }

    @Override
    public int getItemCount() {
        return restaurantSourceList.size();
    }

}
