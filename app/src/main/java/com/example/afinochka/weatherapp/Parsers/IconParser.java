package com.example.afinochka.weatherapp.Parsers;


import com.example.afinochka.weatherapp.R;

public class IconParser {

    public static int chooseIconByWeatherDescription(String description){
        switch(description){
            case "Clear":
                return R.drawable.weather_clear;
            case "ClearNight":
                return R.drawable.weather_clear_night;
            case "Rain":
                return R.drawable.weather_rain_day;
            case "RainNight":
                return R.drawable.weather_rain_night;
            case "Snow":
                return R.drawable.weather_snow_day;
            case "SnowNight":
                return R.drawable.weather_snow_night;
            case "Clouds":
                return R.drawable.weather_clouds;
            case "CloudsNight":
                return R.drawable.weather_clouds_night;
            default:
                return R.drawable.weather_clear;
        }
    }

}
