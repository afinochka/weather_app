package com.example.afinochka.weatherapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.afinochka.weatherapp.Models.WeatherCard;
import com.example.afinochka.weatherapp.R;

import java.util.List;

public class WhetherAdapter extends RecyclerView.Adapter<WeatherVH> {

    private List<WeatherCard> data;
    Context context;

    public WhetherAdapter(List<WeatherCard> data, int layout, Context context) {
        this.data = data;
        this.context = context;
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

        holder.setWeekDay(card.getWeekDay());
        holder.setDate(card.getDate());
        holder.setWeatherDescription(card.getWeatherDescription());
        holder.setTemperature(card.getTemperature());
        holder.setPressure(String.valueOf(card.getPressure()));
        holder.setHumidity(String.valueOf(card.getHumidity()));
        holder.setHumidity2(String.valueOf(card.getHumidity2()));
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

