package com.example.afinochka.weatherapp.API;


import com.example.afinochka.weatherapp.Models.JSONResponse;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface APIFunctions {
    @GET("forecast")
    public Call<JSONResponse> getWeatherByLocation(@Query("lat") double latitude,
                                                   @Query("lon") double longitude);

    @GET("forecast")
    public Call<JSONResponse> getWeatherByCityName(@Query("q") String cityName);
}
