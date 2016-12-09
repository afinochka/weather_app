package com.example.afinochka.weatherapp.Models;


public class WeatherCard
{
    private int id;
    private String weekDay;
    private String date;
    private String weatherDescription;
    private String temperature;
    private int pressure;
    private int humidity;
    private int humidity2;
    private int weatherImage;

    public WeatherCard(){}
    public WeatherCard(int id, String weekDay, String date, String weatherDescription,
                       String temperature, int pressure, int humidity, int humidity2,
                       int weatherImage){
        this.id = id;
        this.weekDay = weekDay;
        this.date = date;
        this.weatherDescription = weatherDescription;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.humidity2 = humidity2;
        this.weatherImage = weatherImage;
    }

    public WeatherCard(String weekDay, String date, String weatherDescription,
                       String temperature, int pressure, int humidity, int humidity2,
                       int weatherImage){
        this.weekDay = weekDay;
        this.date = date;
        this.weatherDescription = weatherDescription;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.humidity2 = humidity2;
        this.weatherImage = weatherImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getHumidity2() {
        return humidity2;
    }

    public void setHumidity2(int humidity2) {
        this.humidity2 = humidity2;
    }

    public int getWeatherImage() {
        return weatherImage;
    }

    public void setWeatherImage(int weatherImage) {
        this.weatherImage = weatherImage;
    }
}
