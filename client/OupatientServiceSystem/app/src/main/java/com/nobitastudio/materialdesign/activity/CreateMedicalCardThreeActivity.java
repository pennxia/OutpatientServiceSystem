package com.nobitastudio.materialdesign.activity;

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
import com.nobitastudio.materialdesign.bean.Message;
import com.nobitastudio.materialdesign.bean.User;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.HttpUtil;
import com.nobitastudio.materialdesign.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CreateMedicalCardThreeActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    ActionBar actionBar;
    EditText ownerNameEditText;
    EditText ownerSexEditText;
    EditText ownerIdCardEditText;
    EditText ownerAddressEditText;
    Button agreeAndCreateButton;
    CardView networkCacheCardView;
    CardView errorCardView;
    TextView errorTextView;

    User user;
    Gson gson;
    String ownerAccount;

    static final String DONT_INPUT_NAME = "请输入姓名";
    static final String DONT_INPUT_SEX = "请输入性别";
    static final String DONT_INPUT_IDCARD = "请输入身份证号";
    static final String DONT_INPUT_ADDRESS = "请输入联系地址";
    static final String NAME_IS_ILLEGAL = "请输入正确姓名";
    static final String SEX_IS_ILLEGAL = "请输入正确性别";
    static final String IDCARD_IS_ILLEGAL = "请输入正确身份证号";
    static final String ENROLL_FAIL = "办理失败，请重试";
    static final String ACCESS_TIMEOUT = "网络连接超时";

    boolean isTest = true;  //因为虚拟机上不能打出来中文，传递给服务器的数据会有问题，在真机上测试都是没问题的，只需要测试与服务器交互部分。通过这个条件跳过很多没用的过程，在真实发布的时候，去除该条件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_medical_card_three);

        init();
    }

    private void init() {

        initBaseData();

        //init widget
        toolbar = findViewById(R.id.activity_createmedicalcardthree_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(null);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ownerNameEditText = findViewById(R.id.activity_createmedicalcardthree_name_edittext);
        ownerSexEditText = findViewById(R.id.activity_createmedicalcardthree_sex_edittext);
        ownerIdCardEditText = findViewById(R.id.activity_createmedicalcardthree_idcard_edittext);
        ownerAddressEditText = findViewById(R.id.activity_createmedicalcardthree_address_edittext);
        agreeAndCreateButton = findViewById(R.id.activity_createmedicalcardthree_agreeandcreate_button);
        networkCacheCardView = findViewById(R.id.activity_createmedicalcardthree_networkcache_cardview);
        errorCardView = findViewById(R.id.activity_createmedicalcardthree_error_cardview);
        errorTextView = findViewById(R.id.activity_createmedicalcardthree_error_textview);

        //init listener
        agreeAndCreateButton.setOnClickListener(this);
    }

    private void initBaseData() {
        ownerAccount = AppDataUtil.getOwnerAccount();
        user = AppDataUtil.getUser();
        gson = new Gson();
    }


    @Override
    public void onClick(View v) {

        int viewId = v.getId();
        if (viewId == R.id.activity_createmedicalcardthree_agreeandcreate_button) {
            String ownerName = ownerNameEditText.getText().toString();
            String ownerSex = ownerSexEditText.getText().toString();
            String ownerIdCard = ownerIdCardEditText.getText().toString();
            String ownerAddress = ownerAddressEditText.getText().toString();
            if (ownerName.isEmpty()) {
                showErrorCardView(DONT_INPUT_NAME);
            } else if (ownerSex.isEmpty()) {
                showErrorCardView(DONT_INPUT_SEX);
            } else if (ownerIdCard.isEmpty()) {
                showErrorCardView(DONT_INPUT_IDCARD);
            } else if (ownerAddress.isEmpty()) {
                showErrorCardView(DONT_INPUT_ADDRESS);
            } else if (!ownerNameIsChinese(ownerName)) {
                showErrorCardView(NAME_IS_ILLEGAL);
            } else if (!ownerSex.equals("男") && !ownerSex.equals("女")) {
                showErrorCardView(SEX_IS_ILLEGAL);
            } else if (ownerIdCard.length() != 18 || !ownerIdCardIsRight(ownerIdCard)) {
                showErrorCardView(IDCARD_IS_ILLEGAL);
            } else {
                // all message is right create medicalcard
                showNetworkCacheCardView();
                String requestAction = "userCreateMedicalCard";
                String parameter = "&account=" + user.getUserAccount()
                        + "&ownerAccount=" + ownerAccount
                        + "&ownerName=" + ownerName
                        + "&ownerSex=" + ownerSex
                        + "&ownerIdCard=" + ownerIdCard
                        + "&ownerAddress=" + ownerAddress;
                HttpUtil.sendOkHttpRequest(requestAction, parameter, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        closeNetworkCacheCardView();
                        showErrorCardView(ACCESS_TIMEOUT);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        MedicalCard medicalCard = gson.fromJson(response.body().string(), MedicalCard.class);
                        closeNetworkCacheCardView();
                        if (medicalCard.getOwnerName().isEmpty() || medicalCard.getOwnerAccount().equals(0)) {
                            //create fail
                            showErrorCardView(ENROLL_FAIL);
                        } else {
                            //create success .bind the medicalcard to the user.finish
                            AppDataUtil.getMedicalCards().add(medicalCard);
                            Utility.showToastLong(CreateMedicalCardThreeActivity.this, "恭喜您已办理成功~");
                            CreateMedicalCardThreeActivity.this.finish();
                        }
                    }
                });
            }

        }
    }

    /**
     * judge the id card whether is legal. 1 ~ 17 is number.and the 18 is number or X .x
     *
     * @param ownerIdCard
     * @return
     */
    boolean ownerIdCardIsRight(String ownerIdCard) {

        boolean idCardIsRight = true;
        for (int i = 0; i < ownerIdCard.length() - 1; i++) {
            char c = ownerIdCard.charAt(i);
            if (!('0' <= c && c <= '9')) {
                idCardIsRight = false;
                break;
            }
        }

        if (idCardIsRight) {
            char c2 = ownerIdCard.charAt(ownerIdCard.length() - 1);
            if (!('0' <= c2 && c2 <= '9') && c2 != 'x' && c2 != 'X') {
                idCardIsRight = false;
            }
        }

        return idCardIsRight;
    }

    /**
     * judget ownerName whether it is chinese.
     *
     * @param ownerName
     * @return ownerName is chinese returen true,or return false
     */
    boolean ownerNameIsChinese(String ownerName) {
        boolean isChinese = true;
        for (int i = 0; i < ownerName.length(); i++) {
            char c = ownerName.charAt(i);
            if (c < 0x4E00 || c > 0x9FA5) {  //汉字的字节码范围：0x4E00 ~ 0x9FA5
                isChinese = false;
                break;
            }
        }
        return isChinese;
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
                    Thread.sleep(1200);
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
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

}
