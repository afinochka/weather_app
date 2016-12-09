package com.example.afinochka.weatherapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {

    @SerializedName("speed")
    @Expose
    private double speed;
    @SerializedName("deg")
    @Expose
    private int deg;

    /**
     * No args constructor for use in serialization
     */
    public Wind() {
    }

    /**
     * @param speed
     * @param deg
     */
    public Wind(double speed, int deg) {
        this.speed = speed;
        this.deg = deg;
    }

    /**
     * @return The speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * @param speed The speed
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * @return The deg
     */
    public int getDeg() {
        return deg;
    }

    /**
     * @param deg The deg
     */
    public void setDeg(int deg) {
        this.deg = deg;
    }

}
