package com.example.medicalapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QRScannerActivity extends AppCompatActivity {

    public static final int CAMERA_PERMISSION_CODE = 101;
    public TextView data_text;
    public ConstraintLayout layout_scanner ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_scanner);

        layout_scanner = findViewById(R.id.layout_scanner);
        data_text = findViewById(R.id.data_text);


                if(Build.VERSION.SDK_INT >= 23){
                    if(checkPermission(Manifest.permission.CAMERA)){
                        OpenScanner();
                    }
                    else{
                        requestPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
                    }

                }else {
                    OpenScanner();
                }


    }

    private void OpenScanner() {
        new IntentIntegrator(QRScannerActivity.this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(this, "Blank", Toast.LENGTH_SHORT).show();
            }else{
                String rezultat = result.getContents().trim();
                if(rezultat.equals("NETESTAT")){
                    layout_scanner.setBackgroundColor(Color.BLUE);
                    data_text.setText("STARE : " + result.getContents());

                }
                else
                if(rezultat.equals("TESTAT NEGATIV")){

                    layout_scanner.setBackgroundColor(Color.GREEN);
                    data_text.setText("STARE : " + result.getContents());

                }
                else
                if(rezultat.equals("TESTAT POZITIV")){

                    layout_scanner.setBackgroundColor(Color.RED);
                    data_text.setText("STARE : " + result.getContents());

                }
                else {
                    layout_scanner.setBackgroundColor(Color.GRAY);
                    data_text.setText("STARE" + result.getContents());


                }



            }


        }else{
            Toast.makeText(this, "Blank", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermission(String permission){
        int result  = ContextCompat.checkSelfPermission(QRScannerActivity.this, permission);
        if(result == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else {
            return false;
        }
    }



    private void requestPermission(String permission, int code){
        if(ActivityCompat.shouldShowRequestPermissionRationale(QRScannerActivity.this, permission)){


        }else{
            ActivityCompat.requestPermissions(QRScannerActivity.this, new String[]{permission}, code);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_PERMISSION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    OpenScanner();

                }
        }
    }
}