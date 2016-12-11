package com.example.afinochka.weatherapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.afinochka.weatherapp.Models.LastWeather;

public class DatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "weatherManager";

    private static final String TABLE_LAST_WEATHER = "last_weather";

    private static final String KEY_LAST_WEATHER_ID = "last_weather_id";
    private static final String KEY_CITY = "city";
    private static final String KEY_COUNTRY = "country";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_TEMPERATURE = "temperature";
    private static final String KEY_ICON_ID = "icon_id";
    private static final String KEY_CURRENT = "current";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void addLastWeather(LastWeather weather) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_CITY, weather.getCity());
        values.put(KEY_COUNTRY, weather.getCountry());
        values.put(KEY_DESCRIPTION, weather.getWeatherDescription());
        values.put(KEY_TEMPERATURE, weather.getTemperature());
        values.put(KEY_ICON_ID, weather.getIconId());
        values.put(KEY_CURRENT, weather.getCurrentDate());

        db.insert(TABLE_LAST_WEATHER, null, values);
        db.close();
    }

    @Override
    public LastWeather getLastWeather() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LAST_WEATHER, new String[]{KEY_LAST_WEATHER_ID,
                        KEY_CITY, KEY_COUNTRY, KEY_DESCRIPTION, KEY_TEMPERATURE,
                        KEY_ICON_ID, KEY_CURRENT},
                KEY_LAST_WEATHER_ID + "=?", new String[]{String.valueOf(1)},
                null, null, null, null);

        LastWeather weather = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            weather = new LastWeather(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), Double.parseDouble(cursor.getString(4)),
                    Integer.parseInt(cursor.getString(5)), cursor.getString(6));
            cursor.close();
        }

        db.close();
        return weather;
    }

    @Override
    public int updateLastWeather(LastWeather weather) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_CITY, weather.getCity());
        values.put(KEY_COUNTRY, weather.getCountry());
        values.put(KEY_DESCRIPTION, weather.getWeatherDescription());
        values.put(KEY_TEMPERATURE, weather.getTemperature());
        values.put(KEY_ICON_ID, weather.getIconId());
        values.put(KEY_CURRENT, weather.getCurrentDate());

        int updated = db.update(TABLE_LAST_WEATHER, values, KEY_LAST_WEATHER_ID + " =?",
                new String[]{String.valueOf(weather.getId())});

        db.close();
        return updated;
    }

    @Override
    public void deleteLastWeather(LastWeather weather) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LAST_WEATHER, KEY_LAST_WEATHER_ID + " = ?",
                new String[]{String.valueOf(weather.getId())});
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LAST_WEATHER, null, null);
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WEATHER_CARDS_TABLE = "CREATE TABLE " + TABLE_LAST_WEATHER + "("
                + KEY_LAST_WEATHER_ID + " INTEGER PRIMARY KEY,"
                + KEY_CITY + " TEXT,"
                + KEY_COUNTRY + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_TEMPERATURE + " TEXT,"
                + KEY_ICON_ID + " INT,"
                + KEY_CURRENT + " TEXT"
                + ")";

        db.execSQL(CREATE_WEATHER_CARDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAST_WEATHER);
        onCreate(db);
    }


}
