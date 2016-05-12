package io.keiji.weatherforecasts;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;

public class GetWeatherForecastApiTask extends AsyncTask<String, Void, String> {

    private final Context context;
    Exception exception;

    public GetWeatherForecastApiTask(Context context) {
        this.context = context;
    }

    protected String doInBackground(String... params) {
        try {
            return WeatherApi.getWeather(context, params[0]);
        } catch (IOException e) {
            exception = e;
        }
        return null;
    }
}
