package io.keiji.weatherforecasts;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FragmentCityList extends Fragment implements AdapterView.OnItemClickListener {
    public static final String KEY_LIST = "key_list";

    private Callback callback;

    public interface Callback {
        public void onCitySelected(GetAreaApi.Prefecture.City city);
    }

    private ArrayList<GetAreaApi.Prefecture.City> cityList;

    private final BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return cityList.size();
        }

        @Override
        public Object getItem(int position) {
            return cityList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GetAreaApi.Prefecture.City pref = (GetAreaApi.Prefecture.City) getItem(position);
            TextView textView = new TextView(getActivity());
            textView.setText(pref.title);
            return textView;
        }
    };

    public static FragmentCityList getInstance(ArrayList<GetAreaApi.Prefecture.City> cityList) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_LIST, cityList);

        FragmentCityList fragment = new FragmentCityList();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof Callback) {
            callback = (Callback) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cityList = (ArrayList<GetAreaApi.Prefecture.City>) getArguments().getSerializable(KEY_LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ListView listView = new ListView(getActivity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return listView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GetAreaApi.Prefecture.City city = (GetAreaApi.Prefecture.City) adapter.getItem(position);
        callback.onCitySelected(city);
    }
}
