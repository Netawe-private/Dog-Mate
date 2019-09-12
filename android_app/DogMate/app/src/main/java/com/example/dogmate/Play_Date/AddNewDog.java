package com.example.dogmate.Play_Date;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.VolleyError;
import com.example.dogmate.Constants;
import com.example.dogmate.IResult;
import com.example.dogmate.JsonHelperService;
import com.example.dogmate.Login.LoginActivity;
import com.example.dogmate.R;
import com.example.dogmate.Show_Location.ShowLocations;
import com.example.dogmate.VolleyService;
import com.squareup.picasso.Picasso;


import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dogmate.Constants.ADD_DOG_PATH;


public class AddNewDog extends AppCompatActivity implements View.OnClickListener{
    IResult mResultCallback = null;
    VolleyService mVolleyService;
    SharedPreferences sharedPreferenceFile;
    SharedPreferences.Editor editor;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.et_dogname) EditText mDogNameEditText;
    @BindView(R.id.im_dogphoto) ImageView mDogPhoto;
    @BindView(R.id.sp_cities) Spinner mCities;
    @BindView(R.id.et_neighborhood) EditText mNeighborhoodEditText;
    @BindView(R.id.sp_sizes) Spinner mSizes;
    @BindView(R.id.sp_breeds) Spinner mBreeds;
    @BindView(R.id.sp_temperaments) Spinner mTemperaments;
    @BindView(R.id.btn_createprofile) Button mCreateProfileButton;

    private static final int RESULT_LOAD_IMAGE = 1;
    String dogName;
    String city;
    String neighborhood;
    String  dogSize;
    String breed;
    String temperament;
    Uri dogImage;
    private String TAG = "Add_Dog";

    Uri mSelectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_dog);
        ButterKnife.bind(this);
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,this);
        sharedPreferenceFile =  getSharedPreferences(Constants.SHAREDPREF_NAME, 0);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        // Enable the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);
        mDogPhoto.setOnClickListener(this);
    }

    public void onClickAddDog(View view){
        boolean isValid = checkDataEntered(mDogNameEditText, mSelectedImage, mCities,
                                            mNeighborhoodEditText, mSizes, mBreeds, mTemperaments);
        if (isValid) {
            dogName = mDogNameEditText.getText().toString();
            breed = mBreeds.getSelectedItem().toString();
            city = mCities.getSelectedItem().toString();
            neighborhood = mNeighborhoodEditText.getText().toString();
            dogSize = mSizes.getSelectedItem().toString();
            temperament = mTemperaments.getSelectedItem().toString();
            String username = sharedPreferenceFile.getString("username", null);
            String credentials = String.format("%s:%s",
                                    sharedPreferenceFile.getString("username", null),
                                    sharedPreferenceFile.getString("password", null));
            JSONObject requestJson = JsonHelperService.createAddDogRequest(dogName, breed, dogSize,
                                    temperament,neighborhood, username,city,"");
            mVolleyService.postDataStringResponseVolley("POST_ADD_DOG", ADD_DOG_PATH,
                                                                        requestJson, credentials);
            // get fields and upload them to DB.
            // save the registration date

            Intent intent = new Intent(AddNewDog.this, DogProfileApproved.class );
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {

            mSelectedImage = data.getData();
            Picasso.get().load(mSelectedImage).fit().centerCrop().into(mDogPhoto);
        }
    }

    boolean isEmpty(EditText text) {
        String str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean isEmptyImageView(Uri uri) {
        return (uri == null);
    }

    boolean isSpinnerSelected(Spinner spinner) {
        return (spinner.getSelectedItemPosition() == 0);
    }

    boolean checkDataEntered(EditText mDogNameEditText, Uri mSelectedImage, Spinner mCities, EditText mNeighborhoodEditText, Spinner mSizes, Spinner mBreeds, Spinner mTemperaments) {


        if (isEmpty(mDogNameEditText)) {
            Toast t = Toast.makeText(this, "Please enter your dog's name", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

//        else if (isEmptyImageView(mSelectedImage)) {
//            Toast t = Toast.makeText(this, "Please enter your dog's image", Toast.LENGTH_SHORT);
//            t.show();
//            return false;
//        }

        else if (isSpinnerSelected(mCities)) {
            Toast t = Toast.makeText(this, "Please select your city", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        if (isEmpty(mNeighborhoodEditText)) {
            Toast t = Toast.makeText(this, "Please enter your neighborhood", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        else if (isSpinnerSelected(mSizes)) {
            Toast t = Toast.makeText(this, "Please select your dog's size", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        else if (isSpinnerSelected(mBreeds)) {
            Toast t = Toast.makeText(this, "Please select your dog's breed", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        else if (isSpinnerSelected(mTemperaments)) {
            Toast t = Toast.makeText(this, "Please select your dog's temperament", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        else
            return true;
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
                String  errorResponse = mVolleyService.parseVolleyError(error);
                Toast errorMessage = Toast.makeText(getApplicationContext(),
                        errorResponse, Toast.LENGTH_SHORT);
                errorMessage.show();
            }

            @Override
            public void notifySuccessString(String requestType, String response) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley String post" + response);
//                try {
//                    JSONObject responseJson = new JSONObject(response);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                Intent intent = new Intent(AddNewDog.this, DogProfileApproved.class );
                startActivity(intent);

            }
        };
    }
}




