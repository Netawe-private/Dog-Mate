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

    private RatingBar waterRatingNature;
    private CheckBox shadowResNature;
    private CheckBox isReleaseDogNature;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_find_location_nature, container,false);

        waterRatingNature = rootView.findViewById(R.id.waterResRatingNature);
        shadowResNature = rootView.findViewById(R.id.isShadowNature);
        isReleaseDogNature = rootView.findViewById(R.id.isReleaseDogNature);

        if (getArguments() != null){
            setParametersNature(getArguments().getString("shadowPlace"),
                                                getArguments().getString("availableWater"),
                                                getArguments().getString("releaseDogIsAllowed"));
        }

        return rootView;
    }

    public boolean getIsReleaseDogNature(){
        return isReleaseDogNature.isChecked();
    }

    public float getWaterRatingNature(){
        return waterRatingNature.getRating();
    }

    public boolean getShadowResNature(){
        return shadowResNature.isChecked();
    }

    private void setParametersNature(String shadowPlace, String availableWater,
                                                                String releaseDogIsAllowed){
        if (availableWater != null){
            this.waterRatingNature.setRating(Float.valueOf(availableWater));
        }
        this.waterRatingNature.setIsIndicator(true);

        if (shadowPlace != null){
            this.shadowResNature.setChecked(Boolean.parseBoolean(shadowPlace));
        }
        this.shadowResNature.setEnabled(false);
        this.isReleaseDogNature.setChecked(Boolean.parseBoolean(releaseDogIsAllowed));
        this.isReleaseDogNature.setEnabled(false);

    }


}
