package com.ujs.datashow2.common;

import com.commonsware.cwac.cache.WebImageCache;

public class CommonUtil {
    // 获取google地图缩略图http://maps.google.com/maps/api/staticmap?markers=39.9409769,116.1966794&zoom=12&size=400x400&sensor=false
    //参考文档https://developers.google.com/maps/documentation/staticmaps/?hl=zh-CN#Sensor
    private static final String staticMapUrl = "http://maps.google.com/maps/api/staticmap?markers=%f,%f&zoom=12&size=%dx%d&sensor=false&language=zh-CN";
    
    
    public static String getStaticmapurl(double latitude,double longitude,int sizeWidth,int sizeHeight) {
        return String.format(staticMapUrl, latitude,longitude,sizeWidth,sizeHeight);
    }
    
}
