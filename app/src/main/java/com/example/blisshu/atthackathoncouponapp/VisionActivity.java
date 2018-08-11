package com.example.blisshu.atthackathoncouponapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;




public class VisionActivity extends AppCompatActivity {

    SurfaceView cameraView;
    TextView textView;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
    private boolean switchingActivities;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision);

        cameraView = (SurfaceView) findViewById(R.id.surface_view);
        textView = (TextView) findViewById(R.id.text_view);

        switchingActivities = false;

//        Intent myIntent = new Intent(getApplicationContext(), test_activity.class);
//        startActivityForResult(myIntent, 2);

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if (!textRecognizer.isOperational()) {
            Log.w("MainActivity", "Detector dependencies are not yet available");
        } else {

            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();

            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {

                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(VisionActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionID);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    cameraSource.stop();
                }
            });


            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {

                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if(items.size() != 0)
                    {
                        textView.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();
                                for(int i =0;i<items.size();++i)
                                {
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("\n");
                                }

                                String strB = stringBuilder.toString().toLowerCase();
                                strB = strB.replaceAll("[\\s]", "");

                                if(!switchingActivities) {
                                    if (strB.equals("nike")) {
                                        Intent myIntent = new Intent(getApplicationContext(), test_activity.class);
                                        myIntent.putExtra("coupon_brand", "nike");
                                        startActivityForResult(myIntent, 104);

                                        showMessage("Loading Nike Locations via Google Maps");
                                        switchingActivities = true;
                                    } else if (strB.equals("tide")) {
                                        Intent myIntent = new Intent(getApplicationContext(), test_activity.class);
                                        myIntent.putExtra("coupon_brand", "tide");
                                        startActivityForResult(myIntent, 105);

                                        showMessage("Loading Tide Locations via Google Maps");
                                        switchingActivities = true;
                                    } else if (strB.equals("subway")) {
                                        Intent myIntent = new Intent(getApplicationContext(), test_activity.class);
                                        myIntent.putExtra("coupon_brand", "subway");
                                        startActivityForResult(myIntent, 106);

                                        showMessage("Loading Subway Locations via Google Maps");
                                        switchingActivities = true;
                                    }
                                }



                                textView.setText(stringBuilder.toString());
                            }
                        });
                    }
                }
            });
        }
    }

    public void showMessage(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }
}
