package com.ujs.datashow2.meidiarecorder;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import com.ujs.datashow2.R;
import com.ujs.datashow2.bubble.AFMessage;
import com.ujs.datashow2.bubble.BubbleListAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * 按住说话界面
 * 
 * @author zouning
 * 
 */
public class AudioActivity extends Activity implements OnLongClickListener,
        OnTouchListener{

    private static final String tag = "AudioActivity";

    private Button btn_audio;
    private Popup dialog;

    private static final int FLAG_LISTEN = 0;
    private static final int FLAG_TALK = 1;
    private int flag;

    private AudioRecorder recorder;

    private String AudioStorePath;

    private ArrayList<AFMessage> list;
    private String fileName;
    private ListView lvAudio;
    private BubbleListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio);
        dialog = new Popup(this);
        btn_audio = (Button) findViewById(R.id.button_audio_press);
        btn_audio.setOnLongClickListener(this);
        btn_audio.setOnTouchListener(this);

        AudioStorePath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/AfterWork";
        File dir = new File(AudioStorePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Log.d(tag, "audio store path:" + AudioStorePath);

        // 添加listview
        lvAudio = (ListView) findViewById(R.id.listView_audio);
        list = new ArrayList<AFMessage>();
        adapter = new BubbleListAdapter(this, list);
        lvAudio.setAdapter(adapter);
        lvAudio.setClickable(false);
    }

    @Override
    public boolean onLongClick(View v) {
        // TODO Auto-generated method stub
        dialog.show();
        this.flag = FLAG_TALK;
        Log.d(tag, "开始录音");

        recorder = new AudioRecorder(this);
        fileName = new Date().getTime() + "";
        recorder.recordAsAMR(AudioStorePath, fileName);
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction()) {
        case MotionEvent.ACTION_UP:
            flag = FLAG_LISTEN;
            Log.d(tag, "录音结束");
            recorder.stop();
            dialog.dismiss();

            // 添加到listview中
            AFMessage audioMessage = new AFMessage(AudioStorePath
                    + File.separator + fileName + ".amr",
                    AFMessage.TYPE_INCOMING_AUDIO);
            list.add(audioMessage);
            adapter.notifyDataSetChanged();
            break;

        default:
            break;
        }
        return false;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        // TODO Auto-generated method stub
        AFMessage audioMessage = list.get(position);
        AudioPlayer player = new AudioPlayer();
        player.play(audioMessage.text);
    }
}
