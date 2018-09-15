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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.adapter.PaymentSimpleDataRecycleViewAdapter;
import com.nobitastudio.materialdesign.bean.AllPayment;
import com.nobitastudio.materialdesign.bean.CheckInspectionCost;
import com.nobitastudio.materialdesign.bean.ClientCheckInspectionCost;
import com.nobitastudio.materialdesign.bean.ClientDrugCost;
import com.nobitastudio.materialdesign.bean.ClientOperationCost;
import com.nobitastudio.materialdesign.bean.ClientRegistrationCost;
import com.nobitastudio.materialdesign.bean.DrugCost;
import com.nobitastudio.materialdesign.bean.Message;
import com.nobitastudio.materialdesign.bean.OperationCost;
import com.nobitastudio.materialdesign.bean.Orders;
import com.nobitastudio.materialdesign.bean.PaymentSimpleData;
import com.nobitastudio.materialdesign.bean.Registration;
import com.nobitastudio.materialdesign.bean.User;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.HttpUtil;
import com.nobitastudio.materialdesign.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.internal.Util;

/**
 * it show all pay,have pay,wait pay,cancel pay.
 * create by nobita 2018.01.17
 */
public class OutpatientPaymentActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    ActionBar actionBar;
    TextView allPaymentTextView;
    ImageView allPaymentImageView;
    TextView waitPaymentTextView;
    ImageView waitPaymentImageView;
    TextView havePaymentTextView;
    ImageView havePaymentImageView;
    TextView cancelPaymentTextView;
    ImageView cancelPaymentImageView;
    RecyclerView recyclerView;
    CardView networkCacheCardView;
    CardView errorCardView;
    TextView errorTextView;

    Message message;
    Gson gson;
    User user;
    List<AllPayment> allPayments;
    List<ClientRegistrationCost> clientRegistrationCosts;
    List<ClientDrugCost> clientDrugCosts;
    List<ClientOperationCost> clientOperationCosts;
    List<ClientCheckInspectionCost> clientCheckInspectionCosts;

    List<PaymentSimpleData> displayPaymentSimpleDataList;
    PaymentSimpleDataRecycleViewAdapter adapter;
    int currentPaymentNo = 0;

    static int ALL_PAYMENT_NO = 6001;
    static int WAIT_PAYMENT_NO = 6002;
    static int HAVE_PAYMENT_NO = 6003;
    static int CANCEL_PAYMENT_NO = 6004;

    static final String ACCESS_TIMEOUT = "网络连接超时";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outpatient_payment);

        init();
    }

    private void init() {

        initBaseData();

        //init widget
        toolbar = findViewById(R.id.activity_outpatientpaymentactivity_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(null);
        }
        allPaymentTextView = findViewById(R.id.activity_outpatientpaymentactivity_all_textview);
        allPaymentImageView = findViewById(R.id.activity_outpatientpaymentactivity_all_imageview);
        waitPaymentTextView = findViewById(R.id.activity_outpatientpaymentactivity_wait_textview);
        waitPaymentImageView = findViewById(R.id.activity_outpatientpaymentactivity_wait_imageview);
        havePaymentTextView = findViewById(R.id.activity_outpatientpaymentactivity_have_textview);
        havePaymentImageView = findViewById(R.id.activity_outpatientpaymentactivity_have_imageview);
        cancelPaymentTextView = findViewById(R.id.activity_outpatientpaymentactivity_cancel_textview);
        cancelPaymentImageView = findViewById(R.id.activity_outpatientpaymentactivity_cancel_imageview);
        recyclerView = findViewById(R.id.activity_outpatientpaymentactivity_recycleview);
        networkCacheCardView = findViewById(R.id.activity_outpatientpaymentactivity_networkcache_cardview);
        errorCardView = findViewById(R.id.activity_outpatientpaymentactivity_error_cardview);
        errorTextView = findViewById(R.id.activity_outpatientpaymentactivity_error_textview);

        //init listener
        allPaymentTextView.setOnClickListener(this);
        allPaymentImageView.setOnClickListener(this);
        waitPaymentTextView.setOnClickListener(this);
        waitPaymentImageView.setOnClickListener(this);
        havePaymentTextView.setOnClickListener(this);
        havePaymentImageView.setOnClickListener(this);
        cancelPaymentTextView.setOnClickListener(this);
        cancelPaymentImageView.setOnClickListener(this);

        //init adapter
        adapter = new PaymentSimpleDataRecycleViewAdapter(this, displayPaymentSimpleDataList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        //init allPayment data
        initAllPaymentData();
    }

    private void initBaseData() {
        allPayments = new ArrayList<>();
        clientRegistrationCosts = new ArrayList<>();
        clientDrugCosts = new ArrayList<>();
        clientOperationCosts = new ArrayList<>();
        clientCheckInspectionCosts = new ArrayList<>();
        displayPaymentSimpleDataList = new ArrayList<>();
        gson = new Gson();
        user = AppDataUtil.getUser();

        //save clientRegistrations to global data.
        //if need,save drugList,operationList,checkinSpectionList.
        AppDataUtil.setAllPayments(allPayments);
        AppDataUtil.setDisplayPaymentSimpleDataList(displayPaymentSimpleDataList);

    }

    private void initAllPaymentData() {
        //show network cardView
        showNetworkCacheCardView();

        String requestAction = "getAllPayment";
        String parameter = "&account=" + user.getUserAccount();
        HttpUtil.sendOkHttpRequest(requestAction, parameter, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                closeNetworkCacheCardView();
                showErrorCardView(ACCESS_TIMEOUT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                AllPayment allPayment = new Gson().fromJson(response.body().string(), AllPayment.class);
                closeNetworkCacheCardView();
                clientRegistrationCosts.addAll(allPayment.getClientRegistrationCosts());
                clientDrugCosts.addAll(allPayment.getClientDrugCosts());
                clientOperationCosts.addAll(allPayment.getClientOperationCosts());
                clientCheckInspectionCosts.addAll(allPayment.getClientCheckInspectionCosts());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showSelectedPayment(WAIT_PAYMENT_NO);
                    }
                });
            }
        });

    }

    @Override
    public void onClick(View v) {

        int viewId = v.getId();
        if (viewId == R.id.activity_outpatientpaymentactivity_all_textview || viewId == R.id.activity_outpatientpaymentactivity_all_imageview) {
            //show all payment 1.update ui 2. update adapter
            showSelectedPayment(ALL_PAYMENT_NO);
        } else if (viewId == R.id.activity_outpatientpaymentactivity_wait_textview || viewId == R.id.activity_outpatientpaymentactivity_wait_imageview) {
            //show wait payment 1.update ui 2. update adapter
            showSelectedPayment(WAIT_PAYMENT_NO);
        } else if (viewId == R.id.activity_outpatientpaymentactivity_have_textview || viewId == R.id.activity_outpatientpaymentactivity_have_imageview) {
            //show have payment 1.update ui 2. update adapter
            showSelectedPayment(HAVE_PAYMENT_NO);
        } else if (viewId == R.id.activity_outpatientpaymentactivity_cancel_textview || viewId == R.id.activity_outpatientpaymentactivity_cancel_imageview) {
            //show cancel payment 1.update ui 2. update adapter
            showSelectedPayment(CANCEL_PAYMENT_NO);
        }

    }

    private void showSelectedPayment(int needToshowPaymentNo) {
        if (currentPaymentNo != needToshowPaymentNo) {
            //update UI
            updatePaymentKindsUi(needToshowPaymentNo);
            //update adapter
            updateAdapter(needToshowPaymentNo);
            currentPaymentNo = needToshowPaymentNo;
        }
    }

    private void updatePaymentKindsUi(int needToshowPaymentNo) {
        darkPaymentKindsUi(currentPaymentNo);
        lightPaymentKindsUi(needToshowPaymentNo);
    }

    private void darkPaymentKindsUi(int needToDarkPayment) {
        if (needToDarkPayment == ALL_PAYMENT_NO) {
            allPaymentTextView.setTextColor(getResources().getColor(R.color.colorBlack));
            allPaymentImageView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        } else if (needToDarkPayment == WAIT_PAYMENT_NO) {
            waitPaymentTextView.setTextColor(getResources().getColor(R.color.colorBlack));
            waitPaymentImageView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        } else if (needToDarkPayment == HAVE_PAYMENT_NO) {
            havePaymentTextView.setTextColor(getResources().getColor(R.color.colorBlack));
            havePaymentImageView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        } else if (needToDarkPayment == CANCEL_PAYMENT_NO) {
            cancelPaymentTextView.setTextColor(getResources().getColor(R.color.colorBlack));
            cancelPaymentImageView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        }
    }

    private void lightPaymentKindsUi(int needToLightPayment) {
        if (needToLightPayment == ALL_PAYMENT_NO) {
            allPaymentTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
            allPaymentImageView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else if (needToLightPayment == WAIT_PAYMENT_NO) {
            waitPaymentTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
            waitPaymentImageView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else if (needToLightPayment == HAVE_PAYMENT_NO) {
            havePaymentTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
            havePaymentImageView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else if (needToLightPayment == CANCEL_PAYMENT_NO) {
            cancelPaymentTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
            cancelPaymentImageView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    private void updateAdapter(int needToShowPaymentNo) {

        displayPaymentSimpleDataList.clear();
        PaymentSimpleData paymentSimpleData; //for save memory,use a object.only chage it's data.

        // add registration data
        if (clientRegistrationCosts.size() > 0) {
            for (ClientRegistrationCost clientRegistrationCost : clientRegistrationCosts) {
                Registration registration = clientRegistrationCost.getRegistration();
                Orders orders = clientRegistrationCost.getOrders();
                Integer paymentItemType = Utility.CHARGE_ITEM_TYPE_REGISTER;
                String itemCostId = registration.getRegistrationNo();
                Double cost = orders.getAllPrice();
                Integer payState = orders.getOrderState();
                String payOrCancelTime = orders.getPayOrCancelTime();
                paymentSimpleData = new PaymentSimpleData(paymentItemType, itemCostId, cost, payState, payOrCancelTime);
                if (needToShowPaymentNo == ALL_PAYMENT_NO) {
                    displayPaymentSimpleDataList.add(paymentSimpleData);
                } else if (needToShowPaymentNo == WAIT_PAYMENT_NO) {
                    if (payState.equals(0)) {
                        displayPaymentSimpleDataList.add(paymentSimpleData);
                    }
                } else if (needToShowPaymentNo == HAVE_PAYMENT_NO) {
                    if (payState.equals(1)) {
                        displayPaymentSimpleDataList.add(paymentSimpleData);
                    }
                } else if (needToShowPaymentNo == CANCEL_PAYMENT_NO) {
                    if (payState.equals(2)) {
                        displayPaymentSimpleDataList.add(paymentSimpleData);
                    }
                }
            }
        }

        //add DrugCost data
        if (clientDrugCosts.size() > 0) {
            for (ClientDrugCost clientDrugCost : clientDrugCosts) {
                DrugCost drugCost = clientDrugCost.getDrugCost();
                Orders orders = clientDrugCost.getOrders();
                Integer paymentItemType = Utility.CHARGE_ITEM_TYPE_DRUGCOST;
                String itemCostId = drugCost.getDrugCostId();
                Double cost = drugCost.getAllPrice();
                Integer payState = orders.getOrderState();
                String payOrCancelTime = orders.getPayOrCancelTime();
                paymentSimpleData = new PaymentSimpleData(paymentItemType, itemCostId, cost, payState, payOrCancelTime);
                if (needToShowPaymentNo == ALL_PAYMENT_NO) {
                    displayPaymentSimpleDataList.add(paymentSimpleData);
                } else if (needToShowPaymentNo == WAIT_PAYMENT_NO) {
                    if (payState.equals(0)) {
                        displayPaymentSimpleDataList.add(paymentSimpleData);
                    }
                } else if (needToShowPaymentNo == HAVE_PAYMENT_NO) {
                    if (payState.equals(1)) {
                        displayPaymentSimpleDataList.add(paymentSimpleData);
                    }
                } else if (needToShowPaymentNo == CANCEL_PAYMENT_NO) {
                    if (payState.equals(2)) {
                        displayPaymentSimpleDataList.add(paymentSimpleData);
                    }
                }
            }
        }

        //add OperationCost data
        if (clientOperationCosts.size() > 0) {
            for (ClientOperationCost clientOperationCost : clientOperationCosts) {
                OperationCost operationCost = clientOperationCost.getOperationCost();
                Orders orders = clientOperationCost.getOrders();

                Integer paymentItemType = Utility.CHARGE_ITEM_TYPE_OPERATIONCOST;
                String orderId = operationCost.getOperationCostId();
                Double cost = operationCost.getAllPrice();
                Integer payState = orders.getOrderState();
                String payOrCancelTime = orders.getPayOrCancelTime();
                paymentSimpleData = new PaymentSimpleData(paymentItemType, orderId, cost, payState, payOrCancelTime);
                if (needToShowPaymentNo == ALL_PAYMENT_NO) {
                    displayPaymentSimpleDataList.add(paymentSimpleData);
                } else if (needToShowPaymentNo == WAIT_PAYMENT_NO) {
                    if (payState.equals(0)) {
                        displayPaymentSimpleDataList.add(paymentSimpleData);
                    }
                } else if (needToShowPaymentNo == HAVE_PAYMENT_NO) {
                    if (payState.equals(1)) {
                        displayPaymentSimpleDataList.add(paymentSimpleData);
                    }
                } else if (needToShowPaymentNo == CANCEL_PAYMENT_NO) {
                    if (payState.equals(2)) {
                        displayPaymentSimpleDataList.add(paymentSimpleData);
                    }
                }
            }
        }

        //add CheckInspectionCost data
        if (clientCheckInspectionCosts.size() > 0) {
            for (ClientCheckInspectionCost clientCheckInspectionCost : clientCheckInspectionCosts) {
                CheckInspectionCost checkInspectionCost = clientCheckInspectionCost.getCheckInspectionCost();
                Orders orders = clientCheckInspectionCost.getOrders();

                Integer paymentItemType = Utility.CHARGE_ITEM_TYPE_CHECKINSPECTIONCOST;
                String orderId = checkInspectionCost.getCheckInspectionCostId();
                Double cost = checkInspectionCost.getAllPrice();
                Integer payState = orders.getOrderState();
                String payOrCancelTime = orders.getPayOrCancelTime();
                paymentSimpleData = new PaymentSimpleData(paymentItemType, orderId, cost, payState, payOrCancelTime);
                if (needToShowPaymentNo == ALL_PAYMENT_NO) {
                    displayPaymentSimpleDataList.add(paymentSimpleData);
                } else if (needToShowPaymentNo == WAIT_PAYMENT_NO) {
                    if (payState.equals(0)) {
                        displayPaymentSimpleDataList.add(paymentSimpleData);
                    }
                } else if (needToShowPaymentNo == HAVE_PAYMENT_NO) {
                    if (payState.equals(1)) {
                        displayPaymentSimpleDataList.add(paymentSimpleData);
                    }
                } else if (needToShowPaymentNo == CANCEL_PAYMENT_NO) {
                    if (payState.equals(2)) {
                        displayPaymentSimpleDataList.add(paymentSimpleData);
                    }
                }
            }
        }
        adapter.notifyDataSetChanged();

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
