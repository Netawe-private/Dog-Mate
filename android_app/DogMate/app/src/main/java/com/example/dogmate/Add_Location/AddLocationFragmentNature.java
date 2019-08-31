package com.example.dogmate.Add_Location;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dogmate.R;

public class AddLocationFragmentNature extends Fragment  {

    private RatingBar amountOfShadowRatingNature;
    private RatingBar waterResNature;
    private CheckBox isReleaseDogNature;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_find_location_nature, container,false);

        amountOfShadowRatingNature = rootView.findViewById(R.id.degreeOfShadowRatingNature);
        waterResNature = rootView.findViewById(R.id.waterResNature);
        isReleaseDogNature = (CheckBox)rootView.findViewById(R.id.isReleaseDogNature);

        if (getArguments() != null){
            setParametersNature(getArguments().getString("rating"),
                                                getArguments().getString("water_resource"),
                                                getArguments().getString("release_dog"));
        }

        return rootView;
    }

    public boolean getIsReleaseDogNature(){
        return isReleaseDogNature.isChecked();
    }

    public float getAmountOfShadowRatingNature(){
        return amountOfShadowRatingNature.getRating();
    }

    public float getWaterResNature(){
        return waterResNature.getRating();
    }

    private void setParametersNature(String amountOfShadowRatingNature, String waterResourceRating, String releaseDog){
        if (amountOfShadowRatingNature != null){
            this.amountOfShadowRatingNature.setRating(Float.valueOf(amountOfShadowRatingNature));
        }
        this.amountOfShadowRatingNature.setIsIndicator(true);

        if (waterResourceRating != null){
            this.waterResNature.setRating(Float.valueOf(waterResourceRating));
        }
        this.waterResNature.setIsIndicator(true);
        this.isReleaseDogNature.setChecked(Boolean.parseBoolean(releaseDog));
        this.isReleaseDogNature.setEnabled(false);

    }


}
