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
    EditText editTextAddionalServices;
    CheckBox nextToBeachCamping;
    CheckBox isFoodAroundCamping;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_find_location_vacation_camping, container,false);
        editTextAddionalServices = rootView.findViewById(R.id.editTextAddionalServicesCamping);
        nextToBeachCamping = rootView.findViewById(R.id.nextToBeachCamping);
        isFoodAroundCamping = rootView.findViewById(R.id.isFoodAroundCamping);

        return rootView;
    }

    public boolean getIsFoodAroundCamping(){
        return isFoodAroundCamping.isChecked();
    }
    public boolean getNextToBeachCamping(){
        return nextToBeachCamping.isChecked();
    }
    public String getEditTextAddionalServices(){
        return editTextAddionalServices.getText().toString();
    }

}
