package com.example.afinochka.weatherapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sys {

    @SerializedName("population")
    @Expose
    private int population;

    /**
     * No args constructor for use in serialization
     *
     */
    public Sys() {
    }

    /**
     *
     * @param population
     */
    public Sys(int population) {
        super();
        this.population = population;
    }

    /**
     *
     * @return
     * The population
     */
    public int getPopulation() {
        return population;
    }

    /**
     *
     * @param population
     * The population
     */
    public void setPopulation(int population) {
        this.population = population;
    }

}