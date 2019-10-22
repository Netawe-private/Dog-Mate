package com.example.dogmate.Play_Date;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.dogmate.Constants;
import com.example.dogmate.IResult;
import com.example.dogmate.JsonHelperService;
import com.example.dogmate.Login.LoginActivity;
import com.example.dogmate.R;
import com.example.dogmate.Show_Location.ShowLocations;
import com.example.dogmate.VolleyMultipartRequest;
import com.example.dogmate.VolleyService;
import com.example.dogmate.resizeBitmap;
import com.squareup.picasso.Picasso;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.example.dogmate.Constants.ADD_DOG_PATH;


public class AddNewDog extends AppCompatActivity implements View.OnClickListener{
    IResult mResultCallback = null;
    VolleyService mVolleyService;
    SharedPreferences sharedPreferenceFile;
    SharedPreferences.Editor editor;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.et_dogname) EditText mDogNameEditText;
    @BindView(R.id.im_dogphoto)ImageView mDogPhoto;
    @BindView(R.id.sp_cities) Spinner mCities;
    @BindView(R.id.et_neighborhood) EditText mNeighborhoodEditText;
    @BindView(R.id.sp_sizes) Spinner mSizes;
    @BindView(R.id.sp_breeds) Spinner mBreeds;
    @BindView(R.id.sp_temperaments) Spinner mTemperaments;
    @BindView(R.id.btn_createprofile) Button mCreateProfileButton;

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int PERMISSION_REQUEST_CODE = 200;
    String dogName;
    String city;
    String neighborhood;
    String  dogSize;
    String breed;
    String temperament;
    Uri mSelectedImage;
    String imageServerName;
    private String TAG = "Add_Dog";

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
            // The user has uploaded an image and imageServerName has returned from the server
            if (imageServerName != null) {
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
                        temperament, neighborhood, username, city, imageServerName);
                mVolleyService.postDataStringResponseVolley("POST_ADD_DOG", ADD_DOG_PATH,
                        requestJson, credentials);
                // get fields and upload them to DB.
                // save the registration date

                Intent intent = new Intent(AddNewDog.this, DogProfileApproved.class);
                startActivity(intent);
            }
            else{   // The imageServerName has not returned from server
                Toast.makeText(getApplicationContext(), "Your dog's image was not uploaded correctly. Please reload it or upload other image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        // check permission for entering to the user's storage
        if (!checkPermission()) {
            //start the image uploading process
            openActivity();
        } else {
            if (checkPermission()) {
                requestPermissionAndContinue();
            } else {
                openActivity();
            }
        }
    }

    private boolean checkPermission() {

        return ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ;
    }

    private void requestPermissionAndContinue() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle(getString(R.string.permission_necessary));
                alertBuilder.setMessage(R.string.storage_permission_is_encessary_to_wrote_event);
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(AddNewDog.this, new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.e("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(AddNewDog.this, new String[]{WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        } else {
            openActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (permissions.length > 0 && grantResults.length > 0) {

                boolean flag = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        flag = false;
                    }
                }
                if (flag) {
                    openActivity();
                } else {
                    finish();
                }

            } else {
                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void openActivity() {
        //further process after giving permission to download images
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {

            mSelectedImage = data.getData();
            Picasso.get().load(mSelectedImage).fit().centerCrop().into(mDogPhoto);

            try {
                //getting bitmap object from URI
                Bitmap initialBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mSelectedImage);

                //decreasing the bitmap size so that large images can be uploaded + uploading is faster
                Bitmap scaledBitmap = resizeBitmap.scaleBitmap(initialBitmap, getRealPathFromURI(mSelectedImage),mDogPhoto);

                //upload the image to the server
                uploadBitmap(scaledBitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(final Bitmap bitmap) {

        String imageName = getRealPathFromURI(mSelectedImage);

        //a Multipart Volley Request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Constants.ADD_IMAGE_PATH,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        String resultResponse = new String(response.data);
                        try {
                            JSONObject obj = new JSONObject(resultResponse);
                            if (response.statusCode == 200)
                            {
                                imageServerName = obj.getString("imageName");
                                System.out.println("Success, status code 200");
                            }
                            else{
                                Log.i("Unexpected", obj.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse networkResponse = error.networkResponse;
                        String errorMessage = "Unknown error";
                        if (networkResponse == null) {
                            if (error.getClass().equals(TimeoutError.class)) {
                                errorMessage = "Request timeout";
                            } else if (error.getClass().equals(NoConnectionError.class)) {
                                errorMessage = "Failed to connect server";
                            }
                        }else if (networkResponse.statusCode == 413) {
                            errorMessage = "Image file is too large, change the image";
                        }
                        else {
                            String result = new String(networkResponse.data);
                            try {
                                JSONObject response = new JSONObject(result);
                                String message = response.getString("message");

                                Log.e("Error Message", message);

                                if (networkResponse.statusCode == 404) {
                                    errorMessage = "Resource not found";
                                } else if (networkResponse.statusCode == 401) {
                                    errorMessage = message+" Please login again";
                                } else if (networkResponse.statusCode == 400) {
                                    errorMessage = message+ " Check your inputs";
                                } else if (networkResponse.statusCode == 500) {
                                    errorMessage = message+" Something is getting wrong";
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i("Error", errorMessage);
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }) {

            // create the headers part
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                try {
                    Map<String, String> headers = new HashMap<>();
                    String credentials = "x1234:ezjLSVmdQg98nFmH";
                    String auth = "Basic "
                            + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    headers.put("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
                    headers.put("Authorization", auth);
                    headers.put("Accept", "*/*");
                    headers.put("Host", "vmedu196.mtacloud.co.il");
                    headers.put("Accept-Encoding", "gzip, deflate");
                    headers.put("Connection","close");
                    headers.put("Cache-Control","no-cache");
                    return headers;
                } catch (Exception e) {
                    Log.e("Volley", "Authentication Failure");
                }
                return super.getParams();
            }

            // create the body part (the image)
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("image", new DataPart(imageName, getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        // add the multipart request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
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

        else if (isEmptyImageView(mSelectedImage)) {
            Toast t = Toast.makeText(this, "Please enter your dog's image", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

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




