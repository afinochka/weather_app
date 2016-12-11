package com.example.afinochka.weatherapp.Models;

public class LastWeather {

    private int id = 1;
    private String city;
    private String country;
    private String weatherDescription;
    private double temperature;
    private int iconId;
    private String currentDate;

    public LastWeather(){}

    public LastWeather(int id, String city, String country, String weatherDescription,
                       double temperature, int iconId, String currentDate){
        this.id = id;
        this.city = city;
        this.country = country;
        this.weatherDescription = weatherDescription;
        this.temperature = temperature;
        this.iconId = iconId;
        this.currentDate = currentDate;
    }

    public LastWeather(String city, String country, String weatherDescription,
                       double temperature, int iconId){
        this.city = city;
        this.country = country;
        this.weatherDescription = weatherDescription;
        this.temperature = temperature;
        this.iconId = iconId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
}
