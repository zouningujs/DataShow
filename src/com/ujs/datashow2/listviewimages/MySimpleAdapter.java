package com.ujs.datashow2.listviewimages;

import java.util.ArrayList;
import java.util.HashMap;

import com.commonsware.cwac.cache.WebImageCache;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MySimpleAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<HashMap<String, Object>> list;
    private int layoutID;
    private String falg[];
    private int ItemIDs[];
    protected String tag = "MySimpleAdapter";
    private Context mContext;
    private WebImageCache cache;

    public MySimpleAdapter(Context context, ArrayList<HashMap<String, Object>> applist,
            int layoutID, String flag[], int ItemIDs[],WebImageCache cache) {
        // TODO Auto-generated constructor stub
        mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = applist;
        this.layoutID = layoutID;
        this.falg = flag;
        this.ItemIDs = ItemIDs;
        this.cache = cache;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        convertView = mInflater.inflate(layoutID, null);
        for (int i = 0; i < falg.length; ++i) {
            if (convertView.findViewById(ItemIDs[i]) instanceof ImageView) {
                //对image是resource 或者是 drawable情况的处理
                ImageView iv = (ImageView) convertView.findViewById(ItemIDs[i]);
                String url = (String) list.get(position).get(falg[i]);
                try {
                    cache.handleImageView(iv, url, "cache");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.e(tag, e.getMessage());
                }
            } else if (convertView.findViewById(ItemIDs[i]) instanceof TextView) {
                TextView tv = (TextView) convertView.findViewById(ItemIDs[i]);
                tv.setText(((String) list.get(position).get(falg[i])));
            }
        }
        return convertView;
    }

    
    
    
    
    
    
}
