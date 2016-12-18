package com.example.afinochka.weatherapp;

import android.app.Application;

import com.example.afinochka.weatherapp.API.API;

public class WeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        API.init();
    }
}
