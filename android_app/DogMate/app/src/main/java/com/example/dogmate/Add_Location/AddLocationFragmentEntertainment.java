package com.example.dogmate.Add_Location;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
        hasShadowCheck =(CheckBox) rootView.findViewById(R.id.hasShadowCheck);
        degreeOfShadowRatingEnt = rootView.findViewById(R.id.degreeOfShadowRatingEnt);
        if (getArguments() != null){
            setParametersEntertainment(getArguments().getString("amount_shadow"),
                    getArguments().getString("shadow"),
                    getArguments().getString("sitting_inside"));
        }


        return rootView;
    }

    public boolean getSittingInsideCheck(){
        return isSittingInsideCheck.isChecked();
    }

    public boolean getHasShadowCheck(){
        return hasShadowCheck.isChecked();
    }

    public float degreeOfShadowRatingEnt(){
        return degreeOfShadowRatingEnt.getRating();
    }

    public boolean validateShadowField(){
        if (getHasShadowCheck() && degreeOfShadowRatingEnt() == 0){
            degreeOfShadowRatingEnt.setBackgroundResource(R.drawable.shapes);
        }
        return true;
    }

    private void setAmountOfShadowRatingEnt(Float rating) {
        degreeOfShadowRatingEnt.setRating(rating);
        degreeOfShadowRatingEnt.setEnabled(false);
    }

    private void setHasShadowCheck(boolean hasShadowCheck) {
        this.hasShadowCheck.setChecked(hasShadowCheck);
    }

    private void setIsSittingInsideCheck(boolean isSittingInsideCheck) {
        this.isSittingInsideCheck.setChecked(isSittingInsideCheck);
    }

    private void setParametersEntertainment(String amountShadow, String hadShadow, String sittingInside){
        setHasShadowCheck(Boolean.parseBoolean(hadShadow));
        setIsSittingInsideCheck(Boolean.parseBoolean(sittingInside));
        setAmountOfShadowRatingEnt(Float.valueOf(amountShadow));

    }


}
