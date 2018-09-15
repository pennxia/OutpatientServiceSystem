package com.nobitastudio.materialdesign.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.nobitastudio.materialdesign.adapter.DrugDetailsRecycleViewAdapter;
import com.nobitastudio.materialdesign.bean.ClientDrugList;
import com.nobitastudio.materialdesign.bean.ClientDrugListData;
import com.nobitastudio.materialdesign.bean.DoctorNote;
import com.nobitastudio.materialdesign.bean.Message;
import com.nobitastudio.materialdesign.bean.Orders;
import com.nobitastudio.materialdesign.bean.User;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.HttpUtil;
import com.nobitastudio.materialdesign.util.Utility;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DrugDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    ActionBar actionBar;
    RecyclerView recyclerView;
    TextView allPriceTextView;
    TextView patientNameTextView;
    TextView diagnosisDoctorNameTextView;
    TextView medicalCardNoTextView;
    TextView orderNameTextView;
    TextView orderStateTextView;
    TextView orderCreateTimeTextView;
    TextView orderPayMethodTextView;
    TextView orderSerialNoTextView;
    TextView doctorNoteTextView;
    Button payNowOrHavePayButton;
    CardView payNowOrHavePayCardView;
    CardView networkCacheCardView;
    CardView errorCardView;
    TextView errorTextView;
    FrameLayout payChoiceFrameLayout;
    TextView cancelImageView;
    ImageView aliPayImageView;
    ImageView vxPayImageView;
    CardView paySuccessCardView;

    User user;
    String drugCostId;
    Gson gson;
    DrugDetailsRecycleViewAdapter adapter;
    ClientDrugListData clientDrugListData;
    Message message;

    // below is test data
    String userId;
    String tradeNo;
    String tradeName;
    String backParams;
    String notifyUrl;
    Long cost;

    static final String ACCESS_TIMEOUT = "网络连接超时";
    static final String GETTING_PAY_STATE = "正在获取支付状态";
    static final String GETTING_PAY_STATE_FAIL = "获取支付状态失败";
    static final String HAVE_PAY = "已支付";
    static final String PAY_NOW = "立即支付";
    static final String HAVE_CANCEL = "已取消";
    static final String BEING_VERIFICATION = "正在验证";
    static final String UNKNOWN_ERROR = "发生未知错误";
    static final String PAY_FAIL = "支付失败";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_details);

        init();
    }

    private void init() {

        initBaseData();
        initTestData();

        //init widget
        toolbar = findViewById(R.id.activity_drugdetails_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(null);
        }
        recyclerView = findViewById(R.id.activity_drugdetails_recycleview);
        allPriceTextView = findViewById(R.id.activity_drugdetails_allprice_textview);
        patientNameTextView = findViewById(R.id.activity_drugdetails_patientname_textview);
        medicalCardNoTextView = findViewById(R.id.activity_drugdetails_medicalcardno_textview);
        diagnosisDoctorNameTextView = findViewById(R.id.activity_drugdetails_diagnosisdoctorname_textview);
        orderNameTextView = findViewById(R.id.activity_drugdetails_ordername_textview);
        orderStateTextView = findViewById(R.id.activity_drugdetails_orderstate_textview);
        orderCreateTimeTextView = findViewById(R.id.activity_drugdetails_ordercreatetime_textview);
        orderPayMethodTextView = findViewById(R.id.activity_drugdetails_orderpaymethod_textview);
        orderSerialNoTextView = findViewById(R.id.activity_drugdetails_orderpayserialno_textview);
        doctorNoteTextView = findViewById(R.id.activity_drugdetails_doctornote_textview);
        payNowOrHavePayButton = findViewById(R.id.activity_drugdetails_paynoworhavepay_button);
        payNowOrHavePayCardView = findViewById(R.id.activity_drugdetails_paynoworhavepay_cardview);
        networkCacheCardView = findViewById(R.id.activity_drugdetails_networkcache_cardview);
        errorCardView = findViewById(R.id.activity_drugdetails_error_cardview);
        errorTextView = findViewById(R.id.activity_drugdetails_error_textview);
        payChoiceFrameLayout = findViewById(R.id.activity_drugdetails_paychioce_framelayout);
        cancelImageView = findViewById(R.id.activity_drugdetails_cancel_textview);
        aliPayImageView = findViewById(R.id.activity_drugdetails_alipay_imageview);
        vxPayImageView = findViewById(R.id.activity_drugdetails_vxpay_imageview);
        paySuccessCardView = findViewById(R.id.activity_drugdetails_paysuccess_cardview);

        //init listener
        payNowOrHavePayButton.setOnClickListener(this);
        cancelImageView.setOnClickListener(this);
        aliPayImageView.setOnClickListener(this);
        vxPayImageView.setOnClickListener(this);

        //ini Adapter
        adapter = new DrugDetailsRecycleViewAdapter(clientDrugListData.getClientDrugLists());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        //by network to init clientDrugListData.
        initClientListData();

    }

    private void initBaseData() {
        user = AppDataUtil.getUser();
        drugCostId = AppDataUtil.getItemCostId();  //the orderid is drugCostId.
        gson = new Gson();
        clientDrugListData = new ClientDrugListData();

        //save global data
        AppDataUtil.setClientDrugListData(clientDrugListData);
    }

    private void initTestData() {
        userId = user.getUserAccount();
        tradeNo = drugCostId;
        tradeName = "药品单费";
        backParams = "name=2&age=22";
        notifyUrl = "http://101.200.13.92/notify/alipayTestNotify";
        cost = Long.valueOf("10".trim());
        //init trpay
        TrPay.getInstance(this).initPaySdk("appkey", "mychannel");
    }


    @Override
    public void onClick(View v) {

        int viewId = v.getId();
        if (viewId == R.id.activity_drugdetails_paynoworhavepay_button) {
            // prepare to pay this order
            payChoiceFrameLayout.setVisibility(View.VISIBLE);
            payNowOrHavePayCardView.setVisibility(View.GONE);
        } else if (viewId == R.id.activity_drugdetails_cancel_textview) {
            // payChoiceFrameLayout disappear
            payChoiceFrameLayout.setVisibility(View.GONE);
            payNowOrHavePayCardView.setVisibility(View.VISIBLE);
        } else if (viewId == R.id.activity_drugdetails_alipay_imageview) {
            // ali pay start
            payChoiceFrameLayout.setVisibility(View.GONE);
            callAliPayInterface(tradeName, tradeNo, cost, backParams, notifyUrl, userId);
        } else if (viewId == R.id.activity_drugdetails_vxpay_imageview) {
            // vx pay start
            payChoiceFrameLayout.setVisibility(View.GONE);
            callWXPayInterface(tradeName, tradeNo, cost, backParams, notifyUrl, userId);
        }

    }

    private void initClientListData() {

        showNetworkCacheCardView();
        lockPayNowOrHavePayButton(GETTING_PAY_STATE);
        String requestAction = "getDrugListDetails";
        String parameter = "&drugCostId=" + drugCostId;
        HttpUtil.sendOkHttpRequest(requestAction, parameter, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                closeNetworkCacheCardView();
                showErrorCardView(ACCESS_TIMEOUT);
                lockPayNowOrHavePayButton(GETTING_PAY_STATE_FAIL);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ClientDrugListData tempClientDrugListData = gson.fromJson(response.body().string(), ClientDrugListData.class);
                closeNetworkCacheCardView();
                clientDrugListData.getClientDrugLists().addAll(tempClientDrugListData.getClientDrugLists());
                clientDrugListData.setDiagnosisDoctorName(tempClientDrugListData.getDiagnosisDoctorName());
                clientDrugListData.setMedicalCardNo(tempClientDrugListData.getMedicalCardNo());
                clientDrugListData.setPatientName(tempClientDrugListData.getPatientName());
                clientDrugListData.setOrders(tempClientDrugListData.getOrders());
                clientDrugListData.setDoctorNote(tempClientDrugListData.getDoctorNote());

                updateUi();
            }
        });

    }

    private void updateUi() {

        if (clientDrugListData.getClientDrugLists().size() > 0) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // drug list
                    adapter.notifyDataSetChanged();

                    //drug details
                    List<ClientDrugList> clientDrugLists = clientDrugListData.getClientDrugLists();
                    Double allPrice = new Double(0);
                    for (ClientDrugList clientDrugList : clientDrugLists) {
                        Double price = clientDrugList.getPrice() * clientDrugList.getPurchaseNumber();
                        allPrice = allPrice + price;
                    }
                    allPriceTextView.setText(String.valueOf(allPrice));
                    patientNameTextView.setText(clientDrugListData.getPatientName());
                    medicalCardNoTextView.setText(String.valueOf(clientDrugListData.getMedicalCardNo()));
                    diagnosisDoctorNameTextView.setText(clientDrugListData.getDiagnosisDoctorName());

                    //order
                    Orders orders = clientDrugListData.getOrders();
                    orderNameTextView.setText(orders.getOrderName());
                    orderStateTextView.setText(Utility.handelIntegerOrderStateToStr(orders.getOrderState()));
                    orderCreateTimeTextView.setText(Utility.handleStrDate(orders.getOrderCreateTime()));
                    orderPayMethodTextView.setText(orders.getPayMethod());
                    orderSerialNoTextView.setText(orders.getPaySerialNumber());

                    //doctor note
                    DoctorNote doctorNote = clientDrugListData.getDoctorNote();
                    doctorNoteTextView.setText(doctorNote.getNote());

                    // by order state update button
                    if (orders.getOrderState().equals(0)) {
                        // wait for pay
                        recoveryPayNowOrHavePayButton(PAY_NOW);
                    } else if (orders.getOrderState().equals(1)) {
                        // have pay
                        lockPayNowOrHavePayButton(HAVE_PAY);
                    } else if (orders.getOrderState().equals(2)) {
                        // have cancel
                        lockPayNowOrHavePayButton(HAVE_CANCEL);
                    }
                }
            });
        }
    }

    private void callAliPayInterface(String tradeName, String tradeNo, Long cost, String backParams, String notifyurl, String userId) {
        TrPay.getInstance(this).callAlipay(tradeName, tradeNo, cost, backParams, notifyurl, userId, new PayResultListener() {
            @Override
            public void onPayFinish(Context context, String outtradeno, int resultCode, String resultString, int payType, Long amount, String tradename) {
                if (resultCode == TrPayResult.RESULT_CODE_SUCC.getId()) {//1：支付成功回调
                    TrPay.getInstance((Activity) context).closePayView();//关闭快捷支付页面
                    Toast.makeText(DrugDetailsActivity.this, resultString, Toast.LENGTH_LONG).show();
                    //支付成功逻辑处理
                    //pay success . save the data to server .wait
                    userPaySuccess(clientDrugListData.getOrders().getPaySerialNumber());
                } else if (resultCode == TrPayResult.RESULT_CODE_FAIL.getId()) {//2：支付失败回调
                    TrPay.getInstance((Activity) context).closePayView();//关闭快捷支付页面
                    //支付失败逻辑处理
                    userPayFail();
                }
            }

        });
    }

    private void callWXPayInterface(String tradeName, String tradeNo, Long cost, String backParams, String notifyurl, String userId) {
        TrPay.getInstance(this).callWxPay(tradeName, tradeNo, cost, backParams, notifyurl, userId, new PayResultListener() {
            @Override
            public void onPayFinish(Context context, String outtradeno, int resultCode, String resultString, int payType, Long amount, String tradename) {
                if (resultCode == TrPayResult.RESULT_CODE_SUCC.getId()) {//1：支付成功回调
                    TrPay.getInstance((Activity) context).closePayView();//关闭快捷支付页面
                    Toast.makeText(DrugDetailsActivity.this, resultString, Toast.LENGTH_LONG).show();
                    //支付成功逻辑处理
                    //pay success . save the data to server .wait
                    userPaySuccess(clientDrugListData.getOrders().getPaySerialNumber());
                } else if (resultCode == TrPayResult.RESULT_CODE_FAIL.getId()) {//2：支付失败回调
                    TrPay.getInstance((Activity) context).closePayView();//关闭快捷支付页面
                    //支付失败逻辑处理
                    userPayFail();
                }
            }
        });
    }

    private void userPaySuccess(String paySerialNumber) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                payNowOrHavePayCardView.setVisibility(View.VISIBLE);
            }
        });
        confirmDrugOrder(paySerialNumber);
    }

    private void userPayFail() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showErrorCardView(PAY_FAIL);
                payNowOrHavePayCardView.setVisibility(View.VISIBLE);
                recoveryPayNowOrHavePayButton(PAY_NOW);
            }
        });
    }

    /**
     * it is called after user pay success
     *
     * @param paySerialNumber
     */
    private void confirmDrugOrder(String paySerialNumber) {

        showNetworkCacheCardView();
        lockPayNowOrHavePayButton(BEING_VERIFICATION);
        String requestAction = "confirmOrder";
        String parameter = "&paySerialNumber=" + paySerialNumber;
        HttpUtil.sendOkHttpRequest(requestAction, parameter, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //because of network,the client can't recive message,but has success.
                closeNetworkCacheCardView();
                showErrorCardView(ACCESS_TIMEOUT);
                lockPayNowOrHavePayButton(ACCESS_TIMEOUT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                message = gson.fromJson(response.body().string(), Message.class);
                closeNetworkCacheCardView();
                if (message.isSuccess()) {
                    // show pay success
                    showPaySuccessCardView();
                    lockPayNowOrHavePayButton(HAVE_PAY);
                } else {
                    // other problems.it cause update fail
                    showErrorCardView(UNKNOWN_ERROR);
                    lockPayNowOrHavePayButton(UNKNOWN_ERROR);
                }
            }
        });

    }

    /**
     * let the button don't lock .react to click.
     *
     * @param inf
     */
    private void recoveryPayNowOrHavePayButton(final String inf) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                payNowOrHavePayButton.setClickable(true);
                payNowOrHavePayButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                payNowOrHavePayButton.setText(inf);
            }
        });
    }

    /**
     * Lock button.let it don't react to click.
     *
     * @param inf
     */
    private void lockPayNowOrHavePayButton(final String inf) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                payNowOrHavePayButton.setClickable(false);
                payNowOrHavePayButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
                payNowOrHavePayButton.setText(inf);
            }
        });
    }

    /**
     * this method must run at no mainthread
     */
    private void showPaySuccessCardView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                paySuccessCardView.setVisibility(View.VISIBLE);
            }
        });
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                paySuccessCardView.setVisibility(View.GONE);
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
        }
        return true;
    }

}
