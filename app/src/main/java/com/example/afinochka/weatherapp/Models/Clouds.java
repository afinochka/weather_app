package com.example.afinochka.weatherapp.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clouds {

    @SerializedName("all")
    @Expose
    private int all;

    /**
     * No args constructor for use in serialization
     */
    public Clouds() {
    }

    /**
     * @param all
     */
    public Clouds(int all) {
        this.all = all;
    }

    /**
     * @return The all
     */
    public int getAll() {
        return all;
    }

    /**
     * @param all The all
     */
    public void setAll(int all) {
        this.all = all;
    }

}
