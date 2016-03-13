package com.atomic.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.atomic.weatherapp.data.model.Channel;
import com.atomic.weatherapp.data.model.Weather;
import com.atomic.weatherapp.data.remote.WeatherAPI;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.textView_city) TextView city;
    @Bind(R.id.textView_lastupdated) TextView lastUpdated;
    @Bind(R.id.textView_temperature) TextView temperature;
    @Bind(R.id.textView_conditions) TextView conditions;
    @Bind(R.id.button_refresh) Button refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_refresh)
    public void refreshData(){
        WeatherAPI.Factory.getInstance().getWeather().enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Channel query = response.body().getQuery().getResults().getChannel();

                temperature.setText(query.getItem().getCondition().getTemp()+" °C");
                city.setText(query.getLocation().getCity());
                lastUpdated.setText(query.getLastBuildDate());
                conditions.setText(query.getItem().getCondition().getText());
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
               // Log.e("Failed while fetching new data", t.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }
}
