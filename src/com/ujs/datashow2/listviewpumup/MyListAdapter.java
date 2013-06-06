package com.ujs.datashow2.listviewpumup;

import java.util.ArrayList;
import java.util.HashMap;

import com.ujs.datashow2.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * 自定义adapter
 * 实现点击就在列表中弹出一个框
 * @author zouning
 *
 */
public class MyListAdapter extends BaseAdapter{
    
    private Context mContext;
    private int mLastPosition = -1;
    
    private LayoutInflater mInflater;
    private ArrayList<HashMap<String, Object>> list;
    private int layoutID;
    private String falg[];
    private int ItemIDs[];
    protected String tag = "MyListAdapter";
    
    public MyListAdapter(Context context, ArrayList<HashMap<String, Object>> applist,
            int layoutID, String flag[], int ItemIDs[]){
        mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = applist;
        this.layoutID = layoutID;
        this.falg = flag;
        this.ItemIDs = ItemIDs;
    }
    
    static class ViewHolder{
        TextView tvName ;
        TextView tvAge ;
        View hint;
        TextView tvHintYes;
        TextView tvHintNo;
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
        ViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(layoutID, null);
            holder = new ViewHolder();
            holder.tvAge = (TextView)convertView.findViewById(R.id.age);
            holder.tvName = (TextView)convertView.findViewById(R.id.name);
            holder.hint = convertView.findViewById(R.id.hint);
            holder.tvHintYes = (TextView)convertView.findViewById(R.id.tv_hint_yes);
            holder.tvHintNo = (TextView)convertView.findViewById(R.id.tv_hint_no);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        
        //设置数据
        holder.tvName.setText((String)list.get(position).get(falg[0]));
        holder.tvAge.setText((String)list.get(position).get(falg[1]));
        
        if (position == mLastPosition) {
            holder.hint.setVisibility(View.VISIBLE);
        }else {
            holder.hint.setVisibility(View.GONE);
        }
        
        return convertView;
    }
    
    /**
     * 单击列表选项,显示弹出框或者隐藏
     * @param view
     * @param position
     */
    public void changeHintVisable(View view,int position){

        if (position != mLastPosition) {
            mLastPosition = position;
        }else {
            mLastPosition = -1;
        }
        
        notifyDataSetChanged();//加了反而出错
    }

}
