package com.example.android.popularmovieapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static android.content.ContentValues.TAG;
import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by SHOW on 7/30/2018.
 */

public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.GridViewHolder> {
    String mName;
    String mImage;
    Context mContext;
    Context context1;
    private List<Movie> mMovieList = null;
    private  static final String IMAGE_URL = "http://image.tmdb.org/t/p/w300/";


    public ImageGridAdapter(Context context, List<Movie> movieList){
        mContext = context;
        mMovieList = movieList;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.image_grid;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        GridViewHolder viewHolder = new GridViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {

        //populate views
        final Movie movie = mMovieList.get(position);

        holder.name.setText(movie.getName());
        try {
            Picasso.with(mContext)
                    .load(IMAGE_URL + movie.getImage())
                    .fit()
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .into(holder.image);
            Log.i("Picasso", "This is from Picasso" + mMovieList );

        } catch (Exception e) {
            Log.i("Error", "This is from Picasso", e);
        }

        //Set onClick Listener and gather all info to be displayed in DetailsActivity via implicit intent

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("Image", movie.getImage() );
                intent.putExtra("Overview", movie.getOverview());
                intent.putExtra("Name", movie.getName());
                intent.putExtra("movieId", movie.getMovieId());
                intent.putExtra("releaseDate", movie.getReleaseDate());
                intent.putExtra("rating", movie.getRating());
                intent.putExtra("backdrop", movie.getBackdrop());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
       return mMovieList.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;


        public GridViewHolder(View itemView){
            super (itemView);

            image = itemView.findViewById(R.id.movie_img);
            name = itemView.findViewById(R.id.movie_name);

        }
    }

}
