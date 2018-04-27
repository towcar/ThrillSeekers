package com.carsonskjerdal.app.thrillseekers.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.carsonskjerdal.app.thrillseekers.BuilderManager;
import com.carsonskjerdal.app.thrillseekers.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nightonke.boommenu.Animation.BoomEnum;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.BoomButtonBuilder;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.util.ArrayList;
import java.util.List;

import static com.nightonke.boommenu.ButtonEnum.Ham;

/**
 * Created by Carson on 4/17/2018.
 * <p>
 * Feel free to use code just give credit please :)
 */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleApiClient mGoogleApiClient;
    private ArrayList<Pair> piecesAndButtons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.carsonskjerdal.app.thrillseekers.R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setupBoom();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng sydney = new LatLng(-33.852, 151.211);
        map.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        map.setMyLocationEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
    }

    public void setupBoom() {
        final BoomMenuButton bmb = findViewById(R.id.bmb);

        bmb.setButtonEnum(Ham);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_4);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_4);
        bmb.setRippleEffect(true);

        // set up the item builders with individual specs and clicks
        HamButton.Builder builder = new HamButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        Toast.makeText(MapActivity.this, "Clicked " + index, Toast.LENGTH_SHORT).show();
                    }
                })
                .normalColorRes(R.color.boomColor1)
                .normalImageRes(R.drawable.brewmapicon)
                .normalText("Show All")
                .subNormalText("Displays every location on map");


        bmb.addBuilder(builder);

        HamButton.Builder builder2 = new HamButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        Toast.makeText(MapActivity.this, "Clicked " + index, Toast.LENGTH_SHORT).show();
                    }
                })
                .normalColorRes(R.color.boomColor2)
                .normalImageRes(R.drawable.brewmapicon)
                .normalText("Show Closest")
                .subNormalText("Displays the closest marker to your location");

        bmb.addBuilder(builder2);

        HamButton.Builder builder3 = new HamButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        Toast.makeText(MapActivity.this, "Clicked " + index, Toast.LENGTH_SHORT).show();
                    }
                })
                .normalColorRes(R.color.boomColor3)
                .normalImageRes(R.drawable.brewmapicon)
                .normalText("Settings")
                .subNormalText("Brings up the settings menu");

        bmb.addBuilder(builder3);

        HamButton.Builder builder4 = new HamButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        Toast.makeText(MapActivity.this, "Clicked " + index, Toast.LENGTH_SHORT).show();
                    }
                })
                .normalColorRes(R.color.boomColor4)
                .normalImageRes(R.drawable.brewmapicon)
                .normalText("Help")
                .subNormalText("Brings Up Detailed Information");

        bmb.addBuilder(builder4);


    }

}
