package com.example.dogmate;

public class Constants {
    public static final String CREATE_LOCATION_PATH = "http://vmedu196.mtacloud.co.il/locations";
    public static final String GET_REVIEWS_PATH = "http://vmedu196.mtacloud.co.il/reviews?locationId=%s";
    public static final String GET_LOCATION_PATH = "http://vmedu196.mtacloud.co.il/locations?locationId=%s";
    public static final String ADD_REVIEW_PATH = "http://vmedu196.mtacloud.co.il/reviews";
    public static final String DELETE_REVIEW_PATH = "http://vmedu196.mtacloud.co.il/reviews?reviewId=%s&username=%s";
    public static final String SEARCH_LOCATION_PATH = "http://vmedu196.mtacloud.co.il/locations/_search";
    public static final String REVIEW_COMMENT_PATH = "http://vmedu196.mtacloud.co.il/reviews/comments";
    public static final String REGISTRATION_PATH = "http://vmedu196.mtacloud.co.il/register";
    public static final String LOGIN_PATH = "http://vmedu196.mtacloud.co.il/login";
    public static final String ADD_DOG_PATH = "http://vmedu196.mtacloud.co.il/dogs";


    public static final String GET_REVIEWS_REQUEST_TYPE = "GET_REVIEWS";
    public static final String DELETE_REVIEWS_REQUEST_TYPE = "DELETE_REVIEW";
    public static final String ADD_COMMENT_REVIEW_REQUEST_TYPE = "ADD_COMMENT_REVIEW";
    public static final String ADD_REVIEWS_REQUEST_TYPE = "ADD_REVIEW";
    public static final String DELETE_LOCATION_REQUEST_TYPE ="DELETE_LOCATION";

    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9002;
    public static final int CAMERA_REQUEST_CODE = 100;

    public static final String SHAREDPREF_NAME = "SHAREDDB";
    public static final int MY_PERMISSION_REQUEST_FINE_LOCATION = 101;
    public static final int MY_PERMISSION_REQUEST_COARSE_LOCATION = 102;






}
