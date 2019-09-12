package com.example.dogmate.Login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.dogmate.IResult;
import com.example.dogmate.JsonHelperService;
import com.example.dogmate.R;
import com.example.dogmate.VolleyService;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

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
    ImageView lnkImageImageView;
    Button createAccount;
    Uri selectedImage;
    IResult mResultCallback = null;
    VolleyService mVolleyService;
    private String TAG = "Registration";


    private static final int RESULT_LOAD_IMAGE = 1;

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
        lnkImageImageView = (ImageView) findViewById(R.id.im_dogphoto);
        createAccount = (Button) findViewById(R.id.createAccount);

        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) // Checks the API level of the device
        {
            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

//        lnkImageImageView.setOnClickListener(this);
//
//        //validations of all fields
//        createAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClickLoginButton(View view) {
//                boolean isValid = checkDataEntered(firstNameEditText, lastNameEditText, userNameEditText, emailAddressEditText, passwordEditText, numberOfDogsEditText);
//                if (isValid) {
//
//                    // send data to server
//
//                    JSONObject parameter = new JSONObject();
//
//                    try {
//                        parameter.put("firstNameEditText", firstNameEditText);
//                        parameter.put("lastNameEditText", lastNameEditText);
//                        parameter.put("userNameEditText", userNameEditText);
//                        parameter.put("passwordEditText", passwordEditText);
//                        parameter.put("usernameEditText", emailAddressEditText);
//                        parameter.put("numOfDogs", numberOfDogsEditText);
//                        if (selectedImage != null){                         // add image only if it was uploaded
//                            parameter.put("imageUrl", selectedImage);}
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST,
//                            REGISTRATION_PATH, parameter,
//                            new Response.Listener<JSONObject>() {
//                                @Override
//                                public void onResponse(JSONObject response) {
//                                    Log.e("Rest Response", response.toString());
//                                    Intent intent = new Intent(RegistrationActivity.this, RegistrationApproved.class );
//                                    startActivity(intent);
//                                }
//                            },
//                            new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError error) {
//                                    Log.e("Rest Response", error.toString());
//                                }
//                            }){
//                        @Override
//                        public Map<String, String> getHeaders() throws AuthFailureError {
//                            try {
//                                Map<String, String> headers  = new HashMap<>();
//                                headers.put("Content-Type", "application/json");
//                                return headers;
//                            } catch (Exception e) {
//                                Log.e("Volley", "Authentication Filure" );
//                            }
//                            return super.getParams();
//                        }
//                    };
//
//                    mRequestQueue.add(objectRequest);
//
//                    //MyApplication.getInstance().addToRequestQueue(objectRequest, "postRequest");
//
//                    // validate that the userNameEditText doesn't exist
//                    // send a verification usernameEditText to the user
//
//
//                }
//            }
//        });
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
            JSONObject requestJson = JsonHelperService.createRegistrationRequest(firstName, lastName,
                                            userName, password, email, numberOfDogs, selectedImage);
            mVolleyService.postDataStringResponseVolleyWithoutAuth("POST_REGISTRATION_REQUEST",REGISTRATION_PATH,requestJson,null);
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
            selectedImage = data.getData();

            Picasso.get().load(selectedImage).fit().centerCrop().into(lnkImageImageView);
        }
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
            return true; // need to validate that the user name doesn't exist in the DB
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














