package com.example.dogmate.Play_Date;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.dogmate.Constants;
import com.example.dogmate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements
        SearchViewModel.SearchViewModelListener,
        DogsAdapter.DogsAdapterListener {

    public static final String SEARCH_FRAGMENT_TAG = "search_fragment_tag";
    public static final String RESULTS_FRAGMENT_TAG = "results_fragment_tag";

    private SearchViewModel mSearchViewModel;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();


        if(savedInstanceState == null) {            // If no state was saved, set Search Fragment
            setSearchFragment();
        }

        initViewModel();                // Stores and manages the data, survives configuration changes such as screen rotations
    }

    @Override
    public void onBackPressed() {               // If the user presses Back while being in the Results Fragment, go beck to the Search Fragment
        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(RESULTS_FRAGMENT_TAG);
        if(fragment != null){
            setSearchFragment();
        } else {
            super.onBackPressed();      // Get out of the activity
        }
    }

    /* A function of the Search View Model. The view model sends events / data to the Activity.
    Search with the Search Fragment -> the Fragment sends criteria to the View Model ->
    the view model calls the server + updates the Activity with this function ->
    the Activity displays the Result Fragment*/
    @Override
    public void onSearchResults() {
        ResultsFragment resultsFragment = new ResultsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_fragment_container, resultsFragment, RESULTS_FRAGMENT_TAG)
                .commit();      // display the Result Fragment instead of the Search Fragment
    }

    @Override
    public void onEmailSent() {
        Toast.makeText(this, "Email sent successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void emailDogOwner(String ownerUserName) {
        mSearchViewModel.sendEMail(ownerUserName);
    }

    // Display the Search Fragment
    private void setSearchFragment() {
        SearchFragment searchFragment = new SearchFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_fragment_container, searchFragment, SEARCH_FRAGMENT_TAG)
                .commit();
    }

    // Links the view model to this activity. If something happens in the view model, the activity will "know" about it
    private void initViewModel() {
        mSearchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        mSearchViewModel.setListener(this);         // The Activity sends itself as a listener to the View Model
        String userName = getSharedPreferences(Constants.SHAREDPREF_NAME, 0)
                .getString("username", "");
        mSearchViewModel.setUserName(userName);
    }
}
