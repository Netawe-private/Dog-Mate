package com.example.dogmate.Scan_Location;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.dogmate.Add_Location.AddLocationFragmentEntertainment;
import com.example.dogmate.Add_Location.AddLocationFragmentNature;
import com.example.dogmate.Add_Location.AddLocationFragmentParks;
import com.example.dogmate.Add_Location.AddLocationFragmentServicesShop;
import com.example.dogmate.Add_Location.AddLocationFragmentServicesVet;
import com.example.dogmate.Add_Location.AddLocationFragmentVacationCamping;
import com.example.dogmate.Add_Location.AddLocationFragmentVacationHotel;
import com.example.dogmate.Add_Review.AddReview;
import com.example.dogmate.IResult;
import com.example.dogmate.R;
import com.example.dogmate.VolleyService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import static com.example.dogmate.Constants.GET_REVIEWS_PATH;

public class LocationDetails extends AppCompatActivity implements OnMapReadyCallback {
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    String TAG;
    private TextView locatioNameView;
    private TextView locationAddressView;
    private MapView mMapView;
    private LatLng locationScanLatLng;
    private String locationName;
    private RatingBar ratingLocationDetails;
    JSONObject locationDetails;
    JSONArray locationReviews;

    float locationRating;
    VolleyService mVolleyService;
    IResult mResultCallback = null;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        // Enable the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        locationDetails = null;
        try {
            locationDetails = new JSONObject(intent.getStringExtra("locationDetails"));
            locationName = locationDetails.getString("locationName");
            locationScanLatLng = new LatLng(locationDetails.getDouble("latitude"),
                                            locationDetails.getDouble("longitude"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        locationAddressView = findViewById(R.id.locationDetailsAddress);
        locatioNameView = findViewById(R.id.locationDetailsName);
        ratingLocationDetails = findViewById(R.id.ratingLocationDetails);

        initGoogleMap(savedInstanceState);
        setLocationDetails(locationDetails);
        openLocationFragment(locationDetails);
    }

    private void initGoogleMap(Bundle savedInstanceState){
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
    }

    public void openLocationFragment(JSONObject locationDetails){

        try {
            String locationType = locationDetails.getString("locationType");
            String locationSubType = locationDetails.getString("locationSubType");

            Bundle data = addFieldsToBundleData(locationDetails);
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
                case "Services":
                    if (locationSubType.equalsIgnoreCase("Veterinarians") ||
                            locationSubType.equalsIgnoreCase("Barber Shop")){
                        AddLocationFragmentServicesVet servicesVet = new AddLocationFragmentServicesVet();
                        servicesVet.setArguments(data);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                servicesVet, "servicesVet").commit();
                    } else if (locationSubType.equalsIgnoreCase("Pet Supply Store")) {
                        AddLocationFragmentServicesShop servicesShop = new AddLocationFragmentServicesShop();
                        servicesShop.setArguments(data);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                servicesShop, "servicesShop").commit();
                    }
                    break;
                case "Dog Parks":
                    AddLocationFragmentParks parks = new AddLocationFragmentParks();
                    parks.setArguments(data);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,
                            parks, "parks").commit();
                    break;
                case "Vacation":
                    if (locationSubType.equalsIgnoreCase("Hotel") ||
                            locationSubType.equalsIgnoreCase("Zimmer")){
                        AddLocationFragmentVacationHotel vacationHotel = new AddLocationFragmentVacationHotel();
                        vacationHotel.setArguments(data);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                vacationHotel, "vacationHotel").commit();
                    }
                    else if(locationSubType.equalsIgnoreCase("Camping Site")) {
                        AddLocationFragmentVacationCamping vacationCamping = new AddLocationFragmentVacationCamping();
                        vacationCamping.setArguments(data);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                vacationCamping, "vacationCamping").commit();
                    }
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

    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + response);

            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post error: " + error);
                Toast errorMessage = Toast.makeText(getApplicationContext(),
                        "an error occurred", Toast.LENGTH_SHORT);
                errorMessage.show();
            }

            @Override
            public void notifySuccessString(String requestType, String response) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + response);
                try {
                    locationReviews = new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                locationRating = calculateRatingByReviews(locationReviews);
                if (locationRating == 0) {
                    ratingLocationDetails.setVisibility(View.GONE);
                } else {
                    ratingLocationDetails.setRating(locationRating);
                }
            }
        };
    }

    private void setLocationDetails(JSONObject locationDetails) {
        try {
            locationScanLatLng = new LatLng(
                    locationDetails.getDouble("latitude"),
                    locationDetails.getDouble("longitude"));
            locationName = locationDetails.getString("locationName");
            locatioNameView.setText(locationName);
            locationAddressView.setText(locationDetails.getString("address"));
            String locationId = locationDetails.get("locationId").toString();
            //get reviews from DB
            String requestUrl = String.format(GET_REVIEWS_PATH, locationId);
            mVolleyService.getDataVolleyStringResponseVolley("GET_REVIEWS_CALL",requestUrl);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private float calculateRatingByReviews(JSONArray reviewsArray){
        float averageRating;
        float sum = 0;
        try {
            for (int i = 0; i < reviewsArray.length(); i++) {
                JSONObject jsonObject = reviewsArray.getJSONObject(i);
                sum += Float.valueOf(jsonObject.getString("reviewRank"));
        }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        averageRating = sum /reviewsArray.length();
        return  averageRating;
    }

    public void  goToReview(View view){
        Intent intent = new Intent(LocationDetails.this, AddReview.class);
        intent.putExtra("locationDetails",String.valueOf(locationDetails));
        intent.putExtra("reviews",String.valueOf(locationReviews));
        intent.putExtra("locationRating", locationRating);

        startActivity(intent);
    }

}

