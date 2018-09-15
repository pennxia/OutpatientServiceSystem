package com.nobitastudio.materialdesign.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.activity.HealthNewsActivity;
import com.nobitastudio.materialdesign.activity.MainActivity;
import com.nobitastudio.materialdesign.activity.MyAppointmentActivity;
import com.nobitastudio.materialdesign.activity.MyElectronicCaseActivity;
import com.nobitastudio.materialdesign.activity.MyMedicalCardsActivity;
import com.nobitastudio.materialdesign.activity.NavigationHospitalGuideActivity;
import com.nobitastudio.materialdesign.activity.OutpatientDepartmentActivity;
import com.nobitastudio.materialdesign.activity.OutpatientPaymentActivity;
import com.nobitastudio.materialdesign.activity.TestActivity;
import com.nobitastudio.materialdesign.adapter.HealthNewsRecycleViewAdapter;
import com.nobitastudio.materialdesign.adapter.HospitalActivityUltraPagerAdapter;
import com.nobitastudio.materialdesign.bean.HealthNews;
import com.nobitastudio.materialdesign.bean.HospitalActivity;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraDepthScaleTransformer;

import java.util.ArrayList;
import java.util.List;

public class HospitalHomeFragment extends Fragment implements View.OnClickListener {

    MainActivity activity;

    RecyclerView recyclerView;
    UltraViewPager ultraViewPager;
    LinearLayout appointmentRegisterLinearlayout;
    LinearLayout outpatientPayLinearlayout;
    LinearLayout medicalCardLinearlayout;
    LinearLayout navigationGuideLinearlayout;
    LinearLayout smartFindVisitLinearlayout;
    LinearLayout onlineConsultingLinearlayout;
    LinearLayout testLinearlayout;
    LinearLayout registerDetailsLinearlayout;
    LinearLayout electronicRecordsLinearlayout;
    LinearLayout payDetailsLinearlayout;
    LinearLayout healthNewsLinearlayout;
    TextView headlinesTextView;
    ImageView headlinesImageView;
    TextView lectureTextView;
    ImageView lectureImageView;

    PagerAdapter pagerAdapter;
    HealthNewsRecycleViewAdapter recycleViewAdapter;

    List<HospitalActivity> hospitalActivityList;
    List<HealthNews> healthNewsHeadlinesList;
    List<HealthNews> healthNewsLectureList;
    List<HealthNews> displayHealthNewsList;
    Context mContext;
    Gson gson;

    static final int HEALTH_NEWS_HEADLINES_NO = 4001;
    static final int HEALTH_NEWS_Lecture_NO = 4002;
    static int CURRENT_ADAPTER_NO;

    private static int MAIN_ACITIVITY_NO = 5001; // references mainActivity
    private static int HOSPIALHOME_FRAGMENT_NO = 5003; // references HospitalHomeFragment

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initBaseData();
        if (mContext == null) {
            mContext = container.getContext();
        }
        View view = inflater.inflate(R.layout.fragment_hospitalhome, container, false);
        activity = (MainActivity) getActivity();
        initWidget(view);
        initViewPager();
        initRecycleView();
        return view;
    }

    private void initBaseData() {
        CURRENT_ADAPTER_NO = HEALTH_NEWS_HEADLINES_NO;
        gson = new Gson();
        hospitalActivityList = AppDataUtil.getHospitalActivityList();
        healthNewsHeadlinesList = AppDataUtil.getHealthNewsHeadlinesList();
        healthNewsLectureList = AppDataUtil.getHealthNewsLectureList();
        displayHealthNewsList = new ArrayList<>();
        displayHealthNewsList.addAll(healthNewsHeadlinesList);
    }

    private void initWidget(View view) {
        recyclerView = view.findViewById(R.id.fragment_hospitalhome_recyclerview);
        ultraViewPager = view.findViewById(R.id.fragment_hospital_home_ultraviewpager);
        appointmentRegisterLinearlayout = view.findViewById(R.id.fragment_hospitalhome_appointmentregister_linearlayout);
        outpatientPayLinearlayout = view.findViewById(R.id.fragment_hospitalhome_outpatientpay_linearlayout);
        medicalCardLinearlayout = view.findViewById(R.id.fragment_hospitalhome_medicalcard_linearlayout);
        navigationGuideLinearlayout = view.findViewById(R.id.fragment_hospitalhome_navigationguide_linearlayout);
        smartFindVisitLinearlayout = view.findViewById(R.id.fragment_hospitalhome_smartfindvisit_linearlayout);
        onlineConsultingLinearlayout = view.findViewById(R.id.fragment_hospitalhome_onlineconsulting_linearlayout);
        testLinearlayout = view.findViewById(R.id.fragment_hospitalhome_test_linearlayout);
        registerDetailsLinearlayout = view.findViewById(R.id.fragment_hospitalhome_registerdetails_linearlayout);
        electronicRecordsLinearlayout = view.findViewById(R.id.fragment_hospitalhome_electronicrecords_linearlayout);
        payDetailsLinearlayout = view.findViewById(R.id.fragment_hospitalhome_paydetails_linearlayout);
        healthNewsLinearlayout = view.findViewById(R.id.fragment_hospitalhome_healthnews_linearlayout);
        headlinesTextView = view.findViewById(R.id.fragment_hospitalhome_headlines_textview);
        headlinesImageView = view.findViewById(R.id.fragment_hospitalhome_headlines_imageview);
        lectureTextView = view.findViewById(R.id.fragment_hospitalhome_lecture_textview);
        lectureImageView = view.findViewById(R.id.fragment_hospitalhome_lecture_imageview);

        initWidgetListener();
    }

    private void initWidgetListener() {
        appointmentRegisterLinearlayout.setOnClickListener(this);
        outpatientPayLinearlayout.setOnClickListener(this);
        medicalCardLinearlayout.setOnClickListener(this);
        navigationGuideLinearlayout.setOnClickListener(this);
        smartFindVisitLinearlayout.setOnClickListener(this);
        onlineConsultingLinearlayout.setOnClickListener(this);
        testLinearlayout.setOnClickListener(this);
        registerDetailsLinearlayout.setOnClickListener(this);
        electronicRecordsLinearlayout.setOnClickListener(this);
        payDetailsLinearlayout.setOnClickListener(this);
        healthNewsLinearlayout.setOnClickListener(this);
        headlinesTextView.setOnClickListener(this);
        headlinesImageView.setOnClickListener(this);
        lectureTextView.setOnClickListener(this);
        lectureImageView.setOnClickListener(this);
    }

    private void initViewPager() {
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        pagerAdapter = new HospitalActivityUltraPagerAdapter(true, activity, hospitalActivityList);
        ultraViewPager.setAdapter(pagerAdapter);
        ultraViewPager.setMultiScreen(0.75f);
        ultraViewPager.setItemRatio(1.0f);
        ultraViewPager.setPageTransformer(false, new UltraDepthScaleTransformer());
        ultraViewPager.setInfiniteLoop(true);
        ultraViewPager.setAutoScroll(4000);

        //init indicator.
        ultraViewPager.initIndicator();

        //set style of indicators
        ultraViewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.parseColor("#32B9AA"))
                .setNormalColor(Color.WHITE)
                .setMargin(0, 0, 0, 20)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));

        //set the alignment
        ultraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        //construct built-in indicator, and add it to  UltraViewPager
        ultraViewPager.getIndicator().build();
    }

    private void initRecycleView() {
        recycleViewAdapter = new HealthNewsRecycleViewAdapter(activity, displayHealthNewsList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(recycleViewAdapter);
    }


    @Override
    public void onClick(View v) {

        int viewId = v.getId();
        if (viewId == R.id.fragment_hospitalhome_appointmentregister_linearlayout) {
            //the user click the first icon appointmentregister,jump to OutpatientDepartmentActivity
            AppDataUtil.updateEntranceActivityNo(MAIN_ACITIVITY_NO);
            Intent intent = new Intent(activity, OutpatientDepartmentActivity.class);
            startActivity(intent);

        } else if (viewId == R.id.fragment_hospitalhome_outpatientpay_linearlayout) {
            //the user click the second icon outpatientpay,jump to outpatientPayActivity
            Intent intent = new Intent(activity, OutpatientPaymentActivity.class);
            startActivity(intent);

        } else if (viewId == R.id.fragment_hospitalhome_medicalcard_linearlayout) {
            //the user click the third icon,jump to myMedicalCardsActivity
            AppDataUtil.updateEntranceActivityNo(MAIN_ACITIVITY_NO);
            Intent intent = new Intent(activity, MyMedicalCardsActivity.class);
            startActivity(intent);

        } else if (viewId == R.id.fragment_hospitalhome_navigationguide_linearlayout) {
            //the user click the fourth icon，jump to navigationGuideActivity
            AppDataUtil.updateEntranceActivityNo(HOSPIALHOME_FRAGMENT_NO);
            Intent intent = new Intent(activity, NavigationHospitalGuideActivity.class);
            startActivity(intent);

        } else if (viewId == R.id.fragment_hospitalhome_smartfindvisit_linearlayout) {
            //the user click the fiveth icon，jump to smartFindVisitActivity

        } else if (viewId == R.id.fragment_hospitalhome_onlineconsulting_linearlayout) {
            //the user click the sixth icon，jump to onlineConsultingActivity

        } else if (viewId == R.id.fragment_hospitalhome_test_linearlayout) {
            //this is test icon
            Intent intent = new Intent(activity, TestActivity.class);
            startActivity(intent);

        } else if (viewId == R.id.fragment_hospitalhome_registerdetails_linearlayout) {
            //the user click the seventh icon，jump to registerDetailsActivity
            Intent intent = new Intent(activity, MyAppointmentActivity.class);
            startActivity(intent);

        } else if (viewId == R.id.fragment_hospitalhome_electronicrecords_linearlayout) {
            //the user click the eightth icon，jump to ElectronicCaseActivity
            Intent intent = new Intent(activity, MyElectronicCaseActivity.class);
            startActivity(intent);

        } else if (viewId == R.id.fragment_hospitalhome_paydetails_linearlayout) {
            //the user click the nineth icon，jump to payDetailsActivity


        } else if (viewId == R.id.fragment_hospitalhome_healthnews_linearlayout) {
            //the user click the tenth icon，jump to healthNewsActivity
            Intent intent = new Intent(activity, HealthNewsActivity.class);
            startActivity(intent);

        } else if (viewId == R.id.fragment_hospitalhome_headlines_textview || viewId == R.id.fragment_hospitalhome_headlines_imageview) {
            //recycleview show healthnews
            if (CURRENT_ADAPTER_NO != HEALTH_NEWS_HEADLINES_NO){
                showHealthNewsList(HEALTH_NEWS_HEADLINES_NO);
            }
        } else if (viewId == R.id.fragment_hospitalhome_lecture_textview || viewId == R.id.fragment_hospitalhome_lecture_imageview) {
            //recycleview show doctorclass
            if (CURRENT_ADAPTER_NO != HEALTH_NEWS_Lecture_NO){
                showHealthNewsList(HEALTH_NEWS_Lecture_NO);
            }
        }
    }

    private void showHealthNewsList(int healthNewsClassNo){
        if (healthNewsClassNo == HEALTH_NEWS_HEADLINES_NO){
            //show healines news,update adapter and ui
            CURRENT_ADAPTER_NO = HEALTH_NEWS_HEADLINES_NO;
            //update adapter
            displayHealthNewsList.clear();
            displayHealthNewsList.addAll(healthNewsHeadlinesList);
            recycleViewAdapter.notifyDataSetChanged();
            //update ui
            headlinesTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
            headlinesImageView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            lectureTextView.setTextColor(getResources().getColor(R.color.colorBlack));
            lectureImageView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        }else if (healthNewsClassNo == HEALTH_NEWS_Lecture_NO){
            //show lecture new,update adapter and ui.
            CURRENT_ADAPTER_NO = HEALTH_NEWS_Lecture_NO;
            //update adapter
            displayHealthNewsList.clear();
            displayHealthNewsList.addAll(healthNewsLectureList);
            recycleViewAdapter.notifyDataSetChanged();
            //update ui
            headlinesTextView.setTextColor(getResources().getColor(R.color.colorBlack));
            headlinesImageView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            lectureTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
            lectureImageView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }


}
