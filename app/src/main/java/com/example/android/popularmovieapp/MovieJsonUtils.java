package com.example.android.popularmovieapp;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

import static com.example.android.popularmovieapp.DetailActivity.youtube;
import static com.example.android.popularmovieapp.MainActivity.movies;

/**
 * Created by SHOW on 7/30/2018.
 */

public class MovieJsonUtils {

    private static final String SEARCH_RESULT = "results";
    private static final String POSTER_PATH = "poster_path";
    private static final String TITLE = "title";
    private static final String OVERVIEW = "overview";
    private static final String MOVIE_ID = "id";
    private static final String RELEASE_DATE = "release_date";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String BACKDROP_PATH = "backdrop_path";
    private static final String YOUTUBE_KEY = "key";


    public static List<Movie> parseMovieJson(String json) {
        movies.clear();

        try {
            JSONObject mainJsonObject = new JSONObject(json);
            Movie movie;

            JSONArray movieArray = mainJsonObject.getJSONArray(SEARCH_RESULT);
            for ( int i = 0; i < movieArray.length(); i++ ) {
                JSONObject mainJsonObject2 = movieArray.getJSONObject(i);
                String poster_path = mainJsonObject2.optString(POSTER_PATH);
                String title = mainJsonObject2.optString(TITLE);
                String overview = mainJsonObject2.optString(OVERVIEW);
                int movieId = mainJsonObject2.getInt(MOVIE_ID);
                String releaseDate = mainJsonObject2.optString(RELEASE_DATE);
                String rating = mainJsonObject2.optString(VOTE_AVERAGE);
                String backdrop = mainJsonObject2.optString(BACKDROP_PATH);

                movie = new Movie(poster_path, title, overview, movieId, rating, backdrop, releaseDate);
                movie.setImage(poster_path);
                movie.setName(title);
                movie.setOverview(overview);
                movie.setMovieId(movieId);
                movie.setRating(rating);
                movie.setBackdrop(backdrop);
                movie.setReleaseDate(releaseDate);

                movies.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }

    public static List<Youtube> parseToYouTube (String json){

        try {
            JSONObject mJsonObject = new JSONObject(json);
            JSONArray videosArray = mJsonObject.getJSONArray(SEARCH_RESULT);
            Youtube movieYou;

            for (int i = 0; i < videosArray.length(); i++) {
                JSONObject mJsonObject2 = videosArray.getJSONObject(i);
                String youTubeKey = mJsonObject2.optString(YOUTUBE_KEY);
                movieYou = new Youtube(youTubeKey);
                movieYou.setKey(youTubeKey);
                youtube.add(movieYou);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return youtube;
    }
}
