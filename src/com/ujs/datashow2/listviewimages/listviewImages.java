package com.ujs.datashow2.listviewimages;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.commonsware.cwac.bus.SimpleBus;
import com.commonsware.cwac.cache.AsyncCache;
import com.commonsware.cwac.cache.WebImageCache;
import com.ujs.datashow2.R;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class listviewImages extends Activity {
    private static final String tag = "listviewImages";
    private ListView lvImages;
    private WebImageCache cache = null;
    private SimpleBus bus = new SimpleBus();
    private Drawable placeholder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Log.d(tag, getCacheDir().getAbsolutePath());
        setContentView(R.layout.listview_images);
        lvImages = (ListView) findViewById(R.id.listView_images);
        WebImageCache cache = getCache();
        // cache.handleImageView(imageview, url, tag)
        HashMap<String, Object> url1 = new HashMap<String, Object>();
        url1.put("url",
                "http://img.4gdm.com/pic/201103/02/4gdm21777201103022304311.jpg");

        HashMap<String, Object> url2 = new HashMap<String, Object>();
        url2.put("url",
                "http://img.4gdm.com/pic/201103/02/4gdm21777201103022304311.jpg");

        HashMap<String, Object> url3 = new HashMap<String, Object>();
        url3.put(
                "url",
                "http://images5.fanpop.com/image/photos/30700000/clannad-and-clannad-after-story-lubasakura-30798158-800-500.jpg");

        HashMap<String, Object> url4 = new HashMap<String, Object>();
        url4.put("url",
                "http://www.17ehe.com/pic/201002/26/2010226101018775.jpg");

        HashMap<String, Object> url5 = new HashMap<String, Object>();
        url5.put("url",
                "http://animeguyspl0x.files.wordpress.com/2011/09/clannad-5.jpg");

        HashMap<String, Object> url6 = new HashMap<String, Object>();
        url6.put("url", "http://clannad.ffsky.cn/pic/cover/fullvoice.jpg");

        HashMap<String, Object> url7 = new HashMap<String, Object>();
        url7.put("url", "http://im.glogster.com/media/10/45/58/26/45582675.jpg");

        HashMap<String, Object> url8 = new HashMap<String, Object>();
        url8.put(
                "url",
                "http://images5.fanpop.com/image/photos/24700000/Clannad-Pics-clannad-and-clannad-after-story-24746585-1920-1200.jpg");

        ArrayList<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();
        datas.add(url1);
        datas.add(url2);
        datas.add(url3);
        datas.add(url4);
        datas.add(url5);
        datas.add(url6);
        datas.add(url7);
        datas.add(url8);

        MySimpleAdapter adapter = new MySimpleAdapter(this, datas,
                R.layout.list_images_item, new String[] { "url" },
                new int[] { R.id.imageView_image }, getCache());

        lvImages.setAdapter(adapter);

    }

    synchronized WebImageCache getCache() {
        if (cache == null) {
            placeholder = getResources().getDrawable(R.drawable.placeholder);
            cache = new WebImageCache(getCacheDir(), bus, policy, 101,
                    placeholder);
        }

        return (cache);
    }

    private AsyncCache.DiskCachePolicy policy = new AsyncCache.DiskCachePolicy() {
        public boolean eject(File file) {
            return (System.currentTimeMillis() - file.lastModified() > 1000
                    * 60 * 60 * 24 * 7);
        }
    };

}
