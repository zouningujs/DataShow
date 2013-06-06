package com.ujs.datashow2.map;

import com.google.android.maps.MapActivity;
import com.ujs.datashow2.R;
import com.ujs.datashow2.common.BaseActivity;
import com.ujs.datashow2.common.CommonUtil;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

/*
 * <uses-permission android:name="android.permission.INTERNET"/>
 *<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
 *<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
 *测试用debug.keystore MD5 2A:96:0C:06:03:BB:1C:06:84:D8:4E:D7:46:BD:23:05
 *api key 0kIq8n8npqI4Z-rRaROim_160wjHhpuUNuxP90w
 *到https://developers.google.com/android/maps-api-signup?hl=zh-CN注册
 */
public class MapMain extends BaseActivity implements LocationListener {

    private static final String MAP_URL = "http://gmaps-samples.googlecode.com/svn/trunk/articles-android-webmap/simple-android-map.html";
    private static final String tag = "MapMain";
    private WebView webView;
    private Location mostRecentLocation;
    private boolean errorFlag = false;
    private ImageView ivSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_main);
        webView = (WebView) findViewById(R.id.webView1);
        ivSite = (ImageView)findViewById(R.id.imageView_site_img);
        getLocation();
        setupWebView();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        try {
            String siteUrl = CommonUtil.getStaticmapurl(39.9409769,116.1966794, 150, 100);
            Log.d(tag, "url:" + siteUrl);
            getCache().handleImageView(ivSite, siteUrl, "cache");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            errorFlag = true;
            return;
        }
        Criteria criteria = new Criteria();
        if (criteria == null) {
            errorFlag = true;
            return;
        }
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = locationManager.getBestProvider(criteria, true);
        // In order to make sure the device is getting the location, request
        // updates.
        if (provider == null) {
            Log.e(tag, "provider null");
            errorFlag = true;
            return;
        }
        locationManager.requestLocationUpdates(provider, 1, 0, this);
        mostRecentLocation = locationManager.getLastKnownLocation(provider);
    }

    private void setupWebView() {
        if (errorFlag) {
            return;
        }
        final String centerURL = "javascript:centerAt("
                + mostRecentLocation.getLatitude() + ","
                + mostRecentLocation.getLongitude() + ")";
        webView.getSettings().setJavaScriptEnabled(true);
        // Wait for the page to load then send the location information
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url){
              webView.loadUrl(centerURL);
            }
          });
        Log.d(tag, "Ladutude:" + mostRecentLocation.getLatitude() + " Longitude:" + mostRecentLocation.getLongitude());
        webView.loadUrl(MAP_URL);
        /** Allows JavaScript calls to access application resources **/
//        webView.addJavascriptInterface(new JavaScriptInterface(), "android");
    }

    /**
     * Sets up the interface for getting access to Latitude and Longitude data
     * from device
     **/
    private class JavaScriptInterface {
        public double getLatitude() {
            return mostRecentLocation.getLatitude();
        }

        public double getLongitude() {
            return mostRecentLocation.getLongitude();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

}
