package com.example.afinochka.weatherapp.Database;

import com.example.afinochka.weatherapp.Models.LastWeather;
import com.example.afinochka.weatherapp.Models.WeatherCard;

import java.util.List;

public interface IDatabaseHandler {

    void addLastWeather(LastWeather weather);

    LastWeather getLastWeather();

    int updateLastWeather(LastWeather weather);

    void deleteLastWeather(LastWeather weather);

    void deleteAll();

}
