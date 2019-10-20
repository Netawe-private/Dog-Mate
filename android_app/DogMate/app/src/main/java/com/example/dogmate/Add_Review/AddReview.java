package com.example.dogmate.Add_Review;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.dogmate.Constants;
import com.example.dogmate.IResult;
import com.example.dogmate.JsonHelperService;
import com.example.dogmate.R;
import com.example.dogmate.Scan_Location.LocationDetails;
import com.example.dogmate.Show_Location.ShowLocations;
import com.example.dogmate.VolleyService;
import net.glxn.qrgen.android.QRCode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static com.example.dogmate.Constants.ADD_COMMENT_REVIEW_REQUEST_TYPE;
import static com.example.dogmate.Constants.ADD_REVIEWS_REQUEST_TYPE;
import static com.example.dogmate.Constants.ADD_REVIEW_PATH;
import static com.example.dogmate.Constants.DELETE_LOCATION_REQUEST_TYPE;
import static com.example.dogmate.Constants.DELETE_REVIEWS_REQUEST_TYPE;
import static com.example.dogmate.Constants.DELETE_REVIEW_PATH;
import static com.example.dogmate.Constants.GET_LOCATION_PATH;
import static com.example.dogmate.Constants.GET_REVIEWS_PATH;
import static com.example.dogmate.Constants.GET_REVIEWS_REQUEST_TYPE;
import static com.example.dogmate.Constants.REVIEW_COMMENT_PATH;
import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;


public class AddReview extends AppCompatActivity {
    SharedPreferences sharedPreferenceFile;
    RatingBar reviewRating;
    EditText reviewComment;
    TextView locationNameView;
    TextView locationAddressView;
    LinearLayout reviewLayout;
    JSONArray locationReviews;
    JSONObject locationDetails;
    String locationName;
    String locationAddress ;
    String locationId;
    IResult mResultCallback = null;
    VolleyService mVolleyService;
    String credentials;
    String username;
    boolean isAdmin;
    private String TAG = "AddReviewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        sharedPreferenceFile =  getSharedPreferences(Constants.SHAREDPREF_NAME, 0);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();


        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,this);
        credentials = String.format("%s:%s",sharedPreferenceFile.getString("username", NULL),
                sharedPreferenceFile.getString("password", NULL));
        username = sharedPreferenceFile.getString("username", NULL);
        isAdmin = sharedPreferenceFile.getBoolean("isAdmin", false);
        reviewComment = findViewById(R.id.editTextReviewComment);
        reviewRating = findViewById(R.id.reviewRating);
        locationNameView = findViewById(R.id.textViewLocName);
        locationAddressView = findViewById(R.id.textViewLocAddress);
        reviewLayout =findViewById(R.id.reviewLinearLayout);

        Intent intent = getIntent();

        try {
            locationDetails = new JSONObject(intent.getStringExtra("locationDetails"));
            locationReviews = new JSONArray(intent.getStringExtra("reviews"));
            locationName = locationDetails.getString("locationName");
            locationAddress = locationDetails.getString("address");
            locationId = locationDetails.getString("locationId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setLocationNameRatingAndAddress(locationName, locationAddress);
        generateAndSetQRCode(locationId);
        sendGetReviewsRequest();

    }

    public void onAddReviewToLocation(View view){
        String reviewCommentText = reviewComment.getText().toString();
        Float reviewRatingAmount = reviewRating.getRating();
        if (validateRatingField(reviewRatingAmount)) {
            JSONObject requestJson = JsonHelperService.createAddReviewRequestJson(locationId,
                    reviewCommentText, reviewRatingAmount, username);
            mVolleyService.postDataStringResponseVolley(ADD_REVIEWS_REQUEST_TYPE,
                    ADD_REVIEW_PATH,
                    requestJson, credentials);
        }
    }

    public boolean validateRatingField(Float rating){
        if (rating == 0.0){
            Toast noRatingMessage = Toast.makeText(getApplicationContext(),
                    "Please submit a rating before sending a review", Toast.LENGTH_LONG);
            noRatingMessage.show();
            return false;
        }
        else {
            return true;
        }
    }

    public void setLocationNameRatingAndAddress(String name, String address){
        locationAddressView.setText(address);
        locationNameView.setText(name);
    }

    public void addReviewsToView(JSONArray locationReviews){
        JSONArray reviewsArray = locationReviews;
        TextView noReview = findViewById(R.id.textViewLocationReviews);
        reviewLayout.removeAllViews();
        if (reviewsArray.length() == 0){
            noReview.setText(R.string.noReviews);
        }
        else{
            noReview.setText(R.string.Reviews);
            for (int n = 0; n < reviewsArray.length(); n++)
                {
                    try {
                        JSONObject reviewObject = reviewsArray.getJSONObject(n);
                        LinearLayout reviewBlock = new LinearLayout(this);
                        LinearLayout.LayoutParams reviewBlockParams = new LinearLayout.LayoutParams
                                                                (LinearLayout.LayoutParams.MATCH_PARENT,
                                                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        reviewBlockParams.setMargins(0,0,0,15);
                        reviewBlock.setOrientation(LinearLayout.VERTICAL);
                        reviewBlock.setLayoutParams(reviewBlockParams);

                        LinearLayout nameBlock = new LinearLayout(this);
                        nameBlock.setLayoutParams(reviewBlockParams);
                        nameBlock.setOrientation(LinearLayout.HORIZONTAL);

                        TextView reviewerName = new TextView(this);
                        reviewerName.setPadding(40,10,10,10);
                        reviewerName.setText(reviewObject.getString("username"));
                        nameBlock.addView(reviewerName);

                        TextView reviewDate = new TextView(this);
                        String dateTime = reviewObject.getString("createdAt");
                        reviewDate.setText(reviewDateTimeFormat(dateTime));
                        reviewDate.setPadding(30,30, 0,15);
                        nameBlock.addView(reviewDate);
                        reviewBlock.addView(nameBlock);

                        LinearLayout ratingBlock = new LinearLayout(this);
                        ratingBlock.setLayoutParams(reviewBlockParams);
                        ratingBlock.setOrientation(LinearLayout.HORIZONTAL);

                        RatingBar reviewRating = new RatingBar(this);
                        reviewRating.setRating(Float.valueOf(reviewObject.getString("reviewRank")));
                        reviewRating.setNumStars(5);
                        reviewRating.setIsIndicator(true);
                        reviewRating.setPadding(20,0,0,0);
                        ratingBlock.addView(reviewRating);


                        LinearLayout.LayoutParams iconButtonParams = new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT);
                        iconButtonParams.setMargins(30,0,0,0);

                        //add delete button only in case user is admin or wrote the comment
                        if (isAdmin || reviewObject.getString("username").equals(username)){
                            ImageButton deleteButton = new ImageButton(this );
                            deleteButton.setImageResource(R.drawable.ic_delete_black_24dp);
                            deleteButton.setLayoutParams(iconButtonParams);
                            deleteButton.setOnClickListener(deleteButtonOnClickListener);
                            deleteButton.setTag(reviewObject.getString("reviewId"));
                            ratingBlock.addView(deleteButton);
                        }
                        ImageButton likeButton = new ImageButton(this );
                        likeButton.setImageResource(R.drawable.thumb_up_24dp);
                        likeButton.setLayoutParams(iconButtonParams);
                        likeButton.setOnClickListener(likeButtonOnClickListener);
                        likeButton.setTag(reviewObject.getString("reviewId"));
                        ratingBlock.addView(likeButton);

                        TextView reviewLikes = new TextView(this);
                        int numberOfLikes = calculateNumberOfLikes(reviewObject.getJSONArray("commentList"));
                        reviewLikes.setText(String.valueOf(numberOfLikes));
                        reviewLikes.setPadding(30,30, 0,15);
                        reviewLikes.setTag(reviewObject.getString("reviewId"));
                        ratingBlock.addView(reviewLikes);
                        reviewBlock.addView(ratingBlock);
                        TextView reviewComment =new TextView(this);
                        reviewComment.setText(reviewObject.getString("reviewContent"));
                        reviewComment.setPadding(30,0,10,10);
                        reviewComment.setLayoutParams(reviewBlockParams);
                        reviewBlock.addView(reviewComment);

                        reviewLayout.addView(reviewBlock);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
    }

    private View.OnClickListener likeButtonOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            String buttonId = view.getTag().toString();
            boolean isHelpful = false;
            for (int i = 0; i < locationReviews.length(); i++){
                try {
                    JSONObject review = locationReviews.getJSONObject(i);
                    if (review.getString("reviewId").equalsIgnoreCase(buttonId)){
                         JSONArray reviewComments = review.getJSONArray("commentList");
                         for (int j = 0 ; j < reviewComments.length(); j++){
                             JSONObject reviewComment = reviewComments.getJSONObject(j);
                             if (reviewComment.getString("username").equalsIgnoreCase(username)){
                                 isHelpful = reviewComment.getBoolean("helpful");
                             }
                         }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            JSONObject requestJson = JsonHelperService.createReviewHelpfulRequest(buttonId, username, !isHelpful);
            mVolleyService.postDataStringResponseVolley(ADD_COMMENT_REVIEW_REQUEST_TYPE, REVIEW_COMMENT_PATH,
                                                            requestJson, credentials );
        }
    };

    private View.OnClickListener deleteButtonOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            String deleteButtonReviewId = view.getTag().toString();
            String requestUrl = String.format(DELETE_REVIEW_PATH,deleteButtonReviewId, "x1234");
            mVolleyService.deleteDataStringResponseVolley(DELETE_REVIEWS_REQUEST_TYPE,
                    requestUrl, credentials );

        }
    };

    public void onClickDeleteLocation(View view){
        String requestUrl = String.format(GET_LOCATION_PATH, locationId);
        mVolleyService.deleteDataStringResponseVolley(DELETE_LOCATION_REQUEST_TYPE,
                requestUrl, credentials);

    }

    public int calculateNumberOfLikes(JSONArray commentList){
        int numberOfLikes = 0;
        for ( int i = 0 ; i < commentList.length(); i++){
            try {
                JSONObject commentToComment = new JSONObject(commentList.get(i).toString());
                if (commentToComment.getBoolean("helpful")){
                    numberOfLikes++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return numberOfLikes;
    }

    public void generateAndSetQRCode(String locationId){
        Bitmap qrBitMap = QRCode.from(locationId).bitmap();
        ImageView qrImageView = (ImageView) findViewById(R.id.locationQR);
        qrImageView.setImageBitmap(qrBitMap);
    }

    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType,JSONObject response) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + response);
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post error: " + error);
                String message;
                String responseBody = "";
                try {
                    responseBody = new String(error.networkResponse.data, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (requestType.equalsIgnoreCase(DELETE_LOCATION_REQUEST_TYPE) && responseBody.contains("Unauthorized")){
                    message = "Only system admin is authorized to delete locations";
                } else {
                    message = "Error has occurred";
                }
                Toast errorMessage = Toast.makeText(getApplicationContext(),
                        message, Toast.LENGTH_LONG);
                errorMessage.show();
            }

            @Override
            public void notifySuccessString(String requestType, String response) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + response);
                handleRequest( requestType, response);
            }
        };
    }

    private void handleRequest(String requestType, String response){
        switch (requestType){
            case GET_REVIEWS_REQUEST_TYPE:
                try {
                    JSONArray updatedLocationReviews = new JSONArray(response);
                    locationReviews = updatedLocationReviews;
                    addReviewsToView(updatedLocationReviews);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case ADD_REVIEWS_REQUEST_TYPE:
                sendGetReviewsRequest();
                Toast addReviewMessage = Toast.makeText(getApplicationContext(),
                        "Great Success! Your review was added!", Toast.LENGTH_LONG);
                addReviewMessage.show();
                break;
            case ADD_COMMENT_REVIEW_REQUEST_TYPE:
                sendGetReviewsRequest();
                Toast likeReviewMessage = Toast.makeText(getApplicationContext(),
                        "Thank you for commenting!", Toast.LENGTH_LONG);
                likeReviewMessage.show();
                break;
            case DELETE_REVIEWS_REQUEST_TYPE:
                sendGetReviewsRequest();
                Toast deleteReviewMessage = Toast.makeText(getApplicationContext(),
                        "Review was deleted!", Toast.LENGTH_LONG);
                deleteReviewMessage.show();
                break;
            case DELETE_LOCATION_REQUEST_TYPE:
                sendGetReviewsRequest();
                Toast deleteLocationMessage = Toast.makeText(getApplicationContext(),
                        "Location is marked as deleted and will no be shown in the future", Toast.LENGTH_LONG);
                deleteLocationMessage.show();
                Intent intent = new Intent(AddReview.this, ShowLocations.class);
                startActivity(intent);

        }
    }

    private String reviewDateTimeFormat(String dateTime){
        String returnDate = dateTime.replace("T", " ").split("\\+")[0] ;
        String format = "yyyy-MM-dd HH:mm:ss";

        try {
            SimpleDateFormat utcFormatter = new SimpleDateFormat(format);
            utcFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date reviewDate = utcFormatter.parse(dateTime.replace("T", " ").split("\\+")[0]);

            SimpleDateFormat phoneTimeFormatter = new SimpleDateFormat(format);
            phoneTimeFormatter.setTimeZone(TimeZone.getDefault());
            returnDate = phoneTimeFormatter.format(reviewDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnDate;
    }

    private void sendGetReviewsRequest(){
        String requestUrl = String.format(GET_REVIEWS_PATH, locationId);
        mVolleyService.getDataVolleyStringResponseVolley(GET_REVIEWS_REQUEST_TYPE, requestUrl, credentials);
    }

}
