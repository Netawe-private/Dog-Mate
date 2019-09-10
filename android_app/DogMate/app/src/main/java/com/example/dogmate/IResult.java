package com.example.dogmate;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

public interface IResult {
    void notifySuccess(String requestType,JSONObject response);
    void notifyError(String requestType,VolleyError error);
    void notifySuccessString(String requestType, String response);

}