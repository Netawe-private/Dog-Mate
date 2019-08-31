package com.example.dogmate.Show_Location;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.dogmate.Add_Location.AddLocation;
import com.example.dogmate.Add_Review.AddReview;
import com.example.dogmate.R;
import com.example.dogmate.Scan_Location.ScanLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowLocations extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    protected DrawerLayout drawer;
    NavigationView navigationView;
    private GoogleMap locationsMap;
    AutoCompleteTextView editTextFilledExposedDropdown;
    List<String> categoriesArray;
    ArrayAdapter<String> categoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

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

            case R.id.nav_review:
                navigationView.setCheckedItem(R.id.nav_review);
                nextActivity = new Intent(ShowLocations.this, AddReview.class);
                startActivity(nextActivity);
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
        locationsMap = googleMap;

        LatLng telAviv = new LatLng(32.078948, 34.772278);
        locationsMap.addMarker(new MarkerOptions().position(telAviv).title("Marker in Tel Aviv"));
        locationsMap.moveCamera(CameraUpdateFactory.newLatLngZoom(telAviv,15));
    }

    public void onSearchLocationButton(View v){
        if (validateField(editTextFilledExposedDropdown)){
            Toast successMessage = Toast.makeText(this, "Searching...", Toast.LENGTH_SHORT);
            successMessage.show();
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
}
