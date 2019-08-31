package com.example.dogmate.Add_Review;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.dogmate.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AddReview extends AppCompatActivity {
    RatingBar reviewRating;
    EditText reviewComment;
    TextView locationNameView;
    TextView locationAddressView;
    RatingBar locationRating;
    LinearLayout reviewLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        // Enable the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);

        reviewComment = findViewById(R.id.editTextReviewComment);
        reviewRating = findViewById(R.id.reviewRating);
        locationNameView = findViewById(R.id.textViewLocName);
        locationAddressView = findViewById(R.id.textViewLocAddress);
        locationRating = findViewById(R.id.locationRating);
        reviewLayout =findViewById(R.id.reviewLinearLayout);

        Intent intent = getIntent();
        String scanningResult = intent.getStringExtra("scan_result");


        addReviewsToView();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) // Checks the API level of the device
        {
            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
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

                ImageButton likeButton = new ImageButton(this );
                ImageButton deleteButton = new ImageButton(this );
                //likeButton.setIcon(getResources().getDrawable(R.drawable.thumb_up_24dp));
                likeButton.setImageResource(R.drawable.thumb_up_24dp);
                deleteButton.setImageResource(R.drawable.ic_delete_black_24dp);
                //likeButton.setBackgroundColor(Color.TRANSPARENT);
                LinearLayout.LayoutParams iconButtonParams = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                iconButtonParams.setMargins(30,0,0,0);
                deleteButton.setLayoutParams(iconButtonParams);
                likeButton.setLayoutParams(iconButtonParams);
                deleteButton.setId(object.getInt("id"));
                likeButton.setId(object.getInt("id"));
                ratingBlock.addView(likeButton);
                ratingBlock.addView(deleteButton);


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

    private View.OnClickListener likeButtonOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            ImageButton thumbButton = (ImageButton) view;
            int buttonId = view.getId();

        }
    };

    public void onClickDeleteLocation(View view){

    }

}
