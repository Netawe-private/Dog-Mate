package com.example.dogmate.Scan_Location;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.dogmate.Add_Location.AddLocationFragmentEntertainment;
import com.example.dogmate.Add_Location.AddLocationFragmentNature;
import com.example.dogmate.Add_Location.AddLocationFragmentParks;
import com.example.dogmate.Add_Location.AddLocationFragmentServicesShop;
import com.example.dogmate.Add_Location.AddLocationFragmentServicesVet;
import com.example.dogmate.Add_Location.AddLocationFragmentVacationCamping;
import com.example.dogmate.Add_Location.AddLocationFragmentVacationHotel;
import com.example.dogmate.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class LocationDetails extends AppCompatActivity implements OnMapReadyCallback {
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    private TextView locatioNameView;
    private TextView locationAddressView;
    private MapView mMapView;
    private LatLng locationScanLatLng;
    private String locationName;
    private RatingBar ratingLocationDetails;
    String locationRating;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        // Enable the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);

        initGoogleMap(savedInstanceState);

        Intent intent = getIntent();
        String scanningResult = intent.getStringExtra("scan_result");

        locationAddressView = findViewById(R.id.locationDetailsAddress);
        locatioNameView = findViewById(R.id.locationDetailsName);
        ratingLocationDetails = findViewById(R.id.ratingLocationDetails);

        try {
            JSONObject scanningResultJson = new JSONObject(scanningResult);
            locationScanLatLng = new LatLng(
                    scanningResultJson.getDouble("location_lat"),
                    scanningResultJson.getDouble("location_lng"));
            locationRating = scanningResultJson.getString("rating");
            locationName = scanningResultJson.getString("location_name");
            locatioNameView.setText(locationName);
            locationAddressView.setText(scanningResultJson.getString("address"));

            if (locationRating.equals("0") ){
                ratingLocationDetails.setVisibility(View.GONE);
            }
            else {
                ratingLocationDetails.setRating(Float.valueOf(locationRating));
            }
            openLocationFragment(scanningResultJson);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initGoogleMap(Bundle savedInstanceState){
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
    }

    public void openLocationFragment(JSONObject scanResult){

        try {
            String locationType = scanResult.getString("location_type");
            Bundle data = addFieldsToBundleData(scanResult);
            switch (locationType){
                case "Nature":
                    AddLocationFragmentNature nature = new AddLocationFragmentNature();
                    nature.setArguments(data);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,
                            nature, "nature").commit();
                    break;
                case "Entertainment":
                    AddLocationFragmentEntertainment Entertainment = new AddLocationFragmentEntertainment();
                    Entertainment.setArguments(data);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,
                            Entertainment, "entertainment").commit();
                    break;
                case "Services_shop":
                    AddLocationFragmentServicesShop servicesShop = new AddLocationFragmentServicesShop();
                    servicesShop.setArguments(data);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,
                            servicesShop, "servicesShop").commit();
                    break;
                case "Services_vet":
                    AddLocationFragmentServicesVet servicesVet = new AddLocationFragmentServicesVet();
                    servicesVet.setArguments(data);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,
                            servicesVet, "servicesVet").commit();
                    break;
                case "Park":
                    AddLocationFragmentParks parks = new AddLocationFragmentParks();
                    parks.setArguments(data);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,
                            parks, "parks").commit();
                    break;
                case "Vacation_camping":
                    AddLocationFragmentVacationCamping vacationCamping = new AddLocationFragmentVacationCamping();
                    vacationCamping.setArguments(data);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,
                            vacationCamping, "vacationCamping").commit();
                    break;
                case "Vacation_hotel":
                    AddLocationFragmentVacationHotel vacationHotel = new AddLocationFragmentVacationHotel();
                    vacationHotel.setArguments(data);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,
                            vacationHotel, "vacationHotel").commit();
                    break;
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

    }

    private Bundle addFieldsToBundleData(JSONObject scanResult){
        Iterator<String> keys = scanResult.keys();
        Bundle data = new Bundle();

        while (keys.hasNext()){
            String key = keys.next();
            try {
                String jsonValue = String.valueOf(scanResult.get(key));
                data.putString(key,jsonValue);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(locationScanLatLng).title(locationName));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(locationScanLatLng,15));
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


}

