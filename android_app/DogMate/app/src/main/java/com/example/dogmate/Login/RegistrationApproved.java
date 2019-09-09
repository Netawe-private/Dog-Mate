package com.example.dogmate.Login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.dogmate.Play_Date.AddNewDog;
import com.example.dogmate.Play_Date.DogProfileApproved;
import com.example.dogmate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationApproved extends AppCompatActivity {

    @BindView(R.id.btn_back_to_login) Button mBackToLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_approved);
        ButterKnife.bind(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) // Checks the API level of the device
        {
            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        mBackToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationApproved.this, LoginActivity.class );
                startActivity(intent);
            }
        });
    }
}
