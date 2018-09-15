package com.nobitastudio.materialdesign.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.bean.Diagnosis;
import com.nobitastudio.materialdesign.bean.Doctor;
import com.nobitastudio.materialdesign.bean.MedicalCard;
import com.nobitastudio.materialdesign.bean.Message;
import com.nobitastudio.materialdesign.bean.OutpatientDepartment;
import com.nobitastudio.materialdesign.bean.User;
import com.nobitastudio.materialdesign.bean.Visit;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.HttpUtil;
import com.nobitastudio.materialdesign.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterResultActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    ActionBar actionBar;
    Button cancleAppointmentButton;
    TextView registerResultOutpatientTextview;
    TextView registerResultDoctorNameTextview;
    TextView registerResultPatientNameTextview;
    TextView registerResultYearMonthDateTextview;
    TextView registerResultTimeSlotTextview;
    TextView registerResultDiagnosisNoTextview;
    TextView registerResultCostTextview;
    TextView registerResultaddressTextview;
    TextView registerResultAppointmengSoueceTextview;
    CardView networkCacheCardView;
    CardView errorCardView;
    TextView errorTextView;

    Doctor selectedDoctor;
    User user;
    MedicalCard selectedMedicalCard;
    OutpatientDepartment selectedOutpatientDepartment;
    Visit selectedVisit;
    Diagnosis diagnosis;
    Gson gson;

    Message message;

    static final String CANCEL_REGISTER = "正在取消";
    static final String ACCESS_TIMEOUT = "网络连接超时";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_result);

        init();
    }

    private void init() {

        initBaseData();

        //initwidget
        toolbar = findViewById(R.id.activity_personal_center_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(null);
        }

        cancleAppointmentButton = findViewById(R.id.activity_register_result_cancel_button);
        registerResultOutpatientTextview = findViewById(R.id.activity_register_result_department_textview);
        registerResultDoctorNameTextview = findViewById(R.id.activity_register_result_doctorname_textview);
        registerResultPatientNameTextview = findViewById(R.id.activity_register_result_patientname_textview);
        registerResultYearMonthDateTextview = findViewById(R.id.activity_register_result_yearmonthdate_textview);
        registerResultTimeSlotTextview = findViewById(R.id.activity_register_result_timeslot_textview);
        registerResultDiagnosisNoTextview = findViewById(R.id.activity_register_result_diagnosisno_textview);
        registerResultCostTextview = findViewById(R.id.activity_register_result_cost_textview);
        registerResultaddressTextview = findViewById(R.id.activity_register_result_address_textview);
        registerResultAppointmengSoueceTextview = findViewById(R.id.activity_register_result_source_textview);
        networkCacheCardView = findViewById(R.id.activity_register_result_network_cache_cardview);
        errorCardView = findViewById(R.id.activity_register_result_error_cardview);
        errorTextView = findViewById(R.id.activity_register_result_error_textview);

        //init widget data
        registerResultOutpatientTextview.setText(selectedOutpatientDepartment.getOutpatientDepartmentName());
        registerResultDoctorNameTextview.setText(selectedDoctor.getDoctorName());
        registerResultPatientNameTextview.setText(selectedMedicalCard.getOwnerName());
        registerResultYearMonthDateTextview.setText(Utility.handleYear(selectedVisit.getYears()));
        registerResultTimeSlotTextview.setText(selectedVisit.getTimeSlot());
        registerResultDiagnosisNoTextview.setText(diagnosis.getDiagnosisNo() + " 号");
        registerResultCostTextview.setText(selectedVisit.getCost().toString() + " 元");
        registerResultaddressTextview.setText(selectedOutpatientDepartment.getDepartmentAddress());
        registerResultAppointmengSoueceTextview.setText("门诊服务系统APP");

        //init listener
        cancleAppointmentButton.setOnClickListener(this);
    }

    private void initBaseData() {
        selectedDoctor = AppDataUtil.getSelectedDoctor();
        user = AppDataUtil.getUser();
        selectedMedicalCard = AppDataUtil.getSelectedMedicalCard();
        selectedOutpatientDepartment = AppDataUtil.getSelectedOutpatientDepartment();
        selectedVisit = AppDataUtil.getSelectedVisit();
        diagnosis = AppDataUtil.getDiagnosis();
        gson = new Gson();
    }

    @Override
    public void onClick(View v) {

        int viewId = v.getId();
        if (viewId == R.id.activity_register_result_cancel_button) {
            //cancel the appointment
            showNetworkCacheCardView();
            cancelAppointment(diagnosis.getRegistrationNo());
        }
    }

    public void cancelAppointment(String registrationNo) {
        String requestAction = "cancelRegistration";
        String parameter = "&registrationNo=" + registrationNo;
        HttpUtil.sendOkHttpRequest(requestAction, parameter, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                closeNetworkCacheCardView();
                showErrorCardView(ACCESS_TIMEOUT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                message = gson.fromJson(response.body().string(), Message.class);
                closeNetworkCacheCardView();
                if (message.isSuccess()) {
                    try {
                        Thread.sleep(1200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    RegisterResultActivity.this.finish();

                } else {
                    //cancel fail.let user retry
                    showErrorCardView(ACCESS_TIMEOUT);
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
