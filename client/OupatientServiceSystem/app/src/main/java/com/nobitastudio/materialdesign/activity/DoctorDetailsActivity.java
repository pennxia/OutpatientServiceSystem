package com.nobitastudio.materialdesign.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.adapter.DoctorDetailsActivityRecycleViewAdapter;
import com.nobitastudio.materialdesign.bean.Doctor;
import com.nobitastudio.materialdesign.bean.MyDoctor;
import com.nobitastudio.materialdesign.bean.OutpatientDepartment;
import com.nobitastudio.materialdesign.bean.User;
import com.nobitastudio.materialdesign.bean.Visit;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.HttpUtil;
import com.nobitastudio.materialdesign.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DoctorDetailsActivity extends AppCompatActivity
        implements View.OnClickListener {

    Toolbar toolbar;
    ActionBar actionBar;
    CollapsingToolbarLayout collapsingToolbar;
    FloatingActionButton addCollectionFab;
    ImageView doctorImageView;
    RecyclerView recyclerView;
    TextView doctorDetailsIntroductionTextTextview;
    ImageView doctorDetailsIntroductionArrowTextview;
    TextView doctorDetailsIntroductionTextview;
    TextView doctorDetailsSpecialityTextTextview;
    ImageView doctorDetailsSpecialityArrowTextview;
    TextView doctorDetailsSpecialityTextview;
    TextView availableNumberTextView;
    ImageView availableNumberImageView;
    TextView allNumberTextView;
    ImageView allNumberImageView;
    CardView networkCacheCardView;
    CardView errorCardView;
    TextView errorTextView;

    DoctorDetailsActivityRecycleViewAdapter adapter;

    OutpatientDepartment selectedOutpatientDepartment;
    Doctor selectedDoctor;
    User user;
    Gson gson;
    List<Visit> visits;
    List<Visit> visitsDisplayed;
    List<Visit> visitsAvailable;


    boolean doctorIntroductionIsOpen;
    boolean doctorSpecialityIsOpen;
    boolean availableNumberIsChoosed;
    boolean doctorIsCollected;

    static final String ACCESS_TIMEOUT = "网络连接超时";
    static final String VISIT_NO_EXIT = "未找到出诊记录";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        init();
    }

    private void init() {

        //init basal data.
        initBasalData();

        //init widget
        toolbar = findViewById(R.id.activity_doctordetails_toolbar);
        collapsingToolbar = findViewById(R.id.activity_doctordetails_collapsing_toolbar);
        doctorImageView = findViewById(R.id.doctor_image_view);
        addCollectionFab = findViewById(R.id.activity_doctordetails_fab);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        doctorDetailsIntroductionTextTextview = findViewById(R.id.activity_doctordetails_introduction_text_textview);
        doctorDetailsIntroductionArrowTextview = findViewById(R.id.activity_doctordetails_introduction_arrow_imageview);
        doctorDetailsIntroductionTextview = findViewById(R.id.activity_doctordetails_introduction_textview);
        doctorDetailsSpecialityTextTextview = findViewById(R.id.activity_doctordetails_speciality_text_textview);
        doctorDetailsSpecialityArrowTextview = findViewById(R.id.activity_doctordetails_speciality_arrow_imageview);
        doctorDetailsSpecialityTextview = findViewById(R.id.activity_doctordetails_speciality_textview);
        availableNumberTextView = findViewById(R.id.activity_doctordetails_available_number_textview);
        availableNumberImageView = findViewById(R.id.activity_doctordetails_available_number_imageview);
        allNumberTextView = findViewById(R.id.activity_doctordetails_all_number_textview);
        allNumberImageView = findViewById(R.id.activity_doctordetails_all_number_imageview);
        networkCacheCardView = findViewById(R.id.activity_doctordetails_network_cache_cardview);
        errorCardView = findViewById(R.id.activity_doctordetails_error_cardview);
        errorTextView = findViewById(R.id.activity_doctordetails_error_textview);

        recyclerView = findViewById(R.id.activity_doctordetails_number_source_recycleview);

        //init widget data and normal data
        doctorIntroductionIsOpen = false;
        doctorSpecialityIsOpen = false;
        availableNumberIsChoosed = true;

        collapsingToolbar.setTitle(selectedDoctor.getDoctorName());
        Glide.with(this).load(R.drawable.ic_doctor).into(doctorImageView);
        if (selectedDoctor != null) {
            Glide.with(this).load(Utility.getDoctorImageRequestAddress(selectedDoctor.getDoctorId())).into(doctorImageView);
        }
        doctorDetailsIntroductionTextview.setText(selectedDoctor.getIntroduction());
        doctorDetailsSpecialityTextview.setText(selectedDoctor.getSpecialty());

        //let network cache appear
        showNetworkCacheCardView();

        //init adapter
        adapter = new DoctorDetailsActivityRecycleViewAdapter(DoctorDetailsActivity.this, visitsDisplayed);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(DoctorDetailsActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        //init listener
        addCollectionFab.setOnClickListener(this);
        doctorDetailsIntroductionTextTextview.setOnClickListener(this);
        doctorDetailsIntroductionArrowTextview.setOnClickListener(this);
        doctorDetailsSpecialityTextTextview.setOnClickListener(this);
        doctorDetailsSpecialityArrowTextview.setOnClickListener(this);
        availableNumberTextView.setOnClickListener(this);
        availableNumberImageView.setOnClickListener(this);
        allNumberTextView.setOnClickListener(this);
        allNumberImageView.setOnClickListener(this);

        //init normal data and widget data
        initNormalData();

        //init visit data
        initVisitData();
    }

    private void initBasalData() {

        visits = new ArrayList<>();
        visitsDisplayed = new ArrayList<>();
        visitsAvailable = new ArrayList<>();
        selectedOutpatientDepartment = AppDataUtil.getSelectedOutpatientDepartment();
        selectedDoctor = AppDataUtil.getSelectedDoctor();
        user = AppDataUtil.getUser();
        gson = new Gson();

        //save data to global visits data.
        AppDataUtil.setVisits(visits);
        //save data to global visitsAvailable data.
        AppDataUtil.setVisitsAvailable(visitsAvailable);
        //save data to global visitsDisplayed data.
        AppDataUtil.setVisitsDisplayed(visitsDisplayed);

    }

    private void initNormalData() {

        //init fab
        for (MyDoctor myDoctor : user.getMyDoctors()) {
            doctorIsCollected = false;
            if (myDoctor.getDoctorId().equals(selectedDoctor.getDoctorId())) {
                doctorIsCollected = true;
                break;
            }
        }
        if (doctorIsCollected) {
            addCollectionFab.setImageResource(R.drawable.ic_heart_red);
        } else {
            addCollectionFab.setImageResource(R.drawable.ic_heart_white);
        }
    }

    private void initVisitData() {
        String requestAction = "getAllVisitByDoctorId";
        String parameter = "&doctorId=" + selectedDoctor.getDoctorId();
        HttpUtil.sendOkHttpRequest(requestAction, parameter, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                closeNetworkCacheCardView();
                showErrorCardView(ACCESS_TIMEOUT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                List<Visit> tempVisit = gson.fromJson(response.body().string(), new TypeToken<List<Visit>>() {
                }.getType());
                closeNetworkCacheCardView();
                if (tempVisit.size() > 0) {
                    visits.addAll(tempVisit);
                    for (Visit visit : visits) {
                        if (visit.getLeftAmount().intValue() > 0) {
                            visitsAvailable.add(visit);
                            //init visitsDisplayed
                            visitsDisplayed.add(visit);
                        }
                    }
                    notifyAdapter();

                } else {
                    //can't find any the doctor's visit
                    showErrorCardView(VISIT_NO_EXIT);
                }
            }

        });
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.activity_doctordetails_fab) {
            if (doctorIsCollected) {
                //cancel collect doctor
                doctorIsCollected = false;
                addCollectionFab.setImageResource(R.drawable.ic_heart_white);
                String requestAction = "removeDoctorFromMyDoctor";
                String parameter = "&account=" + user.getUserAccount() + "&doctorId=" + selectedDoctor.getDoctorId();
                HttpUtil.onlySendHttpRequest(requestAction, parameter);
                for (int i = 0; i < user.getMyDoctors().size(); i++) {
                    if (user.getMyDoctors().get(i).getDoctorId().equals(selectedDoctor.getDoctorId()))
                        user.getMyDoctors().remove(i);
                }
                Utility.showToastLong(this, "你已取消收藏该医生");

            } else {
                //collect doctor
                doctorIsCollected = true;
                addCollectionFab.setImageResource(R.drawable.ic_heart_red);
                String requestAction = "addDoctorToMyDoctor";
                String parameter = "&account=" + user.getUserAccount() + "&doctorId=" + selectedDoctor.getDoctorId();
                HttpUtil.onlySendHttpRequest(requestAction, parameter);
                MyDoctor myDoctor = new MyDoctor();
                myDoctor.setAccount(Long.valueOf(user.getUserAccount()));
                myDoctor.setDoctorId(selectedDoctor.getDoctorId());
                user.getMyDoctors().add(myDoctor);
                Utility.showToastLong(this, "你已成功收藏该医生至“我的医生”中~");
            }


        } else if (viewId == R.id.activity_doctordetails_introduction_text_textview || viewId == R.id.activity_doctordetails_introduction_arrow_imageview) {
            //open or close doctor introduction
            if (doctorIntroductionIsOpen) {
                doctorDetailsIntroductionTextTextview.setText("展开");
                doctorDetailsIntroductionArrowTextview.setImageResource(R.drawable.ic_arrow_down);
                doctorDetailsIntroductionTextview.setSingleLine(true);

            } else {
                doctorDetailsIntroductionTextTextview.setText("收起");
                doctorDetailsIntroductionArrowTextview.setImageResource(R.drawable.ic_arrow_up);
                doctorDetailsIntroductionTextview.setSingleLine(false);
            }
            doctorIntroductionIsOpen = !doctorIntroductionIsOpen;

        } else if (viewId == R.id.activity_doctordetails_speciality_text_textview || viewId == R.id.activity_doctordetails_speciality_arrow_imageview) {
            //open or close doctor speciality
            if (doctorSpecialityIsOpen) {
                doctorDetailsSpecialityTextTextview.setText("展开");
                doctorDetailsSpecialityArrowTextview.setImageResource(R.drawable.ic_arrow_down);
                doctorDetailsSpecialityTextview.setSingleLine(true);

            } else {
                doctorDetailsSpecialityTextTextview.setText("收起");
                doctorDetailsSpecialityArrowTextview.setImageResource(R.drawable.ic_arrow_up);
                doctorDetailsSpecialityTextview.setSingleLine(false);
            }
            doctorSpecialityIsOpen = !doctorSpecialityIsOpen;

        } else if (viewId == R.id.activity_doctordetails_available_number_textview || viewId == R.id.activity_doctordetails_available_number_imageview) {
            // update data then notify adapter
            if (!availableNumberIsChoosed) {
                //init adapter data;
                visitsDisplayed.clear();
                for (Visit visit : visitsAvailable) {
                    visitsDisplayed.add(visit);
                    adapter.notifyDataSetChanged();
                    availableNumberIsChoosed = true;
                }

                //init widget
                availableNumberTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
                availableNumberImageView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                allNumberTextView.setTextColor(getResources().getColor(R.color.colorBlack));
                allNumberImageView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            }

        } else if (viewId == R.id.activity_doctordetails_all_number_textview || viewId == R.id.activity_doctordetails_all_number_imageview) {
            // update data then notify adapter
            if (visits != null) {
                if (availableNumberIsChoosed) {
                    //init adapter data;
                    visitsDisplayed.clear();
                    for (Visit visit : visits) {
                        visitsDisplayed.add(visit);
                        notifyAdapter();
                        availableNumberIsChoosed = false;
                    }
                    //init widget
                    availableNumberTextView.setTextColor(getResources().getColor(R.color.colorBlack));
                    availableNumberImageView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    allNumberTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
                    allNumberImageView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            // user choose comeback
            finish();
        } else {
            //user click other chioce
            finish();
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
