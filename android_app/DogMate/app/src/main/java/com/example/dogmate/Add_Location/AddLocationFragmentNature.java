package com.example.dogmate.Add_Location;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RatingBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dogmate.R;

public class AddLocationFragmentNature extends Fragment {
    RatingBar degreeOfShadowRatingNature;
    RatingBar waterResNature;
    CheckBox isReleaseDogNature;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_find_location_nature, container,false);

        degreeOfShadowRatingNature = rootView.findViewById(R.id.degreeOfShadowRatingNature);
        waterResNature = rootView.findViewById(R.id.hasShadowCheck);
        isReleaseDogNature = (CheckBox)rootView.findViewById(R.id.isReleaseDogNature);

        return rootView;
    }

    public boolean getIsReleaseDogNature(){
        return isReleaseDogNature.isChecked();
    }

    public float getDegreeOfShadowRatingNature(){
        return degreeOfShadowRatingNature.getRating();
    }

    public float getWaterResNature(){
        return waterResNature.getRating();
    }
}
