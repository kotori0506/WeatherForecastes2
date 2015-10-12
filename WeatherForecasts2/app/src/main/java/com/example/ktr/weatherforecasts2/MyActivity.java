package com.example.ktr.weatherforecasts2;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import android.os.Handler;


public class MyActivity extends ActionBarActivity {

    private TextView textView;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        textView=(TextView)findViewById(R.id.tv_main);

        Thread thread = new Thread(){
            @Override
        public void run(){
             try {
                 final String data = WeatherApi.getWeather(MyActivity.this,
                         "400040");

                 handler.post(new Runnable(){
                                 @Override
                                public void run(){
                                     textView.setText(data);
                                 }
                              });
             }catch (final IOException e){
                 handler.post(new Runnable(){
                     @Override
                    public void run(){
                         Toast.makeText(MyActivity.this,e.getMessage(),
                                 Toast.LENGTH_SHORT).show();
                     }
                 });
             }
            }
        };

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
