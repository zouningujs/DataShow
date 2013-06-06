package com.ujs.datashow2.bubble;

import java.util.ArrayList;

import com.ujs.datashow2.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * 气泡式显示消息列表
 * 
 * <pre>
 * listview添加下面属性让列表显示最后一项
 * android:stackFromBottom="true"
 * android:transcriptMode="alwaysScroll"
 * android:layout_above="@+id/bubble_input"
 * </pre>
 * 
 * @author zouning
 * 
 */
public class Bubble extends Activity implements OnClickListener {

    private ListView lvDatas;
    private EditText etText;
    private Button btnSend;

    private BubbleListAdapter adapter;
    private ArrayList<AFMessage> datas;

    private boolean flagMsg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bubble);

        lvDatas = (ListView) findViewById(R.id.listView_bubble);
        etText = (EditText) findViewById(R.id.editText_bubble_input);
        btnSend = (Button) findViewById(R.id.button_bubble_send);

        btnSend.setOnClickListener(this);

        datas = new ArrayList<AFMessage>();

        AFMessage message1 = new AFMessage();
        message1.type = AFMessage.TYPE_INCOMING_TEXT;
        message1.text = "zou";

        datas.add(message1);

        adapter = new BubbleListAdapter(this, datas);

        lvDatas.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        String text = etText.getText().toString();
        if (flagMsg) {
            AFMessage message = new AFMessage(text,
                    AFMessage.TYPE_INCOMING_TEXT);
            datas.add(message);
        } else {
            AFMessage message = new AFMessage(text,
                    AFMessage.TYPE_OUTGOING_TEXT);
            datas.add(message);
        }
        flagMsg = !flagMsg;
        adapter.notifyDataSetChanged();
        etText.setText("");
    }

}
