package com.ujs.datashow2.bubble;

import com.ujs.datashow2.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class BubbleViewHolder {
    private static final String tag = "BubbleViewHolder";

    private static final int IncomingTextItemLayoutId = R.layout.bubble_item;
    private static final int OutgoingTextItemLayoutId = R.layout.bubble_text_out_item;
    private static final int IncomingAudioItemLayoutId = R.layout.audio_bubble_incoming_item;
    
    private static LayoutInflater mInflater = null;

    public static ViewHolderText getIncomingTextHolder(Context context) {
        ViewHolderText incomingTextHolder = null;
        incomingTextHolder = new ViewHolderText();
        incomingTextHolder.convertView = getInflater(context).inflate(
                IncomingTextItemLayoutId, null);
        incomingTextHolder.tvMessage = (TextView) incomingTextHolder.convertView
                .findViewById(R.id.textView_bubble_context);
        return incomingTextHolder;
    }

    public static ViewHolderText getOutgoingTextHolder(Context context) {
        ViewHolderText outgoingTextHolder = null;
        outgoingTextHolder = new ViewHolderText();
        outgoingTextHolder.convertView = getInflater(context).inflate(
                OutgoingTextItemLayoutId, null);
        outgoingTextHolder.tvMessage = (TextView) outgoingTextHolder.convertView
                .findViewById(R.id.textView_bubble_out_text_context);

        return outgoingTextHolder;
    }
    
    public static ViewHolderText getIncomingAudioHolder(Context context) {
        ViewHolderText incomingAudioHolder = null;
        incomingAudioHolder = new ViewHolderText();
        incomingAudioHolder.convertView = getInflater(context).inflate(
                IncomingAudioItemLayoutId, null);
        incomingAudioHolder.tvMessage = (TextView) incomingAudioHolder.convertView
                .findViewById(R.id.textView_audio_incoming_context);
        
        return incomingAudioHolder;
    }

    private static LayoutInflater getInflater(Context context){
        if (mInflater == null) {
            mInflater = LayoutInflater.from(context);
        }
        return mInflater;
    }
}
