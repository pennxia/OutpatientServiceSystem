package com.nobitastudio.materialdesign.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.bean.DepartmentIntroduction;
import com.nobitastudio.materialdesign.bean.OutpatientDepartment;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OutpatientIntroductionActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    ActionBar actionBar;
    TextView toolbarTextView;
    TextView locationTextView;
    TextView floorTextView;
    TextView areaTextView;
    TextView outpatientIntroductionTextView;
    CardView networkCacheCardView;
    CardView errorCardView;
    TextView errorTextView;

    Gson gson;
    OutpatientDepartment outpatientDepartment;
    DepartmentIntroduction departmentIntroduction;

    static final String ACCESS_TIMEOUT = "网络连接超时";
    static final String DONT_FIND_DEPARTMENTINTRODCTION_RECORDS = "未查找到相关信息";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outpatient_introduction);

        init();
    }

    private void init() {

        initBaseData();

        //init widget
        toolbar = findViewById(R.id.activity_outpatientintroduction_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(null);
        }
        toolbarTextView = findViewById(R.id.activity_outpatientintroduction_toolbar_textview);
        locationTextView = findViewById(R.id.activity_outpatientintroduction_location_textview);
        floorTextView = findViewById(R.id.activity_outpatientintroduction_floor_textview);
        areaTextView = findViewById(R.id.activity_outpatientintroduction_area_textview);
        outpatientIntroductionTextView = findViewById(R.id.activity_outpatientintroduction_outpatientintroduction_textview);
        networkCacheCardView = findViewById(R.id.activity_outpatientintroduction_networkcache_cardview);
        errorCardView = findViewById(R.id.activity_outpatientintroduction_error_cardview);
        errorTextView = findViewById(R.id.activity_outpatientintroduction_error_textview);

        //init outpatientIntroduction data.
        initOutpatientIntroduction();

    }

    private void initBaseData() {
        gson = new Gson();
        outpatientDepartment = AppDataUtil.getSelectedOutpatientDepartment();
    }

    private void initOutpatientIntroduction() {
        showNetworkCacheCardView();
        String requestAction = "getDepartmentIntroduction";
        String parameter = "&departmentNo=" + outpatientDepartment.getOutpatientDepartmentId();
        HttpUtil.sendOkHttpRequest(requestAction, parameter, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                closeNetworkCacheCardView();
                showErrorCardView(ACCESS_TIMEOUT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                departmentIntroduction = gson.fromJson(response.body().string(),DepartmentIntroduction.class);
                closeNetworkCacheCardView();
                if (departmentIntroduction.getArea().isEmpty() || departmentIntroduction.getDepartmentNo().equals(0)){
                    //don't find departmentIntroduction
                    showErrorCardView(DONT_FIND_DEPARTMENTINTRODCTION_RECORDS);
                    OutpatientIntroductionActivity.this.finish();
                }else {
                    //find data.init widget data
                    initWidgetData(departmentIntroduction);
                }
            }

        });
    }

    private void initWidgetData(final DepartmentIntroduction departmentIntroduction) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toolbarTextView.setText(outpatientDepartment.getOutpatientDepartmentName());
                locationTextView.setText(departmentIntroduction.getLocation());
                floorTextView.setText(departmentIntroduction.getFloor());
                areaTextView.setText(departmentIntroduction.getArea());
                outpatientIntroductionTextView.setText(departmentIntroduction.getIntroduction());
            }
        });
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

}
