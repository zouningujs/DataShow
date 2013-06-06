package com.ujs.datashow2.proto;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.util.Log;

import ctxb.proto.BaseTypeProto.MessageBoardSummary;
import ctxb.proto.BaseTypeProto.PosterInfo;

public class MyProtoTest {
    private static final String tag = "MyProtoTest";
    
    private static String PROTO_TEST_URL = "http://10.0.2.2:8080/CTPassportServer/protoTest";

    public void test()  {
        try {
            PosterInfo info = PosterInfo.newBuilder().setMobileNumber("123").setPortraitUrl("123").build();
            
            HttpURLConnection connection = (HttpURLConnection) new URL(PROTO_TEST_URL).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            info.writeTo(connection.getOutputStream());
            
            MessageBoardSummary response = MessageBoardSummary.parseFrom(connection.getInputStream());
            Log.d(tag,"[id:" + response.getId() + ",version:" + response.getVersion() + "]");
            
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        
        
    }
}
