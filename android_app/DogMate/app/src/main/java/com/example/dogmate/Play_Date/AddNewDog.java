package com.example.dogmate.Play_Date;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.dogmate.R;
import com.squareup.picasso.Picasso;


import butterknife.BindView;
import butterknife.ButterKnife;


public class AddNewDog extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.toolbar) Toolbar mToolbar;

    @BindView(R.id.et_dogname) EditText mDogNameEditText;
    @BindView(R.id.im_dogphoto) ImageView mDogPhoto;
    @BindView(R.id.sp_cities) Spinner mCities;
    @BindView(R.id.et_neighborhood) EditText mNeighborhoodEditText;
    @BindView(R.id.sp_sizes) Spinner mSizes;
    @BindView(R.id.sp_breeds) Spinner mBreeds;
    @BindView(R.id.sp_temperaments) Spinner mTemperaments;
    @BindView(R.id.btn_createprofile) Button mCreateProfileButton;

    private static final int RESULT_LOAD_IMAGE = 1;

    Uri mSelectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_dog);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mDogPhoto.setOnClickListener(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) // Checks the API level of the device
        {
            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        //validations of all fields
        mCreateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = checkDataEntered(mDogNameEditText, mSelectedImage, mCities, mNeighborhoodEditText, mSizes, mBreeds, mTemperaments);
                if (isValid) {

                    // get fields and upload them to DB.
                    // save the registration date

                    Intent intent = new Intent(AddNewDog.this, DogProfileApproved.class );
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

            mSelectedImage = data.getData();
            Picasso.get().load(mSelectedImage).fit().centerCrop().into(mDogPhoto);
        }
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean isEmptyImageView(Uri uri) {
        return (uri == null);
    }

    boolean isSpinnerSelected(Spinner spinner) {
        return (spinner.getSelectedItemPosition() == 0);
    }

    boolean checkDataEntered(EditText mDogNameEditText, Uri mSelectedImage, Spinner mCities, EditText mNeighborhoodEditText, Spinner mSizes, Spinner mBreeds, Spinner mTemperaments) {


        if (isEmpty(mDogNameEditText)) {
            Toast t = Toast.makeText(this, "Please enter your dog's name", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        else if (isEmptyImageView(mSelectedImage)) {
            Toast t = Toast.makeText(this, "Please enter your dog's image", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        else if (isSpinnerSelected(mCities)) {
            Toast t = Toast.makeText(this, "Please select your city", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        if (isEmpty(mNeighborhoodEditText)) {
            Toast t = Toast.makeText(this, "Please enter your neighborhood", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        else if (isSpinnerSelected(mSizes)) {
            Toast t = Toast.makeText(this, "Please select your dog's size", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        else if (isSpinnerSelected(mBreeds)) {
            Toast t = Toast.makeText(this, "Please select your dog's breed", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        else if (isSpinnerSelected(mTemperaments)) {
            Toast t = Toast.makeText(this, "Please select your dog's temperament", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        else
            return true;
    }
}




