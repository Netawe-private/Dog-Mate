package com.example.dogmate.Add_Location;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.dogmate.DrawerMenu;
import com.example.dogmate.R;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;

import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

public class AddLocation extends DrawerMenu {
    Spinner categoriesSpinner;
    Spinner subCatSpinner;

    List<String> natureArray;
    List<String> servicesArray;
    List<String> entertainmentArray;
    List<String> dogParkArray;
    List<String> vacationArray;

    ArrayAdapter<String> natureAdapter;
    ArrayAdapter<String> serviceAdapter;
    ArrayAdapter<String> entertainmentAdapter;
    ArrayAdapter<String> dogParkAdapter;
    ArrayAdapter<String> vacationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_location);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_add_location, null, false);
        drawer.addView(contentView, 0);
        categoriesSpinner = findViewById(R.id.categories_spinner);
        subCatSpinner = findViewById(R.id.subcategories_spinner);
        final String TAG = "PlacesAutoAdapter";

        addListenerOnCategoriesSpinnerItemSelection();


        natureArray = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.nature_array)));
        servicesArray = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.services_array)));
        entertainmentArray = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.entertainment_array)));
        dogParkArray = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.dog_parks_array)));
        vacationArray = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.vacation_array)));


        natureAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, natureArray);
        serviceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, servicesArray);
        entertainmentAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, entertainmentArray);
        dogParkAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dogParkArray);
        vacationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, vacationArray);


        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "@string/google_api_key");
        }

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));




        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }

    public void addListenerOnCategoriesSpinnerItemSelection() {
        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categoriesSpinner.getSelectedItem().toString();
                String[] categories = getResources().getStringArray(R.array.categories_array);
                if (selectedCategory.equals(categories[0])) {
                    subCatSpinner.setAdapter(natureAdapter);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new AddLocationFragmentNature()).commit();

                } else if (selectedCategory.equals(categories[1])) {
                    subCatSpinner.setAdapter(dogParkAdapter);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new AddLocationFragmentParks()).commit();

                } else if (selectedCategory.equals(categories[2])) {
                    subCatSpinner.setAdapter(serviceAdapter);
                    addListenerOnSubCategoriesSpinnerItemSelection();

                } else if (selectedCategory.equals(categories[3])) {
                    subCatSpinner.setAdapter(vacationAdapter);
                    addListenerOnSubCategoriesSpinnerItemSelection();

                } else if (selectedCategory.equals(categories[4])) {
                    subCatSpinner.setAdapter(entertainmentAdapter);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new AddLocationFragmentEntertainment()).commit();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void addListenerOnSubCategoriesSpinnerItemSelection() {
        subCatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSubCategory = subCatSpinner.getSelectedItem().toString();
                if (selectedSubCategory.equalsIgnoreCase("Veterinarians")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new AddLocationFragmentServicesVet()).commit();
                }
                else if (selectedSubCategory.equalsIgnoreCase("Pet Supply Store")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new AddLocationFragmentServicesShop()).commit();
                }
                else if (selectedSubCategory.equalsIgnoreCase("Barber Shop")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new Fragment()).commit();
                }
                else if (selectedSubCategory.equalsIgnoreCase("Hotel") ||
                        selectedSubCategory.equalsIgnoreCase("Zimmer")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new AddLocationFragmentVacationHotel()).commit();
                }

                else if (selectedSubCategory.equalsIgnoreCase("Camping Site")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new AddLocationFragmentVacationCamping()).commit();
                }

                else if (selectedSubCategory.equalsIgnoreCase("Dog Pension")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new Fragment()).commit();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}