package com.example.afinochka.weatherapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coord {

    @SerializedName("lon")
    @Expose
    private double lon;
    @SerializedName("lat")
    @Expose
    private double lat;

    /**
     * No args constructor for use in serialization
     */
    public Coord() {
    }

    /**
     * @param lon
     * @param lat
     */
    public Coord(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    /**
     * @return The lon
     */
    public double getLon() {
        return lon;
    }

    /**
     * @param lon The lon
     */
    public void setLon(int lon) {
        this.lon = lon;
    }

    /**
     * @return The lat
     */
    public double getLat() {
        return lat;
    }

    /**
     * @param lat The lat
     */
    public void setLat(int lat) {
        this.lat = lat;
    }

}
