package com.example.dogmate.Add_Location;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dogmate.R;

public class AddLocationFragmentServicesVet extends Fragment {
    CheckBox serviceHoursVet;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_find_location_services_vet, container,false);
        serviceHoursVet = rootView.findViewById(R.id.serviceHoursVet);

        if (getArguments() != null){
            setParametersServerVet(getArguments().getString("service_hours"));
        }

        return rootView;
    }

    public boolean getIsTwentyFourServiceHoursVet(){
        return serviceHoursVet.isChecked();
    }


    private void setParametersServerVet(String serviceHours){
        if (serviceHours != null){
            this.serviceHoursVet.setChecked(Boolean.parseBoolean(serviceHours));
        }
        this.serviceHoursVet.setEnabled(false);


    }
}