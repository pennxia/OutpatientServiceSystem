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
import com.nobitastudio.materialdesign.bean.MedicalCard;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BindMedicalCardOneActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    ActionBar actionBar;
    EditText medicalCardNoEditText;
    Button nextStepButton;
    CardView networkCacheCardView;
    CardView errorCardView;
    TextView errorTextView;

    MedicalCard waitForBindMedicalCard;
    Gson gson;

    static final String DONT_INPUT_MEDICALCARDNO = "请输入诊疗卡号";
    static final String MEDICALCARDNO_IS_RONG = "请输入正确诊疗卡号";
    static final String MEDICALCARDNO_DONT_FIND = "未查询到该诊疗卡";
    static final String ACCESS_TIMEOUT = "网络连接超时";
    static final int MEDICALCARDNO_LENGTH = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_medical_card_one);

        init();
    }

    private void init() {

        initBaseData();

        //init widget
        toolbar = findViewById(R.id.activity_bindmedicalcardone_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(null);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        medicalCardNoEditText = findViewById(R.id.activity_bindmedicalcardone_medicalcardno_edittext);
        nextStepButton = findViewById(R.id.activity_bindmedicalcardone_nextstep_button);
        networkCacheCardView = findViewById(R.id.activity_bindmedicalcardone_networkcache_cardview);
        errorCardView = findViewById(R.id.activity_bindmedicalcardone_error_cardview);
        errorTextView = findViewById(R.id.activity_bindmedicalcardone_error_textview);

        //init listener
        nextStepButton.setOnClickListener(this);
    }

    private void initBaseData() {
        gson = new Gson();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.activity_bindmedicalcardone_nextstep_button) {
            //prepare to bind medicalcard.will init waitForBindMedicalCard.
            String medicalCardNo = medicalCardNoEditText.getText().toString();
            if (medicalCardNo.isEmpty()) {
                showErrorCardView(DONT_INPUT_MEDICALCARDNO);
            } else if (medicalCardNo.length() != MEDICALCARDNO_LENGTH || !medicalCardNoIsNumber(medicalCardNo)) {
                showErrorCardView(MEDICALCARDNO_IS_RONG);
            } else {
                //the medicalcard is legal
                showNetworkCacheCardView();
                lockNextStepButton();
                String requestAction = "medicalCardWhetherExist";
                String parameter = "&medicalCardNo=" + medicalCardNo;
                HttpUtil.sendOkHttpRequest(requestAction, parameter, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        closeNetworkCacheCardView();
                        showErrorCardView(ACCESS_TIMEOUT);
                        recoveryNextStepButton();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        waitForBindMedicalCard = gson.fromJson(response.body().string(), MedicalCard.class);
                        closeNetworkCacheCardView();
                        if (waitForBindMedicalCard.getOwnerName().isEmpty() || waitForBindMedicalCard.getOwnerAccount().equals(0)) {
                            //don't find the medicalcard
                            showErrorCardView(MEDICALCARDNO_DONT_FIND);
                            recoveryNextStepButton();
                        } else {
                            //save waitForBindMedicalCard to global.
                            AppDataUtil.setWaitForBindMedicalCard(waitForBindMedicalCard);
                            Intent intent = new Intent(BindMedicalCardOneActivity.this, BindMedicalCardTwoActivity.class);
                            startActivity(intent);
                            BindMedicalCardOneActivity.this.finish();
                        }

                    }
                });


            }


        }
    }

    boolean medicalCardNoIsNumber(String medicalCardNo) {
        boolean isNumber = true;
        for (int i = 0; i < medicalCardNo.length(); i++) {
            char c = medicalCardNo.charAt(i);
            if (!(c <= '9' && c >= '0')) {
                isNumber = false;
                break;
            }
        }
        return isNumber;
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
