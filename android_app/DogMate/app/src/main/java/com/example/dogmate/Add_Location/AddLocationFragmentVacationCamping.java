package com.example.dogmate.Add_Location;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dogmate.R;

public class AddLocationFragmentVacationCamping extends Fragment {
    EditText editTextAdditionalServices;
    CheckBox nextToBeachCamping;
    CheckBox isFoodAroundCamping;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_find_location_vacation_camping, container, false);
        editTextAdditionalServices = rootView.findViewById(R.id.editTextAddionalServicesCamping);
        nextToBeachCamping = rootView.findViewById(R.id.nextToBeachCamping);
        isFoodAroundCamping = rootView.findViewById(R.id.isFoodAroundCamping);

        if (getArguments() != null){
            setParametersVacationCamping(getArguments().getString("additional_info"),
                                                    getArguments().getString("next_beach"),
                                                    getArguments().getString("food_around"));
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

    private void setParametersVacationCamping(String additionalInfo, String nextToBeach, String foodAround) {
        if (nextToBeach != null) {
            this.nextToBeachCamping.setChecked(Boolean.parseBoolean(nextToBeach));
        }
        this.nextToBeachCamping.setEnabled(false);

        if (foodAround != null) {
            this.isFoodAroundCamping.setChecked(Boolean.parseBoolean(foodAround));
        }
        this.isFoodAroundCamping.setEnabled(false);

        if (additionalInfo != null) {
            this.editTextAdditionalServices.setText(additionalInfo);
        }
        editTextAdditionalServices.setEnabled(false);
    }
}
