package com.example.dogmate.Play_Date;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.dogmate.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchFragment extends Fragment {

    private SearchViewModel mSearchViewModel;

    @BindView(R.id.sp_cities) Spinner mCities;
    @BindView(R.id.sp_sizes) Spinner mSizes;
    @BindView(R.id.sp_breeds) Spinner mBreeds;
    @BindView(R.id.sp_temperaments) Spinner mTemperaments;
    @BindView(R.id.et_neighborhood) EditText mNeighborhoodEdiText;

    private String mCity, mSize, mBreed, mTemperament, mNeighborhood;   // the values of those strings are sent to the View Model

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {                 // Display the fragment within the Activity
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {         //Called when a fragment is first attached to its context
        super.onAttach(context);
        mSearchViewModel = ViewModelProviders.of((FragmentActivity) context).get(SearchViewModel.class);         //Links the View Model to this Fragment
    }

    @OnClick(R.id.btn_search)    //When clicking on the search button and the validations are fine, the view model will commit search
    void searchDog(){
        if(getFields()){
            mSearchViewModel.commitSearch(mCity, mSize, mBreed, mTemperament, mNeighborhood);
        } else {
            Toast.makeText(getContext(), "Please select a city", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean getFields() {
        mCity = String.valueOf(mCities.getSelectedItem());
        if(mCity.equals("")){
            return false;
        }
        mSize = String.valueOf(mSizes.getSelectedItem());
        mBreed = String.valueOf(mBreeds.getSelectedItem());
        mTemperament = String.valueOf(mTemperaments.getSelectedItem());
        mNeighborhood = String.valueOf(mNeighborhoodEdiText.getText());
        return true;
    }
}
