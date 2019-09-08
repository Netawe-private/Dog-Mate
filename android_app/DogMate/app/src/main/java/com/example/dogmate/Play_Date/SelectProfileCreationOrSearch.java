package com.example.dogmate.Play_Date;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.dogmate.Login.SendEmailPage;
import com.example.dogmate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectProfileCreationOrSearch extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;

    @BindView(R.id.btn_createDogProfile) Button mCreateProfileButton;
    @BindView(R.id.btn_findPlayDate) Button mFindPlayDateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile_creation_or_search);
        ButterKnife.bind(this);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) // Checks the API level of the device
        {
            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        mCreateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectProfileCreationOrSearch.this, AddNewDog.class );
                startActivity(intent);
            }
        });

        mFindPlayDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectProfileCreationOrSearch.this, AddNewDog.class );
                startActivity(intent);
            }
        });
    }
}


