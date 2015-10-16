package com.example.ktr.weatherforecasts2;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;


/**
 * Created by ktr on 2015/10/08.
 */

public class GetWeatherForecastApiTask extends AsyncTask<String, Void, String> {

    private final Context context;
    Exception exception;

    public GetWeatherForecastApiTask(Context context){
        this.context = context;
    }
    @Override
    protected String doInBackground(String... params){
        try {
            return WeatherApi.getWeather(context,params[0]);
        }catch (IOException e){
            exception = e;
        }
        return null;
    }
}
