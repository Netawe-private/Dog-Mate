package com.example.dogmate.Play_Date;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.dogmate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DogProfileApproved extends AppCompatActivity {

    @BindView(R.id.btn_back_to_login) Button searchDog;
    @BindView(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_profile_approved);
        ButterKnife.bind(this);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        // Enable the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);

        searchDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DogProfileApproved.this, SearchActivity.class );
                startActivity(intent);
            }
        });
    }
}


