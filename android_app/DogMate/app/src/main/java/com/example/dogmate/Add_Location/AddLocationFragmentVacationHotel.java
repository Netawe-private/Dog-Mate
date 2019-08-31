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

public class AddLocationFragmentVacationHotel extends Fragment {

    EditText editTextAddionalServicesHotel;
    CheckBox nextToBeachHotel;
    CheckBox isFoodAroundHotel;
    RatingBar hotelStars;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_find_location_vacation_hotel, container,false);
        editTextAddionalServicesHotel = rootView.findViewById(R.id.editTextAddionalServicesHotel);
        nextToBeachHotel = rootView.findViewById(R.id.nextToBeachHotel);
        isFoodAroundHotel = rootView.findViewById(R.id.isFoodAroundHotel);
        hotelStars = rootView.findViewById(R.id.hotelStars);
        if (getArguments() != null){
            setParametersVacationHotel(getArguments().getString("additional_info"),
                    getArguments().getString("next_beach"),
                    getArguments().getString("food_around"),
                    getArguments().getString("stars_number") );
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

    private void setParametersVacationHotel(String additionalInfo, String nextToBeach, String foodAround, String numberStars) {
        if (nextToBeach != null) {
            this.nextToBeachHotel.setChecked(Boolean.parseBoolean(nextToBeach));
        }
        this.nextToBeachHotel.setEnabled(false);

        if (foodAround != null) {
            this.isFoodAroundHotel.setChecked(Boolean.parseBoolean(foodAround));
        }
        this.isFoodAroundHotel.setEnabled(false);

        if (additionalInfo != null) {
            this.editTextAddionalServicesHotel.setText(additionalInfo);
        }
        editTextAddionalServicesHotel.setEnabled(false);

        if (numberStars != null){
            this.hotelStars.setRating(Float.valueOf(numberStars));
        }
        this.hotelStars.setIsIndicator(true);
    }
}
