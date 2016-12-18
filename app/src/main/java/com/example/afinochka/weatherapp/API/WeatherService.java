package com.example.afinochka.weatherapp.API;


import com.example.afinochka.weatherapp.Models.JSONResponse;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface WeatherService {

    @GET("forecast")
    Observable<JSONResponse> getWeatherByLocation(@Query("lat") double latitude,
                                               @Query("lon") double longitude);

    @GET("forecast")
    Observable<JSONResponse> getWeatherByCityName(@Query("q") String cityName);
}
