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

public class AddLocationFragmentServicesVet extends Fragment {
    CheckBox serviceHoursVet;
    RatingBar priceLevel;
    EditText editTextTreatment;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_find_location_services_vet, container,false);
        serviceHoursVet = rootView.findViewById(R.id.serviceHoursVet);
        priceLevel = rootView.findViewById(R.id.priceLevelVet);
        editTextTreatment = rootView.findViewById(R.id.editTextTreatmentVet);

        if (getArguments() != null){
            setParametersServerVet(getArguments().getString("openAllDay"),
                    getArguments().getString("priceLevel"),getArguments().getString("treatment"));
        }

        return rootView;
    }

    public boolean getIsTwentyFourServiceHoursVet(){
        return serviceHoursVet.isChecked();
    }

    public String getEditTextTreatment() {
        return editTextTreatment.getText().toString();
    }

    public float getPriceLevel() {
        return priceLevel.getRating();
    }

    private void setParametersServerVet(String openAllDay, String priceLevel, String treatment){
        if (openAllDay != null){
            this.serviceHoursVet.setChecked(Boolean.parseBoolean(openAllDay));
        }
        this.serviceHoursVet.setEnabled(false);

        if (priceLevel != null){
            this.priceLevel.setRating(Float.valueOf(priceLevel));
        }
        this.priceLevel.setIsIndicator(true);

        if (treatment != null){
            this.editTextTreatment.setText(treatment);
        }
        this.editTextTreatment.setEnabled(false);



    }
}