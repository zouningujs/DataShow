package com.ujs.datashow2.listviewpumup;

import java.util.ArrayList;
import java.util.HashMap;

import com.ujs.datashow2.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * 点击listview的一项,在当前项和下一项插入一个菜单的效果
 * 
 * @author zouning
 * 
 */
public class ListViewPupup extends Activity implements OnItemClickListener {
    private static final String tag = "ListViewPupup";
    private ListView listView;
    MyListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_popup);

        listView = (ListView) findViewById(R.id.listView_popup);
        ArrayList<HashMap<String, Object>> users = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 10; ++i) {
            HashMap<String, Object> user = new HashMap<String, Object>();
            user.put("username", "姓名(" + i + ")");
            user.put("age", (20 + i) + "");
            users.add(user);
        }

        adapter = new MyListAdapter(this, users,
                R.layout.list_item, new String[] { "username", "age" },
                new int[] { R.id.name, R.id.age });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        // TODO Auto-generated method stub
        adapter.changeHintVisable(view, position);
    }

}
