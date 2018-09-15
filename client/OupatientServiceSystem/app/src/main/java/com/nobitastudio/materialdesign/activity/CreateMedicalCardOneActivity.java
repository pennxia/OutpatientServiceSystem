package com.nobitastudio.materialdesign.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.bean.Message;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CreateMedicalCardOneActivity extends AppCompatActivity implements View.OnClickListener {


    Toolbar toolbar;
    ActionBar actionBar;
    EditText ownerAccountEditText;
    Button nextStepButton;
    CardView networkCacheCardView;
    CardView errorCardView;
    TextView errorTextView;

    Gson gson;
    Message message;
    String ownerAccount;

    static final String DONT_INPUT_ACCOUNT = "请输入手机号";
    static final String ACCOUNT_IS_RONG = "请输入正确手机号";
    static final String ACCOUNT_HASBEEN_LIMITED = "该号码办理数以上限";
    static final String ACCESS_TIMEOUT = "网络连接超时";
    static final int PHONE_NUM_LENGTH = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_medical_card_one);

        init();
    }


    private void init() {

        initBaseData();

        //init widget
        toolbar = findViewById(R.id.activity_createmedicalcardone_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(null);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ownerAccountEditText = findViewById(R.id.activity_createmedicalcardone_phonenumber_edittext);
        nextStepButton = findViewById(R.id.activity_createmedicalcardone_nextstep_button);
        networkCacheCardView = findViewById(R.id.activity_createmedicalcardone_networkcache_cardview);
        errorCardView = findViewById(R.id.activity_createmedicalcardone_error_cardview);
        errorTextView = findViewById(R.id.activity_createmedicalcardone_error_textview);

        //init listener
        nextStepButton.setOnClickListener(this);
    }

    private void initBaseData() {
        gson = new Gson();
    }

    @Override
    public void onClick(View v) {

        int viewId = v.getId();
        if (viewId == R.id.activity_createmedicalcardone_nextstep_button) {
            ownerAccount = ownerAccountEditText.getText().toString();
            if (ownerAccount.length() == 0) {
                //show the alert information
                showErrorCardView(DONT_INPUT_ACCOUNT);
            } else if (ownerAccount.length() != PHONE_NUM_LENGTH) {
                showErrorCardView(ACCOUNT_IS_RONG);
            } else {
                //the account is right
                showNetworkCacheCardView();
                lockNextStepButton();
                String requestAction = "whetherCreateMedicalCard";
                String parameter = "&ownerAccount=" + ownerAccount;
                HttpUtil.sendOkHttpRequest(requestAction, parameter, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        closeNetworkCacheCardView();
                        showErrorCardView(ACCESS_TIMEOUT);
                        recoveryNextStepButton();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        message = gson.fromJson(response.body().string(), Message.class);
                        closeNetworkCacheCardView();
                        if (message.isSuccess()) {
                            recoveryNextStepButton();
                            AppDataUtil.setOwnerAccount(ownerAccount);
                            //for save message number.there enter CreateMedicalCardThreeActivity
                            Intent intent = new Intent(CreateMedicalCardOneActivity.this, CreateMedicalCardTwoActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            showErrorCardView(ACCOUNT_HASBEEN_LIMITED);
                            recoveryNextStepButton();
                        }
                    }

                });
            }
        }

    }

    private void recoveryNextStepButton() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nextStepButton.setClickable(true);
                nextStepButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        });
    }

    private void lockNextStepButton() {
        nextStepButton.setClickable(false);
        nextStepButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
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
                    Thread.sleep(1800);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
        }
        return true;
    }

}
