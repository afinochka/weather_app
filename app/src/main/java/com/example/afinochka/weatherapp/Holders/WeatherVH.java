package com.example.afinochka.weatherapp.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.afinochka.weatherapp.R;

public class WeatherVH extends RecyclerView.ViewHolder {

    private TextView weekDay;
    private TextView date;
    private TextView weatherDescription;
    private TextView temperature;
    private TextView pressure;
    private TextView humidity;
    private TextView windSpeed;

    private ImageView weatherImage;

    public WeatherVH(View itemView) {
        super(itemView);
        weekDay = (TextView) itemView.findViewById(R.id.week_day);
        date = (TextView) itemView.findViewById(R.id.date);
        weatherDescription = (TextView) itemView.findViewById(R.id.weather_description);
        temperature = (TextView) itemView.findViewById(R.id.temperature);
        pressure = (TextView) itemView.findViewById(R.id.pressure);
        humidity = (TextView) itemView.findViewById(R.id.humidity);
        windSpeed = (TextView) itemView.findViewById(R.id.wind_speed);

        weatherImage = (ImageView) itemView.findViewById(R.id.weather_image);
    }

    public void setWeekDay(String value) {
        this.weekDay.setText(value);
    }

    public void setDate(String value) {
        this.date.setText(value);
    }

    public void setWeatherDescription(String value) {
        this.weatherDescription.setText(value);
    }

    public void setTemperature(String value) {
        this.temperature.setText(value);
    }

    public void setPressure(String value) {
        this.pressure.setText(value);
    }

    public void setHumidity(String value) {
        this.humidity.setText(value);
    }

    public void setWindSpeed(String value) {
        this.windSpeed.setText(value);
    }

    public void setWeatherImage(int value) {
        this.weatherImage.setImageResource(value);
    }
}