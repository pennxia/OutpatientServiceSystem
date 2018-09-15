package com.nobitastudio.materialdesign.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.bean.AllPayment;
import com.nobitastudio.materialdesign.util.HttpUtil;
import com.nobitastudio.materialdesign.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class TestActivity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        init();
    }

    private void init(){

        String requestAction = "getAllPayment";
        String paramenter = "&account=15709932234";
        HttpUtil.sendOkHttpRequest(requestAction, paramenter, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                AllPayment allPayment = new Gson().fromJson(response.body().string(),AllPayment.class);
                int a = 1;

            }
        });

    }

    private void initBaseData() {


    }


    @Override
    public void onClick(View v) {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            finish();
        }
        return true;
    }

}
