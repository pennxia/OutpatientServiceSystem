package com.nobitastudio.materialdesign.activity;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.adapter.HealthNewsRecycleViewAdapter;
import com.nobitastudio.materialdesign.adapter.HospitalActivityUltraPagerAdapter;
import com.nobitastudio.materialdesign.adapter.SimpleHealthNewsUltraPagerAdapter;
import com.nobitastudio.materialdesign.bean.HealthNews;
import com.nobitastudio.materialdesign.bean.SimpleHealthNews;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraDepthScaleTransformer;

import java.util.ArrayList;
import java.util.List;

public class HealthNewsActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    ActionBar actionBar;
    UltraViewPager ultraViewPager;
    TextView headlinesTextView;
    ImageView headlinesImageView;
    TextView lectureTextView;
    ImageView lectureImageView;
    RecyclerView recyclerView;

    PagerAdapter pagerAdapter;  //SimpleHealthNewsUltraPagerAdapter
    HealthNewsRecycleViewAdapter recycleViewAdapter;

    List<SimpleHealthNews> simpleHealthNewsList;  //the list from loginActivity
    List<HealthNews> healthNewsHeadlinesList;
    List<HealthNews> healthNewsLectureList;
    List<HealthNews> displayHealthNewsList;
    Gson gson;

    static final int HEALTH_NEWS_HEADLINES_NO = 4001;
    static final int HEALTH_NEWS_Lecture_NO = 4002;
    static int CURRENT_ADAPTER_NO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_news);

        init();
    }

    private void init() {

        initBaseData();

        //init widget
        initWidget();
        //init viewPager
        initViewPager();
        //init recycleView
        initRecycleView();

    }

    private void initBaseData() {
        gson = new Gson();
        simpleHealthNewsList = AppDataUtil.getSimpleHealthNewsList();
        healthNewsHeadlinesList = AppDataUtil.getHealthNewsHeadlinesList();
        healthNewsLectureList = AppDataUtil.getHealthNewsLectureList();
        displayHealthNewsList = new ArrayList<>();
        displayHealthNewsList.addAll(healthNewsHeadlinesList);
        CURRENT_ADAPTER_NO = HEALTH_NEWS_HEADLINES_NO;
    }

    private void initWidget() {
        toolbar = findViewById(R.id.activity_healthnews_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(null);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ultraViewPager = findViewById(R.id.activity_healthnews_ultraviewpager);
        headlinesTextView = findViewById(R.id.activity_healthnews_headlines_textview);
        headlinesImageView = findViewById(R.id.activity_healthnews_headlines_imageview);
        lectureTextView = findViewById(R.id.activity_healthnews_lecture_textview);
        lectureImageView = findViewById(R.id.activity_healthnews_lecture_imageview);
        recyclerView = findViewById(R.id.activity_healthnews_recyclerview);

        headlinesTextView.setOnClickListener(this);
        headlinesImageView.setOnClickListener(this);
        lectureTextView.setOnClickListener(this);
        lectureImageView.setOnClickListener(this);
    }

    private void initViewPager() {
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        pagerAdapter = new SimpleHealthNewsUltraPagerAdapter(true, this, simpleHealthNewsList);
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
        recycleViewAdapter = new HealthNewsRecycleViewAdapter(this, displayHealthNewsList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(recycleViewAdapter);
    }

    @Override
    public void onClick(View v) {

        int viewId = v.getId();
        if (viewId == R.id.activity_healthnews_headlines_textview || viewId == R.id.activity_healthnews_headlines_imageview) {
            //recycleview show healthnews
            if (CURRENT_ADAPTER_NO != HEALTH_NEWS_HEADLINES_NO) {
                showHealthNewsList(HEALTH_NEWS_HEADLINES_NO);
            }

        } else if (viewId == R.id.activity_healthnews_lecture_textview || viewId == R.id.activity_healthnews_lecture_imageview) {
            //recycleview show doctorclass
            if (CURRENT_ADAPTER_NO != HEALTH_NEWS_Lecture_NO) {
                showHealthNewsList(HEALTH_NEWS_Lecture_NO);
            }

        }
        //other click operation
    }

    private void showHealthNewsList(int healthNewsClassNo) {
        if (healthNewsClassNo == HEALTH_NEWS_HEADLINES_NO) {
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
        } else if (healthNewsClassNo == HEALTH_NEWS_Lecture_NO) {
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

}
