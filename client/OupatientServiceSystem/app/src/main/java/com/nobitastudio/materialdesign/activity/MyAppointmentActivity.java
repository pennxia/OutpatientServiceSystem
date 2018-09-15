package com.nobitastudio.materialdesign.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.adapter.AllRegistrationRecycleViewAdapter;
import com.nobitastudio.materialdesign.bean.ClientRegistration;
import com.nobitastudio.materialdesign.bean.User;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.HttpUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyAppointmentActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar actionBar;
    RecyclerView myAppointmentRecycleView;
    CardView networkCacheCardView;
    CardView errorCardView;
    TextView errorTextView;

    User user;
    Gson gson;

    AllRegistrationRecycleViewAdapter adapter;

    static final String ACCESS_TIMEOUT = "网络连接超时";
    static final String DONT_FIND_REGISTER_RECORD = "未查找您的挂号记录";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointment);

        init();
    }

    private void init() {

        initBaseData();

        //init widget
        toolbar = findViewById(R.id.activity_myappointment_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(null);
        }
        myAppointmentRecycleView = findViewById(R.id.activity_myappointment_recyclerview);
        networkCacheCardView = findViewById(R.id.activity_myappointment_network_cache_cardview);
        errorCardView = findViewById(R.id.activity_myappointment_error_cardview);
        errorTextView = findViewById(R.id.activity_my_appointment_error_textview);

        //init adapter
        adapter = new AllRegistrationRecycleViewAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        myAppointmentRecycleView.setLayoutManager(gridLayoutManager);
        myAppointmentRecycleView.setAdapter(adapter);


        //update and display appointment data
        updateMyAppointment();

    }

    private void initBaseData() {
        user = AppDataUtil.getUser();
        gson = new Gson();
    }

    private void updateMyAppointment() {

        showNetworkCacheCardView();
        AppDataUtil.getClientRegistrations().clear();
        adapter.notifyDataSetChanged();
        String requestAction = "queryAllClientRegistrationByAccount";
        String parameter = "&account=" + AppDataUtil.getUser().getUserAccount();
        HttpUtil.sendOkHttpRequest(requestAction, parameter, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                closeNetworkCacheCardView();
                showErrorCardView(ACCESS_TIMEOUT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                List<ClientRegistration> clientRegistrations = gson.fromJson(response.body().string(), new TypeToken<List<ClientRegistration>>() {
                }.getType());
                closeNetworkCacheCardView();
                if (clientRegistrations.size() > 0) {
                    //find records
                    AppDataUtil.getClientRegistrations().addAll(clientRegistrations);
                    notifyAdapter();
                } else {
                    //dont find records
                    showErrorCardView(DONT_FIND_REGISTER_RECORD);
                }
            }
        });

    }

    private void showNetworkCacheCardView() {
        networkCacheCardView.setVisibility(View.VISIBLE);
    }

    /**
     * it must run at no mainthread
     */
    private void closeNetworkCacheCardView() {
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                networkCacheCardView.setVisibility(View.GONE);
            }
        });
    }

    private void showErrorCardView(final String alterInf) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                errorTextView.setText(alterInf);
                errorCardView.setVisibility(View.VISIBLE);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1600);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            errorCardView.setVisibility(View.GONE);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void notifyAdapter() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
        } else {
            finish();
        }
        return true;
    }


}
