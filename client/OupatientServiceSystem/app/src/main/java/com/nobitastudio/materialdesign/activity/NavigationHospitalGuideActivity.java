package com.nobitastudio.materialdesign.activity;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.adapter.OutpatientRecycleViewAdapter;
import com.nobitastudio.materialdesign.bean.OutpatientDepartment;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * it show map and navigation function and outpatient address
 * create by nobita 2018.01.17
 */
public class NavigationHospitalGuideActivity extends AppCompatActivity implements View.OnClickListener, INaviInfoCallback {

    Toolbar toolbar;
    ActionBar actionBar;
    ImageView drvieImageView;
    TextView driveTextView;
    ImageView walkImageView;
    TextView walkTextView;
    ImageView cycleImageView;
    TextView cycleTextView;
    TextView moreInfTextView;
    ImageView moreInfImageView;
    RecyclerView recyclerView;
    LinearLayout seeMoreLinearLayout;
    LinearLayout bottomLineLinearLayout;
    CardView networkCacheCardView;
    CardView errorCardView;
    TextView errorTextView;


    OutpatientRecycleViewAdapter adapter;

    List<OutpatientDepartment> outpatientDepartments;
    List<OutpatientDepartment> displayOutpatientDepartments;
    Gson gson;

    static final String ACCESS_TIMEOUT = "网络连接超时";
    static final String DONT_FIND_DEPARTMENT = "未查找到科室记录";
    static final LatLng SHZ_UNIVERSITY_FIRST_AFFILIATED_HOSPITAL_LATLNG = new LatLng(44.299419, 86.059641); //经度：86.059641,纬度：44.299419,注意构造函数是 LatLng（纬度，经度）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_hospital_guide);

        init();
    }

    private void init() {

        initBaseData();

        //init widget
        toolbar = findViewById(R.id.activity_navigationhospitalguideactivity_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(null);
        }
        drvieImageView = findViewById(R.id.activity_navigationhospitalguideactivity_drive_imageview);
        driveTextView = findViewById(R.id.activity_navigationhospitalguideactivity_drive_textview);
        walkImageView = findViewById(R.id.activity_navigationhospitalguideactivity_walk_imageview);
        walkTextView = findViewById(R.id.activity_navigationhospitalguideactivity_walk_textview);
        cycleImageView = findViewById(R.id.activity_navigationhospitalguideactivity_cycle_imageview);
        cycleTextView = findViewById(R.id.activity_navigationhospitalguideactivity_cycle_textview);
        moreInfTextView = findViewById(R.id.activity_navigationhospitalguideactivity_moreinf_textview);
        moreInfImageView = findViewById(R.id.activity_navigationhospitalguideactivity_moreinf_imageview);
        recyclerView = findViewById(R.id.activity_navigationhospitalguideactivity_recycleview);
        seeMoreLinearLayout = findViewById(R.id.activity_navigationhospitalguideactivity_seemore_linearlayout);
        bottomLineLinearLayout = findViewById(R.id.activity_navigationhospitalguideactivity_bottomline_linearlayout);
        networkCacheCardView = findViewById(R.id.activity_navigationhospitalguideactivity_networkcache_cardview);
        errorCardView = findViewById(R.id.activity_navigationhospitalguideactivity_error_cardview);
        errorTextView = findViewById(R.id.activity_navigationhospitalguideactivity_error_textview);

        //init listener
        drvieImageView.setOnClickListener(this);
        driveTextView.setOnClickListener(this);
        walkImageView.setOnClickListener(this);
        walkTextView.setOnClickListener(this);
        cycleImageView.setOnClickListener(this);
        cycleTextView.setOnClickListener(this);
        moreInfTextView.setOnClickListener(this);
        moreInfImageView.setOnClickListener(this);
        seeMoreLinearLayout.setOnClickListener(this);

        //ini Adapter
        adapter = new OutpatientRecycleViewAdapter(this, displayOutpatientDepartments);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        initOutpatientDatas();
    }

    private void initBaseData() {
        outpatientDepartments = new ArrayList<>();
        displayOutpatientDepartments = new ArrayList<>();
        gson = new Gson();

        //save the data to global util.
        AppDataUtil.setOutpatientDepartments(outpatientDepartments);
    }

    @Override
    public void onClick(View v) {

        int viewId = v.getId();

        if (viewId == R.id.activity_navigationhospitalguideactivity_drive_imageview || viewId == R.id.activity_navigationhospitalguideactivity_drive_textview) {
            //by drive go to hospital
            AmapNaviParams params = new AmapNaviParams(null, null, new Poi("石河子大学医学院第一附属医院", SHZ_UNIVERSITY_FIRST_AFFILIATED_HOSPITAL_LATLNG, ""), AmapNaviType.DRIVER);
            params.setUseInnerVoice(true);
            AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), params, NavigationHospitalGuideActivity.this);

        } else if (viewId == R.id.activity_navigationhospitalguideactivity_walk_imageview || viewId == R.id.activity_navigationhospitalguideactivity_walk_textview) {
            // by walk go to hospital
            AmapNaviParams params = new AmapNaviParams(null, null, new Poi("石河子大学医学院第一附属医院", SHZ_UNIVERSITY_FIRST_AFFILIATED_HOSPITAL_LATLNG, ""), AmapNaviType.WALK);
            params.setUseInnerVoice(true);
            AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), params, NavigationHospitalGuideActivity.this);

        } else if (viewId == R.id.activity_navigationhospitalguideactivity_cycle_imageview || viewId == R.id.activity_navigationhospitalguideactivity_cycle_textview) {
            // by cycle go to hospital
            AmapNaviParams params = new AmapNaviParams(null, null, new Poi("石河子大学医学院第一附属医院", SHZ_UNIVERSITY_FIRST_AFFILIATED_HOSPITAL_LATLNG, ""), AmapNaviType.RIDE);
            params.setUseInnerVoice(true);
            AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), params, NavigationHospitalGuideActivity.this);

        } else if (viewId == R.id.activity_navigationhospitalguideactivity_moreinf_textview || viewId == R.id.activity_navigationhospitalguideactivity_moreinf_imageview) {
            // show more inner hospital navigation inf.
            Intent intent = new Intent(this, OutpatientDepartmentActivity.class);
            startActivity(intent);
        } else if (viewId == R.id.activity_navigationhospitalguideactivity_seemore_linearlayout) {
            //update adapter .if all data show.let the linearLayout gone.
            updateDisplayOutpatientDepartments();
        }
    }

    private void initOutpatientDatas() {

        showNetworkCacheCardView();
        // if need,add more date to outpatientDepartments,get date from server
        String requestAction = "getAllDepartment";
        HttpUtil.sendOkHttpRequest(requestAction, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                closeNetworkCacheCardView();
                showErrorCardView(ACCESS_TIMEOUT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // let the requestOutpatientCardview gone in mainactivity
                List<OutpatientDepartment> outpatientDepartmentsTemp = gson.fromJson(response.body().string(), new TypeToken<List<OutpatientDepartment>>() {
                }.getType());
                closeNetworkCacheCardView();
                if (outpatientDepartmentsTemp.size() > 0) {
                    for (OutpatientDepartment outpatientDepartment : outpatientDepartmentsTemp) {
                        outpatientDepartments.add(outpatientDepartment);
                    }
                    updateDisplayOutpatientDepartments();
                } else {
                    // don't find department.
                    showErrorCardView(DONT_FIND_DEPARTMENT);
                    seeMoreLinearLayout.setVisibility(View.GONE);
                }
            }
        });

    }

    /**
     * every time.the adapter data add 15 data. if
     */
    private void updateDisplayOutpatientDepartments() {
        int displayOutpatientDepartmentsSize = displayOutpatientDepartments.size();
        for (int i = displayOutpatientDepartmentsSize; i < displayOutpatientDepartmentsSize + 15; i++) {
            if (i < outpatientDepartments.size()) {
                displayOutpatientDepartments.add(outpatientDepartments.get(i));
            } else {
                if (bottomLineLinearLayout.getVisibility() == View.GONE){
                    bottomLineLinearLayout.setVisibility(View.VISIBLE);
                }
                if (seeMoreLinearLayout.getVisibility() == View.VISIBLE){
                    seeMoreLinearLayout.setVisibility(View.GONE);
                }
                break;
            }
        }
        notifyAdapter();
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
        }
        return true;
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

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onArriveDestination(boolean b) {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onStopSpeaking() {

    }

    @Override
    public void onReCalculateRoute(int i) {

    }

    @Override
    public void onExitPage(int i) {

    }

    @Override
    public void onStrategyChanged(int i) {

    }

    @Override
    public View getCustomNaviBottomView() {
        return null;
    }

    @Override
    public View getCustomNaviView() {
        return null;
    }

    @Override
    public void onArrivedWayPoint(int i) {

    }
}
