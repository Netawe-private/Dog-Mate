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
import com.google.android.material.textfield.TextInputEditText;

public class AddLocationFragmentServicesShop extends Fragment {
    CheckBox includeDelivetyService;
    EditText editTextDelAreaService;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_find_location_services_shop, container,false);
        editTextDelAreaService = rootView.findViewById(R.id.editTextDelAreaService);
        includeDelivetyService = rootView.findViewById(R.id.includeDelivetyService);
        return rootView;
    }

    public String getEditTextDelAreaService(){
        return editTextDelAreaService.getText().toString();
    }

    public boolean getIncludeDeliveryService(){
        return includeDelivetyService.isChecked();
    }
}
