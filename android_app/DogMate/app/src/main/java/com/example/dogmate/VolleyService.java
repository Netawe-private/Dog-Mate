package com.example.dogmate;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class VolleyService {

    IResult mResultCallback = null;
    Context mContext;

    public VolleyService(IResult resultCallback, Context context){
        mResultCallback = resultCallback;
        mContext = context;
    }



    public void postDataJSONResponseVolley(final String requestType, String url, JSONObject sendObj, @Nullable String credentials){
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);

            JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.POST, url,sendObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if(mResultCallback != null)
                        mResultCallback.notifySuccess(requestType,response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(mResultCallback != null)
                        mResultCallback.notifyError(requestType,error);

                }
            })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    try {
                        Map<String, String> headers  = new HashMap<>();
                        String credentials = "x1234:ezjLSVmdQg98nFmH";
                        String auth = "Basic "
                                + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                        headers.put("Content-Type", "application/json");
                        headers.put("Authorization", auth);
                        return headers;
                    } catch (Exception e) {
                        Log.e("Volley", "Authentication Failure" );
                    }
                    return super.getParams();
                }
                @Override
                public byte[] getBody() {

                    return sendObj.toString().getBytes();
                }
//                @Override
//                public String dgetBodyContentType() {
//                    return "application/json";
//                }

            };

            queue.add(jsonObj);


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void postDataStringResponseVolley(final String requestType, String url, JSONObject parameters, @Nullable String credentials){
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(mResultCallback != null)
                        mResultCallback.notifySuccessString (requestType,response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(mResultCallback != null)
                        mResultCallback.notifyError(requestType,error);
                }
            })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    try {
                        Map<String, String> headers  = new HashMap<>();
                        String credentials = "x1234:ezjLSVmdQg98nFmH";
                        String auth = "Basic "
                                + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                        headers.put("Content-Type", "application/json");
                        headers.put("Authorization", auth);
                        return headers;
                    } catch (Exception e) {
                        Log.e("Volley", "Authentication Failure" );
                    }
                    return super.getParams();
                }
                @Override
                public byte[] getBody() {

                    return parameters.toString().getBytes();
                }
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

            };

            queue.add(stringRequest);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void getDataVolleyStringResponseVolley(final String requestType, String url){
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                @Override
                public void onResponse(String  response) {
                    if(mResultCallback != null)
                        mResultCallback.notifySuccessString(requestType, response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(mResultCallback != null)
                        mResultCallback.notifyError(requestType, error);
                }
            })
            {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                try {
                    Map<String, String> headers  = new HashMap<>();
                    String credentials = "x1234:ezjLSVmdQg98nFmH";
                    String auth = "Basic "
                            + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", auth);
                    return headers;
                } catch (Exception e) {
                    Log.e("Volley", "Authentication Failure" );
                }
                return super.getParams();
            }
        };
        queue.add(stringRequest);

        }catch(Exception e){

        }
    }

    public void deleteDataStringResponseVolley(final String requestType, String url, @Nullable String credentials){
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);

            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(mResultCallback != null)
                        mResultCallback.notifySuccessString (requestType, response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(mResultCallback != null)
                        mResultCallback.notifyError(requestType,error);
                }
            })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    try {
                        Map<String, String> headers  = new HashMap<>();
                        String credentials = "x1234:ezjLSVmdQg98nFmH";
                        String auth = "Basic "
                                + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                        headers.put("Content-Type", "application/json");
                        headers.put("Authorization", auth);
                        return headers;
                    } catch (Exception e) {
                        Log.e("Volley", "Authentication Failure" );
                    }
                    return super.getParams();
                }
//                @Override
//                public byte[] getBody() {
//                    return parameters.toString().getBytes();
//                }
//                @Override
//                public String getBodyContentType() {
//                    return "application/json";
//                }

            };

            queue.add(stringRequest);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}