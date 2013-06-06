package com.ujs.datashow2.map;

import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.ujs.datashow2.R;

public class Map2 extends MapActivity {
    private MapView mapView = null;
    private MapController mc;

    @Override
    protected void onCreate(Bundle icicle) {
        // TODO Auto-generated method stub
        super.onCreate(icicle);
        setContentView(R.layout.map2);
        mapView = (MapView)findViewById(R.id.mapview);
        mc = mapView.getController();
        mapView.setTraffic(true); //
        mapView.setSatellite(false);
        mapView.setStreetView(true);
      //GeoPoint gp = new GeoPoint((int)(39.269259  1000000), (int)(115.255762  1000000));//yixian
        GeoPoint gp = new GeoPoint((int)(39.951000000), (int)(116.371000000));//beijing
        //mc.animateTo(gp);
        //mc.setZoom(12);
        mc.setCenter(gp);
        //to display zoom control in MapView
        mapView.setBuiltInZoomControls(true);
    }

    @Override
    protected boolean isRouteDisplayed() {
        // TODO Auto-generated method stub
        return false;
    }

}
