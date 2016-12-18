package com.example.afinochka.weatherapp.Handler;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class SPHandler {
    public static final String STORAGE_NAME = "WeatherPreferences";

    private static SharedPreferences data = null;
    private static SharedPreferences.Editor editor = null;
    private static Context context = null;

    public static void init(Context ctx) {
        context = ctx;
    }

    private static void init() {
        data = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
        editor = data.edit();
    }

    public static boolean contains(String key){
        if (data == null) {
            init();
        }

        return data.contains(key);
    }

    public static void addStringProperty(String name, String value) {
        if (data == null) {
            init();
        }
        editor.putString(name, value);
        editor.apply();
    }

    public static String getStringProperty(String name) {
        if (data == null) {
            init();
        }
        return data.getString(name, null);
    }

    public static void addIntProperty(String name, int value) {
        if (data == null) {
            init();
        }
        editor.putInt(name, value);
        editor.apply();
    }

    public static int getIntProperty(String name) {
        if (data == null) {
            init();
        }
        return data.getInt(name, 0);
    }

    public static void removeProperty(String name){
        if(data == null)
            init();
        editor.remove(name);
        editor.apply();
    }

    public static void clear() {
        if (data == null) {
            init();
        }
        editor.clear();
        editor.apply();
    }
}
