package com.ujs.datashow2;

import java.io.File;
import java.util.ArrayList;

import com.ujs.datashow2.R.id;
import com.ujs.datashow2.bubble.Bubble;
import com.ujs.datashow2.jni.MyJni;
import com.ujs.datashow2.jni.MyJni2;
import com.ujs.datashow2.listviewimages.listviewImages;
import com.ujs.datashow2.listviewpumup.ListViewPupup;
import com.ujs.datashow2.map.Map2;
import com.ujs.datashow2.map.MapMain;
import com.ujs.datashow2.meidiarecorder.AudioActivity;
import com.ujs.datashow2.meidiarecorder.AudioPlayer;
import com.ujs.datashow2.meidiarecorder.AudioRecorder;
import com.ujs.datashow2.multiContacts.CopyContactsListMultiple;
import com.ujs.datashow2.notifywin.NotifyUtil;
import com.ujs.datashow2.preference.ListPreference;
import com.ujs.datashow2.proto.MyProtoTest;
import com.ujs.datashow2.pullrefresh.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/**
 * <pre>
 * 各种小知识点的集合
 * 知识点主要从精通Android 3 书中整理出来
 * </pre>
 * @author zouning
 *
 */ 
public class DataShow2Activity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    private TextView tvMsg ;
    
    private static final int Code_multl_contact = 100;
    private static AudioRecorder recorder;
    private final Context mContext = DataShow2Activity.this;
    
    private Button btnRecordStart;
    private Button btnRecordStop;

    private static final String tag = "DataShow2Activity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tvMsg = (TextView)findViewById(R.id.textView_msg);
        findViewById(R.id.button_preference).setOnClickListener(this);
        findViewById(R.id.button_clear).setOnClickListener(this);
        findViewById(R.id.button_readPreference).setOnClickListener(this);
        findViewById(R.id.button_multiContacts).setOnClickListener(this);
        btnRecordStart = (Button)findViewById(R.id.button_recoder);
        btnRecordStart.setOnClickListener(this);
        btnRecordStop = (Button)findViewById(R.id.button_recoder_stop);
        btnRecordStop.setOnClickListener(this);
        btnRecordStop.setEnabled(false);
        findViewById(R.id.button_play).setOnClickListener(this);
        findViewById(R.id.button_notify).setOnClickListener(this);
        findViewById(R.id.button_listview).setOnClickListener(this);
        findViewById(R.id.button_prototest).setOnClickListener(this);
        findViewById(R.id.button_listview_images).setOnClickListener(this);
        findViewById(R.id.button_audio_activity).setOnClickListener(this);
        findViewById(R.id.button_bubble).setOnClickListener(this);
        findViewById(R.id.button_pullrefresh).setOnClickListener(this);
        findViewById(R.id.button_map).setOnClickListener(this);
        findViewById(R.id.button_map2).setOnClickListener(this);
        findViewById(R.id.btn_jni).setOnClickListener(this);
        findViewById(R.id.btn_jni2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        if (id == R.id.button_preference) {
            Intent intent = new Intent(this,ListPreference.class);
            startActivity(intent);
        }else if (id == R.id.button_clear) {
            tvMsg.setText("");
        }else if (id == R.id.button_readPreference) {
            //读取首选项配置
            //SharedPreferences pref = getSharedPreferences("com.ujs.datashow2", 0);
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            //android:key的值
            String listPrefValue = pref.getString(getString(R.string.selected_flight_sort_option), "0");
            tvMsg.setText(listPrefValue);
        }else if (id == R.id.button_multiContacts) {
            Intent intent = new Intent();
            intent.setClass(this, CopyContactsListMultiple.class);
            startActivityForResult(intent, Code_multl_contact);
        }else if (id == R.id.button_recoder) {
            getRecorder(mContext).recordAsAMR(Environment.getExternalStorageDirectory().getAbsolutePath(),"sound");
            btnRecordStart.setEnabled(false);
            btnRecordStop.setEnabled(true);
        }else if (id == R.id.button_recoder_stop) {
            getRecorder(mContext).stop();
            btnRecordStart.setEnabled(true);
            btnRecordStop.setEnabled(false);
        }else if (id == R.id.button_play) {
            AudioPlayer player = new AudioPlayer();
            player.play(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "sound.amr");
        }else if (id == R.id.button_notify) {
            NotifyUtil.notify(DataShow2Activity.this);
        }else if (id == R.id.button_listview) {
            Intent intent = new Intent(this,ListViewPupup.class);
            startActivity(intent);
        }else if (id == R.id.button_prototest) {
            MyProtoTest proto = new MyProtoTest();
            proto.test();
        }else if (id == R.id.button_listview_images) {
            Intent intent = new Intent(this,listviewImages.class);
            startActivity(intent);
        }else if (id == R.id.button_audio_activity) {
            Intent intent = new Intent(this,AudioActivity.class);
            startActivity(intent);
        }else if (id == R.id.button_bubble) {
            Intent intent = new Intent(this,Bubble.class);
            startActivity(intent);
        }else if (id == R.id.button_pullrefresh) {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }else if (id == R.id.button_map) {
            Intent intent = new Intent(this,MapMain.class);
            startActivity(intent);
        }else if (id == R.id.button_map2) {
            Intent intent = new Intent(this,Map2.class);
            startActivity(intent);
        }else if (id == R.id.btn_jni) {
            Intent intent = new Intent(this,MyJni.class);
            startActivity(intent);
        }else if (id == R.id.btn_jni2) {
            Intent intent = new Intent(this,MyJni2.class);
            startActivity(intent);
        }
        
        
        
        
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Code_multl_contact) {
            if (data == null) {
                Log.e(tag, "data returned is null");
                return;
            }
            Bundle bundle = data.getExtras();
            ArrayList<String> contactsSelected = bundle.getStringArrayList("GET_CONTACT");
            if (contactsSelected == null || contactsSelected.size() == 0) {
                Log.w(tag, "没有选择联系人");
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (String contact:contactsSelected) {
                String[] infos = contact.split("\n");
                if (infos != null && infos.length == 2) {
                    sb.append("name:" + infos[0]);
                    sb.append("phone:" + infos[1]);
                    sb.append("\n");
                }else {
                    sb.append(contact);
                }
                infos = null;
            }
            tvMsg.setText(sb.toString());
        }
    }
    
    private static AudioRecorder getRecorder(Context context){
        if(recorder == null){
            recorder = new AudioRecorder(context);
        }
        return recorder;
    }
}