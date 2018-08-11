package com.example.android.popularmovieapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DetailActivity extends AppCompatActivity {

    TextView overview_tv;
    ImageView image_tv;
    TextView name_tv;
    String youKey;
    TextView ratings;
    Context context;
    TextView release_date;
    ImageView backdrop_poster;

    public static List<Youtube> youtube;
    private  static final String IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    private static final String THE_MOVIEDB_URL2 = "https://api.themoviedb.org/3/movie/";
    private static final String MOVIE_QUERY2 = "api_key";
    private static final String API_KEY2 = "";
    private String SEARCH_QUERY2 = "videos";
    private String SEARCH_ID;
    int movieId;
    String homepage;



    //Navigation arrow on the acton bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        youtube = new ArrayList<>();
        //Navigation arrow on the acton bar; check also override onOptionsItemSelected
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        context = getApplicationContext();

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        overview_tv = findViewById(R.id.overview);
        image_tv = findViewById(R.id.image);
        name_tv = findViewById(R.id.name);
        ratings = findViewById(R.id.ratings);
        release_date = findViewById(R.id.release_date);
        backdrop_poster = findViewById(R.id.backdrop_poster);

        String name = getIntent().getStringExtra("Name");
        String overview = getIntent().getStringExtra("Overview");
        String image = getIntent().getStringExtra("Image");
        movieId = getIntent().getIntExtra("movieId", 1);
        String backdrop = getIntent().getStringExtra("backdrop");
        String releaseDate = getIntent().getStringExtra("releaseDate");
        String rating = getIntent().getStringExtra("rating");
        String df = String.valueOf(getIntent().getIntExtra("movieId", 1));
        //Toast.makeText(DetailActivity.this, "MovieId String " + df, Toast.LENGTH_SHORT).show();

        name_tv.setText(name);
        overview_tv.setText(overview);
        ratings.setText("Rating: " + rating);
        release_date.setText("Release Date: " + releaseDate);

        Picasso.with(context)
                .load(IMAGE_URL + backdrop)
                .fit()
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(backdrop_poster);

        Picasso.with(context)
                .load(IMAGE_URL + image)
                .fit()
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(image_tv);

        //new GetYouTubeKeyAsyncTask().execute();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, "Error getting intents", Toast.LENGTH_SHORT).show();
    }

    public URL buildUrl2(String stringUrl) {
        Uri uri = Uri.parse(THE_MOVIEDB_URL2).buildUpon()
                .appendPath(stringUrl)
                .appendPath(SEARCH_QUERY2)
                .appendQueryParameter(MOVIE_QUERY2, API_KEY2)
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException exception) {
            Log.e(TAG,"Error creating URL", exception);
        }
        return url;
    }

//    private class GetYouTubeKeyAsyncTask extends AsyncTask<String, Void, List<Youtube>> {
//
//        @Override
//        protected List<Youtube> doInBackground(String...strings){
//
//            String g = String.valueOf(movieId);
//
//            // Create URL object
//            URL url = buildUrl2(g);
//
//            // Perform HTTP request to the URL and receive a JSON response back
//            String jsonResponse = "";
//            try {
//                jsonResponse = getResponseFromHttpUrl(url);
//            } catch (Exception e) {
//
//                e.printStackTrace();
//            }
//
//            youtube = MovieJsonUtils.parseToYouTube(jsonResponse);
//            //Toast.makeText(DetailActivity.this, "YouTube Key parsed" + youtube, Toast.LENGTH_SHORT).show();
//
//            // Return the {@link Movie} object as the result fo the {@link TsunamiAsyncTask}
//            return youtube;
//        }
//
//        /**
//         * Update the screen with the given Movie (which was the result of the
//         */
//        @Override
//        protected void onPostExecute(List<Youtube> youtub) {
//            //super.onPostExecute(youtube);
//            new GetYouTubeKeyAsyncTask().cancel(true);
//
//            youtub = youtube;
//            Toast.makeText(DetailActivity.this, "YouTube Key" + youtub, Toast.LENGTH_SHORT).show();
//
//            if (youtube != null) {
//                youKey = youtube.get(0).getKey();
//
//                Toast.makeText(DetailActivity.this, "YouKey" + youKey, Toast.LENGTH_LONG).show();
//                webView = findViewById(R.id.web_view);
//                webView.loadUrl("https://www.youtube.com/watch?v=" + youKey);
//            }
//        }
//    }
}
