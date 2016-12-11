package com.example.afinochka.weatherapp.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afinochka.weatherapp.API.API;
import com.example.afinochka.weatherapp.Adapters.WeatherAdapter;
import com.example.afinochka.weatherapp.Animation.GoAnimation;
import com.example.afinochka.weatherapp.Database.DatabaseHandler;
import com.example.afinochka.weatherapp.Models.JSONResponse;
import com.example.afinochka.weatherapp.Models.LastWeather;
import com.example.afinochka.weatherapp.Models.List;
import com.example.afinochka.weatherapp.Models.WeatherCard;
import com.example.afinochka.weatherapp.Parsers.AsyncXMLParser;
import com.example.afinochka.weatherapp.Parsers.DateParser;
import com.example.afinochka.weatherapp.Parsers.IconParser;
import com.example.afinochka.weatherapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "WeatherAppTag";
    private final String METRIC_OF_CELSIUS = "°C";

    private RecyclerView recyclerView;
    private WeatherAdapter adapter;

    private boolean wasUpdate;
    private DatabaseHandler db;
    private java.util.List<WeatherCard> data = new ArrayList<>();

    private double latitude;
    private double longitude;

    private TextView city;
    private TextView country;
    private TextView weather;

    private ImageView iconWeather;
    private Toolbar toolbar;

    private ProgressDialog progressDialog;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60; // 1 minute
    private static final int PERMISSION_REQUEST_CODE = 100;

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        db = new DatabaseHandler(this);

        checkPermissions();
        /*if (checkPermissions())
            checkContentView();
        else
            setContent(R.layout.nothing_to_show_layout);*/

    }

    public void checkContentView() {
        if (isOnline()) {
            setLocationManager();
            setContent(R.layout.main_layout_without_collapse);
        } else if ((!isOnline() && getLastWeather() != null)) {
            setContent(R.layout.main_layout_without_collapse);
            addValuesToViews(getLastWeather());
        } else
            setContent(R.layout.nothing_to_show_layout);


    }

    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            checkContentView();
            return true;
        }else
            requestPermissions();
        return false;

    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkContentView();
                } else {
                    setContent(R.layout.nothing_to_show_layout);
                }
            }
        }
    }

    private void setLocationManager() {
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
        } catch (SecurityException ex) {
            Log.e(TAG, "Location permissions exception");
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void setContent(int layout) {
        switch (layout) {
            case R.layout.main_layout_without_collapse:

                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Updating...");
                progressDialog.show();

                setContentView(layout);

                toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
                initRecyclerView();

                city = (TextView) findViewById(R.id.city);
                country = (TextView) findViewById(R.id.country);
                weather = (TextView) findViewById(R.id.weather);
                iconWeather = (ImageView) findViewById(R.id.icon_weather);

                wasUpdate = false;
                break;
            case R.layout.nothing_to_show_layout:
                if (!wasUpdate)
                    setContentView(layout);

                sendToast("Please, enable permissions for application or network");
                Button update = (Button) findViewById(R.id.update);
                update.setOnClickListener(view -> {
                    GoAnimation.usedAnimation(getApplicationContext(), update, this);
                });
                break;
            default:
                break;
        }
    }

    private void initRecyclerView() {
        adapter = new WeatherAdapter(data);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.update_menu_item) {
            sendToast("Updating…");
            updateWeather();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateWeather() {

        if (!isOnline())
            sendToast("Network is not connected");
        else {

            API.get().getWeatherByLocation(latitude, longitude).enqueue(new Callback<JSONResponse>() {
                @Override
                public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {

                    String country =
                            getCountryByCountryCode(response.body().getCity().getCountry());

                    writeWeatherToDatabase(response.body(), country);
                    addValuesToViews(getLastWeather());
                    fillRecyclerView(response.body());
                    getSupportActionBar().setTitle("now");
                }

                @Override
                public void onFailure(Call<JSONResponse> call, Throwable t) {
                    sendToast("Something wrong \n" + t.getMessage());
                }
            });
        }
    }

    private void fillRecyclerView(JSONResponse response) {
        data.clear();
        String currentDate = DateParser.getCurrentDateByEnglishFormat();
        for (List l : response.getList()) {
            String date = l.getDtTxt();
            if (!date.contains(currentDate) && date.contains("12:00:00")) {
                WeatherCard card = new WeatherCard();
                card.setWeekDay(DateParser.weekDayByDate(date));
                card.setDate(DateParser.parseDateFromEnglishDate(date));
                card.setWeatherDescription(l.getWeather().get(0).getMain());
                card.setTemperature(l.getMain().getTemp());
                card.setPressure((int) (l.getMain().getPressure()));
                card.setHumidity((int) (l.getMain().getHumidity()));
                card.setWindSpeed(l.getWind().getSpeed());
                card.setWeatherImage(IconParser.
                        chooseIconByWeatherDescription(l.getWeather().get(0).getMain()));
                data.add(card);
                adapter.notifyDataSetChanged();
            }
        }

    }

    private void addValuesToViews(LastWeather lastWeather) {
        String weatherString = lastWeather.getWeatherDescription() + " "
                + lastWeather.getTemperature() + METRIC_OF_CELSIUS;

        city.setText(lastWeather.getCity());
        country.setText(lastWeather.getCountry());
        weather.setText(weatherString);
        iconWeather.setImageResource(lastWeather.getIconId());
        getSupportActionBar().setTitle("Last update: " + lastWeather.getCurrentDate());

        progressDialog.dismiss();
    }

    private LastWeather getLastWeather() {
        return db.getLastWeather();
    }

    private String getCountryByCountryCode(String countryCode) {
        AsyncXMLParser asyncTask = new AsyncXMLParser(MainActivity.this);
        asyncTask.execute(countryCode);
        try {
            return asyncTask.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    private void writeWeatherToDatabase(JSONResponse response, String country) {

        LastWeather weather = new LastWeather();

        String description = response.getList().get(0).getWeather().get(0).getMain();

        weather.setCity(response.getCity().getName());
        weather.setCountry(country);
        weather.setWeatherDescription(description);
        weather.setTemperature(response.getList().get(0).getMain().getTemp());
        weather.setCurrentDate(DateParser.getCurrentDate());

        int iconId;
        if (DateParser.dayOrNight(response.getList().get(0).getDtTxt()).equals("Night"))
            iconId = IconParser.chooseIconByWeatherDescription(description + "Night");
        else
            iconId = IconParser.chooseIconByWeatherDescription(description);
        weather.setIconId(iconId);

        if (getLastWeather() != null) {
            db.updateLastWeather(weather);
        } else
            db.addLastWeather(weather);
    }


    private void sendToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void setWasUpdate(boolean wasUpdate) {
        this.wasUpdate = wasUpdate;
    }

    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            updateWeather();
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
    };
}
