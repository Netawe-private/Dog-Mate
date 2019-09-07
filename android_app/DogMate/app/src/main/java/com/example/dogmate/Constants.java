package com.example.dogmate;

public class Constants {
    public static final String CREATE_LOCATION_PATH = "http://vmedu196.mtacloud.co.il/locations";
    public static final String GET_REVIEWS_PATH = "http://vmedu196.mtacloud.co.il/reviews?locationId=%s";
    public static final String GET_LOCATION_PATH = "http://vmedu196.mtacloud.co.il/locations?locationId=%s";
    public static final String ADD_REVIEW_PATH = "http://vmedu196.mtacloud.co.il/reviews";
    public static final String DELETE_REVIEW_PATH = "http://vmedu196.mtacloud.co.il/reviews?reviewId=%s&username=%s";
    public static final String SEARCH_LOCATION_PATH = "http://vmedu196.mtacloud.co.il/locations/_search";
    public static final String REVIEW_COMMENT_PATH = "http://vmedu196.mtacloud.co.il/reviews/comments";

    public static final String GET_REVIEWS_REQUEST_TYPE = "GET_REVIEWS";
    public static final String DELETE_REVIEWS_REQUEST_TYPE = "DELETE_REVIEW";
    public static final String ADD_COMMENT_REVIEW_REQUEST_TYPE = "ADD_COMMENT_REVIEW";
    public static final String ADD_REVIEWS_REQUEST_TYPE = "ADD_REVIEW";
    public static final String DELETE_LOCATION_REQUEST_TYPE ="DELETE_LOCATION";




}
