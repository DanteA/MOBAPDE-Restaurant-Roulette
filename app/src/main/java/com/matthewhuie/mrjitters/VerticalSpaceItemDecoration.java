package com.matthewhuie.mrjitters;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Iraetus on 19/02/2017.
 */

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration{
    private final int verticalSpaceHeight;

    public VerticalSpaceItemDecoration(int verticalSpaceHeight){
        this.verticalSpaceHeight = verticalSpaceHeight;
    }


    public void getItemOffset(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
        outRect.bottom = verticalSpaceHeight;
    }
}
