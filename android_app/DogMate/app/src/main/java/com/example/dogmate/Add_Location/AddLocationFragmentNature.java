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

public class AddLocationFragmentNature extends Fragment  {

    private RatingBar shadowLevelRatingNatureRatingBar;
    private CheckBox availableWaterCheckBox;
    private CheckBox isReleaseDogCheckbox;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_find_location_nature, container,false);

        shadowLevelRatingNatureRatingBar = rootView.findViewById(R.id.shadowLevelRatingNature);
        availableWaterCheckBox = rootView.findViewById(R.id.availableWaterNature);
        isReleaseDogCheckbox = rootView.findViewById(R.id.releaseDogIsAllowedNature);

        if (getArguments() != null){
            setParametersNature(getArguments().getString("shadowLevel"),
                                                getArguments().getString("availableWater"),
                                                getArguments().getString("releaseDogIsAllowed"));
        }

        return rootView;
    }

    public boolean getIsReleaseDogCheckBox(){
        return isReleaseDogCheckbox.isChecked();
    }

    public float getShadowLevelRatingNatureRatingBar(){
        return shadowLevelRatingNatureRatingBar.getRating();
    }

    public boolean getAvailableWaterCheckBox(){
        return availableWaterCheckBox.isChecked();
    }

    private void setParametersNature(String shadowLevel, String availableWater,
                                                                String releaseDogIsAllowed){
        if (shadowLevel != null){
            this.shadowLevelRatingNatureRatingBar.setRating(Float.valueOf(shadowLevel));
        }
        this.shadowLevelRatingNatureRatingBar.setIsIndicator(true);

        if (availableWater != null){
            this.availableWaterCheckBox.setChecked(Boolean.parseBoolean(availableWater));
        }
        this.availableWaterCheckBox.setEnabled(false);

        if (releaseDogIsAllowed != null){
            this.isReleaseDogCheckbox.setChecked(Boolean.parseBoolean(releaseDogIsAllowed));
        }
        this.isReleaseDogCheckbox.setEnabled(false);

    }


}
