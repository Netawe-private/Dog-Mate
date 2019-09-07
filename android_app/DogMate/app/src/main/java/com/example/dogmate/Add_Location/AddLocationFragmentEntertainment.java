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

public class AddLocationFragmentEntertainment extends Fragment {
    CheckBox isSittingInsideCheck;
    CheckBox hasShadowCheck;
    RatingBar degreeOfShadowRatingEnt;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_location_entertainment, container,false);

        isSittingInsideCheck = (CheckBox)rootView.findViewById(R.id.isSittingInsideCheck);
        hasShadowCheck =(CheckBox) rootView.findViewById(R.id.hasShadowCheckEnt);
        degreeOfShadowRatingEnt = rootView.findViewById(R.id.degreeOfShadowRatingEnt);
        if (getArguments() != null){
            setParametersEntertainment(getArguments().getString("shadowLevel"),
                    getArguments().getString("shadowPlace"),
                    getArguments().getString("sittingInsideAllowed"));
        }


        return rootView;
    }

    public boolean getSittingInsideCheck(){
        return isSittingInsideCheck.isChecked();
    }

    public boolean getHasShadowCheck(){
        return hasShadowCheck.isChecked();
    }

    public float getDegreeOfShadowRatingEnt(){
        return degreeOfShadowRatingEnt.getRating();
    }

    public boolean validateShadowField(){
        if (getHasShadowCheck() && getDegreeOfShadowRatingEnt() == 0){
            degreeOfShadowRatingEnt.setBackgroundResource(R.drawable.shapes);
        }
        return true;
    }

    private void setAmountOfShadowRatingEnt(Float rating) {
        degreeOfShadowRatingEnt.setRating(rating);
        degreeOfShadowRatingEnt.setIsIndicator(true);
    }

    private void setHasShadowCheck(boolean hasShadowCheck) {
        this.hasShadowCheck.setChecked(hasShadowCheck);
        this.hasShadowCheck.setEnabled(false);
    }

    private void setIsSittingInsideCheck(boolean isSittingInsideCheck) {
        this.isSittingInsideCheck.setChecked(isSittingInsideCheck);
        this.isSittingInsideCheck.setEnabled(false);
    }

    private void setParametersEntertainment(String shadowLevel, String shadowPlace, String sittingInsideAllowed){
        setHasShadowCheck(Boolean.parseBoolean(shadowPlace));
        setIsSittingInsideCheck(Boolean.parseBoolean(sittingInsideAllowed));
        setAmountOfShadowRatingEnt(Float.valueOf(shadowLevel));

    }


}
