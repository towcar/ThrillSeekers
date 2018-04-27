package com.carsonskjerdal.app.thrillseekers.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carsonskjerdal.app.thrillseekers.R;
import com.carsonskjerdal.app.thrillseekers.data.DatabaseHelper;
import com.carsonskjerdal.app.thrillseekers.models.Places;

import agency.tango.materialintroscreen.SlideFragment;
import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;


/**
 * Created by Carson on 4/26/2018.
 * <p>
 * Feel free to use code just give credit please :)
 */
public class CustomSlideFragment1 extends SlideFragment {

    KonfettiView konfettiView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_custom_slide, container, false);
        //konfettiView = view.findViewById(R.id.konfettiView);

        //builKonfetti();

        //buildDatabase();

        return view;
    }

    @Override
    public int backgroundColor() {
        return R.color.fourth_slide_background;
    }

    @Override
    public int buttonsColor() {
        return R.color.fourth_slide_buttons;
    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return getString(R.string.error_message);
    }

    private void builKonfetti(){

        konfettiView.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(new Size(12, 12))
                .setPosition(-50f, 1000f, -50f, -50f)
                .stream(300, 5000L);
    }

    private void buildDatabase(){
        final DatabaseHelper db = new DatabaseHelper(getActivity());
        Places place;

        place = new Places("name", "lat", "lon", "brewery");
        db.addPlace(place);

    }
}
