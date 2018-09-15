package com.nobitastudio.materialdesign.activity;

import android.support.design.widget.Snackbar;
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
import com.nobitastudio.materialdesign.adapter.MyDoctorActivityRecycleViewAdapter;
import com.nobitastudio.materialdesign.bean.ClientDoctor;
import com.nobitastudio.materialdesign.bean.MyDoctor;
import com.nobitastudio.materialdesign.bean.User;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyDoctorActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar actionBar;
    RecyclerView recyclerView;
    CardView networkCacheCardView;
    CardView errorCardView;
    TextView errorTextView;

    User user;
    Gson gson;
    List<ClientDoctor> clientDoctors;

    MyDoctorActivityRecycleViewAdapter adapter;

    int myDoctorSize;

    static final String ACCESS_TIMEOUT = "网络连接超时";
    static final String DONT_FIND_DOCTOR = "您还未收藏医生";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_doctor);

        init();
    }

    private void init() {

        initBaseData();

        //init widget
        toolbar = findViewById(R.id.activity_my_doctor_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(null);
        }

        recyclerView = findViewById(R.id.activity_my_doctor_recycleview);
        networkCacheCardView = findViewById(R.id.activity_my_doctor_network_cache_cardview);
        errorCardView = findViewById(R.id.activity_my_doctor_error_cardview);
        errorTextView = findViewById(R.id.activity_my_doctor_error_textview);

        //init adapter
        adapter = new MyDoctorActivityRecycleViewAdapter(this, clientDoctors);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        //init client doctors data
        initClientDoctor();
    }

    private void initBaseData() {
        clientDoctors = new ArrayList<>();
        user = AppDataUtil.getUser();
        gson = new Gson();

        myDoctorSize = AppDataUtil.getUser().getMyDoctors().size();

        //save data to global data
        AppDataUtil.setClientDoctors(clientDoctors);
    }

    private void initClientDoctor() {

        showNetworkCacheCardView();
        String requestAction = "queryAllClientDoctorByAccount";
        String parameter = "&account=" + user.getUserAccount();
        HttpUtil.sendOkHttpRequest(requestAction, parameter, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                closeNetworkCacheCardView();
                showErrorCardView(ACCESS_TIMEOUT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                List<ClientDoctor> clientDoctorsTemp = gson.fromJson(response.body().string(), new TypeToken<List<ClientDoctor>>() {
                }.getType());
                closeNetworkCacheCardView();
                if (clientDoctorsTemp.size() > 0) {
                    clientDoctors.addAll(clientDoctorsTemp);
                    notifyAdapter();
                } else {
                    //don't find the user's doctor
                    showErrorCardView(DONT_FIND_DOCTOR);
                }
            }
        });

    }

    public void showSnackbarLong(final View view, final String prompt, final String buttonText, final int selectedDoctor) {
        Snackbar.make(view, prompt, Snackbar.LENGTH_LONG)
                .setAction(buttonText, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //user choose cancel to collect the doctor.
                        String account = AppDataUtil.getUser().getUserAccount();
                        Integer doctorId = AppDataUtil.getClientDoctors().get(selectedDoctor).getDoctor().getDoctorId();
                        //remove the doctor from mydoctor in server
                        String requestAction = "removeDoctorFromMyDoctor";
                        String parameter = "&account=" + account + "&doctorId=" + doctorId;
                        HttpUtil.onlySendHttpRequest(requestAction, parameter);
                        //remove the doctor from mydoctor in local
                        for (int i = 0; i < AppDataUtil.getUser().getMyDoctors().size(); i++) {
                            MyDoctor myDoctor = AppDataUtil.getUser().getMyDoctors().get(i);
                            if (myDoctor.getDoctorId().equals(doctorId)) {
                                AppDataUtil.getUser().getMyDoctors().remove(i);
                                break;
                            }
                        }
                        myDoctorSize = AppDataUtil.getUser().getMyDoctors().size();
                        //update the recycleview adapter and UI.
                        showNetworkCacheCardView();
                        clientDoctors.clear();
                        notifyAdapter();
                        initClientDoctor();
                    }
                }).show();
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

    /**
     * if the user cancel collect the doctor at the nextactivity(doctorDetailsActivity),this activity must update in time.
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        if (myDoctorSize != AppDataUtil.getUser().getMyDoctors().size()) {
            showNetworkCacheCardView();
            clientDoctors.clear();
            notifyAdapter();
            initClientDoctor();
            myDoctorSize = AppDataUtil.getUser().getMyDoctors().size();
        }
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
