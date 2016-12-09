package com.example.afinochka.weatherapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("temp")
    @Expose
    private double temp;
    @SerializedName("pressure")
    @Expose
    private int pressure;
    @SerializedName("humidity")
    @Expose
    private int humidity;
    @SerializedName("temp_min")
    @Expose
    private double tempMin;
    @SerializedName("temp_max")
    @Expose
    private double tempMax;

    /**
     * No args constructor for use in serialization
     */
    public Main() {
    }

    /**
     * @param humidity
     * @param pressure
     * @param tempMax
     * @param temp
     * @param tempMin
     */
    public Main(double temp, int pressure, int humidity, double tempMin, double tempMax) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
    }

    /**
     * @return The temp
     */
    public double getTemp() {
        return temp;
    }

    /**
     * @param temp The temp
     */
    public void setTemp(double temp) {
        this.temp = temp;
    }

    /**
     * @return The pressure
     */
    public int getPressure() {
        return pressure;
    }

    /**
     * @param pressure The pressure
     */
    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    /**
     * @return The humidity
     */
    public int getHumidity() {
        return humidity;
    }

    /**
     * @param humidity The humidity
     */
    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    /**
     * @return The tempMin
     */
    public double getTempMin() {
        return tempMin;
    }

    /**
     * @param tempMin The temp_min
     */
    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    /**
     * @return The tempMax
     */
    public double getTempMax() {
        return tempMax;
    }

    /**
     * @param tempMax The temp_max
     */
    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

}
