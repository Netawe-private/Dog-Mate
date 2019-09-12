package com.example.dogmate.Scan_Location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.dogmate.Constants;
import com.example.dogmate.IResult;
import com.example.dogmate.R;
import com.example.dogmate.VolleyService;
import com.google.zxing.Result;

import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.example.dogmate.Constants.CAMERA_REQUEST_CODE;
import static com.example.dogmate.Constants.GET_LOCATION_PATH;
import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class ScanLocation extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    String TAG;
    SharedPreferences sharedPreferenceFile;
    private ZXingScannerView mScannerView;
    LinearLayout linearLayout;
    String credentials;
    String username;
    VolleyService mVolleyService;
    IResult mResultCallback = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_location);
        sharedPreferenceFile =  getSharedPreferences(Constants.SHAREDPREF_NAME, 0);
        credentials = String.format("%s:%s",sharedPreferenceFile.getString("username", NULL),
                sharedPreferenceFile.getString("password", NULL));
        username = sharedPreferenceFile.getString("username", NULL);
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,this);
        mScannerView = new ZXingScannerView(this);
        askForCameraPermissions();
        //onActivityStart();

    }

    public void onActivityStart(){
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        Log.w("handleResult", result.getText());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan result");
        String locationId = result.getText();
        String requestUrl = String.format(GET_LOCATION_PATH, locationId);
        mVolleyService.getDataVolleyStringResponseVolley("GETCALL",
                requestUrl, credentials);
    }


    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + response);

            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post error: " + error);
                Toast errorMessage = Toast.makeText(getApplicationContext(),
                        "an error occurred", Toast.LENGTH_SHORT);
                errorMessage.show();
            }

            @Override
            public void notifySuccessString(String requestType, String response) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + response);

                Intent intent = new Intent(ScanLocation.this, LocationDetails.class);
                intent.putExtra("locationDetails", response);
                startActivity(intent);
            }
        };
    }

    private void askForCameraPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                onActivityStart();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
}
