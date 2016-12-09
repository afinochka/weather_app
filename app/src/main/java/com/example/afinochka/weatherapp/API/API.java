package com.example.afinochka.weatherapp.API;

import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    private static APIFunctions apiFunctions;
    private static Retrofit retrofit;

    private API(){}

    private static Retrofit getRetrofit(){
        return retrofit;
    }

    private static Retrofit createRetrofit(String url){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(createInterceptor());
        httpClient.connectTimeout(20, TimeUnit.SECONDS);
        httpClient.readTimeout(20, TimeUnit.SECONDS);
        httpClient.writeTimeout(20, TimeUnit.SECONDS);

        OkHttpClient client = createClient(httpClient);

        Retrofit retrofit;

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);
        retrofit = builder.build();

        return retrofit;

    }

    private static Interceptor createInterceptor() {
        return chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("appid", APIConst.API_KEY)
                    .addQueryParameter("units", "metric")
                    .build();
            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        };
    }

    private static OkHttpClient createClient(OkHttpClient.Builder httpClient){
        return httpClient.build();
    }

    public static APIFunctions get(){
        if(apiFunctions == null)
            createAPI(APIConst.URL);
        return apiFunctions;
    }

    private static void createAPI(String url){
        retrofit = createRetrofit(url);
        apiFunctions = retrofit.create(APIFunctions.class);
    }

}
