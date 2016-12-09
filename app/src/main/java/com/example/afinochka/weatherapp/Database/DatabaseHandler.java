package com.example.afinochka.weatherapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.afinochka.weatherapp.Models.WeatherCard;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "weatherManager";

    private static final String TABLE_WEATHER_CARDS = "weather_cards";

    private static final String KEY_WEATHER_CARD_ID = "weather_card_id";
    private static final String KEY_WEEK_DAY = "week_day";
    private static final String KEY_DATE = "date";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_TEMPERATURE = "temperature";
    private static final String KEY_PRESSURE = "pressure";
    private static final String KEY_HUMIDITY = "humidity";
    private static final String KEY_HUMIDITY_2 = "humidity_2";
    private static final String KEY_WEATHER_IMAGE = "weather_image";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void addWeatherCard(WeatherCard weatherCard) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_WEEK_DAY, weatherCard.getWeekDay());
        values.put(KEY_DATE, weatherCard.getDate());
        values.put(KEY_DESCRIPTION, weatherCard.getWeatherDescription());
        values.put(KEY_TEMPERATURE, weatherCard.getTemperature());
        values.put(KEY_PRESSURE, weatherCard.getPressure());
        values.put(KEY_HUMIDITY, weatherCard.getHumidity());
        values.put(KEY_HUMIDITY_2, weatherCard.getHumidity2());
        values.put(KEY_WEATHER_IMAGE, weatherCard.getWeatherImage());

        db.insert(TABLE_WEATHER_CARDS, null, values);
        db.close();
    }

    @Override
    public WeatherCard getWeatherCard(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_WEATHER_CARDS, new String[]{KEY_WEATHER_CARD_ID,
                        KEY_WEEK_DAY, KEY_DATE, KEY_DESCRIPTION, KEY_TEMPERATURE,
                        KEY_PRESSURE, KEY_HUMIDITY, KEY_HUMIDITY_2, KEY_WEATHER_IMAGE},
                        KEY_WEATHER_CARD_ID + "=?", new String[]{String.valueOf(id)},
                        null, null, null, null);

        WeatherCard card = null;
        if(cursor != null) {
            cursor.moveToFirst();
            card = new WeatherCard(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getString(4),
                    Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)),
                    Integer.parseInt(cursor.getString(7)), Integer.parseInt(cursor.getString(8)));
            cursor.close();
        }

        db.close();
        return card;
    }

    @Override
    public List<WeatherCard> getAllWeatherCards() {
        List<WeatherCard> cardList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_WEATHER_CARDS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                WeatherCard card = new WeatherCard();
                card.setId(Integer.parseInt(cursor.getString(0)));
                card.setWeekDay(cursor.getString(1));
                card.setDate(cursor.getString(2));
                card.setWeatherDescription(cursor.getString(3));
                card.setTemperature(cursor.getString(4));
                card.setPressure(Integer.parseInt(cursor.getString(5)));
                card.setHumidity(Integer.parseInt(cursor.getString(6)));
                card.setHumidity2(Integer.parseInt(cursor.getString(7)));
                card.setWeatherImage(Integer.parseInt(cursor.getString(8)));

                cardList.add(card);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return cardList;
    }

    @Override
    public int getWeatherCardsCount() {
        String query = "SELECT * FROM " + TABLE_WEATHER_CARDS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        int count = cursor.getCount();

        cursor.close();
        db.close();

        return count;
    }

    @Override
    public int updateWeatherCard(WeatherCard weatherCard) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_WEEK_DAY, weatherCard.getWeekDay());
        values.put(KEY_DATE, weatherCard.getDate());
        values.put(KEY_DESCRIPTION, weatherCard.getWeatherDescription());
        values.put(KEY_TEMPERATURE, weatherCard.getTemperature());
        values.put(KEY_PRESSURE, weatherCard.getPressure());
        values.put(KEY_HUMIDITY, weatherCard.getHumidity());
        values.put(KEY_HUMIDITY_2, weatherCard.getHumidity2());
        values.put(KEY_WEATHER_IMAGE, weatherCard.getWeatherImage());

        int updated = db.update(TABLE_WEATHER_CARDS, values, KEY_WEATHER_CARD_ID + " =?",
                new String[]{String.valueOf(weatherCard.getId())});

        db.close();
        return updated;
    }

    @Override
    public void deleteWeatherCard(WeatherCard message) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WEATHER_CARDS, KEY_WEATHER_CARD_ID + " = ?",
                new String[]{String.valueOf(message.getId())});
        db.close();
    }

    @Override
    public void deleteAllWeatherCards() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WEATHER_CARDS, null, null);
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WEATHER_CARDS_TABLE = "CREATE TABLE " + TABLE_WEATHER_CARDS + "("
                + KEY_WEATHER_CARD_ID + " INTEGER PRIMARY KEY,"
                + KEY_WEEK_DAY + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_TEMPERATURE + " TEXT,"
                + KEY_PRESSURE + " INT,"
                + KEY_HUMIDITY + " INT,"
                + KEY_HUMIDITY_2 + " INT,"
                + KEY_WEATHER_IMAGE + " INT"
                + ")";

        db.execSQL(CREATE_WEATHER_CARDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER_CARDS);
        onCreate(db);
    }












}
