package com.example.dogmate.Login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dogmate.MyApplication;
import com.example.dogmate.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    EditText firstName;
    EditText lastName;
    EditText userName;
    EditText emailAddress;
    EditText password;
    EditText numberOfDogs;
    ImageView lnkImage;
    Button createAccount;
    Uri selectedImage;

    private static final int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firstName = (EditText) findViewById(R.id.email);
        lastName = (EditText) findViewById(R.id.lastName);
        userName = (EditText) findViewById(R.id.userName);
        emailAddress = (EditText) findViewById(R.id.emailAddress);
        password = (EditText) findViewById(R.id.password);
        numberOfDogs = (EditText) findViewById(R.id.numberOfDogs);
        lnkImage = (ImageView) findViewById(R.id.im_dogphoto);
        createAccount = (Button) findViewById(R.id.createAccount);

        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        String url = "http://vmedu196.mtacloud.co.il/register";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) // Checks the API level of the device
        {
            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        lnkImage.setOnClickListener(this);

        //validations of all fields
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = checkDataEntered(firstName, lastName, userName, emailAddress, password, numberOfDogs);
                if (isValid) {

                    // send data to server

                    JSONObject parameter = new JSONObject();

                    try {
                        parameter.put("firstName", firstName);
                        parameter.put("lastName", lastName);
                        parameter.put("userName", userName);
                        parameter.put("password", password);
                        parameter.put("email", emailAddress);
                        parameter.put("numOfDogs", numberOfDogs);
                        if (selectedImage != null){                         // add image only if it was uploaded
                            parameter.put("imageUrl", selectedImage);}

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST,
                            url, parameter,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.e("Rest Response", response.toString());
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("Rest Response", error.toString());
                                }
                            }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            try {
                                Map<String, String> headers  = new HashMap<>();
                                headers.put("Content-Type", "application/json");
                                return headers;
                            } catch (Exception e) {
                                Log.e("Volley", "Authentication Filure" );
                            }
                            return super.getParams();
                        }
                    };

                    mRequestQueue.add(objectRequest);

                    //MyApplication.getInstance().addToRequestQueue(objectRequest, "postRequest");

                    // validate that the userName doesn't exist
                    // send a verification email to the user

                    Intent intent = new Intent(RegistrationActivity.this, SendEmailPage.class );
                    startActivity(intent);
                }
            }
        });
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
            selectedImage = data.getData();

            Picasso.get().load(selectedImage).fit().centerCrop().into(lnkImage);
        }
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean isEmailValid(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isPasswordValid(EditText text) {
        CharSequence pass = text.getText().toString();
        if (TextUtils.isEmpty(pass))
            return false;
        else {
            for (int i = 0; i < pass.length(); i++) {
                if (!Character.isLetterOrDigit(pass.charAt(i)) && !Character.toString(pass.charAt(i)).equals("_") &&
                        !Character.toString(pass.charAt(i)).equals("-"))
                    return false;
            }
        }
        return true;
    }

    boolean isNumOfDogsValid(EditText text) {
        CharSequence dogs = text.getText().toString();
        if (TextUtils.isEmpty(dogs))
            return false;
        else if (!TextUtils.isDigitsOnly(dogs))
            return false;
        return true;
    }

    boolean checkDataEntered(EditText firstName, EditText lastName, EditText userName, EditText emailAddress, EditText password, EditText numberOfDogs) {


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

        else if (isEmailValid(emailAddress) == false) {
            Toast t = Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        else if (isPasswordValid(password) == false) {
            Toast t = Toast.makeText(this, "Password must contain English letters, numbers, _, - ", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        else if (isNumOfDogsValid(numberOfDogs) == false) {
            Toast t = Toast.makeText(this, "Please enter the number of dogs you own", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        else
            return true; // need to validate that the user name doesn't exist in the DB
    }



}














