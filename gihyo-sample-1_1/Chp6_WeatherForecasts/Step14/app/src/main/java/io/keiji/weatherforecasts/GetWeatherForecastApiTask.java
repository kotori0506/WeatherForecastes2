package io.keiji.weatherforecasts;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

public class GetWeatherForecastApiTask extends AsyncTask<String, Void, WeatherForecast> {

    private final Context context;
    Exception exception;

    public GetWeatherForecastApiTask(Context context) {
        this.context = context;
    }

    @Override
    protected WeatherForecast doInBackground(String... params) {
        try {
            return WeatherApi.getWeather(context, params[0]);
        } catch (IOException e) {
            exception = e;
        } catch (JSONException e) {
            exception = e;
        }
        return null;
    }
}
