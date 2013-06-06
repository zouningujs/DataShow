package com.ujs.datashow2.common;

import java.io.File;

import com.commonsware.cwac.bus.SimpleBus;
import com.commonsware.cwac.cache.AsyncCache;
import com.commonsware.cwac.cache.WebImageCache;
import com.ujs.datashow2.R;

import android.app.Activity;
import android.graphics.drawable.Drawable;

public class BaseActivity extends Activity{
    private WebImageCache cache;
    private SimpleBus bus = new SimpleBus();
    private Drawable placeholder = null;
    
    protected synchronized WebImageCache getCache() {
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
