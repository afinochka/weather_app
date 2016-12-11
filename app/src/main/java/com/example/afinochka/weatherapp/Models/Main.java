package com.example.afinochka.weatherapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("temp")
    @Expose
    private double temp;
    @SerializedName("temp_min")
    @Expose
    private double tempMin;
    @SerializedName("temp_max")
    @Expose
    private double tempMax;
    @SerializedName("pressure")
    @Expose
    private double pressure;
    @SerializedName("sea_level")
    @Expose
    private double seaLevel;
    @SerializedName("grnd_level")
    @Expose
    private double grndLevel;
    @SerializedName("humidity")
    @Expose
    private double humidity;
    @SerializedName("temp_kf")
    @Expose
    private double tempKf;

    /**
     * No args constructor for use in serialization
     *
     */
    public Main() {
    }

    /**
     *
     * @param seaLevel
     * @param humidity
     * @param pressure
     * @param grndLevel
     * @param tempMax
     * @param temp
     * @param tempKf
     * @param tempMin
     */
    public Main(double temp, double tempMin, double tempMax, double pressure, double seaLevel,
                double grndLevel, double humidity, double tempKf) {
        super();
        this.temp = temp;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.pressure = pressure;
        this.seaLevel = seaLevel;
        this.grndLevel = grndLevel;
        this.humidity = humidity;
        this.tempKf = tempKf;
    }

    /**
     *
     * @return
     * The temp
     */
    public double getTemp() {
        return temp;
    }

    /**
     *
     * @param temp
     * The temp
     */
    public void setTemp(double temp) {
        this.temp = temp;
    }

    /**
     *
     * @return
     * The tempMin
     */
    public double getTempMin() {
        return tempMin;
    }

    /**
     *
     * @param tempMin
     * The temp_min
     */
    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    /**
     *
     * @return
     * The tempMax
     */
    public double getTempMax() {
        return tempMax;
    }

    /**
     *
     * @param tempMax
     * The temp_max
     */
    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    /**
     *
     * @return
     * The pressure
     */
    public double getPressure() {
        return pressure;
    }

    /**
     *
     * @param pressure
     * The pressure
     */
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    /**
     *
     * @return
     * The seaLevel
     */
    public double getSeaLevel() {
        return seaLevel;
    }

    /**
     *
     * @param seaLevel
     * The sea_level
     */
    public void setSeaLevel(double seaLevel) {
        this.seaLevel = seaLevel;
    }

    /**
     *
     * @return
     * The grndLevel
     */
    public double getGrndLevel() {
        return grndLevel;
    }

    /**
     *
     * @param grndLevel
     * The grnd_level
     */
    public void setGrndLevel(double grndLevel) {
        this.grndLevel = grndLevel;
    }

    /**
     *
     * @return
     * The humidity
     */
    public double getHumidity() {
        return humidity;
    }

    /**
     *
     * @param humidity
     * The humidity
     */
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    /**
     *
     * @return
     * The tempKf
     */
    public double getTempKf() {
        return tempKf;
    }

    /**
     *
     * @param tempKf
     * The temp_kf
     */
    public void setTempKf(double tempKf) {
        this.tempKf = tempKf;
    }

}
