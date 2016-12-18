package com.example.afinochka.weatherapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.afinochka.weatherapp.Holders.WeatherVH;
import com.example.afinochka.weatherapp.Models.WeatherCard;
import com.example.afinochka.weatherapp.R;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherVH> {

    private List<WeatherCard> data;

    public WeatherAdapter(List<WeatherCard> data) {
        this.data = data;
    }

    @Override
    public WeatherVH onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_card, viewGroup, false);

        return new WeatherVH(itemView);
    }

    @Override
    public void onBindViewHolder(WeatherVH holder, int i) {

        WeatherCard card = data.get(i);
        String temperature = String.valueOf(card.getTemperature()) + "°C";
        String pressure = String.valueOf(card.getPressure()) + "hpa";
        String humidity = String.valueOf(card.getHumidity()) + "%";
        String speed = String.valueOf(card.getWindSpeed()) + "m/s";

        holder.setWeekDay(card.getWeekDay());
        holder.setDate(card.getDate());
        holder.setWeatherDescription(card.getWeatherDescription());
        holder.setTemperature(temperature);
        holder.setPressure(pressure);
        holder.setHumidity(humidity);
        holder.setWindSpeed(speed);
        holder.setWeatherImage(card.getWeatherImage());

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}

