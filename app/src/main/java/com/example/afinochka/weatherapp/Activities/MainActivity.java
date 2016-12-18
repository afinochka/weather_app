package com.example.afinochka.weatherapp.Activities;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.afinochka.weatherapp.API.API;
import com.example.afinochka.weatherapp.API.Const;
import com.example.afinochka.weatherapp.Adapter.WeatherAdapter;
import com.example.afinochka.weatherapp.Handler.SPHandler;
import com.example.afinochka.weatherapp.Models.JSONResponse;
import com.example.afinochka.weatherapp.Models.List;
import com.example.afinochka.weatherapp.Models.WeatherCard;
import com.example.afinochka.weatherapp.Parsers.AsyncXMLParser;
import com.example.afinochka.weatherapp.Parsers.DateParser;
import com.example.afinochka.weatherapp.Parsers.IconParser;
import com.example.afinochka.weatherapp.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private final String TAG = "WeatherAppTag";
    private final String METRIC_OF_CELSIUS = "°C";

    private WeatherAdapter adapter;

    private boolean wasUpdate;
    private ArrayList<WeatherCard> cards = new ArrayList<>();

    private double latitude;
    private double longitude;

    private boolean isLoading;

    private TextView city;
    private TextView country;
    private TextView weather;
    private RecyclerView recyclerView;
    private ImageView iconWeather;
    private ImageView loadingIndicator;
    private ImageView nothing;

    private final static int PERMISSION_REQUEST_CODE = 100;
    private final static int REQUEST_CHECK_SETTINGS = 0;

    ReactiveLocationProvider locationProvider;
    private Observable<Location> locationUpdatesObservable;

    private Subscription updatableLocationSubscription;
    private Subscription weatherSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout_without_progress_bar);

        initViews();

        locationProvider = new ReactiveLocationProvider(MainActivity.this);

        if (SPHandler.contains("city"))
            addValuesToViews();
        else
            nothing.setVisibility(View.VISIBLE);
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        loadingIndicator = (ImageView) findViewById(R.id.loading_indicator);
        nothing = (ImageView) findViewById(R.id.nothing);
        city = (TextView) findViewById(R.id.city);
        country = (TextView) findViewById(R.id.country);
        weather = (TextView) findViewById(R.id.weather);
        iconWeather = (ImageView) findViewById(R.id.icon_weather);

        adapter = new WeatherAdapter(cards);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);

        SPHandler.init(MainActivity.this);
    }

    private void getLocation() {

        final LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setNumUpdates(5)
                .setInterval(100);

        try {
            locationUpdatesObservable = locationProvider
                    .checkLocationSettings(
                            new LocationSettingsRequest.Builder()
                                    .addLocationRequest(locationRequest)
                                    .setAlwaysShow(true)
                                    .build()
                    )
                    .flatMap(locationSettingsResult -> locationProvider.getUpdatedLocation(locationRequest));
        }
        catch (SecurityException ex){
            Log.e(TAG, "SecureException " + ex.getMessage());
        }
    }

    @Override
    protected void onLocationPermissionGranted() {
        updatableLocationSubscription = locationUpdatesObservable
                .subscribe(location -> {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    getWeather(location.getLatitude(), location.getLongitude());
                    Log.i(TAG, "latitude: " + latitude + ", longitude: " + longitude);
                });


    }

    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }else
            requestPermission();
        return false;

    }

    @Override
    protected void requestPermission() {
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
                    nothing.setVisibility(View.GONE);
                    showLoadingIndicator(true);
                    getLocation();
                    onLocationPermissionGranted();
                } else {
                    nothing.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.refresh:
                if(!checkPermissions())
                    nothing.setVisibility(View.VISIBLE);
                else {
                    nothing.setVisibility(View.GONE);
                    showLoadingIndicator(true);
                    getLocation();
                    onLocationPermissionGranted();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showLoadingIndicator(boolean show) {
        isLoading = show;
        if (isLoading) {
            loadingIndicator.setVisibility(View.VISIBLE);
            loadingIndicator.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                    .rotationBy(360)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            loadingIndicator.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                                    .rotationBy(360)
                                    .setDuration(500)
                                    .setListener(this);
                        }
                    });
        } else {
            loadingIndicator.animate().cancel();
            loadingIndicator.setVisibility(View.GONE);
        }
    }

    private void getWeather(double lat, double lon) {

        weatherSubscription = API.get()
                .getWeatherByLocation(lat, lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading = false;
                        showLoadingIndicator(false);

                        if (SPHandler.contains("city"))
                            addValuesToViews();

                        Snackbar.make(recyclerView, "Connection error", Snackbar.LENGTH_LONG)
                                .setAction("Try again", v -> {
                                    showLoadingIndicator(true);
                                    getWeather(lat, lon);
                                })
                                .show();
                    }

                    @Override
                    public void onNext(JSONResponse response) {
                        isLoading = false;
                        wasUpdate = true;

                        saveData(response);
                        addValuesToViews();
                        fillRecyclerView(response);
                        if (getSupportActionBar() != null)
                            getSupportActionBar().setTitle("now");

                        showLoadingIndicator(false);
                    }
                });
    }

    private void saveData(JSONResponse response) {
        SPHandler.clear();

        String description = response.getList().get(0).getWeather().get(0).getMain();

        SPHandler.addStringProperty("city", response.getCity().getName());
        SPHandler.addStringProperty("country", getCountryByCountryCode(response.getCity().getCountry()));
        SPHandler.addStringProperty("weather",
                description + " " +
                        response.getList().get(0).getMain().getTemp() + METRIC_OF_CELSIUS);
        SPHandler.addStringProperty("date", DateParser.getCurrentDate());

        int iconId;
        if (DateParser.dayOrNight(response.getList().get(0).getDtTxt()).equals("Night"))
            iconId = IconParser.chooseIconByWeatherDescription(description + "Night");
        else
            iconId = IconParser.chooseIconByWeatherDescription(description);
        SPHandler.addIntProperty("icon", iconId);
    }

    private void addValuesToViews() {
        city.setText(SPHandler.getStringProperty("city"));
        country.setText(SPHandler.getStringProperty("country"));
        weather.setText(SPHandler.getStringProperty("weather"));
        iconWeather.setImageResource(SPHandler.getIntProperty("icon"));

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Last update: " + SPHandler.getStringProperty("date"));
    }

    private void fillRecyclerView(JSONResponse response) {
        cards.clear();
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
                cards.add(card);
                adapter.notifyDataSetChanged();
            }
        }

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

    @Override
    public void onStop() {
        super.onStop();
        if (weatherSubscription != null && !weatherSubscription.isUnsubscribed())
            weatherSubscription.unsubscribe();

        if (updatableLocationSubscription != null && !updatableLocationSubscription.isUnsubscribed())
            updatableLocationSubscription.unsubscribe();
    }

}
