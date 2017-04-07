package com.matthewhuie.mrjitters.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.matthewhuie.mrjitters.R;
import com.matthewhuie.mrjitters.Beans.Tag;

import java.util.ArrayList;

/**
 * Created by Ronnie Nieva on 29/03/2017.
 */

public class TagsListAdapter extends RecyclerView.Adapter<TagsListAdapter.TagViewHolder>{

    private ArrayList<Tag> tagList;

    public TagsListAdapter(ArrayList<Tag> tagList){
        this.tagList = tagList;
    }

    public class TagViewHolder extends RecyclerView.ViewHolder {
        public View context;
        public TextView name;
        public CheckBox check;
        public int tagPosition;

        public TagViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tag_name);
            check = (CheckBox) view.findViewById(R.id.tag_tag_checkbox);
            check.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(((CheckBox) v).isChecked()){
                        tagList.get(tagPosition).setSelected(true);
                    }
                    else
                        tagList.get(tagPosition).setSelected(false);

                }
            });
            context = view;
        }
    }

    @Override
    public TagsListAdapter.TagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag_item, parent, false);

        return new TagsListAdapter.TagViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TagsListAdapter.TagViewHolder holder, int position) {
        Tag tag = tagList.get(position);
        holder.name.setText(tag.getName());
        holder.check.setChecked(tag.isSelected());
        holder.tagPosition = position;
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

}
