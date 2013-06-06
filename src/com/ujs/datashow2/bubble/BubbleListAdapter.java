package com.ujs.datashow2.bubble;

import java.util.ArrayList;
import java.util.HashMap;

import com.ujs.datashow2.R;
import com.ujs.datashow2.meidiarecorder.AudioPlayer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 显示接受到的消息的adapter
 * 
 * @author zouning
 * 
 */
public class BubbleListAdapter extends BaseAdapter implements OnClickListener {

    protected String tag = "BubbleListAdapter";
    private ArrayList<AFMessage> datas;
    private Context mContext;
    private AudioPlayer player = new AudioPlayer();

    public BubbleListAdapter(Context context, ArrayList<AFMessage> applist) {
        // TODO Auto-generated constructor stub
        datas = applist;
        mContext = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        AFMessage message = datas.get(position);
        int type = message.type;
        if (type == AFMessage.TYPE_INCOMING_TEXT) {
            ViewHolderText holderText = BubbleViewHolder
                    .getIncomingTextHolder(mContext);
            convertView = holderText.convertView;
            convertView.setTag(holderText);
            TextView tv = holderText.tvMessage;
            tv.setText(message.text);
        } else if (type == AFMessage.TYPE_OUTGOING_TEXT) {
            ViewHolderText holderText = BubbleViewHolder
                    .getOutgoingTextHolder(mContext);
            convertView = holderText.convertView;
            TextView textView = holderText.tvMessage;
            textView.setText(message.text);
        }else if (type == AFMessage.TYPE_INCOMING_AUDIO) {
            ViewHolderText holderText = BubbleViewHolder.getIncomingAudioHolder(mContext);
            convertView = holderText.convertView;
            TextView textView = holderText.tvMessage;
            textView.setText("          ");
            textView.setTag(R.id.textView_audio_incoming_context, message.text);
            textView.setOnClickListener(this);
        }

        return convertView;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v instanceof TextView) {
            Object audioPath = v.getTag(R.id.textView_audio_incoming_context);
            if (audioPath != null) {
                player.play((String)audioPath);
            }
        }
    }

}
