package com.example.dogmate.Play_Date;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogmate.R;
import com.example.dogmate.model.Dog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultsFragment extends Fragment {

    private SearchViewModel mSearchViewModel;
    private List<Dog> mDogsList;
    private DogsAdapter mDogsAdapter;

    @BindView(R.id.rv_dogs)
    RecyclerView mDogsRecyclerView; // Displays a scrolling list of elements based on large data sets
    @BindView(R.id.tv_no_dogs) TextView mNoDogsMessage;
    @BindView(R.id.tv_headline) TextView mResultsHeadline;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_results, container, false);
        ButterKnife.bind(this, rootView);

        initRecyclerView();

        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mSearchViewModel = ViewModelProviders.of((FragmentActivity) context).get(SearchViewModel.class);        //Links the View Model to this Fragment
        mDogsList = mSearchViewModel.getDogsList();                                                             // gets the dogs list from the view model
    }

    private void initRecyclerView() {
        if(mDogsList.size() == 0){
            mNoDogsMessage.setVisibility(View.VISIBLE);
            mResultsHeadline.setVisibility(View.GONE);
        } else {
            mNoDogsMessage.setVisibility(View.GONE);
            mResultsHeadline.setVisibility(View.VISIBLE);
            mDogsAdapter = new DogsAdapter(getActivity(), (DogsAdapter.DogsAdapterListener) getActivity());     //Init the DogsAdapter Activity
            mDogsAdapter.setDogs(mDogsList);        // sends the dogs list to DogsAdapter
            mDogsRecyclerView.setAdapter(mDogsAdapter);     // Llnks the defined adapter to the RecyclerView
            mDogsRecyclerView.setHasFixedSize(true);        // Improves performance when knowing that changes in content do not change the layout size of the RecyclerView
            mDogsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));     // The RecyclerView fills itself with views provided by a layout manager
        }
    }
}
