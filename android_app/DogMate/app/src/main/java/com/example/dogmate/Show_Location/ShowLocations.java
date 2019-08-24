package com.example.dogmate.Show_Location;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.dogmate.DrawerMenu;
import com.example.dogmate.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowLocations extends DrawerMenu implements OnMapReadyCallback {
    private GoogleMap locationsMap;
    AutoCompleteTextView editTextFilledExposedDropdown;
    List<String> categoriesArray;
    ArrayAdapter<String> categoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_show_locations, null, false);
        drawer.addView(contentView, 0);

        categoriesArray = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.categories_array)));
        categoriesAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, categoriesArray);

        editTextFilledExposedDropdown = findViewById(R.id.categories_spinner);
        editTextFilledExposedDropdown.setAdapter(categoriesAdapter);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
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
