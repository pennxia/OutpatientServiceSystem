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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nobitastudio.materialdesign.adapter.OutpatientRecycleViewAdapter;
import com.nobitastudio.materialdesign.bean.OutpatientDepartment;
import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.bean.User;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OutpatientDepartmentActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar actionBar;
    RecyclerView recyclerView;
    LinearLayout bottomLineLinearLayout;
    CardView networkCacheCardView;
    CardView errorCardView;
    TextView errorTextView;

    OutpatientRecycleViewAdapter adapter;

    List<OutpatientDepartment> outpatientDepartments;
    User user;
    Gson gson;

    static final String ACCESS_TIMEOUT = "网络连接超时";
    static final String DONT_FIND_DEPARTMENT = "未查找到科室记录";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outpatient_department);
        init();
    }

    private void init() {

        //init basal data
        initBaseData();

        //init widget
        toolbar = findViewById(R.id.activity_outpatient_department_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
        recyclerView = findViewById(R.id.activity_outpatient_department_recycleview);
        bottomLineLinearLayout = findViewById(R.id.activity_outpatientdepartment_bottomline_linearlayout);
        networkCacheCardView = findViewById(R.id.activity_outpatient_department_network_cache_cardview);
        errorCardView = findViewById(R.id.activity_outpatient_department_error_cardview);
        errorTextView = findViewById(R.id.activity_outpatient_department_error_textview);

        //ini Adapter
        adapter = new OutpatientRecycleViewAdapter(this, outpatientDepartments);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        initOutpatientDatas();
    }

    private void initBaseData() {
        outpatientDepartments = new ArrayList<>();
        user = AppDataUtil.getUser();
        gson = new Gson();
        //save the data to global util.
        AppDataUtil.setOutpatientDepartments(outpatientDepartments);
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
                    notifyAdapter();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bottomLineLinearLayout.setVisibility(View.VISIBLE);
                        }
                    });
                } else {
                    // don't find department.
                    showErrorCardView(DONT_FIND_DEPARTMENT);
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
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

}
