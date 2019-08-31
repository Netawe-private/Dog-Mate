package com.example.dogmate.Scan_Location;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.dogmate.R;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanLocation extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_location);
        mScannerView = new ZXingScannerView(this);
        onActivityStart();

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
        //Do anything with result here :D
        Log.w("handleResult", result.getText());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan result");
        builder.setMessage(result.getText());
        //openLocationFragment(result.getText());
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();

        //Resume scanning remove if you only want one scan
        //mScannerView.resumeCameraPreview(this);
        Intent intent = new Intent(ScanLocation.this, LocationDetails.class);
        intent.putExtra("scan_result", result.getText());
        startActivity(intent);
    }
}
