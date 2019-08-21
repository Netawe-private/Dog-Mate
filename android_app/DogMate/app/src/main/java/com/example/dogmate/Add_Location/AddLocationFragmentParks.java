package com.example.dogmate.Add_Location;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.dogmate.R;

public class AddLocationFragmentParks extends Fragment {
    View rootView;
    Spinner surfaceTypePark;
    RadioGroup spacePark;
    RadioButton selected;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_find_location_park, container,false);

        surfaceTypePark = rootView.findViewById(R.id.surfaceTypePark);
        spacePark = rootView.findViewById(R.id.spaceParkGroup);
        return rootView;
    }

    public String getSurfaceTypePark(){
        return surfaceTypePark.getSelectedItem().toString();
    }

    public Boolean getSpaceParkOpen(){
        int selectedId = spacePark.getCheckedRadioButtonId();
        selected = (RadioButton) rootView.findViewById(selectedId);
        if (selectedId == R.id.isopenSpacePark){
            return true;
        }
        return false;
    }

    public boolean isValidated(){
        int selectedId = spacePark.getCheckedRadioButtonId();
        if (selectedId == 1){
            spacePark.setBackgroundResource(R.drawable.shapes);
            return false;
        }
        return true;
    }
}
