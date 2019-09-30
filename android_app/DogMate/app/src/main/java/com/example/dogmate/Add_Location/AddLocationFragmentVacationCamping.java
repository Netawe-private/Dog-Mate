package com.example.dogmate.Add_Location;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dogmate.R;
import com.google.android.material.textfield.TextInputLayout;

public class AddLocationFragmentVacationCamping extends Fragment {
    EditText editTextAdditionalServices;
    CheckBox nextToBeachCamping;
    CheckBox isFoodAroundCamping;
    RatingBar priceLevel;
    TextInputLayout textInputLayoutAdditionalServicesCamping;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_find_location_vacation_camping, container, false);
        editTextAdditionalServices = rootView.findViewById(R.id.editTextAddionalServicesCamping);
        nextToBeachCamping = rootView.findViewById(R.id.nextToBeachCamping);
        isFoodAroundCamping = rootView.findViewById(R.id.isFoodAroundCamping);
        priceLevel = rootView.findViewById(R.id.campingPriceLevel);
        textInputLayoutAdditionalServicesCamping = rootView.findViewById(R.id.textInputLayoutAdditionalServicesCamping);

        if (getArguments() != null){
            setParametersVacationCamping(getArguments().getString("vacationAdditionalServices"),
                                                    getArguments().getString("vacationNextToBeach"),
                                                    getArguments().getString("vacationDogFood"),getArguments().getString("priceLevel") );
        }

        return rootView;
    }

    public boolean getIsFoodAroundCamping() {
        return isFoodAroundCamping.isChecked();
    }

    public boolean getNextToBeachCamping() {
        return nextToBeachCamping.isChecked();
    }

    public String getEditTextAdditionalServices() {
        return editTextAdditionalServices.getText().toString();
    }

    public float getPriceLevel() {
        return priceLevel.getRating();
    }

    private void setParametersVacationCamping(String vacationAdditionalServices, String vacationNextToBeach,
                                              String vacationDogFood, String priceLevel) {
        if (vacationNextToBeach != null) {
            this.nextToBeachCamping.setChecked(Boolean.parseBoolean(vacationNextToBeach));
        }
        this.nextToBeachCamping.setEnabled(false);

        if (vacationDogFood != null) {
            this.isFoodAroundCamping.setChecked(Boolean.parseBoolean(vacationDogFood));
        }
        this.isFoodAroundCamping.setEnabled(false);

        if (vacationAdditionalServices != null && !vacationAdditionalServices.equals("")) {
            this.editTextAdditionalServices.setText(vacationAdditionalServices);
            this.editTextAdditionalServices.setTextColor(getResources().getColor(R.color.quantum_black_text));
            this.editTextAdditionalServices.setEnabled(false);
            this.textInputLayoutAdditionalServicesCamping.setEnabled(false);
        }
        else {
            this.textInputLayoutAdditionalServicesCamping.setVisibility(View.GONE);

        }

        if (priceLevel != null){
            this.priceLevel.setRating(Float.valueOf(priceLevel));
        }
        this.priceLevel.setIsIndicator(true);
    }
}
