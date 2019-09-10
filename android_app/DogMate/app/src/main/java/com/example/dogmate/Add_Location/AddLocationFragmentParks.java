package com.example.dogmate.Add_Location;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.dogmate.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddLocationFragmentParks extends Fragment {
    View rootView;
    Spinner surfaceTypePark;
    RadioGroup spacePark;
    RadioButton selected;
    RatingBar cleanliness;
    RatingBar busy;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_find_location_park, container,false);

        surfaceTypePark = rootView.findViewById(R.id.surfaceTypePark);
        spacePark = rootView.findViewById(R.id.spaceParkGroup);
        cleanliness = rootView.findViewById(R.id.ratingBarCleanPark);
        busy = rootView.findViewById(R.id.ratingBarBusyPark);

        if (getArguments() != null){
            setParametersParks(getArguments().getString("gardenType"),
                                                getArguments().getString("gardenSpace"),
                                                getArguments().getString("cleanLevel"),
                                                getArguments().getString("busyLevel"));
        }

        return rootView;
    }

    public String getSurfaceTypePark(){
        return surfaceTypePark.getSelectedItem().toString();
    }

    public float getBusyRating() {
        return busy.getRating();
    }

    public float getCleanliness() {
        return cleanliness.getRating();
    }

    public String getSpaceParkOpen(){
        int selectedId = spacePark.getCheckedRadioButtonId();
        selected = (RadioButton) rootView.findViewById(selectedId);
        if (selectedId == R.id.isopenSpacePark){
            return "Open";
        }else if (selectedId == R.id.isClosedSpacePark){
        return "Gated";
        }
        return null;
    }

    public boolean isValidated(){
        int selectedId = spacePark.getCheckedRadioButtonId();
        if (selectedId == -1){
            spacePark.setBackgroundResource(R.drawable.shapes);
            return false;
        }
        return true;
    }

    private void setParametersParks(String gardenType, String gardenSpace, String cleanLevel, String busyLevel ){
        List<String> dogParkArray = new ArrayList<String>(Arrays.asList(gardenType));
        ArrayAdapter <String> dogParkAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, dogParkArray);
        this.surfaceTypePark.setAdapter(dogParkAdapter);
        this.surfaceTypePark.setEnabled(false);

        if (gardenSpace != null && gardenSpace.equalsIgnoreCase(getString(R.string.openSpace))){
            this.spacePark.check(R.id.isopenSpacePark);
        }
        else {
            this.spacePark.check(R.id.isClosedSpacePark);
        }
        for (int i = 0; i <  this.spacePark.getChildCount(); i++) {
            this.spacePark.getChildAt(i).setEnabled(false);
        }

        if (cleanLevel != null){
            this.cleanliness.setRating(Float.valueOf(cleanLevel));
        }
        this.cleanliness.setIsIndicator(true);

        if (busyLevel != null){
            this.busy.setRating(Float.valueOf(busyLevel));
        }
        this.busy.setIsIndicator(true);
    }
}
