package com.example.dogmate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.dogmate.Add_Location.AddLocationFragmentNature;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.example.dogmate.R.id.fragment_container;

public class ScanLocation extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_location);
        mScannerView = new ZXingScannerView(this);

    }

    public void onActivityStart(View view){
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
        openLocationFragment(result.getText());
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();

        //Resume scanning remove if you only want one scan
        //mScannerView.resumeCameraPreview(this);
    }

    public void openLocationFragment(String scanResult){
        try {
            JSONObject results = new JSONObject(scanResult);
            String locationType = results.getString("location_type");
            switch (locationType){
                case "Nature":

                    break;
                case "Services":
                    break;
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

    }
}
