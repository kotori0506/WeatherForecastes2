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

public class FragmentPrefectureList extends Fragment implements AdapterView.OnItemClickListener {

    public static final String KEY_LIST = "key_list";

    private Callback callback;

    public interface Callback {
        public void onPrefectureSelected(GetAreaApi.Prefecture pref);
    }

    private ArrayList<GetAreaApi.Prefecture> prefList;

    private final BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return prefList.size();
        }

        @Override
        public Object getItem(int position) {
            return prefList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GetAreaApi.Prefecture pref = (GetAreaApi.Prefecture) getItem(position);
            TextView textView = new TextView(getActivity());
            textView.setText(pref.title);
            return textView;
        }
    };

    public static FragmentPrefectureList getInstance(ArrayList<GetAreaApi.Prefecture> prefList) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_LIST, prefList);

        FragmentPrefectureList fragment = new FragmentPrefectureList();
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

        prefList = (ArrayList<GetAreaApi.Prefecture>) getArguments().getSerializable(KEY_LIST);
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
        GetAreaApi.Prefecture pref = (GetAreaApi.Prefecture) adapter.getItem(position);
        callback.onPrefectureSelected(pref);

    }

}
