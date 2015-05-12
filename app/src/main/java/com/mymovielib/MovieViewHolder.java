package com.mymovielib;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by Артур on 10.12.2015.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder

{
    private static final String TAG = MovieViewHolder.class.getSimpleName();
    public ImageView imageView;
    public RelativeLayout back;
    View selectedOverlay;

    public MovieViewHolder(View itemView)
    {
        super(itemView);

        // itemView.setClickable(true);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
      //  back = (LinearLayout) itemView.findViewById(R.id.back);
     //   selectedOverlay = itemView.findViewById(R.id.selected_overlay);


    }


}

