package com.example.dogmate.Play_Date;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) // Checks the API level of the device
        {
            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setSupportActionBar(mToolbar);

        searchDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DogProfileApproved.this, AddNewDog.class );
                startActivity(intent);
            }
        });
    }
}

