package com.example.afinochka.weatherapp.Activities;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afinochka.weatherapp.API.API;
import com.example.afinochka.weatherapp.Animation.GoAnimation;
import com.example.afinochka.weatherapp.Models.JSONResponse;
import com.example.afinochka.weatherapp.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "WeatherAppTag";
    ProgressBar progress;
    ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    public boolean wasUpdate;

    double latitude; // Latitude
    double longitude; // Longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkContentView();

        /*Button getWeather = (Button) findViewById(R.id.get_weather);

        TextView textView = (TextView) findViewById(R.id.location);

        getWeather.setOnClickListener(view -> {


            if (!isOnline())
                Toast.makeText(this, "Network is not connected", Toast.LENGTH_SHORT).show();
            else {
                API.get().getWeatherByCityName("Minsk").enqueue(new Callback<JSONResponse>() {
                    @Override
                    public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                        textView.setText(response.body().getName());
                    }

                    @Override
                    public void onFailure(Call<JSONResponse> call, Throwable t) {
                        textView.setText("Something wrong \n" + t.getMessage());
                    }
                });
                /*Location location = getLocation();
                if (null != location) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    textView.setText("Your Location is - \n" +
                            "Lat: " + latitude + "\n" +
                            "Long: " + longitude + "\n" +
                            "City: " + getCity(latitude, longitude));
                }*/
        // }
        // });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId() == R.id.update_menu_item){
            updateWeather();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView() {
        //recyclerView.setAdapter(new WhetherAdapter(5));
    }

    private void updateWeather(){

        TextView city = (TextView) findViewById(R.id.city);
        TextView country = (TextView) findViewById(R.id.country);
        TextView weather = (TextView) findViewById(R.id.weather);

        if (!isOnline())
            Toast.makeText(this, "Network is not connected", Toast.LENGTH_SHORT).show();
        else {
            API.get().getWeatherByCityName("Minsk").enqueue(new Callback<JSONResponse>() {
                @Override
                public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                    city.setText(response.body().getName());
                    country.setText(response.body().getSys().getCountry());

                    weather.setText(response.body().getWeather().get(0).getDescription()
                    + "\n" + response.body().getMain().getTemp() + "°C");
                }

                @Override
                public void onFailure(Call<JSONResponse> call, Throwable t) {
                    sendToast("Something wrong \n" + t.getMessage());
                }
            });
            Location location = getLocation();
            if (null != location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                sendToast("Location is - \n" +
                        "Lat: " + latitude + "\n" +
                        "Long: " + longitude);
            }
        }
    }

    private void sendToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void checkContentView() {
        if (isOnline()) {
            setContentView(R.layout.activity_main_layout);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
            initRecyclerView();
            wasUpdate = false;
        } else {
            if (!wasUpdate)
                setContentView(R.layout.nothing_to_show_layout);

            sendToast("Please, enable network");
            Button update = (Button) findViewById(R.id.update);
            update.setOnClickListener(view -> {
                GoAnimation.usedAnimation(getApplicationContext(), update, this);
            });

        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public Location getLocation() {

        Location location = null;

        try {
            locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);

            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES,
                    new GPSLocationListener());

            if (locationManager != null) {
                location = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }


        } catch (SecurityException ex) {
            Log.e("weatherTag", ex.getMessage());
        } catch (Exception ex) {
            Log.e("weatherTag", ex.getMessage());
        }

        return location;
    }

    public String getCity(double latitude, double longitude) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                return addresses.get(0).getLocality();
            }

        } catch (IOException ex) {
            Log.e("weatherTag", ex.getMessage());
        }
        return null;
    }

    private class GPSLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    }
}
