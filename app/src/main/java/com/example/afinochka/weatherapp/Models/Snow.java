package com.example.afinochka.weatherapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Snow {

    @SerializedName("3h")
    @Expose
    private double _3h;

    /**
     * No args constructor for use in serialization
     *
     */
    public Snow() {
    }

    /**
     *
     * @param _3h
     */
    public Snow(double _3h) {
        super();
        this._3h = _3h;
    }

    /**
     *
     * @return
     * The _3h
     */
    public double get3h() {
        return _3h;
    }

    /**
     *
     * @param _3h
     * The 3h
     */
    public void set3h(double _3h) {
        this._3h = _3h;
    }

}
