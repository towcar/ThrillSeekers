package com.carsonskjerdal.app.thrillseekers.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.carsonskjerdal.app.thrillseekers.R;
import com.carsonskjerdal.app.thrillseekers.adapters.CustomAdapter;
import com.carsonskjerdal.app.thrillseekers.items.ThrillItem;
import com.carsonskjerdal.slicermenu.animation.SlicerAnimation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Carson on 4/13/2018.
 * <p>
 * Feel free to use code just give credit please :)
 */


public class MainActivity extends AppCompatActivity {

    private CustomAdapter mAdapter;
    public List<ThrillItem> list = new ArrayList<>();
    LocationManager locationManager;
    boolean GpsStatus;
    private static final int permResult = 1;

    private static final long RIPPLE_DURATION = 250;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_content)
    FrameLayout root;
    @BindView(R.id.menu_button)
    View menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.carsonskjerdal.app.thrillseekers.R.layout.activity_main);

        ButterKnife.bind(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }



        //should this be run in an object outside of onCreate? Mostly just text
        // Here, thisActivity is the current activity, if permission called does not equal permission granted.. request permission
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, permResult);
            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }

        // Create the recycler view
        RecyclerView myRecycler = findViewById(R.id.recycler);

        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myRecycler.setLayoutManager(llm);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(myRecycler.getContext(), llm.getOrientation());
        myRecycler.addItemDecoration(dividerItemDecoration);

        //set up a list builder here
        list = buildList();
        /*for (int i = 0; i < listSend.size(); i++) {
            Players player = new Players(listSend.get(i), 0, false);
            list.add(player);
        }*/

        //add item to list

        //should pass list into the custom adapter
        mAdapter = new CustomAdapter(list);


        myRecycler.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        CheckGpsStatus();
        if (!GpsStatus) {
            Toast.makeText(this, "Location Services are not enabled!",
                    Toast.LENGTH_LONG).show();
        }
        //IsGPSEnabled();

        View slicerMenu = LayoutInflater.from(this).inflate(R.layout.menu_slicer, null);
        root.addView(slicerMenu);

        new SlicerAnimation.GuillotineBuilder(slicerMenu, slicerMenu.findViewById(R.id.slicer_button), menuButton)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

    }

    private List<ThrillItem> buildList() {

        //Log.e("buildList","Building");
        //build a list
        List<ThrillItem> list = new ArrayList<>();

        //populate items
        String[] arrayNames = {"Brewery Tour", "Bungee Jumping", "Cave Exploring", //"Camping",
                "Cliff Jumping", "Drive In Theater", "Escape Room Challenge", //"Golfing",
                "Helicopter Tour", "Horseback Riding",
                "Kayaking", "Maze Challenge", "Mountain Biking", "Paintball", "Roller Coaster Parks", "River Rapid Riding", "Sand Surfing", "Scuba Diving", "Shark Cage Diving", "Skydiving",
                "Snowboarding", "Surfing", "Volcano Trekking" ,"Whale Watching", "Wine Tasting", "Yachting", "Zip Lining" };

        Integer[] arrayImages = {R.drawable.brewerylist4, R.drawable.bungeelist2, R.drawable.cavelist2, /*R.drawable.campinglist,*/ R.drawable.clifflist2, R.drawable.drivelist2, R.drawable.escapelist2, //R.drawable.golflist,
                R.drawable.helicopterlist2, R.drawable.horselist2, R.drawable.kayaklist2, R.drawable.mazelist2, R.drawable.bikinglist2, R.drawable.paintlist2, R.drawable.rollercoasterlist2,
                R.drawable.rapidslist2, R.drawable.sandlist2, R.drawable.scubalist2, R.drawable.sharklist2, R.drawable.skydivinglist2, R.drawable.snowboardlist2, R.drawable.surfinglist2, R.drawable.volcanolist2,
                R.drawable.whalelist2, R.drawable.winelist2, R.drawable.yachtlist2, R.drawable.ziplininglist2};

        //loop into list
        for (int i = 0; i < arrayNames.length; i++) {
            ThrillItem item = new ThrillItem(arrayNames[i], arrayImages[i]);
            list.add(item);
        }
        //return filled list
        return list;
    }


    protected void onResume() {
        super.onResume();

        CheckGpsStatus();
        if (!GpsStatus) {
            // View parentLayout = findViewById(android.R.id.content);
            //Snackbar.make(parentLayout, "Location Services are not enabled!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
        //IsGPSEnabled();
    }

    //currently unused, potentially lots of functionallity here
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    public void CheckGpsStatus() {

        Context context = getApplicationContext();

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }



}
