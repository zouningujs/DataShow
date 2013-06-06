package com.ujs.datashow2.pullrefresh;

import java.util.ArrayList;
import java.util.List;

import com.ujs.datashow2.R;
import com.ujs.datashow2.pullrefresh.MyListViewTwo.OnRefreshListener;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

    private List<String> data;
    private BaseAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pullrefresh);

        data = new ArrayList<String>();
        data.add("a");
        data.add("b");
        data.add("c");

        final MyListViewTwo listView = (MyListViewTwo) findViewById(R.id.listView);
        adapter = new BaseAdapter() {
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = new TextView(getApplicationContext());
                tv.setText(data.get(position));
                return tv;
            }

            public long getItemId(int position) {
                return 0;
            }

            public Object getItem(int position) {
                return null;
            }

            public int getCount() {
                return data.size();
            }
        };
        listView.setAdapter(adapter);

        listView.setonRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                new AsyncTask<Void, Void, Void>() {
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        data.add("add a new item");
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        adapter.notifyDataSetChanged();
                        listView.onRefreshComplete();
                    }

                }.execute();
            }
        });
    }
}