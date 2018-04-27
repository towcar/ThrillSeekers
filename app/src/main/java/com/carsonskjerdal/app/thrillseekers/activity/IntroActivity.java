package com.carsonskjerdal.app.thrillseekers.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;

import com.carsonskjerdal.app.thrillseekers.R;
import com.carsonskjerdal.app.thrillseekers.fragments.CustomSlideFragment1;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;

/**
 * Created by Carson on 4/18/2018.
 * <p>
 * Feel free to use code just give credit please :)
 */

public class IntroActivity extends MaterialIntroActivity {

    Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking for first time launch - before calling setContentView()


        enableLastSlideAlphaExitTransition(true);

        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });


        //first
        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.first_slide_background)
                        .buttonsColor(R.color.first_slide_buttons)
                        .image(R.drawable.mnticon)
                        .title("Experience The World Around You")
                        .description("Find hidden activities near you!")
                        .build());


        //second
        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.second_slide_background)
                .buttonsColor(R.color.second_slide_buttons)
                .image(R.drawable.compass)
                .title("Find Your Way Easily")
                .description("We offer an easy connection to Google Maps to find your way to your destination")
                .build());

        //addSlide(new CustomSlide());

        //third
        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.third_slide_background)
                        .buttonsColor(R.color.third_slide_buttons)
                        //.possiblePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS})
                        //.neededPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
                        .image(R.drawable.canoeicon)
                        .title("Expanding Options")
                        .description("We are constantly updating our content thanks to users like you!")
                        .build());

        /*addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.fourth_slide_background)
                .buttonsColor(R.color.fourth_slide_buttons)
                .image(R.drawable.travel)
                .title("Welcome To Thrill Seekers Canada")
                .description("The journey has just begun!")
                .build());*/
        addSlide(new CustomSlideFragment1());
    }


    @Override
    public void onFinish() {
        super.onFinish();
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        //Toast.makeText(this, "Fuck it closed", Toast.LENGTH_SHORT).show();
    }

}
