package com.example.afinochka.weatherapp.API;

import android.app.Application;
import android.util.Log;

import com.example.afinochka.weatherapp.Models.JSONResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

public class API {
    private static final String TAG = Application.class.getCanonicalName();

    private static Observable<JSONResponse> observableRetrofit;
   // private static BehaviorSubject<JSONResponse> observableWeather;
    private static Subscription subscription;

    private static WeatherService apiService;

    private API(){}

    /*private static Retrofit createRetrofit(String url){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(createInterceptor());
        httpClient.connectTimeout(20, TimeUnit.SECONDS);
        httpClient.readTimeout(20, TimeUnit.SECONDS);
        httpClient.writeTimeout(20, TimeUnit.SECONDS);

        OkHttpClient client = httpClient.build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);


        return builder.build();

    }*/

    private static Interceptor createInterceptor() {
        return chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("appid", Const.API_KEY)
                    .addQueryParameter("units", "metric")
                    .addQueryParameter("cnt", "28")
                    .build();
            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        };
    }

    /*public static WeatherService get(){
        if(apiFunctions == null)
            createAPI(Const.URL);
        return apiFunctions;
    }*/

    /*private static void createAPI(String url){
        Retrofit retrofit = createRetrofit(url);
        apiFunctions = retrofit.create(WeatherService.class);
    }*/

    private static OkHttpClient createClient(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(createInterceptor());

        return httpClient.build();
    }

    public static void init() {
        Log.d(TAG, "init");

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory
                                            .createWithScheduler(Schedulers.io());

        //Gson gson = new GsonBuilder().create();

        OkHttpClient client = createClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .client(client)
                .build();

        apiService = retrofit.create(WeatherService.class);

    }

    /*public static void resetObservable() {
        observableWeather = BehaviorSubject.create();

        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = observableRetrofit.subscribe(new Subscriber<JSONResponse>() {
            @Override
            public void onCompleted() {
                //do nothing
            }

            @Override
            public void onError(Throwable e) {
                observableWeather.onError(e);
            }

            @Override
            public void onNext(JSONResponse response) {
                observableWeather.onNext(response);
            }
        });
    }*/

    /*public static Observable<JSONResponse> getWeather(double lat, double lon){
        observableRetrofit = apiService.getWeatherByLocation(lat, lon);
    }*/

    public static WeatherService get(){
        if(apiService == null)
            init();
        return apiService;
    }


    /*public static Observable<JSONResponse> getObservable(double lat, double lon) {

        observableRetrofit = apiService.getWeatherByLocation(lat, lon);

        //observableWeather = BehaviorSubject.create();

        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = observableRetrofit.subscribe(new Subscriber<JSONResponse>() {
            @Override
            public void onCompleted() {
                //do nothing
            }

            @Override
            public void onError(Throwable e) {
                observableWeather.onError(e);
            }

            @Override
            public void onNext(JSONResponse response) {
                observableWeather.onNext(response);
            }
        });

        return observableWeather;
    }*/

}
