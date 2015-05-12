package com.mymovielib;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import info.awesomedevelopment.tvgrid.library.TVGridView;

/**
 * Created by Артур on 10.12.2015.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder>
{

    private TVGridView mRecyclerView;
    private static int selectedItem = -1;
    private List<Movie> mMovieList;
    private LayoutInflater mInflater;
    private Context mContext;

    private final ArrayList<Integer> selected = new ArrayList<>();
    public MoviesAdapter(Context context, TVGridView recyclerView)
    {

        mRecyclerView = recyclerView;

        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mMovieList = new ArrayList<>();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
      //  final int layout = viewType == TYPE_INACTIVE ? R.layout.row_movie : R.layout.item_active;
        View view = mInflater.inflate(R.layout.row_movie, parent, false);

        MovieViewHolder viewHolder = new MovieViewHolder(view);


        return viewHolder;
    }
    @Override
    public void onViewAttachedToWindow(MovieViewHolder vh) {

        System.out.println("FOCUS FIUCN");
    }
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position)
    {
        holder.itemView.setFocusable(true);


      /*  holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.selectView(v, true);
            }
        });*/


        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(final View view, final boolean b) {
                mRecyclerView.selectView(view, b);



            }

        });

    Movie movie = mMovieList.get(position);

        // This is how we use Picasso to load images from the internet.
        Picasso.with(mContext)
                .load(movie.getPoster())
                .placeholder(R.color.colorAccent)
                .into(holder.imageView);

     //   holder.selectedOverlay.getVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
    }



    @Override
    public int getItemCount()
    {
        return (mMovieList == null) ? 0 : mMovieList.size();
    }

    public void setMovieList(List<Movie> movieList)
    {
        this.mMovieList.clear();
        this.mMovieList.addAll(movieList);
        // The adapter needs to know that the data has changed. If we don't call this, app will crash.
        notifyDataSetChanged();
    }






}
