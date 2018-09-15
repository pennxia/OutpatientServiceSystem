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
import com.nobitastudio.materialdesign.bean.MedicalCard;
import com.nobitastudio.materialdesign.bean.Message;
import com.nobitastudio.materialdesign.bean.User;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.HttpUtil;
import com.nobitastudio.materialdesign.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MedicalCardDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    ActionBar actionBar;
    TextView medicalCardOwnerNameTextView;
    TextView medicalCardNoTextView;
    TextView medicalCardOwnerSexTextView;
    TextView medicalCardOwnerIdCardTextView;
    Button agreeAndUnbindButton;
    CardView networkCacheCardView;
    TextView networkCacheTextView;
    CardView errorCardView;
    TextView errorTextView;

    User user;
    MedicalCard selectedMedicalCard;
    Gson gson;
    Message message;

    static final String ACCESS_TIMEOUT = "网络连接超时";
    static final String UNBIND_FAIL = "解绑失败,请重试";
    static final String UNBIND_SUCCESS = "解绑成功~";
    static final String BEING_UNBIND = "正在解绑,请稍后";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_card_details);

        init();
    }

    private void init() {

        initBaseData();

        //init widget
        toolbar = findViewById(R.id.activity_medicalcarddetails_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(null);
        }
        medicalCardOwnerNameTextView = findViewById(R.id.activity_medicalcarddetails_ownername_textview);
        medicalCardNoTextView = findViewById(R.id.activity_medicalcarddetails_medicalcardno_textview);
        medicalCardOwnerSexTextView = findViewById(R.id.activity_medicalcarddetails_ownersex_textview);
        medicalCardOwnerIdCardTextView = findViewById(R.id.activity_medicalcarddetails_owneridcard_textview);
        agreeAndUnbindButton = findViewById(R.id.activity_medicalcarddetails_agreeandunbind_button);
        networkCacheCardView = findViewById(R.id.activity_medicalcarddetails_networkcache_cardview);
        networkCacheTextView = findViewById(R.id.activity_medicalcarddetails_networkcache_textview);
        errorCardView = findViewById(R.id.activity_medicalcarddetails_error_cardview);
        errorTextView = findViewById(R.id.activity_medicalcarddetails_error_textview);

        //init listener
        agreeAndUnbindButton.setOnClickListener(this);

        //init widget data
        medicalCardOwnerNameTextView.setText(selectedMedicalCard.getOwnerName());
        medicalCardNoTextView.setText(selectedMedicalCard.getMedicalCardNo().toString());
        medicalCardOwnerSexTextView.setText(selectedMedicalCard.getOwnerSex());
        medicalCardOwnerIdCardTextView.setText(selectedMedicalCard.getOwnerIdCard());
    }

    private void initBaseData() {
        user = AppDataUtil.getUser();
        selectedMedicalCard = AppDataUtil.getSelectedMedicalCard();
        gson = new Gson();
    }

    @Override
    public void onClick(View v) {

        int viewId = v.getId();
        if (viewId == R.id.activity_medicalcarddetails_agreeandunbind_button) {
            // unbind operation .  the next update should show alert then unbind
            // show alert
            unbindMedicalCard(user.getUserAccount(), selectedMedicalCard.getMedicalCardNo());
        }
    }

    private void unbindMedicalCard(String userAccount, Long medicalCardNo) {

        showNetworkCacheCardView(BEING_UNBIND);
        String requestAction = "userUnbindMedicalCard";
        String parameter = "&account=" + userAccount + "&medicalCardNo=" + medicalCardNo;
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
                    // unbind success
                    Utility.showToastShort(MedicalCardDetailsActivity.this, UNBIND_SUCCESS);
                    AppDataUtil.getMedicalCards().remove(selectedMedicalCard);
                    MedicalCardDetailsActivity.this.finish();
                } else {
                    // unbind fail
                    showErrorCardView(UNBIND_FAIL);
                }
            }
        });
    }

    private void showNetworkCacheCardView(String inf) {
        networkCacheCardView.setVisibility(View.VISIBLE);
        networkCacheTextView.setText(inf);
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
