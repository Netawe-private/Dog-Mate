package com.example.dogmate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.UUID;


public class JsonHelperService {

    public static JSONObject createReviewHelpfulRequest(String reviewId, String username){
        JSONObject returnJson = new JSONObject();
        try {
            returnJson.put("reviewId", reviewId);
            returnJson.put("username", username);
            returnJson.put("helpful", true);
            returnJson.put("comment", "");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnJson;
    }

    public static JSONObject createDeleteReviewRequestJson(String reviewId, String username){
        JSONObject returnJson = new JSONObject();
        try {
            returnJson.put("reviewId", reviewId);
            returnJson.put("username", username);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnJson;
    }

    public static JSONObject createAddLocationRequestJson(String address, String longitude,
                                                          String latitude, String locationName,
                                                          String locationType, String locationSubType)  {
        JSONObject returnJson = new JSONObject();
        try {
            returnJson.put("locationName", locationName);
            returnJson.put("address", address);
            returnJson.put("latitude", latitude);
            returnJson.put("longitude", longitude);
            returnJson.put("locationType", locationType);
            returnJson.put("locationSubType", locationSubType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnJson;
    }


    public static JSONObject createAddLocationVacationRequestJson(String address,
                                                                  String longitude,
                                                                  String latitude,
                                                                  String locationName,
                                                                  String locationSubType,
                                                                  String locationType,
                                                                  String vacationAdditionalServices,
                                                                  boolean vacationDogFood,
                                                                  boolean vacationNextToBeach,
                                                                  int vacationRating,
                                                                  int priceLevel){

        JSONObject returnJson = createLocationRequestJson(address,locationName,locationSubType,
                locationType,longitude,latitude);
        try {
            returnJson.put( "vacationAdditionalServices", vacationAdditionalServices);
            returnJson.put( "vacationDogFood", vacationDogFood);
            returnJson.put( "vacationNextToBeach", vacationNextToBeach);
            returnJson.put( "vacationRating", vacationRating);
            returnJson.put( "priceLevel", priceLevel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnJson;
    }

    public static JSONObject createAddLocationServicesShopRequestJson(String address,
                                                                      String longitude,
                                                                      String latitude,
                                                                      String locationName,
                                                                      String locationSubType,
                                                                      String locationType,
                                                                      String treatment,
                                                                      int priceLevel,
                                                                      boolean doesShipping,
                                                                      String shippingArea ){

        JSONObject returnJson = createLocationRequestJson(address,locationName,locationSubType,
                locationType,longitude,latitude);
        try {
            returnJson.put( "shippingArea", treatment);
            returnJson.put( "priceLevel", priceLevel);
            returnJson.put( "doesShipping", doesShipping);
            returnJson.put( "shippingArea", shippingArea);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnJson;
    }

    public static JSONObject createAddLocationServicesVetRequestJson(String address,
                                                                     String longitude,
                                                                     String latitude,
                                                                     String locationName,
                                                                     String locationSubType,
                                                                     String locationType,
                                                                     String treatment,
                                                                     boolean openAllDay,
                                                                     int priceLevel ){

        JSONObject returnJson = createLocationRequestJson(address,locationName,locationSubType,
                locationType,longitude,latitude);
        try {
            returnJson.put( "shippingArea", treatment);
            returnJson.put( "openAllDay", openAllDay);
            returnJson.put( "priceLevel", priceLevel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnJson;
    }

    public static JSONObject createAddLocationDogParksRequestJson(String address,
                                                                String longitude,
                                                                String latitude,
                                                                String locationName,
                                                                String locationSubType,
                                                                String locationType,
                                                                int busyLevel,
                                                                int cleanLevel, String gardenSpace,
                                                                String gardenType){

        JSONObject returnJson = createLocationRequestJson(address,locationName,locationSubType,
                locationType,longitude,latitude);
        try {
            returnJson.put( "busyLevel", busyLevel);
            returnJson.put( "cleanLevel", cleanLevel);
            returnJson.put( "gardenSpace", gardenSpace);
            returnJson.put( "gardenSpace", gardenType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnJson;
    }
    public static JSONObject createAddLocationNatureRequestJson(String address,
                                                                String longitude,
                                                                String latitude,
                                                                String locationName,
                                                                String locationSubType,
                                                                String locationType,
                                                                boolean availableWater,
                                                                boolean releaseDogIsAllowed,
                                                                int shadowLevel){

        JSONObject returnJson = createLocationRequestJson(address,locationName,locationSubType,
                                                            locationType,longitude,latitude);
        try {
            returnJson.put( "releaseDogIsAllowed", releaseDogIsAllowed);
            returnJson.put( "shadowLevel", shadowLevel);
            returnJson.put( "availableWater", availableWater);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnJson;
    }
    public static JSONObject createAddLocationEntertainmentRequestJson(String address,
                                                          String latitude,
                                                          String locationName,
                                                          String locationSubType,
                                                          String locationType,
                                                          String longitude,
                                                          int shadowLevel,
                                                          boolean shadowPlace,
                                                          boolean sittingInsideAllowed){

        JSONObject returnJson = createLocationRequestJson(address, locationName, locationSubType,
                                                                locationType, longitude, latitude);
        try {
            returnJson.put( "shadowLevel", shadowLevel);
            returnJson.put( "shadowPlace", shadowPlace);
            returnJson.put( "sittingInsideAllowed", sittingInsideAllowed);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnJson;
    }

    private static JSONObject createLocationRequestJson(String address,  String locationName,
                                                       String locationSubType, String locationType,
                                                       String longitude,String latitude){
        JSONObject returnJson = new JSONObject();
        try {
            returnJson.put( "locationType", locationType);
            returnJson.put( "locationSubType", locationSubType);
            returnJson.put( "longitude", longitude);
            returnJson.put( "latitude", latitude);
            returnJson.put( "address", address);
            returnJson.put( "locationName", locationName);
            returnJson.put("locationArea", "Israel");
            returnJson.put("locationComment", "Comment");
            returnJson.put( "locationId", generateLocationId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnJson;
    }

    public static JSONObject createAddReviewRequestJson(String locationId, String reviewContent, String reviewRank, String username){
        JSONObject returnJson = new JSONObject();
        try {
            returnJson.put("locationId", locationId);
            returnJson.put("reviewContent", reviewContent);
            returnJson.put("reviewRank", reviewRank);
            returnJson.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  returnJson;
    }

    public static JSONObject createSearchLocationRequestJsonByType(String locationType)  {
        JSONObject returnJson = new JSONObject();
        try {
            returnJson.put( "locationType", locationType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnJson;
    }

    public static JSONObject createRequestLocationById(String locationId){
        JSONObject returnJson = new JSONObject();
        try {
            returnJson.put("locationId", locationId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  returnJson;
    }

    private static String generateLocationId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
