package io.keiji.weatherforecasts;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

public class PreferenceActivity extends FragmentActivity
        implements FragmentPrefectureList.Callback, FragmentCityList.Callback {

    public static final String EXTRA_SELECTED_CITY = "selected_city";

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
                fragmentPrefectureList = FragmentPrefectureList.getInstance(prefectures);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, fragmentPrefectureList, "PrefectureListFragment")
                        .commit();

            } else if (exception != null) {
                Toast.makeText(PreferenceActivity.this, "Exception is occurred.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private FragmentPrefectureList fragmentPrefectureList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preference);

        if (savedInstanceState == null) {
            new LoadAreaListTask().execute();
        } else {
            fragmentPrefectureList = (FragmentPrefectureList) getSupportFragmentManager()
                    .findFragmentByTag("PrefectureListFragment");
        }
    }

    @Override
    public void onPrefectureSelected(GetAreaApi.Prefecture pref) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(pref.title)
                .replace(R.id.container, FragmentCityList.getInstance(pref.cityList))
                .commit();

    }

    @Override
    public void onCitySelected(GetAreaApi.Prefecture.City city) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SELECTED_CITY, city);
        setResult(RESULT_OK, intent);
        finish();
    }
}
