package com.example.afinochka.weatherapp.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIError {

    @SerializedName("cod")
    @Expose
    private int statusCode;
    @SerializedName("message")
    @Expose
    private String message;

    public APIError() {
    }

    /**
     * @param message
     * @param statusCode
     */
    public APIError(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    /**
     * @return The statusCode
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode The statusCode
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
