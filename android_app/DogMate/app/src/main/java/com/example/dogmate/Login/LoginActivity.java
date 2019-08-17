package com.example.dogmate.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dogmate.MainActivity;
import com.example.dogmate.R;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.insertEmail);
        password = findViewById(R.id.insertPassword);
        btnLogin = findViewById(R.id.btnLogin);

        //validations of email & password
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                boolean isValid = checkDataEntered(email, password);
                if (isValid)
                {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class );
                    startActivity(intent);
                }
            }
        });

        // link to registration
        TextView register = (TextView)findViewById(R.id.lnkRegister);
        register.setMovementMethod(LinkMovementMethod.getInstance());
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    boolean isAdmin(EditText text1, EditText text2){
        String email = text1.getText().toString();
        String pass = text2.getText().toString();
        return (email.equals("admin") && pass.equals("1234"));
    }

    boolean isEmailValid(EditText text){
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean checkDataEntered(EditText email, EditText password) {

        if (isAdmin(email, password))
            return true;

        else if (isEmailValid(email) == false) {
            Toast t = Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        else if (isEmpty(password)) {
            Toast t = Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        else
            return true;  // need to validate whether the email & password exist in the DB
    }
}
