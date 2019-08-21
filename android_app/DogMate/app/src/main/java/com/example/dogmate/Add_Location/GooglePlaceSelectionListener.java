package com.example.dogmate.Add_Location;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;

public class GooglePlaceSelectionListener implements PlaceSelectionListener {
   protected Context context;
   String TAG = "PlacesAutoAdapter";
   String selectedPlaceAddress;
   String selectedPlaceName;
   LatLng selectedPlaceLatLng;

    @Override
    public void onPlaceSelected(Place place) {
        selectedPlaceAddress = place.getAddress();
        selectedPlaceName = place.getName();
        selectedPlaceLatLng = place.getLatLng();
    }

    @Override
    public void onError(Status status) {
        Log.i(TAG, "An error occurred: " + status);
    }

    public LatLng getSelectedPlaceLatLng() {
        return selectedPlaceLatLng;
    }

    public String getSelectedPlaceAddress() {
        return selectedPlaceAddress;
    }

    public String getSelectedPlaceName() {
        return selectedPlaceName;
    }
}
