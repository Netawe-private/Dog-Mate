package com.example.dogmate.Login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dogmate.MyApplication;
import com.example.dogmate.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

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

    // String url = "http://vmedu196.mtacloud.co.il/register";

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

                /*    JSONObject parameter = new JSONObject();

                        try {
                            parameter.put("firstName", firstName);
                            parameter.put("lastName", lastName);
                            parameter.put("userName", userName);
                            parameter.put("password", password);
                            parameter.put("email", emailAddress);
                            parameter.put("numOfDogs", numberOfDogs);
                           if (selectedImage != null){     // add image only if it was uploaded
                                parameter.put("imageUrl", selectedImage);}

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    JsonObjectRequest objectRequest = new JsonObjectRequest(
                            Request.Method.POST,
                            url, parameter,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.e("Rest Response", response.toString());
                                    Log.d("onResponse", String.valueOf(response));

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("Rest Response", error.toString());
                                }

                })
                    {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            try {
                                Map<String, String> headers  = new HashMap<>();
                                String credentials = "x1234:ezjLSVmdQg98nFmH";
                                String auth = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO) {
                                    auth = "Basic "
                                            + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                                }
                                headers.put("Content-Type", "application/json");
                                headers.put("Authorization", auth);
                                return headers;
                            } catch (Exception e) {
                                Log.e("Volley", "Authentication Filure" );
                            }
                            return super.getParams();
                        }};

                        mRequestQueue.add(objectRequest);*/



                    //MyApplication.getInstance().addToRequestQueue(objectRequest, "postRequest");

                    // validate that the userName doesn't exist
                    // send a verification email to the user

                 /*   StringRequest request = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response) {

                                    Log.d("onResponse", response);

                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    NetworkResponse response = error.networkResponse;
                                    String errorMsg = "";
                                    if(response != null && response.data != null){
                                        String errorString = new String(response.data);
                                        Log.i("log error", errorString);
                                    }
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams()
                        {

                            Map<String, String> params = new HashMap<String, String>();

                            String mFirstName = firstName.getText().toString();
                            String mLastName = lastName.getText().toString();
                            String mUserName = userName.getText().toString();
                            String mPassword = password.getText().toString();
                            String mEmailAddress = emailAddress.getText().toString();
                            String mNumberOfDogs = numberOfDogs.getText().toString();
                            String mSelectedImage = selectedImage.toString();

                            params.put("firstName", mFirstName);
                            params.put("lastName", mLastName);
                            params.put("userName", mUserName);
                            params.put("password", mPassword);
                            params.put("email", mEmailAddress);
                            params.put("numOfDogs", mNumberOfDogs);
                            if (selectedImage != null){                         // add image only if it was uploaded
                                params.put("imageUrl", mSelectedImage);}
                            Log.i("sending ", params.toString());

                            return params;
                        }
                    };

                    // Add the reliability on the connection.
                    request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));

                    // Start the request immediately
                    mRequestQueue.add(request);*/

                    new RegistrationActivity.Connection().execute();


                    Intent intent = new Intent(RegistrationActivity.this, SendEmailPage.class );
                    startActivity(intent);
                }
            }
        });
    }
    private class Connection extends AsyncTask {

        @Override
        protected Object doInBackground(Object... arg0) {
            connect();
            return null;
        }
    }

    private void connect() {
        URL url = null;
        try {
            url = new URL("http://vmedu196.mtacloud.co.il/register");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        JSONObject parameter = new JSONObject();

        try {
            parameter.put("firstName", firstName);
            parameter.put("lastName", lastName);
            parameter.put("userName", userName);
            parameter.put("password", password);
            parameter.put("email", emailAddress);
            parameter.put("numOfDogs", numberOfDogs);
            if (selectedImage != null){     // add image only if it was uploaded
                parameter.put("imageUrl", selectedImage);}

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(20000);
            conn.setConnectTimeout(20000);
            try {
                conn.setRequestMethod("POST");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = null;
            try {
                os = conn.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(os, "UTF-8"));
            writer.write(encodeParams(parameter));
            writer.flush();
            writer.close();
            os.close();

            int responseCode=conn.getResponseCode(); // To Check for 200
            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in=new BufferedReader( new InputStreamReader(conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line="";
                while((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                System.out.println("Server response: " + sb.toString());
            }
            System.out.println("Server response: not Ok" );



        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String encodeParams(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while(itr.hasNext()){
            String key= itr.next();
            Object value = params.get(key);
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
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














