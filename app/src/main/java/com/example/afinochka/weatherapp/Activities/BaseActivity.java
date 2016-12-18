package com.example.afinochka.weatherapp.Activities;

import android.Manifest;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.afinochka.weatherapp.RxPermissions;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();


    }

    protected abstract void onLocationPermissionGranted();

    protected abstract void requestPermission();

    public void sendToast(String message){
        Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
