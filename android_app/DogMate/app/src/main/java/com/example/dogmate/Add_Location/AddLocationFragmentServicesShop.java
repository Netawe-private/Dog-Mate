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

public class AddLocationFragmentServicesShop extends Fragment {
    CheckBox includeDelivetyService;
    EditText editTextDelAreaService;
    RatingBar priceLevel;
    EditText editTextTreatment;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_find_location_services_shop, container,false);
        editTextDelAreaService = rootView.findViewById(R.id.editTextDeliveryShop);
        includeDelivetyService = rootView.findViewById(R.id.includeDelivetyService);
        priceLevel = rootView.findViewById(R.id.priceLevelShop);
        editTextTreatment = rootView.findViewById(R.id.editTextTreatmentShop);


        if (getArguments() != null){
            setParametersServerShop(getArguments().getString("shippingArea"),
                    getArguments().getString("doesShipping"),
                    getArguments().getString("priceLevel"),
                    getArguments().getString("treatment"));
        }
        return rootView;
    }

    public String getEditTextDelAreaService(){
        return editTextDelAreaService.getText().toString();
    }

    public boolean getIncludeDeliveryService(){
        return includeDelivetyService.isChecked();
    }

    public boolean validateFields(){
        if (includeDelivetyService.isChecked() && editTextDelAreaService.getText().toString().isEmpty()){
            editTextDelAreaService.setError("Fill delivery area");
            return  false;
        }
        return true;
    }

    public String getEditTextTreatment() {
        return editTextTreatment.getText().toString();
    }

    public float getPriceLevel() {
        return priceLevel.getRating();
    }

    private void setParametersServerShop(String shippingArea, String doesShipping, String priceLevel, String treatment){
        if (doesShipping != null){
            this.includeDelivetyService.setChecked(Boolean.parseBoolean(doesShipping));
        }
        this.includeDelivetyService.setEnabled(false);

        if (shippingArea != null){
            this.editTextDelAreaService.setText(shippingArea);
        }
        editTextDelAreaService.setEnabled(false);

        if (priceLevel != null){
            this.priceLevel.setRating(Float.valueOf(priceLevel));
        }
        this.priceLevel.isIndicator();

        if (treatment != null){
            this.editTextTreatment.setText(treatment);
        }
        this.editTextTreatment.setEnabled(false);

    }
}
