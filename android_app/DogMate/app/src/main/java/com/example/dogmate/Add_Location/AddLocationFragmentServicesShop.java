package com.example.dogmate.Add_Location;

import android.os.Bundle;
import android.text.BoringLayout;
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

        if (getArguments() != null){
            setParametersServerShop(getArguments().getString("delivery_area"),
                    getArguments().getString("delivery_included"));
        }
        return rootView;
    }

    public String getEditTextDelAreaService(){
        return editTextDelAreaService.getText().toString();
    }

    public boolean getIncludeDeliveryService(){
        return includeDelivetyService.isChecked();
    }

    private void setParametersServerShop(String deliveryArea, String isDeliveryIncluded){
        if (isDeliveryIncluded != null){
            this.includeDelivetyService.setChecked(Boolean.parseBoolean(isDeliveryIncluded));
        }
        this.includeDelivetyService.setEnabled(false);

        if (deliveryArea != null){
            this.editTextDelAreaService.setText(deliveryArea);
        }
        editTextDelAreaService.setEnabled(false);

    }
}
