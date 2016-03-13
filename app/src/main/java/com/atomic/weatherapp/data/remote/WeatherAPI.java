package com.atomic.weatherapp.data.remote;

import com.atomic.weatherapp.data.model.Weather;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface WeatherAPI {

    String BASE_URL = "https://query.yahooapis.com/v1/public/";

    @GET("yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20%28select%20woeid%20from%20geo.places%281%29%20where%20text%3D%22Zagreb%2C%20ak%22%29%20and%20u%3D%27c%27&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys")
    Call<Weather> getWeather();

    class Factory{

        public static WeatherAPI service;

        public static WeatherAPI getInstance() {
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();

                WeatherAPI service = retrofit.create(WeatherAPI.class);
                return service;
            }else{
                return service;
            }
        }

    }


}
