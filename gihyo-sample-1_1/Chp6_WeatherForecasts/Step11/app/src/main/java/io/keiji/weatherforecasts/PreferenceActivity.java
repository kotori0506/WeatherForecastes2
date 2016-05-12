package io.keiji.weatherforecasts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

public class PreferenceActivity extends Activity {

    public static final String EXTRA_SELECTED_CITY = "selected_city";

    private TextView textView;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, PreferenceActivity.class);
        return intent;
    }

    private class LoadAreaListTask extends AsyncTask<Void, Void, ArrayList<GetAreaApi.Prefecture>> {
        private Exception exception;

        @Override
        protected ArrayList<GetAreaApi.Prefecture> doInBackground(Void... params) {

            try {
                return GetAreaApi.getPrefectureList(PreferenceActivity.this);
            } catch (IOException e) {
                exception = e;
            } catch (ParserConfigurationException e) {
                exception = e;
            } catch (SAXException e) {
                exception = e;
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<GetAreaApi.Prefecture> prefectures) {
            super.onPostExecute(prefectures);

            if (prefectures != null) {
                // 一覧を表示
                for (GetAreaApi.Prefecture pref : prefectures) {
                    textView.append(" " + pref.title + "\n");
                    for (GetAreaApi.Prefecture.City city : pref.cityList) {
                        textView.append("   " + city.title + "\n");
                    }
                }

            } else if (exception != null) {
                Toast.makeText(PreferenceActivity.this, "Exception is occurred.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        textView = new TextView(this);

        setContentView(textView);

        new LoadAreaListTask().execute();
    }
}
