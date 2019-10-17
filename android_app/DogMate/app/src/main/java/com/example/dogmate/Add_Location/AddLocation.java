package com.example.dogmate.Add_Location;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.android.volley.VolleyError;
import com.example.dogmate.Constants;
import com.example.dogmate.IResult;
import com.example.dogmate.JsonHelperService;
import com.example.dogmate.R;
import com.example.dogmate.VolleyService;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;

import org.json.JSONObject;
import static com.example.dogmate.Constants.CREATE_LOCATION_PATH;
import static com.example.dogmate.R.id.autocomplete_fragment;
import static com.example.dogmate.R.id.fragment_container_add_location;
import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class AddLocation  extends AppCompatActivity {
    SharedPreferences sharedPreferenceFile;
    AutocompleteSupportFragment autocompleteFragment;
    String TAG;
    GooglePlaceSelectionListener listener;
    AutoCompleteTextView categoriesAutoCom;
    AutoCompleteTextView subCategoriesAutoCom;
    VolleyService mVolleyService;
    IResult mResultCallback = null;
    String credentials;
    String username;

    AddLocationFragmentEntertainment entertainmentFrag;
    AddLocationFragmentNature natureFrag;
    AddLocationFragmentParks parksFrag;
    AddLocationFragmentServicesShop servicesShopFrag;
    AddLocationFragmentServicesVet serviceVetFrag;
    AddLocationFragmentVacationCamping vacationCampingFrag;
    AddLocationFragmentVacationHotel vacationHotelFrag;

    List<String> categoriesArray;
    List<String> natureArray;
    List<String> servicesArray;
    List<String> entertainmentArray;
    List<String> dogParkArray;
    List<String> vacationArray;

    ArrayAdapter<String> categoriesAdapter;
    ArrayAdapter<String> natureAdapter;
    ArrayAdapter<String> serviceAdapter;
    ArrayAdapter<String> entertainmentAdapter;
    ArrayAdapter<String> dogParkAdapter;
    ArrayAdapter<String> vacationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TAG = "PlacesAutoAdapter";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        sharedPreferenceFile =  getSharedPreferences(Constants.SHAREDPREF_NAME, 0);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        // Enable the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,this);
        credentials = String.format("%s:%s",sharedPreferenceFile.getString("username", NULL),
                sharedPreferenceFile.getString("password", NULL));
        username = sharedPreferenceFile.getString("username", NULL);


        categoriesArray = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.categories_array)));
        categoriesAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, categoriesArray);

        categoriesAutoCom = findViewById(R.id.categories_spinner);
        categoriesAutoCom.setAdapter(categoriesAdapter);

        subCategoriesAutoCom = findViewById(R.id.subcategories_spinner);

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
        autocompleteFragment.setOnPlaceSelectedListener(listener);

    }

    public void addListenerOnCategoriesSpinnerItemSelection() {
        categoriesAutoCom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categoriesAutoCom.getText().toString();
                String[] categories = getResources().getStringArray(R.array.categories_array);
                subCategoriesAutoCom.setText(null);
                if (selectedCategory.equals(categories[0])) {
                    subCategoriesAutoCom.setAdapter(natureAdapter);
                    natureFrag = new AddLocationFragmentNature();
                    getSupportFragmentManager().beginTransaction().replace(fragment_container_add_location,
                            natureFrag, "Nature").commit();

                } else if (selectedCategory.equals(categories[1])) {
                    subCategoriesAutoCom.setAdapter(dogParkAdapter);
                    parksFrag = new AddLocationFragmentParks();
                    getSupportFragmentManager().beginTransaction().replace(fragment_container_add_location,
                            parksFrag, "Dog Park").commit();

                } else if (selectedCategory.equals(categories[2])) {
                    subCategoriesAutoCom.setAdapter(serviceAdapter);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_add_location,
                            new Fragment()).commit();
                    addListenerOnSubCategoriesSpinnerItemSelection();

                } else if (selectedCategory.equals(categories[3])) {
                    subCategoriesAutoCom.setAdapter(vacationAdapter);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_add_location,
                            new Fragment()).commit();
                    addListenerOnSubCategoriesSpinnerItemSelection();

                } else if (selectedCategory.equals(categories[4])) {
                    subCategoriesAutoCom.setAdapter(entertainmentAdapter);
                    entertainmentFrag = new AddLocationFragmentEntertainment();
                    getSupportFragmentManager().beginTransaction().replace(fragment_container_add_location,
                            entertainmentFrag, "Entertainment").commit();
                }
                subCategoriesAutoCom.showDropDown();
            }

        });

    }

    public void addListenerOnSubCategoriesSpinnerItemSelection() {
        subCategoriesAutoCom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedSubCategory = subCategoriesAutoCom.getText().toString();
                if (selectedSubCategory.equalsIgnoreCase("Veterinarians") || selectedSubCategory.equalsIgnoreCase("Barber Shop") ){
                    serviceVetFrag = new AddLocationFragmentServicesVet();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_add_location,
                            serviceVetFrag, "Vet").commit();
                }
                else if (selectedSubCategory.equalsIgnoreCase("Pet Supply Store")){
                    servicesShopFrag = new AddLocationFragmentServicesShop();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_add_location,
                            servicesShopFrag, "Shop").commit();
                }
                else if (selectedSubCategory.equalsIgnoreCase("Hotel") ||
                        selectedSubCategory.equalsIgnoreCase("Zimmer")){
                    vacationHotelFrag = new AddLocationFragmentVacationHotel();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_add_location,
                            vacationHotelFrag, "Hotel").commit();
                }

                else if (selectedSubCategory.equalsIgnoreCase("Camping Site")){
                    vacationCampingFrag = new AddLocationFragmentVacationCamping();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_add_location,
                            vacationCampingFrag, "Camping").commit();
                }

                else if (selectedSubCategory.equalsIgnoreCase("Dog Pension")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_add_location,
                            new Fragment(),"Pension").commit();
                }

            }

        });
    }

    public void onClickSaveLocation(View view){
        if (validateFields()) {
            String name = listener.getSelectedPlaceName();
            String address = listener.getSelectedPlaceAddress();
            LatLng locationLatLng = listener.getSelectedPlaceLatLng();
            String lat = String.valueOf(locationLatLng.latitude);
            String lng = String.valueOf(locationLatLng.longitude);
            Fragment current = getSupportFragmentManager().findFragmentById(fragment_container_add_location);
            String subCategory = subCategoriesAutoCom.getText().toString();
            String category = categoriesAutoCom.getText().toString();
            JSONObject requestJson = null;
            switch (current.getTag()) {
                case "Entertainment":
                    int shadowLevelEnt = (int) entertainmentFrag.getDegreeOfShadowRatingEnt();
                    boolean sittingInside = entertainmentFrag.getSittingInsideCheck();
                    boolean shadowPlace = entertainmentFrag.getHasShadowCheck();
                    if (entertainmentFrag.validateShadowField()) {
                        requestJson = JsonHelperService.createAddLocationEntertainmentRequestJson
                                (address, lat, name, "Entertainment",
                                        "Entertainment",
                                        lng, shadowLevelEnt, shadowPlace, sittingInside);
                    }
                    break;
                case "Nature":
                    int shadowLevelNature = (int) natureFrag.getShadowLevelRatingNatureRatingBar();
                    boolean releaseDog = natureFrag.getIsReleaseDogCheckBox();
                    boolean waterResource = natureFrag.getAvailableWaterCheckBox();
                    requestJson = JsonHelperService.createAddLocationNatureRequestJson
                            (address, lng, lat, name, subCategory,
                                    category,
                                    waterResource, releaseDog, shadowLevelNature);
                    break;

                case "Dog Park":
                    if (parksFrag.isValidated()) {
                        String space = parksFrag.getSpaceParkOpen();
                        int busyLevevl = (int) parksFrag.getBusyRating();
                        int Cleanlines = (int) parksFrag.getCleanliness();
                        String gardenType = parksFrag.getSurfaceTypePark();
                        requestJson = JsonHelperService.createAddLocationDogParksRequestJson
                                (address, lng, lat, name, subCategory,
                                        category, busyLevevl, Cleanlines, space, gardenType);
                    }
                    break;
                case "Vet":
                    boolean allDayService = serviceVetFrag.getIsTwentyFourServiceHoursVet();
                    int priceLevel = (int) serviceVetFrag.getPriceLevel();
                    String treatmentVet = serviceVetFrag.getEditTextTreatment();
                    requestJson = JsonHelperService.createAddLocationServicesVetRequestJson
                            (address, lng, lat, name, subCategory,
                                    category, treatmentVet, allDayService, priceLevel);
                    break;

                case "Shop":
                    if (servicesShopFrag.validateFields()) {
                        boolean isDelivery = servicesShopFrag.getIncludeDeliveryService();
                        String deliveryAreas = servicesShopFrag.getEditTextDelAreaService();
                        String treatmentShop = servicesShopFrag.getEditTextTreatment();
                        int priceLevelShop = (int) servicesShopFrag.getPriceLevel();
                        requestJson = JsonHelperService.createAddLocationServicesShopRequestJson
                                (address, lng, lat, name, subCategory,
                                        category, treatmentShop, priceLevelShop, isDelivery, deliveryAreas);
                    }
                    break;

                case "Hotel":
                    if (vacationHotelFrag.validateFeilds()) {
                        int hotalStarts = (int) vacationHotelFrag.getNumberOfHotelStarts();
                        int priceLevelHotel = (int) vacationHotelFrag.getPriceLevel();
                        String additionalServicesHotel = vacationHotelFrag.getEditTextAddionalServices();
                        boolean nextToBeachHotel = vacationHotelFrag.getNextToBeachHotel();
                        boolean isFoodAround = vacationHotelFrag.getIsFoodAroundHotel();
                        requestJson = JsonHelperService.createAddLocationVacationRequestJson
                                (address, lng, lat, name, subCategory,
                                        category, additionalServicesHotel, isFoodAround, nextToBeachHotel, hotalStarts, priceLevelHotel);
                    }
                    break;

                case "Camping":
                    boolean nextTobeachCamping = vacationCampingFrag.getNextToBeachCamping();
                    boolean dogFoogAround = vacationCampingFrag.getIsFoodAroundCamping();
                    String additionalServicesCamping = vacationCampingFrag.getEditTextAdditionalServices();
                    int priceLevelCamping = (int) vacationCampingFrag.getPriceLevel();
                    requestJson = JsonHelperService.createAddLocationVacationRequestJson
                            (address, lng, lat, name, subCategory,
                                    category, additionalServicesCamping, dogFoogAround, nextTobeachCamping, 0, priceLevelCamping);
                    break;
                case "Pension":
                    requestJson = JsonHelperService.createAddLocationRequestJson(address,name,
                                                                    subCategory, category, lng, lat);
                    break;
            }

            mVolleyService.postDataStringResponseVolley("ADD_LOCATION_REQUEST",
                    CREATE_LOCATION_PATH, requestJson, credentials);
        }
    }

    public boolean validateFields(){
        Toast errorMessage;
        String name = listener.getSelectedPlaceName();
        String address = listener.getSelectedPlaceAddress();
        if (name == null || address == null) {
            errorMessage = Toast.makeText(getApplicationContext(),
                    "Name field is empty!", Toast.LENGTH_LONG);
            errorMessage.show();
            return false;
        }

        if (categoriesAutoCom.getText().toString().equals("")
            || subCategoriesAutoCom.getText().toString().equals("") ||
                subCategoriesAutoCom.getText().toString().equalsIgnoreCase(null)){
            errorMessage = Toast.makeText(getApplicationContext(),
                    getString(R.string.noCategoryChosen), Toast.LENGTH_LONG);
            errorMessage.show();
            return false;
        } else {
            switch (categoriesAutoCom.getText().toString()){
                case "Nature":
                    if (Arrays.asList(getResources().getStringArray(R.array.nature_array))
                                            .contains(subCategoriesAutoCom.getText().toString())){
                        return true;
                    }
                    break;
                case "Dog Parks":
                    if (Arrays.asList(getResources().getStringArray(R.array.dog_parks_array))
                            .contains(subCategoriesAutoCom.getText().toString())){
                        return true;
                    }
                    break;
                case "Services":
                    if (Arrays.asList(getResources().getStringArray(R.array.services_array))
                            .contains(subCategoriesAutoCom.getText().toString())){
                        return true;
                    }
                    break;
                case "Vacation":
                    if (Arrays.asList(getResources().getStringArray(R.array.vacation_array))
                            .contains(subCategoriesAutoCom.getText().toString())){
                        return true;
                    }
                    break;
                case "Entertainment":
                    if (Arrays.asList(getResources().getStringArray(R.array.entertainment_array))
                            .contains(subCategoriesAutoCom.getText().toString())){
                        return true;
                    }
                    break;
            }
            errorMessage = Toast.makeText(getApplicationContext(),
                    getString(R.string.incorrectCategoryChosen), Toast.LENGTH_LONG);
            errorMessage.show();
            return false;
        }
    }


    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + response);
                Toast approvalMessage = Toast.makeText(getApplicationContext(),
                        "Your Location saved", Toast.LENGTH_LONG);
                approvalMessage.show();
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post error: " + error);
                String message;
                if (error.networkResponse.statusCode == 409){
                    message = "There's a place with that name already! try and find it in Show Location page!";
                }
                else {
                    message = "An error occurred";
                }
                Toast errorMessage = Toast.makeText(getApplicationContext(),
                        message, Toast.LENGTH_LONG);
                errorMessage.show();
            }

            @Override
            public void notifySuccessString(String requestType, String response) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + response);
                Toast approvalMessage = Toast.makeText(getApplicationContext(),
                        "Your Location saved", Toast.LENGTH_LONG);
                approvalMessage.show();
            }
        };
    }




}