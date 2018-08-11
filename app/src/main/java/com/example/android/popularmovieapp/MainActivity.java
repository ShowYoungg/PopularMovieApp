package com.example.android.popularmovieapp;


import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import static android.support.v7.widget.DividerItemDecoration.HORIZONTAL;
import static android.support.v7.widget.DividerItemDecoration.VERTICAL;


public class MainActivity extends AppCompatActivity {

    private ImageGridAdapter mAdapter;
    private RecyclerView mImageGrid;
    private String image;
    private String name;
    public static List<Movie> movies;
    Context context;
    private static final String THE_MOVIEDB_URL = "https://api.themoviedb.org/3/movie/";
    private static final String MOVIE_QUERY = "api_key";
    private static final String API_KEY = "";
    private String SEARCH_QUERY = "popular";
    ProgressBar mLoadingIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = new ArrayList<>();

        mLoadingIndicator = findViewById(R.id.loading_indicator);

        mImageGrid = findViewById(R.id.rv_movies);

        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mImageGrid.setLayoutManager(layoutManager);

        mImageGrid.setHasFixedSize(true);

        ImageGridAdapter mAdapter = new ImageGridAdapter(this, movies);

        mImageGrid.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(this, VERTICAL);
        mImageGrid.addItemDecoration(decoration);

        DividerItemDecoration decoration2 = new DividerItemDecoration(this, HORIZONTAL);
        mImageGrid.addItemDecoration(decoration2);

        new GetJsonAsyncTask().execute();
    }

    //A method to build the URL to be queried
    private URL buildUrl(String stringUrl) {
        Uri uri = Uri.parse(THE_MOVIEDB_URL).buildUpon()
                .appendPath(stringUrl)
                .appendQueryParameter(MOVIE_QUERY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException exception) {
            Log.e(TAG, "Error with creating URL", exception);
        }
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else return null;
        } finally {
            urlConnection.disconnect();
        }
    }

    private class GetJsonAsyncTask extends AsyncTask<String, Void, List<Movie>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(String... strings) {

            // Create URL object
            URL url = buildUrl(SEARCH_QUERY);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = getResponseFromHttpUrl(url);
            } catch (Exception e) {

                e.printStackTrace();
            }

            // Extract relevant fields from the JSON response and create an {@link Movie} object
            movies = MovieJsonUtils.parseMovieJson(jsonResponse);

            // Return Movie object as the result
            return movies;
        }

        /**
         * Update the screen with the given Movie (which was the result of the
         * {@link GetJsonAsyncTask}).
         */
        @Override
        protected void onPostExecute(List<Movie> mov) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            new GetJsonAsyncTask().cancel(true);
            //super.onPostExecute(movies);
            mov = movies;

            if (mov != null) {
                ImageGridAdapter rcv = new ImageGridAdapter(MainActivity.this, mov);
                mImageGrid.setAdapter(rcv);
            } else {
                // populateMovieData();
            }
        }

    }

//    private void populateMovieData() {
//        Movie mov = new Movie("abc", "Tonu Tonero Tony Tonero Tony", "This is a man that assesses my worth", 7878, "This is a man that assesses my worth", "This is a man that assesses my worth", "This is a man that assesses my worth");
//        movies.add(mov);
//
//        mov = new Movie("def", "Cindy", "This is a man that", 858, "def", "def", "def");
//        movies.add(mov);
//
//        mov = new Movie("ghi", "Tona", "This is a man that assesses", 4144, "def", "def", "def");
//        movies.add(mov);
//
//        mov = new Movie("jkl", "Sandra", "assesses my worth", 6222, "def", "def", "def");
//        movies.add(mov);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.param_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_popularity:
                SEARCH_QUERY = "popular";
                Log.i("url", "url: " + buildUrl(SEARCH_QUERY));
                new GetJsonAsyncTask().execute();
                break;
            case R.id.menu_top_rated:
                SEARCH_QUERY = "top_rated";
                Log.i("url", "url: " + buildUrl(SEARCH_QUERY));
                new GetJsonAsyncTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
