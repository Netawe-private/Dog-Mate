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

public class AddLocationFragmentVacationHotel extends Fragment {

    EditText editTextAddionalServicesHotel;
    CheckBox nextToBeachHotel;
    CheckBox isFoodAroundHotel;
    RatingBar hotelStars;
    RatingBar priceLevel;
    TextInputLayout textInputLayoutAdditionalServicesHotel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_find_location_vacation_hotel, container,false);
        editTextAddionalServicesHotel = rootView.findViewById(R.id.editTextAddionalServicesHotel);
        nextToBeachHotel = rootView.findViewById(R.id.nextToBeachHotel);
        isFoodAroundHotel = rootView.findViewById(R.id.isFoodAroundHotel);
        hotelStars = rootView.findViewById(R.id.hotelStars);
        priceLevel = rootView.findViewById(R.id.hotelPriceLevel);
        textInputLayoutAdditionalServicesHotel = rootView.findViewById(R.id.textInputLayoutAdditionalServicesHotel);

        if (getArguments() != null){
            setParametersVacationHotel(getArguments().getString("vacationAdditionalServices"),
                    getArguments().getString("vacationNextToBeach"),
                    getArguments().getString("vacationDogFood"),
                    getArguments().getString("vacationRating"), getArguments().getString("priceLevel") );
        }

        return rootView;
    }

    public boolean getIsFoodAroundHotel(){
        return isFoodAroundHotel.isChecked();
    }
    public boolean getNextToBeachHotel(){
        return nextToBeachHotel.isChecked();
    }
    public String getEditTextAddionalServices(){
        return editTextAddionalServicesHotel.getText().toString();
    }

    public float getPriceLevel() {
        return priceLevel.getRating();
    }

    public float getNumberOfHotelStarts(){
        return hotelStars.getRating();
    }

    public boolean validateFeilds(){
        if (getNumberOfHotelStarts() == 0){
            hotelStars.setBackgroundResource(R.drawable.shapes);
            return false;
        }
        return true;
    }

    private void setParametersVacationHotel(String vacationAdditionalServices, String vacationNextToBeach,
                                            String vacationDogFood, String vacationRating, String priceLevel) {
        if (vacationNextToBeach != null) {
            this.nextToBeachHotel.setChecked(Boolean.parseBoolean(vacationNextToBeach));
        }
        this.nextToBeachHotel.setEnabled(false);

        if (vacationDogFood != null) {
            this.isFoodAroundHotel.setChecked(Boolean.parseBoolean(vacationDogFood));
        }
        this.isFoodAroundHotel.setEnabled(false);

        if (vacationAdditionalServices != null && !vacationAdditionalServices.equals("")) {
            this.editTextAddionalServicesHotel.setText(vacationAdditionalServices);
            this.editTextAddionalServicesHotel.setTextColor(getResources().getColor(R.color.quantum_black_text));
            this.textInputLayoutAdditionalServicesHotel.setEnabled(false);
            this.editTextAddionalServicesHotel.setEnabled(false);
        }
        else {
            this.textInputLayoutAdditionalServicesHotel.setVisibility(View.GONE);
        }

        if (vacationRating != null){
            this.hotelStars.setRating(Float.valueOf(vacationRating));
        }
        this.hotelStars.setIsIndicator(true);

        if (priceLevel != null){
            this.priceLevel.setRating(Float.valueOf(priceLevel));
        }
        this.priceLevel.setIsIndicator(true);
    }
}
