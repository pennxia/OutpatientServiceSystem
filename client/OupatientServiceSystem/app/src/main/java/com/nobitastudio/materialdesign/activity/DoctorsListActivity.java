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
import com.nobitastudio.materialdesign.bean.Doctor;
import com.nobitastudio.materialdesign.bean.DoctorWhetherHasNumber;
import com.nobitastudio.materialdesign.bean.OutpatientDepartment;
import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.adapter.DoctorsListActivityRecycleViewAdapter;
import com.nobitastudio.materialdesign.bean.User;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.HttpUtil;
import com.nobitastudio.materialdesign.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DoctorsListActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar actionBar;
    RecyclerView recyclerView;
    CardView networkCacheCardView;
    CardView errorCardView;
    TextView errorTextView;

    DoctorsListActivityRecycleViewAdapter adapter;

    List<Doctor> doctors;
    List<DoctorWhetherHasNumber> doctorWhetherHasNumbers;
    OutpatientDepartment outpatientDepartment;
    User user;
    Gson gson;

    static final String ACCESS_TIMEOUT = "网络连接超时";
    static final String DOCTOR_NO_EXIT = "未找到医信息";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);

        init();
    }

    private void init() {

        initBaseData();

        //init widget
        toolbar = findViewById(R.id.activity_doctor_list_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
        recyclerView = findViewById(R.id.activity_doctors_list_recyclerview);
        networkCacheCardView = findViewById(R.id.activity_doctors_list_network_cache_cardview);
        errorCardView = findViewById(R.id.activity_doctors_list_error_cardview);
        errorTextView = findViewById(R.id.activity_doctors_list_error_textview);

        //init adapter
        adapter = new DoctorsListActivityRecycleViewAdapter(doctors, doctorWhetherHasNumbers, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        //init doctors dates by outpatientDepartment id
        initDoctorsData();

    }

    private void initBaseData() {

        doctors = new ArrayList<>();
        doctorWhetherHasNumbers = new ArrayList<>();
        gson = new Gson();
        user = AppDataUtil.getUser();
        outpatientDepartment = AppDataUtil.getSelectedOutpatientDepartment();

        //update global DoctorsWhetherHaveNumber data.
        AppDataUtil.setDoctorWhetherHasNumbers(doctorWhetherHasNumbers);
        //update global doctors data.
        AppDataUtil.setDoctors(doctors);
    }

    private void initDoctorsData() {

        showNetworkCacheCardView();

        //get resource from server by intent date;if user can refresh,clear is needed;now,we only test,limit number of doctors to 50;
        //there we only need test.set the doc data.in reality,the doc data should be from server
        //get doctor data from server
        String requestAction = "getAllDoctorByDepartmentNo";
        String parameter = "&departmentNo=" + outpatientDepartment.getOutpatientDepartmentId();
        HttpUtil.sendOkHttpRequest(requestAction, parameter, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showErrorCardView(ACCESS_TIMEOUT);
                closeNetworkCacheCardView();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                List<Doctor> temDoctors = gson.fromJson(response.body().string(), new TypeToken<List<Doctor>>() {
                }.getType());
                //this method contain a wait thread let it here to let apdater update
                closeNetworkCacheCardView();
                if (temDoctors.size() > 0) {
                    for (int i = 0; i < temDoctors.size(); i++) {
                        doctors.add(temDoctors.get(i));
                    }
                    initDoctorsWhetherHaveNumber(doctors);
                    notifyAdapter();
                } else {
                    //can't find any doctor
                    showErrorCardView(DOCTOR_NO_EXIT);
                }

            }
        });

    }

    private void initDoctorsWhetherHaveNumber(List<Doctor> doctors) {

        for (int i = 0; i < doctors.size(); i++) {
            String requestAction = "queryDoctorWhetherHasNumber";
            String parameter = "&doctorId=" + doctors.get(i).getDoctorId();
            HttpUtil.sendOkHttpRequest(requestAction, parameter, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    showErrorCardView(ACCESS_TIMEOUT);
                    closeNetworkCacheCardView();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    DoctorWhetherHasNumber doctorWhetherHasNumber = gson.fromJson(response.body().string(), DoctorWhetherHasNumber.class);
                    doctorWhetherHasNumbers.add(doctorWhetherHasNumber);
                }
            });
        }

        //let thread wait,unitl doctorWhetherHasNumbers cache success.
        while (doctorWhetherHasNumbers.size() != doctors.size()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else {
            //chooce other menuitem;
        }
        return true;
    }

    private void notifyAdapter() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
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
                    Thread.sleep(1200);
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


}
