package com.example.dogmate.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.dogmate.Constants;
import com.example.dogmate.IResult;
import com.example.dogmate.JsonHelperService;
import com.example.dogmate.R;
import com.example.dogmate.Show_Location.ShowLocations;
import com.example.dogmate.VolleyService;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.dogmate.Constants.LOGIN_PATH;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences sharedPreferenceFile;
    SharedPreferences.Editor editor;
    IResult mResultCallback = null;
    VolleyService mVolleyService;
    private String TAG = "Login";


    EditText usernameEditText;
    EditText passwordEditText;
    Button btnLogin;
    String username;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,this);
        sharedPreferenceFile =  getSharedPreferences(Constants.SHAREDPREF_NAME, 0);

        usernameEditText = findViewById(R.id.loginUsername);
        passwordEditText = findViewById(R.id.insertPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // link to registration
        TextView register = (TextView)findViewById(R.id.im_dogphoto);
        register.setMovementMethod(LinkMovementMethod.getInstance());
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onClickLoginButton(View view){
        boolean isValid = checkDataEntered(usernameEditText, passwordEditText);
        if (isValid)
        {
            password = passwordEditText.getText().toString();
            username = usernameEditText.getText().toString();
            JSONObject requestJson = JsonHelperService.createLoginRequest(username, password);
            mVolleyService.postDataStringResponseVolleyWithoutAuth("POST_LOGIN_REQUEST", LOGIN_PATH,
                                                            requestJson, null);

        }
    }

    boolean isAdmin(EditText editTextUsername){
        String username = editTextUsername.getText().toString();
        return (username.equals("x1234") || username.equals("x123"));
    }

    boolean isEmailValid(EditText text){
        String username = text.getText().toString();
        return (!TextUtils.isEmpty(username) /*&& Patterns.EMAIL_ADDRESS.matcher(email).matches()*/);
    }

    boolean isEmpty(EditText text) {
        String str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean checkDataEntered(EditText usernameEditText, EditText password) {

        if (!isEmailValid(usernameEditText)) {
            Toast t = Toast.makeText(this, "Please enter a valid username", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        else if (isEmpty(password)) {
            Toast t = Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT);
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
                try {
                    JSONObject responseJson = new JSONObject(response);
                    addUserDetailsToSharedpreferenceFile(responseJson.getString("username"),
                                                        responseJson.getString("password"),
                                                        responseJson.getBoolean("isAdmin"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(LoginActivity.this, ShowLocations.class );
                startActivity(intent);
            }
        };
    }

    private void addUserDetailsToSharedpreferenceFile(String username, String password, boolean isAdmin){
        editor = sharedPreferenceFile.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putBoolean("isAdmin", isAdmin);
        editor.apply();
    }
}
