package com.carsonskjerdal.app.thrillseekers.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.carsonskjerdal.app.thrillseekers.R;

/**
 * Created by Carson on 4/18/2018.
 * <p>
 * Feel free to use code just give credit please :)
 */
public class SplashActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


        SharedPreferences settings = getSharedPreferences("prefs", 0);
        boolean firstRun = settings.getBoolean("firstRun", false);
        if (firstRun)//if running for first time
        //Splash will load for first time
        {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstRun", true);
            editor.apply();
            Intent i = new Intent(SplashActivity.this, IntroActivity.class);
            startActivity(i);
            finish();
        } else {

            Intent a = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(a);
            finish();
        }


            }
        }, 500);
    }
}

/*
new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!isIntroPlayed) {

                    Intent intent = new Intent(context, IntroActivity.class);
                    startActivity(intent);
                    isIntroPlayed = sharedPref.getBoolean("isIntroPlayed", true);
                    finish();
                } else {
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 500);
 */