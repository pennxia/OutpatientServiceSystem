package com.nobitastudio.materialdesign.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.base.bj.trpayjar.domain.TrPayResult;
import com.base.bj.trpayjar.listener.PayResultListener;
import com.base.bj.trpayjar.utils.TrPay;
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

public class OutpatientRegisterDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    ActionBar actionBar;
    Toolbar toolbar;
    Button confirmPaymentButton;
    Button cancleAppointmentButton;
    TextView registerDetailsOutpatientTextview;
    TextView registerDetailsDoctorNameTextview;
    TextView registerDetailsPatientNameTextview;
    TextView registerDetailsYearMonthDateTextview;
    TextView registerDetailsTimeSlotTextview;
    TextView registerDetailsDiagnosisNoTextview;
    TextView registerDetailsCostTextview;
    TextView registerDetailsaddressTextview;
    TextView registerDetailsAppointmengSoueceTextview;
    TextView registerDetailsRemainingTimeTextView;
    FrameLayout payChoiceFrameLayout;
    CardView cancelOrConfirmCardView;
    TextView cancelPayTextView;
    ImageView aliPayImageView;
    ImageView vxPayImageView;
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

    boolean changeRemainingTimeThreadContinue;
    boolean atMainThread;
    Message message;

    // below is test data
    String userId;
    String tradeNo;
    String tradeName;
    String backParams;
    String notifyUrl;
    Long cost;

    static final String ACCESS_TIMEOUT = "网络连接超时";
    static final String UNKNOWN_ERROR = "发生未知错误";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outpatient_register_details);

        init();
    }

    private void init() {

        initBaseData();

        //init widget
        toolbar = findViewById(R.id.activity_outpatient_register_details_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(null);
        }
        confirmPaymentButton = findViewById(R.id.activity_outpatient_register_details_confirm_button);
        cancleAppointmentButton = findViewById(R.id.activity_outpatient_register_details_cancel_button);
        registerDetailsOutpatientTextview = findViewById(R.id.activity_outpatient_register_details_outpatient_textview);
        registerDetailsDoctorNameTextview = findViewById(R.id.activity_outpatient_register_details_doctorname_textview);
        registerDetailsPatientNameTextview = findViewById(R.id.activity_outpatient_register_details_patientname_textview);
        registerDetailsYearMonthDateTextview = findViewById(R.id.activity_outpatient_register_details_yearmonthdate_textview);
        registerDetailsTimeSlotTextview = findViewById(R.id.activity_outpatient_register_details_timeslot_textview);
        registerDetailsDiagnosisNoTextview = findViewById(R.id.activity_outpatient_register_details_diagnosisno_textview);
        registerDetailsCostTextview = findViewById(R.id.activity_outpatient_register_details_cost_textview);
        registerDetailsaddressTextview = findViewById(R.id.activity_outpatient_register_details_address_textview);
        registerDetailsAppointmengSoueceTextview = findViewById(R.id.activity_outpatient_register_details_source_textview);
        registerDetailsRemainingTimeTextView = findViewById(R.id.activity_outpatient_register_details_remainingtime_textview);
        cancelOrConfirmCardView = findViewById(R.id.activity_outpatientregisterdetails_cancelorconfirm_cardview);
        payChoiceFrameLayout = findViewById(R.id.activity_outpatient_register_details_pay_chioce_framelayout);
        cancelPayTextView = findViewById(R.id.activity_outpatient_register_details_cancel_textview);
        aliPayImageView = findViewById(R.id.activity_outpatient_register_details_alipay_imageview);
        vxPayImageView = findViewById(R.id.activity_outpatient_register_details_vxpay_imageview);
        networkCacheCardView = findViewById(R.id.activity_outpatient_register_details_network_cache_cardview);
        errorCardView = findViewById(R.id.activity_outpatient_register_details_error_cardview);
        errorTextView = findViewById(R.id.activity_outpatient_register_details_error_textview);

        //init widget data
        registerDetailsOutpatientTextview.setText(selectedOutpatientDepartment.getOutpatientDepartmentName());
        registerDetailsDoctorNameTextview.setText(selectedDoctor.getDoctorName());
        registerDetailsPatientNameTextview.setText(selectedMedicalCard.getOwnerName());
        registerDetailsYearMonthDateTextview.setText(Utility.handleYear(selectedVisit.getYears()));
        registerDetailsTimeSlotTextview.setText(selectedVisit.getTimeSlot());
        registerDetailsDiagnosisNoTextview.setText(diagnosis.getDiagnosisNo() + " 号");
        registerDetailsCostTextview.setText(selectedVisit.getCost().toString() + " 元");
        registerDetailsaddressTextview.setText(selectedOutpatientDepartment.getDepartmentAddress());
        registerDetailsAppointmengSoueceTextview.setText("门诊服务系统APP");

        //init listener
        cancleAppointmentButton.setOnClickListener(this);
        confirmPaymentButton.setOnClickListener(this);
        cancelPayTextView.setOnClickListener(this);
        aliPayImageView.setOnClickListener(this);
        vxPayImageView.setOnClickListener(this);

        //init NormalDateAndAction
        initNormalDateAndAction();

        //init test data
        initTestData();

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

    private void initTestData() {

        userId = user.getUserAccount();
        tradeNo = diagnosis.getRegistrationNo();
        tradeName = "挂号费";
        backParams = "name=2&age=22";
        notifyUrl = "http://101.200.13.92/notify/alipayTestNotify";
        cost = Long.valueOf("10".trim());
        //init trpay
        TrPay.getInstance(this).initPaySdk("appkey", "mychannel");
    }

    @Override
    public void onClick(View v) {

        int viewId = v.getId();
        if (viewId == R.id.activity_outpatient_register_details_cancel_button) {

            userPaySuccess(diagnosis.getRegistrationNo());

          /*  // cancle register,save data,then return mainACtivity
            StopRemainingTimeThread();
            cancelAppointment(diagnosis.getRegistrationNo());
            finish();*/

        } else if (viewId == R.id.activity_outpatient_register_details_confirm_button) {
            //confirm register,save data ,then give data to server,and waitfor server's answer.
            cancelOrConfirmCardView.setVisibility(View.GONE);
            payChoiceFrameLayout.setVisibility(View.VISIBLE);

        } else if (viewId == R.id.activity_outpatient_register_details_cancel_textview) {
            cancelOrConfirmCardView.setVisibility(View.VISIBLE);
            payChoiceFrameLayout.setVisibility(View.GONE);

        } else if (viewId == R.id.activity_outpatient_register_details_alipay_imageview) {
            StopRemainingTimeThread();
            registerDetailsRemainingTimeTextView.setText("等待支付");
            cancelOrConfirmCardView.setVisibility(View.GONE);
            payChoiceFrameLayout.setVisibility(View.GONE);
            callAlipayInterface(tradeName, tradeNo, cost, backParams, notifyUrl, userId);

        } else if (viewId == R.id.activity_outpatient_register_details_vxpay_imageview) {
            StopRemainingTimeThread();
            registerDetailsRemainingTimeTextView.setText("等待支付");
            cancelOrConfirmCardView.setVisibility(View.GONE);
            payChoiceFrameLayout.setVisibility(View.GONE);
            callWXpayInterface(tradeName, tradeNo, cost, backParams, notifyUrl, userId);
        }

    }

    private void StopRemainingTimeThread() {
        changeRemainingTimeThreadContinue = false;
    }

    private void callAlipayInterface(String tradeName, String tradeNo, Long cost, String backParams, String notifyurl, String userId) {
        TrPay.getInstance(this).callAlipay(tradeName, tradeNo, cost, backParams, notifyurl, userId, new PayResultListener() {
            @Override
            public void onPayFinish(Context context, String outtradeno, int resultCode, String resultString, int payType, Long amount, String tradename) {
                if (resultCode == TrPayResult.RESULT_CODE_SUCC.getId()) {//1：支付成功回调
                    TrPay.getInstance((Activity) context).closePayView();//关闭快捷支付页面
                    Toast.makeText(OutpatientRegisterDetailsActivity.this, resultString, Toast.LENGTH_LONG).show();
                    //支付成功逻辑处理
                    //pay success . save the data to server .wait
                    userPaySuccess(diagnosis.getRegistrationNo());

                } else if (resultCode == TrPayResult.RESULT_CODE_FAIL.getId()) {//2：支付失败回调
                    TrPay.getInstance((Activity) context).closePayView();//关闭快捷支付页面
                    //支付失败逻辑处理
                    userPayCancelOrFail(diagnosis.getRegistrationNo());
                    OutpatientRegisterDetailsActivity.this.finish();
                }
            }

        });
    }

    private void callWXpayInterface(String tradeName, String tradeNo, Long cost, String backParams, String notifyurl, String userId) {
        TrPay.getInstance(this).callWxPay(tradeName, tradeNo, cost, backParams, notifyurl, userId, new PayResultListener() {
            @Override
            public void onPayFinish(Context context, String outtradeno, int resultCode, String resultString, int payType, Long amount, String tradename) {
                if (resultCode == TrPayResult.RESULT_CODE_SUCC.getId()) {//1：支付成功回调
                    TrPay.getInstance((Activity) context).closePayView();//关闭快捷支付页面
                    Toast.makeText(OutpatientRegisterDetailsActivity.this, resultString, Toast.LENGTH_LONG).show();
                    //支付成功逻辑处理
                    //pay success . save the data to server .wait
                    userPaySuccess(diagnosis.getRegistrationNo());
                } else if (resultCode == TrPayResult.RESULT_CODE_FAIL.getId()) {//2：支付失败回调
                    TrPay.getInstance((Activity) context).closePayView();//关闭快捷支付页面
                    //支付失败逻辑处理
                    userPayCancelOrFail(diagnosis.getRegistrationNo());
                    OutpatientRegisterDetailsActivity.this.finish();
                }
            }
        });
    }

    private void userPaySuccess(String registrationNo) {
        showNetworkCacheCardView();
        confirmAppointment(registrationNo);
    }

    /**
     * cancel the user's registration
     *
     * @param registrationNo
     */
    public void cancelAppointment(String registrationNo) {
        String requestAction = "cancelRegistration";
        String parameter = "&registrationNo=" + registrationNo;
        HttpUtil.onlySendHttpRequest(requestAction, parameter);
    }

    private void confirmAppointment(String registrationNo) {
        String requestAction = "confirmRegistration";
        String parameter = "&registrationNo=" + registrationNo;
        HttpUtil.sendOkHttpRequest(requestAction, parameter, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //because of network,the client can't recive message,but has success.
                closeNetworkCacheCardView();
                showErrorCardView(ACCESS_TIMEOUT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                message = gson.fromJson(response.body().string(), Message.class);
                closeNetworkCacheCardView();
                if (message.isSuccess()) {
                    enterRegisterResultsActivity();
                } else {
                    // other problems.it cause update fail
                    showErrorCardView(UNKNOWN_ERROR);
                }
            }
        });

    }

    private void userPayCancelOrFail(String registrationNo) {
        cancelAppointment(registrationNo);
    }

    private void enterRegisterResultsActivity() {
        Intent intent = new Intent(OutpatientRegisterDetailsActivity.this, RegisterResultActivity.class);
        OutpatientRegisterDetailsActivity.this.startActivity(intent);
        OutpatientRegisterDetailsActivity.this.finish();
    }

    private void initNormalDateAndAction() {
        //inti data
        atMainThread = true;
        changeRemainingTimeThreadContinue = true;
        initRemainingTextViewControler();

    }

    private void initRemainingTextViewControler() {
        //the textview decrease 1 second every second.
        new Thread(new Runnable() {
            @Override
            public void run() {
                int minute = 30;
                int second = 0;
                while ((minute != 0 || second != 0) && changeRemainingTimeThreadContinue) {
                    try {
                        Thread.sleep(1000);
                        if (second == 0) {
                            minute--;
                            second = 59;
                        } else {
                            second--;
                        }
                        if (atMainThread) {
                            changeRemainingTime(minute, second);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (minute == 0 && second == 0) {
                    cancelAppointment(diagnosis.getRegistrationNo());
                    OutpatientRegisterDetailsActivity.this.finish();
                }
            }
        }).start();


    }

    /**
     * changeRemainingTime
     *
     * @param minute minute the minute
     * @param second second the second
     */
    private void changeRemainingTime(int minute, int second) {
        String remainingTimeMinute = minute > 9 ? String.valueOf(minute) : "0" + minute;
        String remainingTimeSecond = second > 9 ? String.valueOf(second) : "0" + second;
        final String remainingTime = remainingTimeMinute + ":" + remainingTimeSecond;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                registerDetailsRemainingTimeTextView.setText(remainingTime);
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
            atMainThread = false;
            finish();
        } else {
            atMainThread = false;
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        atMainThread = false;
        super.onBackPressed();
    }
}
