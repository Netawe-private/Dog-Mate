package com.example.dogmate.Add_Review;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.dogmate.Add_Location.AddLocationFragmentEntertainment;
import com.example.dogmate.Add_Location.AddLocationFragmentNature;
import com.example.dogmate.Add_Location.AddLocationFragmentParks;
import com.example.dogmate.Add_Location.AddLocationFragmentServicesShop;
import com.example.dogmate.Add_Location.AddLocationFragmentServicesVet;
import com.example.dogmate.Add_Location.AddLocationFragmentVacationCamping;
import com.example.dogmate.Add_Location.AddLocationFragmentVacationHotel;
import com.example.dogmate.DrawerMenu;
import com.example.dogmate.R;
import com.example.dogmate.Show_Location.ShowLocations;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.dogmate.R.id.forever;
import static com.example.dogmate.R.id.fragment_container;

public class AddReview  extends DrawerMenu {
    RatingBar reviewRating;
    EditText reviewComment;
    TextView locationNameView;
    TextView locationAddressView;
    RatingBar locationRating;
    TableLayout reviewTable;
    LinearLayout reviewLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_review);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_add_review, drawer, false);
        drawer.addView(contentView, 0);

        reviewComment = findViewById(R.id.editTextReviewComment);
        reviewRating = findViewById(R.id.reviewRating);
        locationNameView = findViewById(R.id.textViewLocName);
        locationAddressView = findViewById(R.id.textViewLocAddress);
        locationRating = findViewById(R.id.locationRating);
        reviewLayout =findViewById(R.id.reviewLinearLayout);

        setLocationNameRatingAndAddress("NAME", "Clay 10 Tel Aviv", Float.valueOf(3));
        addReviewsToView();
    }

    public void onAddReviewToLocation(View view){
        String reviewCommentText = reviewComment.getText().toString();
        Float reviewRatingAmount = reviewRating.getRating();
    }

    public void setLocationNameRatingAndAddress(String name, String address, float rating){
        locationAddressView.setText(address);
        locationNameView.setText(name);
        locationRating.setRating(rating);
    }

    public JSONArray getJsonFromBackend(){
        JSONObject student1 = new JSONObject();
        try {
            student1.put("id", "1");
            student1.put("content", "CONTENT1");
            student1.put("date", "TIME1");
            student1.put("rating", 1);
            student1.put("username", "USERNAME1");


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONObject student2 = new JSONObject();
        try {
            student2.put("id", "2");
            student2.put("content", "CONTENT2");
            student2.put("date", "TIME2");
            student2.put("rating", 2);
            student2.put("username", "USERNAME2");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(student1);
        jsonArray.put(student2);

        return jsonArray;
    }

    public void addReviewsToView(){

        JSONArray jsonFromDB = getJsonFromBackend();
        for(int n = 0; n < jsonFromDB.length(); n++)
        {
            try {
                JSONObject object = jsonFromDB.getJSONObject(n);
                LinearLayout reviewBlock = new LinearLayout(this);
                LinearLayout.LayoutParams reviewBlockParams = new LinearLayout.LayoutParams
                                                        (LinearLayout.LayoutParams.MATCH_PARENT,
                                                        LinearLayout.LayoutParams.WRAP_CONTENT);
                reviewBlockParams.setMargins(0,0,0,15);
                reviewBlock.setOrientation(LinearLayout.VERTICAL);
                reviewBlock.setLayoutParams(reviewBlockParams);


                TextView reviewerName = new TextView(this);
                reviewerName.setPadding(40,10,10,10);
                reviewerName.setText(object.getString("username"));
                reviewBlock.addView(reviewerName);

                LinearLayout ratingBlock = new LinearLayout(this);
                ratingBlock.setLayoutParams(reviewBlockParams);
                ratingBlock.setOrientation(LinearLayout.HORIZONTAL);

                RatingBar reviewRating = new RatingBar(this);
                reviewRating.setRating(Float.valueOf(object.getInt("rating")));
                reviewRating.setNumStars(5);
                reviewRating.setIsIndicator(true);
                reviewRating.setPadding(20,0,0,0);
                ratingBlock.addView(reviewRating);

                TextView reviewDate = new TextView(this);
                reviewDate.setText(object.getString("date"));
                reviewDate.setPadding(30,30, 0,15);
                ratingBlock.addView(reviewDate);
                reviewBlock.addView(ratingBlock);

                TextView reviewComment =new TextView(this);
                reviewComment.setText(object.getString("content"));
                reviewComment.setPadding(40,0,10,10);
                reviewComment.setLayoutParams(reviewBlockParams);
                reviewBlock.addView(reviewComment);

                reviewLayout.addView(reviewBlock);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }



}
