package com.nobitastudio.materialdesign.activity;

import android.app.Activity;
import android.content.Context;
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

import com.base.bj.trpayjar.domain.TrPayResult;
import com.base.bj.trpayjar.listener.PayResultListener;
import com.base.bj.trpayjar.utils.TrPay;
import com.google.gson.Gson;
import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.bean.Message;
import com.nobitastudio.materialdesign.bean.RegistrationForm;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.HttpUtil;
import com.nobitastudio.materialdesign.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    ActionBar actionBar;
    Toolbar toolbar;
    TextView toolbarTextView;
    Button cancelAppointmentAndHaveCancelButton;
    Button cancelRegisterButton;
    Button payNowButton;
    TextView registerResultOutpatientTextView;
    TextView registerResultDoctorNameTextView;
    TextView registerResultPatientNameTextView;
    TextView registerResultYearMonthDateTextView;
    TextView registerResultTimeSlotTextView;
    TextView registerResultDiagnosisNoTextView;
    TextView registerResultCostTextView;
    TextView registerResultAddressTextView;
    TextView registerResultAppointmentSourceTextView;
    FrameLayout payChoiceFrameLayout;
    CardView cancelAppointmentAndHaveCancelCardView;
    CardView cancelRegisterOrPayNowCardView;
    TextView cancelPayTextView;
    ImageView aliPayImageView;
    ImageView vxPayImageView;
    CardView networkCacheCardView;
    CardView errorCardView;
    TextView networkCacheTextView;
    TextView errorTextView;

    RegistrationForm registrationForm;

    String registrationNo;
    Integer orderState;
    Message message;
    Gson gson;

    String userId;
    String tradeNo;
    String tradeName;
    String backParams;
    String notifyurl;
    Long cost;

    static final String REQUEST_DATA = "正在获取订单详情";
    static final String VERIFICATION_PAY = "正在验证";
    static final String CANCEL_FAIL = "取消失败,请重试";
    static final String CANCEL_REGISTER = "正在取消";
    static final String ACCESS_TIMEOUT = "网络连接超时";
    static final String BEING_CANCEL = "正在取消";
    static final String BEING_PAY = "正在支付";
    static final String HAVE_PAY = "已支付";
    static final String WAITFOR_PAY = "待支付";
    static final String HAVE_CANCEL = "已取消";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_details);

        init();
        getRegisterDetailsData();
    }

    private void init() {

        initBaseData();

        //init widget
        toolbar = findViewById(R.id.activity_register_details_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(null);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbarTextView = findViewById(R.id.activity_register_details_toolbarTextView);
        registerResultOutpatientTextView = findViewById(R.id.activity_register_details_outpatient_textview);
        registerResultAddressTextView = findViewById(R.id.activity_register_details_address_textview);
        registerResultDoctorNameTextView = findViewById(R.id.activity_register_details_doctorname_textview);
        registerResultPatientNameTextView = findViewById(R.id.activity_register_details_patient_name_textview);
        registerResultYearMonthDateTextView = findViewById(R.id.activity_register_details_yearmonthdate_textview);
        registerResultTimeSlotTextView = findViewById(R.id.activity_register_details_timeslot_textview);
        registerResultDiagnosisNoTextView = findViewById(R.id.activity_register_details_diagnosis_no_textview);
        registerResultCostTextView = findViewById(R.id.activity_register_details_cost_textview);
        registerResultAppointmentSourceTextView = findViewById(R.id.activity_register_details_appointment_source_textview);
        cancelAppointmentAndHaveCancelButton = findViewById(R.id.activity_register_details_cancelappointmentandhavecancel_button);
        cancelRegisterButton = findViewById(R.id.activity_register_details_cancelregister_button);
        payNowButton = findViewById(R.id.activity_register_details_paynow_button);
        cancelPayTextView = findViewById(R.id.activity_register_details_cancel_payment_textview);
        aliPayImageView = findViewById(R.id.activity_register_details_alipay_imageview);
        vxPayImageView = findViewById(R.id.activity_register_details_vxpay_imageview);
        payChoiceFrameLayout = findViewById(R.id.activity_register_details_pay_choice);
        cancelAppointmentAndHaveCancelCardView = findViewById(R.id.activity_register_details_cancelappointmentandhavecancel_cardview);
        cancelRegisterOrPayNowCardView = findViewById(R.id.activity_register_details_cancelorpaynow_cardview);
        networkCacheCardView = findViewById(R.id.activity_register_details_network_cache_cardview);
        errorCardView = findViewById(R.id.activity_register_details_error_cardview);
        networkCacheTextView = findViewById(R.id.activity_register_details_network_cache_textview);
        errorTextView = findViewById(R.id.activity_register_details_error_textview);

        //init listener
        cancelAppointmentAndHaveCancelButton.setOnClickListener(this);
        cancelRegisterButton.setOnClickListener(this);
        payNowButton.setOnClickListener(this);
        cancelPayTextView.setOnClickListener(this);
        aliPayImageView.setOnClickListener(this);
        vxPayImageView.setOnClickListener(this);

        //init normal data and object
        initNormaldataObject();
    }

    private void initBaseData() {
        gson = new Gson();
        registrationNo = AppDataUtil.getRegistrationNo();
        orderState = AppDataUtil.getOrderState();
    }

    private void initNormaldataObject() {
        userId = "15709932234";
        tradeNo = registrationNo;
        tradeName = "挂号费";
        backParams = "name=2&age=22";
        notifyurl = "http://101.200.13.92/notify/alipayTestNotify";
        cost = Long.valueOf("10".trim());
        //init trpay
        TrPay.getInstance(this).initPaySdk("appkey", "nobitachannel");
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.activity_register_details_paynow_button) {
            //call pay interface
            //callPayInterface();
            payChoiceFrameLayout.setVisibility(View.VISIBLE);
            cancelRegisterOrPayNowCardView.setVisibility(View.GONE);

        } else if (viewId == R.id.activity_register_details_cancelregister_button) {
            //show alert inf to confirm cancel once again.wait to be realised
            cancelAppointment(registrationNo);

        } else if (viewId == R.id.activity_register_details_cancelappointmentandhavecancel_button) {
            //show alert inf to confirm cancel once again.wait realise
            cancelAppointment(registrationNo);

        } else if (viewId == R.id.activity_register_details_cancel_payment_textview) {
            payChoiceFrameLayout.setVisibility(View.GONE);
            cancelRegisterOrPayNowCardView.setVisibility(View.VISIBLE);

        } else if (viewId == R.id.activity_register_details_alipay_imageview) {
            updateToolbarTextView(BEING_PAY);
            payChoiceFrameLayout.setVisibility(View.GONE);
            cancelRegisterOrPayNowCardView.setVisibility(View.VISIBLE);
            callAliPayInterface(tradeName, tradeNo, cost, backParams, notifyurl, userId);

        } else if (viewId == R.id.activity_register_details_vxpay_imageview) {
            updateToolbarTextView(BEING_PAY);
            payChoiceFrameLayout.setVisibility(View.GONE);
            cancelRegisterOrPayNowCardView.setVisibility(View.VISIBLE);
            callVxPayInterface(tradeName, tradeNo, cost, backParams, notifyurl, userId);
        }

    }

    private void initWidgetData() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //init widget data
                registerResultOutpatientTextView.setText(registrationForm.getOutpatientName());
                registerResultDoctorNameTextView.setText(registrationForm.getDoctorName());
                registerResultPatientNameTextView.setText(registrationForm.getMedicalCardOwnerName());
                registerResultYearMonthDateTextView.setText(Utility.handleYear(registrationForm.getYearMonthDate()));
                registerResultTimeSlotTextView.setText(registrationForm.getTimeSlot());
                registerResultDiagnosisNoTextView.setText(registrationForm.getDiagnosisNo() + " 号");
                registerResultCostTextView.setText(registrationForm.getCost() + " 元");
                registerResultAddressTextView.setText(registrationForm.getAddress());
                registerResultAppointmentSourceTextView.setText("门诊服务系统APP");
                if (orderState.equals(0)) {
                    updateToolbarTextView(WAITFOR_PAY);
                    cancelRegisterOrPayNowCardView.setVisibility(View.VISIBLE);
                    cancelAppointmentAndHaveCancelCardView.setVisibility(View.GONE);

                } else if (orderState.equals(1)) {
                    updateToolbarTextView(HAVE_PAY);
                    cancelRegisterOrPayNowCardView.setVisibility(View.GONE);
                    cancelAppointmentAndHaveCancelCardView.setVisibility(View.VISIBLE);
                    recoveryCancelAppointmentAndHaveCancelButton();

                } else if (orderState.equals(2)) {
                    updateToolbarTextView(HAVE_CANCEL);
                    cancelRegisterOrPayNowCardView.setVisibility(View.GONE);
                    cancelAppointmentAndHaveCancelCardView.setVisibility(View.VISIBLE);
                    lockCancelAppointmentAndHaveCancelButton();
                }
            }
        });
    }

    private void callAliPayInterface(String tradeName, String tradeNo, Long cost, String backParams, String notifyurl, String userId) {
        TrPay.getInstance(this).callAlipay(tradeName, tradeNo, cost, backParams, notifyurl, userId, new PayResultListener() {
            @Override
            public void onPayFinish(Context context, String outtradeno, int resultCode, String resultString, int payType, Long amount, String tradename) {
                if (resultCode == TrPayResult.RESULT_CODE_SUCC.getId()) {//1：支付成功回调
                    TrPay.getInstance((Activity) context).closePayView();//关闭快捷支付页面
                    //支付成功逻辑处理
                    //pay success . save the data to server .wait
                    confirmAppointment(registrationNo);

                } else if (resultCode == TrPayResult.RESULT_CODE_FAIL.getId()) {//2：支付失败回调
                    TrPay.getInstance((Activity) context).closePayView();//关闭快捷支付页面
                    //支付失败逻辑处理
                    //do nothing
                    updateToolbarTextView(WAITFOR_PAY);
                }
            }

        });
    }

    private void callVxPayInterface(String tradeName, String tradeNo, Long cost, String backParams, String notifyurl, String userId) {
        TrPay.getInstance(this).callWxPay(tradeName, tradeNo, cost, backParams, notifyurl, userId, new PayResultListener() {
            @Override
            public void onPayFinish(Context context, String outtradeno, int resultCode, String resultString, int payType, Long amount, String tradename) {
                if (resultCode == TrPayResult.RESULT_CODE_SUCC.getId()) {//1：支付成功回调
                    TrPay.getInstance((Activity) context).closePayView();//关闭快捷支付页面
                    //支付成功逻辑处理
                    //pay success . save the data to server .wait
                    confirmAppointment(registrationNo);

                } else if (resultCode == TrPayResult.RESULT_CODE_FAIL.getId()) {//2：支付失败回调
                    TrPay.getInstance((Activity) context).closePayView();//关闭快捷支付页面
                    //支付失败逻辑处理
                    //do nothing
                    updateToolbarTextView(WAITFOR_PAY);
                }
            }

        });


    }

    private void getRegisterDetailsData() {
        showNetworkCacheCardView(REQUEST_DATA);
        String requestAction = "queryRegistrationFormByRegistrationNo";
        String parameter = "&registrationNo=" + registrationNo;
        HttpUtil.sendOkHttpRequest(requestAction, parameter, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                closeNetworkCacheCardView();
                showErrorCardView(ACCESS_TIMEOUT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                registrationForm = gson.fromJson(response.body().string(), RegistrationForm.class);
                closeNetworkCacheCardView();
                initWidgetData();
            }
        });
    }

    private void confirmAppointment(String registrationNo) {
        updateToolbarTextView(HAVE_PAY);
        showNetworkCacheCardView(VERIFICATION_PAY);
        String requestAction = "confirmRegistration";
        String parameter = "&registrationNo=" + registrationNo;
        HttpUtil.sendOkHttpRequest(requestAction, parameter, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //because of network,the client can't recive message,but has success.
                //if other questions i can't handle it.there i think it's network,the message can't reach,most beacause of it.
                closeNetworkCacheCardView();
                showErrorCardView(ACCESS_TIMEOUT);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        orderState = 1;
                        cancelRegisterOrPayNowCardView.setVisibility(View.GONE);
                        cancelAppointmentAndHaveCancelCardView.setVisibility(View.VISIBLE);
                        recoveryCancelAppointmentAndHaveCancelButton();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    message = gson.fromJson(response.body().string(), Message.class);
                    closeNetworkCacheCardView();
                    if (message.isSuccess()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                orderState = 1;
                                cancelRegisterOrPayNowCardView.setVisibility(View.GONE);
                                cancelAppointmentAndHaveCancelCardView.setVisibility(View.VISIBLE);
                                recoveryCancelAppointmentAndHaveCancelButton();
                            }
                        });

                    } else {
                        // other problems.it cause update fail
                        showErrorCardView(ACCESS_TIMEOUT);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                orderState = 1;
                                cancelRegisterOrPayNowCardView.setVisibility(View.GONE);
                                cancelAppointmentAndHaveCancelCardView.setVisibility(View.VISIBLE);
                                recoveryCancelAppointmentAndHaveCancelButton();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void cancelAppointment(String registrationNo) {
        updateToolbarTextView(BEING_CANCEL);
        showNetworkCacheCardView(CANCEL_REGISTER);
        String requestAction = "cancelRegistration";
        String parameter = "&registrationNo=" + registrationNo;
        HttpUtil.sendOkHttpRequest(requestAction, parameter, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                closeNetworkCacheCardView();
                showErrorCardView(ACCESS_TIMEOUT);
                if (orderState.equals(1)) {
                    updateToolbarTextView(HAVE_PAY);
                } else {
                    updateToolbarTextView(WAITFOR_PAY);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                message = gson.fromJson(response.body().string(), Message.class);
                closeNetworkCacheCardView();
                if (message.isSuccess()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            orderState = 2;
                            cancelRegisterOrPayNowCardView.setVisibility(View.GONE);
                            cancelAppointmentAndHaveCancelCardView.setVisibility(View.VISIBLE);
                            lockCancelAppointmentAndHaveCancelButton();
                        }
                    });
                    updateToolbarTextView(HAVE_CANCEL);

                } else {
                    showErrorCardView(CANCEL_FAIL);
                    if (orderState.equals(1)) {
                        updateToolbarTextView(HAVE_PAY);
                    } else {
                        updateToolbarTextView(WAITFOR_PAY);
                    }
                }
            }
        });
    }

    private void updateToolbarTextView(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toolbarTextView.setText(text);
            }
        });
    }

    private void recoveryCancelAppointmentAndHaveCancelButton() {
        cancelAppointmentAndHaveCancelButton.setClickable(true);
        cancelAppointmentAndHaveCancelButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        cancelAppointmentAndHaveCancelButton.setText("申请退号");
    }

    private void lockCancelAppointmentAndHaveCancelButton() {
        cancelAppointmentAndHaveCancelButton.setClickable(false);
        cancelAppointmentAndHaveCancelButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
        cancelAppointmentAndHaveCancelButton.setText("已取消");
    }


    private void showNetworkCacheCardView(final String introducrionInf) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                networkCacheTextView.setText(introducrionInf);
                networkCacheTextView.setVisibility(View.VISIBLE);
            }
        });
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
        }
        return true;
    }

}
