package com.nobitastudio.materialdesign.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.bean.Message;
import com.nobitastudio.materialdesign.bean.PictureVerificationCode;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.HttpUtil;
import com.nobitastudio.materialdesign.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CreateMedicalCardTwoActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    ActionBar actionBar;
    Button getVerificationCodeButton;
    Button nextStepButton;
    EditText pictureVerificationCodeEditText;
    ImageView pictureVerificationCodeImageView;//by glide to load picture into it,there only use local picture verificationCode
    EditText messageVerificationCodeEditText;
    CardView networkCacheCardView;
    CardView errorCardView;
    CardView sendSuccessCardView;
    TextView errorTextView;

    Message message;
    String ownerAccount;
    Gson gson;
    PictureVerificationCode pictureVerificationCode;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String currentDay;
    Integer sendCounts;
    boolean atMainThread;

    static final String DONT_INPUT_PICTURE_VERIFICATION_CODE = "请输入图片验证码";
    static final String DONT_INPUT_MESSAGE_VERIFICATION_CODE = "请输入短信验证码";
    static final String PICTURE_VERIFICATION_CODE_INVALID = "图片验证码错误";
    static final String MESSAGE_VERIFICATION_CODE_INVALID = "短信验证码错误";
    static final String MESSAGE_VERIFICATION_CODE_TIMEOUT = "短信验证码失效";
    static final String SEND_COUNTS_TODAY_OVER = "所发验证码已上限";
    static final String SEND_FAIL = "发送失败,请稍后再试";
    static final String ACCESS_TIMEOUT = "网络连接超时";
    static final int MESSAGE_TYPE = 3; //0.注册账号 1.找回密码 2.修改密码 3.办理诊疗卡 4.绑定诊疗卡

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_medical_card_two);

        init();
    }

    private void init() {

        //initBase data;
        initBaseData();

        //init widget
        toolbar = findViewById(R.id.activity_createmedicalcardotwo_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(null);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        pictureVerificationCodeEditText = findViewById(R.id.activity_createmedicalcardotwo_pictureverificationcode_edittext);
        pictureVerificationCodeImageView = findViewById(R.id.activity_createmedicalcardotwo_pictureverificationcode_imageview);
        messageVerificationCodeEditText = findViewById(R.id.activity_createmedicalcardotwo_messageverificationcode_edittext);
        getVerificationCodeButton = findViewById(R.id.activity_createmedicalcardotwo_getmessageverificationcode_button);
        nextStepButton = findViewById(R.id.activity_createmedicalcardotwo_nextstep_button);
        networkCacheCardView = findViewById(R.id.activity_createmedicalcardotwo_networkcache_cardview);
        errorCardView = findViewById(R.id.activity_createmedicalcardotwo_error_cardview);
        errorTextView = findViewById(R.id.activity_createmedicalcardotwo_error_textview);
        sendSuccessCardView = findViewById(R.id.activity_createmedicalcardotwo_paysuccess_cardview);

        //init widget data.
        initPictureVerificationCode();

        //init listener
        getVerificationCodeButton.setOnClickListener(this);
        nextStepButton.setOnClickListener(this);
    }

    private void initBaseData() {
        ownerAccount = AppDataUtil.getOwnerAccount();
        gson = new Gson();
        currentDay = Utility.getSimpleCurrentTime();
        preferences = getSharedPreferences(currentDay + ownerAccount, MODE_PRIVATE);
        editor = preferences.edit();
        sendCounts = preferences.getInt("sendCounts", 0);
        atMainThread = true;
    }

    private void initPictureVerificationCode() {
        //request for picture verficationcode
        pictureVerificationCodeImageView.setBackground(getResources().getDrawable(R.drawable.verificationcode_cache));
        pictureVerificationCode = null;
        String requestAction = "getPictureVerificationCode";
        HttpUtil.sendOkHttpRequest(requestAction, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initPictureVerificationCode();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                pictureVerificationCode = gson.fromJson(response.body().string(), PictureVerificationCode.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(getApplicationContext()).load(pictureVerificationCode.getUrl()).into(pictureVerificationCodeImageView);
                    }
                });
            }
        });

    }

    @Override
    public void onClick(View v) {

        int viewId = v.getId();
        if (viewId == R.id.activity_createmedicalcardotwo_getmessageverificationcode_button) {
            String pictureVerificationCodeText = pictureVerificationCodeEditText.getText().toString();
            if (pictureVerificationCodeText.isEmpty()) {
                showErrorCardView(DONT_INPUT_PICTURE_VERIFICATION_CODE);
            } else {
                boolean pictureVerificationCodeIsRight = judgePictureVerificationCode(pictureVerificationCodeText);
                if (pictureVerificationCodeIsRight) {
                    // juget the send counts today
                    if (sendCounts < 6) {
                        //can send ,send message verificationCode. and record send count,it must be below 5
                        lockGetVerificationCodeButton();
                        String requestAction = "getVerificationCode";
                        String parameter = "&account=" + ownerAccount + "&messageType=" + MESSAGE_TYPE;
                        HttpUtil.sendOkHttpRequest(requestAction, parameter, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                showErrorCardView(ACCESS_TIMEOUT);
                                recoveryGetVerificationCodeButton();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                message = gson.fromJson(response.body().string(), Message.class);
                                if (message.isSuccess()) {
                                    showSendSuccessCardView();
                                    //send success,record count of the user send message .it must below 5 a day.
                                    sendCounts++;
                                    editor.putInt("sendCounts", sendCounts);
                                    editor.apply();

                                    // create a thread to control the button text
                                    initButtonTextController();

                                } else {
                                    //send fail
                                    recoveryGetVerificationCodeButton();
                                    showErrorCardView(SEND_FAIL);
                                }
                            }

                        });
                    } else {
                        // can't send have over 5
                        showErrorCardView(SEND_COUNTS_TODAY_OVER);
                    }
                } else {
                    showErrorCardView(PICTURE_VERIFICATION_CODE_INVALID);
                    // change the local picture verificationCode
                    if (pictureVerificationCode != null){ //the pictureVerificationCode must be null .else the data will be load two times.
                        initPictureVerificationCode();
                    }
                }
            }

        } else if (viewId == R.id.activity_createmedicalcardotwo_nextstep_button) {
            String verificationCode = messageVerificationCodeEditText.getText().toString();
            if (verificationCode.isEmpty()) {
                showErrorCardView(DONT_INPUT_MESSAGE_VERIFICATION_CODE);
            } else {
                showNetworkCacheCardView();
                lockNextStepButton();
                String requestAction = "userConfirmVerificationCode";
                String parameter = "&account=" + ownerAccount + "&verificationCode=" + verificationCode;
                HttpUtil.sendOkHttpRequest(requestAction, parameter, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        closeNetworkCacheCardView();
                        showErrorCardView(ACCESS_TIMEOUT);
                        recoveryNextStepButton();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        closeNetworkCacheCardView();
                        message = gson.fromJson(response.body().string(), Message.class);
                        if (message.isSuccess()) {
                            Intent intent = new Intent(CreateMedicalCardTwoActivity.this, CreateMedicalCardThreeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            recoveryNextStepButton();
                            if (message.getDescribe().equals("验证码错误")) {
                                showErrorCardView(MESSAGE_VERIFICATION_CODE_INVALID);
                            } else if (message.getDescribe().equals("验证码已失效")) {
                                showErrorCardView(MESSAGE_VERIFICATION_CODE_TIMEOUT);
                            }
                        }
                    }

                });
            }

        }
        //other click operation
    }

    private void lockGetVerificationCodeButton() {
        getVerificationCodeButton.setClickable(false);
        getVerificationCodeButton.setText("正在发送");
        getVerificationCodeButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
    }

    private void recoveryGetVerificationCodeButton() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getVerificationCodeButton.setClickable(true);
                getVerificationCodeButton.setText("获取验证码");
                getVerificationCodeButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        });
    }

    private void lockNextStepButton() {
        nextStepButton.setClickable(false);
        nextStepButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
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

    private void initButtonTextController() {
        new Thread(new Runnable() {
            int seconds = 60;

            @Override
            public void run() {
                while (seconds >= 0) {
                    changeButtonText(seconds);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    seconds--;
                }
            }
        }).start();
    }

    private void changeButtonText(final int seconds) {
        if (atMainThread) {
            if (seconds != 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getVerificationCodeButton.setText("重新获取(" + seconds + "s)");
                    }
                });
            } else {
                recoveryGetVerificationCodeButton();
            }
        }
    }

    /**
     * judge the pictureVerificationCode.
     *
     * @param pictureVerificationCodeText
     * @return if it's right return true or return false
     */
    private boolean judgePictureVerificationCode(String pictureVerificationCodeText) {
        if (pictureVerificationCode == null) {
            return false;
        } else {
            return pictureVerificationCodeText.equals(pictureVerificationCode.getVerificationCode().toString());
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

    /**
     * this method must run at no mainthread
     */
    private void showSendSuccessCardView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sendSuccessCardView.setVisibility(View.VISIBLE);
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
                sendSuccessCardView.setVisibility(View.GONE);
            }
        });

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
        super.onBackPressed();
        atMainThread = false;
    }


}
