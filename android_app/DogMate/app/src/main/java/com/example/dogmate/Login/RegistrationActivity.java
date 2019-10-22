package com.example.dogmate.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.dogmate.Constants;
import com.example.dogmate.IResult;
import com.example.dogmate.JsonHelperService;
import com.example.dogmate.R;
import com.example.dogmate.VolleyMultipartRequest;
import com.example.dogmate.VolleyService;
import com.example.dogmate.resizeBitmap;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.example.dogmate.Constants.REGISTRATION_PATH;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    EditText firstNameEditText;
    String firstName;
    String lastName;
    String userName;
    String email;
    String password;
    int numberOfDogs;
    EditText lastNameEditText;
    EditText userNameEditText;
    EditText emailAddressEditText;
    EditText passwordEditText;
    EditText numberOfDogsEditText;
    ImageView lnkImageView;
    Button createAccount;
    Uri selectedImage;
    IResult mResultCallback = null;
    VolleyService mVolleyService;
    private String TAG = "Registration";
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int PERMISSION_REQUEST_CODE = 200;
    String imageServerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,this);

        firstNameEditText = (EditText) findViewById(R.id.loginUsername);
        lastNameEditText = (EditText) findViewById(R.id.lastName);
        userNameEditText = (EditText) findViewById(R.id.userName);
        emailAddressEditText = (EditText) findViewById(R.id.emailAddress);
        passwordEditText = (EditText) findViewById(R.id.password);
        numberOfDogsEditText = (EditText) findViewById(R.id.numberOfDogs);
        lnkImageView = (ImageView) findViewById(R.id.im_dogphoto);
        createAccount = (Button) findViewById(R.id.createAccount);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) // Checks the API level of the device
        {
            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        lnkImageView.setOnClickListener(this);
    }

    public void onClickRegistration(View view){
        userName = userNameEditText.getText().toString();
        email = emailAddressEditText.getText().toString();
        firstName = firstNameEditText.getText().toString();
        lastName = lastNameEditText.getText().toString();
        password = passwordEditText.getText().toString();
        numberOfDogs = Integer.valueOf(numberOfDogsEditText.getText().toString());

        boolean isValid = checkDataEntered(firstName, lastName, userName, email, password, numberOfDogs);
        if (isValid){
            if (selectedImage != null){
                if (imageServerName != null){
                    JSONObject requestJson = JsonHelperService.createRegistrationRequest(firstName, lastName,
                            userName, password, email, numberOfDogs, imageServerName);
                    mVolleyService.postDataStringResponseVolleyWithoutAuth("POST_REGISTRATION_REQUEST",REGISTRATION_PATH,requestJson,null);
                }
                else{   // an image was uploaded to the UI but not uploaded to the server (image is too large, etc.)
                    Toast.makeText(getApplicationContext(), "Your image was not uploaded correctly. Please reload it or upload other image", Toast.LENGTH_SHORT).show();
                }
            }
            else{       //selectedImage == null, i.e. an image was not uploaded (which is also ok)
                JSONObject requestJson = JsonHelperService.createRegistrationRequest(firstName, lastName,
                        userName, password, email, numberOfDogs, imageServerName);
                mVolleyService.postDataStringResponseVolleyWithoutAuth("POST_REGISTRATION_REQUEST",REGISTRATION_PATH,requestJson,null);
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
                        ActivityCompat.requestPermissions(RegistrationActivity.this, new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.e("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(RegistrationActivity.this, new String[]{WRITE_EXTERNAL_STORAGE,
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
            selectedImage = data.getData();

            Picasso.get().load(selectedImage).fit().centerCrop().into(lnkImageView);

            try {
                //getting bitmap object from URI
                Bitmap initialBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                //decreasing the image size so that large images can be uploaded + uploading is faster
                Bitmap scaledBitmap = resizeBitmap.scaleBitmap(initialBitmap, getRealPathFromURI(selectedImage), lnkImageView);

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

        String imageName = getRealPathFromURI(selectedImage);

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

    boolean isEmpty(String text) {
        return TextUtils.isEmpty(text);
    }

    boolean isEmailValid(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isPasswordValid(String password) {
        if (TextUtils.isEmpty(password))
            return false;
        else {
            for (int i = 0; i < password.length(); i++) {
                if (!Character.isLetterOrDigit(password.charAt(i)) && !Character.toString(password.charAt(i)).equals("_") &&
                        !Character.toString(password.charAt(i)).equals("-"))
                    return false;
            }
        }
        return true;
    }

    boolean isNumOfDogsValid(int dogs) {
        String numberOfDogs = String.valueOf(dogs);
        if (TextUtils.isEmpty(numberOfDogs))
            return false;
        else if (!TextUtils.isDigitsOnly(numberOfDogs))
            return false;
        return true;
    }

    boolean checkDataEntered(String firstName, String lastName, String userName, String emailAddress, String password, int numberOfDogs) {

        if (isEmpty(firstName)) {
            Toast t = Toast.makeText(this, "Please enter your first name", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        else if (isEmpty(lastName)) {
            Toast t = Toast.makeText(this, "Please enter your last name", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        else if (isEmpty(userName)) {
            Toast t = Toast.makeText(this, "Please enter a user name", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        else if (!isEmailValid(emailAddress)) {
            Toast t = Toast.makeText(this, "Please enter a valid usernameEditText address", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        else if (!isPasswordValid(password)) {
            Toast t = Toast.makeText(this, "Password must contain English letters, numbers, _, - ", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        else if (!isNumOfDogsValid(numberOfDogs)) {
            Toast t = Toast.makeText(this, "Please enter the number of dogs you own", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        else
            return true;
    }

    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType,JSONObject response) {
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
                Intent intent = new Intent(RegistrationActivity.this,
                        RegistrationApproved.class );
                startActivity(intent);
            }
        };
    }
}