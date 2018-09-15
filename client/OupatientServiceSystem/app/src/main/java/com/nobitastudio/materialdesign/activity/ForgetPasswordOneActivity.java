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

public class ForgetPasswordOneActivity extends AppCompatActivity  implements View.OnClickListener {

    Toolbar toolbar;
    ActionBar actionBar;
    EditText accountEditText;
    Button nextStepButton;
    CardView networkCacheCardView;
    CardView errorCardView;
    TextView errorTextView;

    String updatePasswordAccount;

    Gson gson;
    Message message;

    static final String DONT_INPUT_ACCOUNT_ALTER_INFORMATION = "请输入手机号";
    static final String ACCOUNT_IS_RONG_ALTER_INFORMATION = "请输入正确手机号";
    static final String ACCOUNT_DONT_ENROLL = "该手机号尚未注册";
    static final String ACCESS_TIMEOUT = "网络连接超时";
    static final int PHONE_NUM_LENGTH = 11;

    static final String queryType = "2";// 1 references enroll  2 references update password.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_one);

        init();
    }

    private void init() {

        initBaseData();

        //init widget
        toolbar = findViewById(R.id.activity_forgetpasswordone_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(null);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        accountEditText = findViewById(R.id.activity_forgetpasswordone_account_edittext);
        nextStepButton = findViewById(R.id.activity_forgetpasswordone_nextstep_button);
        networkCacheCardView = findViewById(R.id.activity_forgetpasswordone_network_cache_cardview);
        errorCardView = findViewById(R.id.activity_forgetpasswordone_error_cardview);
        errorTextView = findViewById(R.id.activity_forgetpasswordone_error_textview);

        //init listener
        nextStepButton.setOnClickListener(this);
    }

    private void initBaseData() {
        gson = new Gson();
    }

    @Override
    public void onClick(View v) {

        int viewId = v.getId();
        if (viewId == R.id.activity_forgetpasswordone_nextstep_button) {
            updatePasswordAccount = accountEditText.getText().toString();
            if (updatePasswordAccount == null || updatePasswordAccount.length() == 0) {
                //show the alert information
                showErrorCardView(DONT_INPUT_ACCOUNT_ALTER_INFORMATION);
            } else if (updatePasswordAccount.length() != PHONE_NUM_LENGTH) {
                showErrorCardView(ACCOUNT_IS_RONG_ALTER_INFORMATION);
            }else {
                //the account is right
                showNetworkCacheCardView();
                lockNextStepButton();
                String requestAction = "whetherHaveRecord";
                String parameter = "&account=" + updatePasswordAccount + "&queryType=" + queryType;
                HttpUtil.sendOkHttpRequest(requestAction, parameter, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        closeNetworkCacheCardView();
                        showErrorCardView(ACCESS_TIMEOUT);
                        recoveryNextStepButton();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        message = gson.fromJson(response.body().string(),Message.class);
                        closeNetworkCacheCardView();
                        if (message.isSuccess()){
                            AppDataUtil.setUpdatePasswordAccount(updatePasswordAccount);
                            Intent intent = new Intent(ForgetPasswordOneActivity.this, ForgetPasswordTwoActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            showErrorCardView(ACCOUNT_DONT_ENROLL);
                            recoveryNextStepButton();
                        }
                    }

                });
            }
        }

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
