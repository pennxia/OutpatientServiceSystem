package com.nobitastudio.materialdesign.util;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {

    static final String SERVERADDRESS = "http://www.nobitastudio.cn/myweb/";
    static final String TESTADDRESS = "http://guolin.tech/api/china";
    static final String SERVERADDRESS2 = "http://www.nobitastudio.cn/";
    static final String SERVERADDRESS3 = "http://www.nobitastudio.cn/nobitaweb/";

    static final String TYPE = ".action?type=android";

    public static void sendOkHttpRequest(String requstAction,okhttp3.Callback callback ){
        sendOkHttpRequest(requstAction,"",callback);
    }

    public static void sendOkHttpRequest(String requstAction,String parameter,okhttp3.Callback callback ){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(SERVERADDRESS + requstAction + TYPE + parameter).build();
        client.newCall(request).enqueue(callback);
    }

    public static void onlySendHttpRequest(String requstAction,String parameter){
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url(SERVERADDRESS + requstAction + TYPE + parameter + "&needResponse=no").build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //below will run at mainactivity,so must add it to sub thread
                    client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void sendTestOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }


}
