package com.example.android.popularmovieapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {

    TextView refresh;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        refresh = findViewById(R.id.refresh);

        //check to see if network connection is available
        if (!isNetworkAvailable()){
            refresh.setVisibility(View.VISIBLE);
            Toast.makeText(SplashScreenActivity.this, "Sorry, there is no active network connection", Toast.LENGTH_LONG).show();

        } else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    refresh.setVisibility(View.VISIBLE);
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    Log.i("Act", "ActivityX could not start");
                }
            }, 2350);
        }




        //set onClck on the refresh texf
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkAvailable()){
                    refresh.setVisibility(View.VISIBLE);
                    Toast.makeText(SplashScreenActivity.this, "Sorry, there is no active network connection", Toast.LENGTH_LONG).show();
                    return;
                }

                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            }
        });
    }


    //this method returns true if there is active network connection
    private boolean isNetworkAvailable() {
        ConnectivityManager con = (ConnectivityManager) getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = con.getActiveNetworkInfo();
        return  activeNetwork != null && activeNetwork.isConnected();
    }
}