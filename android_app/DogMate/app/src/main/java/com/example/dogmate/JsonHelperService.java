package com.example.dogmate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonHelperService {

    public static JSONObject createAddLocationRequestJson(String locationName, String address, String lat,
                                                 String lng, String locationType, String locationSubType)  {
        JSONObject returnJson = new JSONObject();
        try {
            returnJson.put("location_name", locationName);
            returnJson.put("address", address);
            returnJson.put("latitude", lat);
            returnJson.put("longitude", lng);
            returnJson.put("locationType", locationType);
            returnJson.put("locationSubType", locationSubType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnJson;
    }

    public static JSONObject createSearchLocationRequestJson(String locationType)  {
        JSONObject returnJson = new JSONObject();
        try {
            returnJson.put( "locationType", locationType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnJson;
    }

    public static JSONArray createSearchLocationRequestJsonArray (String locationType){
        JSONArray returnArray = new JSONArray();
        JSONObject locationTypeJson = new JSONObject();
        try {
            locationTypeJson.put("locationType", locationType);
            returnArray.put(locationTypeJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  returnArray;
    }
}
