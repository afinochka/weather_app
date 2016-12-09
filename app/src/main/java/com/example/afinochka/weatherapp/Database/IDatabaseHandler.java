package com.example.afinochka.weatherapp.Database;

import com.example.afinochka.weatherapp.Models.WeatherCard;

import java.util.List;

public interface IDatabaseHandler {

    public void addWeatherCard(WeatherCard weatherCard);

    public WeatherCard getWeatherCard(int id);

    public List<WeatherCard> getAllWeatherCards();

    public int getWeatherCardsCount();

    public int updateWeatherCard(WeatherCard message);

    public void deleteWeatherCard(WeatherCard message);

    public void deleteAllWeatherCards();

}
