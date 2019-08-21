package com.example.dogmate.Add_Location;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
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
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;

import com.google.android.libraries.places.widget.AutocompleteSupportFragment;

import static com.example.dogmate.R.id.autocomplete_fragment;
import static com.example.dogmate.R.id.fragment_container;

public class AddLocation extends DrawerMenu {
    Spinner categoriesSpinner;
    Spinner subCatSpinner;
    AutocompleteSupportFragment autocompleteFragment;
    String TAG;
    GooglePlaceSelectionListener listener;

    AddLocationFragmentEntertainment EntertainmentFrag;
    AddLocationFragmentNature NatureFrag;
    AddLocationFragmentParks ParksFrag;
    AddLocationFragmentServicesShop servicesShopFrag;
    AddLocationFragmentServicesVet serviceVetFrag;
    AddLocationFragmentVacationCamping vacationCampingFrag;
    AddLocationFragmentVacationHotel vacationHotelFrag;

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
        View contentView = inflater.inflate(R.layout.activity_add_location, drawer, false);
        drawer.addView(contentView, 0);
        categoriesSpinner = findViewById(R.id.categories_spinner);
        subCatSpinner = findViewById(R.id.subcategories_spinner);
        TAG = "PlacesAutoAdapter";

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
            Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        }

        // Initialize the AutocompleteSupportFragment.
        autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ADDRESS, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment.setCountry("IL");
        listener = new GooglePlaceSelectionListener();
        autocompleteFragment.setOnPlaceSelectedListener(listener); //{
//            @Override
//            public void onPlaceSelected(Place place) {
//                String selectedPlaceAddress = place.getAddress();
//                String selectedPlaceName = place.getName();
//                LatLng selectedPlaceLatLng = place.getLatLng();
//                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
//            }
//
//            @Override
//            public void onError(Status status) {
//                Log.i(TAG, "An error occurred: " + status);
//                Toast message = Toast.makeText(getApplicationContext(),"An error had occurred",Toast.LENGTH_SHORT);
//                message.show();
//            }
//        });
    }

    public void addListenerOnCategoriesSpinnerItemSelection() {
        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categoriesSpinner.getSelectedItem().toString();
                String[] categories = getResources().getStringArray(R.array.categories_array);
                if (selectedCategory.equals(categories[0])) {
                    subCatSpinner.setAdapter(natureAdapter);
                    NatureFrag = new AddLocationFragmentNature();
                    getSupportFragmentManager().beginTransaction().replace(fragment_container,
                            NatureFrag, "Nature").commit();

                } else if (selectedCategory.equals(categories[1])) {
                    subCatSpinner.setAdapter(dogParkAdapter);
                    ParksFrag = new AddLocationFragmentParks();
                    getSupportFragmentManager().beginTransaction().replace(fragment_container,
                            ParksFrag, "Park").commit();

                } else if (selectedCategory.equals(categories[2])) {
                    subCatSpinner.setAdapter(serviceAdapter);
                    addListenerOnSubCategoriesSpinnerItemSelection();

                } else if (selectedCategory.equals(categories[3])) {
                    subCatSpinner.setAdapter(vacationAdapter);
                    addListenerOnSubCategoriesSpinnerItemSelection();

                } else if (selectedCategory.equals(categories[4])) {
                    subCatSpinner.setAdapter(entertainmentAdapter);
                    EntertainmentFrag = new AddLocationFragmentEntertainment();
                    getSupportFragmentManager().beginTransaction().replace(fragment_container,
                            EntertainmentFrag, "Entertainment").commit();
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
                    serviceVetFrag = new AddLocationFragmentServicesVet();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            serviceVetFrag, "Vet").commit();
                }
                else if (selectedSubCategory.equalsIgnoreCase("Pet Supply Store")){
                    servicesShopFrag = new AddLocationFragmentServicesShop();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            servicesShopFrag, "Shop").commit();
                }
                else if (selectedSubCategory.equalsIgnoreCase("Barber Shop")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new Fragment()).commit();
                }
                else if (selectedSubCategory.equalsIgnoreCase("Hotel") ||
                        selectedSubCategory.equalsIgnoreCase("Zimmer")){
                    vacationHotelFrag = new AddLocationFragmentVacationHotel();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            vacationHotelFrag, "Hotel").commit();
                }

                else if (selectedSubCategory.equalsIgnoreCase("Camping Site")){
                    vacationCampingFrag = new AddLocationFragmentVacationCamping();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            vacationCampingFrag, "Camping").commit();
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

    public void onClickSaveLocation(View view){
        validateFields();
        Fragment current = getSupportFragmentManager().findFragmentById(fragment_container);
        switch (current.getTag()){
            case "Entertainment":
                if(EntertainmentFrag.validateShadowField()){
                    //get fields and save to Db
                };
                break;

            case "Nature":
                break;

            case "Park":
                if(ParksFrag.isValidated()){
                    ParksFrag.getSpaceParkOpen();
                }
                break;

            case "Vet":
                break;

            case "Shop":
                break;

            case "Hotel":
                if (vacationHotelFrag.validateFeilds()){
                    //save fields
                }
                break;

            case "Camping":
                break;
        }

        //check if location is empty
        //get location details
        //send to db for save
    }

    public boolean validateFields(){
        //add validation here
        String address = listener.getSelectedPlaceAddress();
        String name = listener.getSelectedPlaceName();

        return true;
    }
}