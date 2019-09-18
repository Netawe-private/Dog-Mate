package com.example.dogmate.Play_Date;

import android.app.Application;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dogmate.model.Dog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchViewModel extends AndroidViewModel {

    private SearchViewModelListener mListener;
    private RequestQueue mRequestQueue;
    private List<Dog> mOnSearchResultsDogList;
    private String mUserName;

    // Any Activity can implement this interface
    public interface SearchViewModelListener{
        void onSearchResults();
        void onEmailSent();
    }

    public SearchViewModel(@NonNull Application application) {
        super(application);
        mRequestQueue = Volley.newRequestQueue(application.getApplicationContext());
        mOnSearchResultsDogList = new ArrayList<>();
    }

    //When clicking on the searh button, those parameters are being sent here from the SearchFragment
    public void commitSearch(String city, String size, String breed, String temperament,
                             String neighborhood) {
        String url = "http://vmedu196.mtacloud.co.il/matching";

        JSONObject parameter = new JSONObject();

        try {
            parameter.put("username", "x123");
            parameter.put("city", city);
            parameter.put("neighborhood", neighborhood);
            parameter.put("dogSize", size);
            parameter.put("dogBreed", breed);
            parameter.put("dogTemper", temperament);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url, parameter,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseSearchResults(response);
                        Log.e("Rest Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response", error.toString());
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                try {
                    Map<String, String> headers = new HashMap<>();
                    String credentials = "x1234:ezjLSVmdQg98nFmH";
                    String auth = "Basic "
                            + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", auth);
                    return headers;
                } catch (Exception e) {
                    Log.e("Volley", "Authentication Failure");
                }
                return super.getParams();
            }
        };

        mRequestQueue.add(objectRequest);
    }


    public void sendEMail(String ownerUserName) {
        String url = "http://vmedu196.mtacloud.co.il/send-email";

        JSONObject parameter = new JSONObject();
        try {
            parameter.put("username", mUserName);
            parameter.put("sendToUsername", ownerUserName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url, parameter,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        mListener.onEmailSent();
                        Log.d("Rest Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Rest Response", error.getMessage());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                try {
                    Map<String, String> headers = new HashMap<>();
                    String credentials = "x1234:ezjLSVmdQg98nFmH";
                    String auth = "Basic "
                            + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", auth);
                    return headers;
                } catch (Exception e) {
                    Log.e("Volley", "Authentication Failure");
                }
                return super.getParams();
            }
        };

        mRequestQueue.add(objectRequest);
    }

    // Setting a listener (SearchActivity) to the View Model
    public void setListener(SearchViewModelListener listener) {
        mListener = listener;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    //Saving the list of dogs that was returned from the server. The Results Fragment 'asks' this list from here
    public List<Dog> getDogsList() {
        return mOnSearchResultsDogList;
    }

    private void parseSearchResults(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray("dogs");
            for(int i = 0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                mOnSearchResultsDogList.add(new Dog(
                        jsonObject.getString("dogName"),
                        jsonObject.getString("imageUrl"),
                        jsonObject.getString("city"),
                        jsonObject.getString("neighborhood"),
                        jsonObject.getString("dogSize"),
                        jsonObject.getString("dogBreed"),
                        jsonObject.getString("dogTemper"),
                        jsonObject.getString("username")));
            }
            mListener.onSearchResults();        // telling the Activity to open the listener of the results
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
