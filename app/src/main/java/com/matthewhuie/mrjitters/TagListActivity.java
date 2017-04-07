package com.matthewhuie.mrjitters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.matthewhuie.mrjitters.Beans.Tag;
import com.matthewhuie.mrjitters.Adapters.TagsListAdapter;
import com.matthewhuie.mrjitters.R;


import java.util.ArrayList;

/**
 * Created by Ronnie Nieva on 29/03/2017.
 */

public class TagListActivity extends AppCompatActivity {
    public static ArrayList<Tag> tagList = new ArrayList<>();;
    private TagsListAdapter tagsListAdapter;
    private RecyclerView tagsListRecyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tags_list);
        setTitle("Select Tags");

        tagsListRecyclerView = (RecyclerView) findViewById(R.id.tags_tags_list);

        tagsListAdapter = new TagsListAdapter(tagList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        tagsListRecyclerView.setLayoutManager(mLayoutManager);
        tagsListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        tagsListRecyclerView.setAdapter(tagsListAdapter);

        populateTagsList();

    }


    private void populateTagsList(){
        if(tagList.size() > 0)
            return;
        Tag tag;

        tag = new Tag();
        tag.setId(1);
        tag.setName("American");
        tag.setSelected(false);
        tagList.add(tag);

        tag = new Tag();
        tag.setId(2);
        tag.setName("Arabian");
        tag.setSelected(false);
        tagList.add(tag);
        tagsListAdapter.notifyDataSetChanged();
    }

}
