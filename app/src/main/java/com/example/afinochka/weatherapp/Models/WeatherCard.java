package com.example.afinochka.weatherapp.Models;


public class WeatherCard
{
    private String weekDay;
    private String date;
    private String weatherDescription;
    private double temperature;
    private int pressure;
    private int humidity;
    private double windSpeed;
    private int weatherImage;

    public WeatherCard(){}
    public WeatherCard(String weekDay, String date, String weatherDescription,
                       double temperature, int pressure, int humidity, double windSpeed,
                       int weatherImage){
        this.weekDay = weekDay;
        this.date = date;
        this.weatherDescription = weatherDescription;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.weatherImage = weatherImage;
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

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
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

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWeatherImage() {
        return weatherImage;
    }

    public void setWeatherImage(int weatherImage) {
        this.weatherImage = weatherImage;
    }
}
