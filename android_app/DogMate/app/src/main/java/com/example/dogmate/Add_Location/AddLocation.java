package com.example.dogmate.Add_Location;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.dogmate.R;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;

import com.google.android.libraries.places.widget.AutocompleteSupportFragment;

import net.glxn.qrgen.android.QRCode;

import static com.example.dogmate.R.id.autocomplete_fragment;
import static com.example.dogmate.R.id.fragment_container;

public class AddLocation  extends AppCompatActivity {

    AutocompleteSupportFragment autocompleteFragment;
    String TAG;
    GooglePlaceSelectionListener listener;
    AutoCompleteTextView categoriesAutoCom;
    AutoCompleteTextView subCategoriesAutoCom;

    AddLocationFragmentEntertainment EntertainmentFrag;
    AddLocationFragmentNature NatureFrag;
    AddLocationFragmentParks ParksFrag;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        // Enable the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);

        TAG = "PlacesAutoAdapter";

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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) // Checks the API level of the device
        {
            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

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
                if (selectedCategory.equals(categories[0])) {
                    subCategoriesAutoCom.setText(R.string.chooseSubCat);
                    subCategoriesAutoCom.setAdapter(natureAdapter);
                    NatureFrag = new AddLocationFragmentNature();
                    getSupportFragmentManager().beginTransaction().replace(fragment_container,
                            NatureFrag, "Nature").commit();

                } else if (selectedCategory.equals(categories[1])) {
                    subCategoriesAutoCom.setText(R.string.chooseSubCat);
                    subCategoriesAutoCom.setAdapter(dogParkAdapter);
                    ParksFrag = new AddLocationFragmentParks();
                    getSupportFragmentManager().beginTransaction().replace(fragment_container,
                            ParksFrag, "Park").commit();

                } else if (selectedCategory.equals(categories[2])) {
                    subCategoriesAutoCom.setText(R.string.chooseSubCat);
                    subCategoriesAutoCom.setAdapter(serviceAdapter);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new Fragment()).commit();
                    addListenerOnSubCategoriesSpinnerItemSelection();

                } else if (selectedCategory.equals(categories[3])) {
                    subCategoriesAutoCom.setText(R.string.chooseSubCat);
                    subCategoriesAutoCom.setAdapter(vacationAdapter);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new Fragment()).commit();
                    addListenerOnSubCategoriesSpinnerItemSelection();

                } else if (selectedCategory.equals(categories[4])) {
                    subCategoriesAutoCom.setText(R.string.chooseSubCat);
                    subCategoriesAutoCom.setAdapter(entertainmentAdapter);
                    EntertainmentFrag = new AddLocationFragmentEntertainment();
                    getSupportFragmentManager().beginTransaction().replace(fragment_container,
                            EntertainmentFrag, "Entertainment").commit();
                }
            }

        });

    }

    public void addListenerOnSubCategoriesSpinnerItemSelection() {
        subCategoriesAutoCom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedSubCategory = subCategoriesAutoCom.getText().toString();
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

    public String generateQRCode(){
        Bitmap qrBitMap = QRCode.from("www.example.org").bitmap();
        String ecodedQR = generateQRStringForBackend(qrBitMap);
        return ecodedQR;
    }

    public String generateQRStringForBackend(Bitmap myBitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

        //python code for backend
        /*
            import base64
            # example img string
            imgstring = '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'
            imgdata = base64.b64decode(imgstring)
            with open ('test.jpg','wb') as f:
                f.write(imgdata)
       */
    }



}