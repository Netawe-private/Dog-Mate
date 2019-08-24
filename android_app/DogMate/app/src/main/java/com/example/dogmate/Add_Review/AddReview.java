package com.example.dogmate.Add_Review;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.dogmate.Add_Location.AddLocationFragmentEntertainment;
import com.example.dogmate.Add_Location.AddLocationFragmentNature;
import com.example.dogmate.Add_Location.AddLocationFragmentParks;
import com.example.dogmate.Add_Location.AddLocationFragmentServicesShop;
import com.example.dogmate.Add_Location.AddLocationFragmentServicesVet;
import com.example.dogmate.Add_Location.AddLocationFragmentVacationCamping;
import com.example.dogmate.Add_Location.AddLocationFragmentVacationHotel;
import com.example.dogmate.DrawerMenu;
import com.example.dogmate.R;

import static com.example.dogmate.R.id.fragment_container;
import static com.example.dogmate.R.id.review_container;

public class AddReview  extends DrawerMenu {
    RatingBar reviewRating;
    EditText reviewComment;
    AddLocationFragmentEntertainment EntertainmentFrag;
    AddLocationFragmentNature NatureFrag;
    AddLocationFragmentParks ParksFrag;
    AddLocationFragmentServicesShop servicesShopFrag;
    AddLocationFragmentServicesVet serviceVetFrag;
    AddLocationFragmentVacationCamping vacationCampingFrag;
    AddLocationFragmentVacationHotel vacationHotelFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_review);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_add_review, drawer, false);
        drawer.addView(contentView, 0);


        reviewComment = findViewById(R.id.editTextReviewComment);
        reviewRating = findViewById(R.id.reviewRating);

    }


}
