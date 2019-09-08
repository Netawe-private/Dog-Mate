package com.example.dogmate.Show_Location;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.dogmate.Add_Location.AddLocation;
import com.example.dogmate.IResult;
import com.example.dogmate.JsonHelperService;
import com.example.dogmate.R;
import com.example.dogmate.Scan_Location.LocationDetails;
import com.example.dogmate.Scan_Location.ScanLocation;
import com.example.dogmate.VolleyService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.dogmate.Constants.SEARCH_LOCATION_PATH;

public class ShowLocations extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    private String TAG = "ShowLocationActivity";

    protected DrawerLayout drawer;
    NavigationView navigationView;
    private GoogleMap mLocationsMap;
    AutoCompleteTextView editTextFilledExposedDropdown;
    List<String> categoriesArray;
    ArrayAdapter<String> categoriesAdapter;
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        categoriesArray = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.categories_array)));
        categoriesAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, categoriesArray);

        editTextFilledExposedDropdown = findViewById(R.id.categories_spinner);
        editTextFilledExposedDropdown.setAdapter(categoriesAdapter);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent nextActivity;
        switch (id){
            case R.id.nav_add_location:
                navigationView.setCheckedItem(R.id.nav_add_location);
                nextActivity = new Intent(ShowLocations.this, AddLocation.class);
                startActivity(nextActivity);
                break;
            case R.id.nav_show_location:
                navigationView.setCheckedItem(R.id.nav_show_location);
                nextActivity = new Intent(ShowLocations.this, ShowLocations.class);
                startActivity(nextActivity);
                break;
            case R.id.nav_matching:
                break;

            case R.id.nav_barcode:
                //navigationView.setCheckedItem(R.id.nav_review);
                nextActivity = new Intent(ShowLocations.this, ScanLocation.class);
                startActivity(nextActivity);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mLocationsMap = googleMap;
        mLocationsMap.setOnInfoWindowClickListener(this);
        //get user location to zoom in
        LatLng telAviv = new LatLng(32.078948, 34.772278);
//        mLocationsMap.addMarker(new MarkerOptions().position(telAviv).title("Marker in Tel Aviv"));
//        mLocationsMap.moveCamera(CameraUpdateFactory.newLatLngZoom(telAviv,15));
    }

    public void onSearchLocationButton(View v){
        if (validateField(editTextFilledExposedDropdown)){
           JSONObject  requestJson = JsonHelperService.
                   createSearchLocationRequestJsonByType(editTextFilledExposedDropdown.getText().toString());
            mVolleyService.postDataStringResponseVolley("POSTCALL_SEARCH_LOCATION",
                    SEARCH_LOCATION_PATH, requestJson, null);
        }

    }

    public boolean validateField(AutoCompleteTextView textInput){
        String locationSearch = textInput.getText().toString();
        if (locationSearch.isEmpty()) {
            Toast errorMessage = Toast.makeText(this, "Please enter a category", Toast.LENGTH_SHORT);
            errorMessage.show();
            return false;
        }
        return true;
    }

    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType,JSONObject response) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + response);
            }

            @Override
            public void notifyError(String requestType,VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post error: " + error);
            }

            @Override
            public void notifySuccessString(String requestType, String response) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + response);
                mLocationsMap.clear();
                try {
                    JSONArray locationsResponse = new JSONArray(response);
                    if (locationsResponse.length() > 0){
                        setMarkersOnMap(locationsResponse);
                    }
                    else {
                        Toast successMessage = Toast.makeText(getApplicationContext(),
                                "No locations found! Want to add one?", Toast.LENGTH_SHORT);
                        successMessage.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void setMarkersOnMap(JSONArray locations){
        try {
            for (int i = 0; i < locations.length(); i++) {
                MarkerOptions options = new MarkerOptions();
                JSONObject jsonObject = locations.getJSONObject(i);
                LatLng locationLatLng = new LatLng(
                        Double.valueOf(jsonObject.getString("latitude")),
                        Double.valueOf(jsonObject.getString("longitude")));
                options.position(locationLatLng);

                options.title( String.format("%s ", jsonObject.getString("locationName")));
                options.snippet( String.format("at %s \ntype: %s subtype %s",
                        jsonObject.getString("address"),
                        jsonObject.getString("locationType"),
                        jsonObject.getString("locationSubType")));
                setMarkerColor(options,jsonObject.getString("locationType"));
                Marker locationMarker = mLocationsMap.addMarker(options);
                locationMarker.setTag(jsonObject);
                mLocationsMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng,10));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void setMarkerColor(MarkerOptions markerOptions, String type){
        switch (type){
            case "Nature":
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                break;
            case "Entertainment":
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                break;
            case "Parks":
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                break;
            case "Services":
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                break;
            case "Vacation":
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                break;
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(ShowLocations.this, LocationDetails.class);
        intent.putExtra("locationDetails",String.valueOf(marker.getTag()));
        startActivity(intent);
    }
}


