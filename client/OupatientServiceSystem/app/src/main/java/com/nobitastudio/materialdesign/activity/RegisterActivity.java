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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.bean.Diagnosis;
import com.nobitastudio.materialdesign.bean.Doctor;
import com.nobitastudio.materialdesign.bean.MedicalCard;
import com.nobitastudio.materialdesign.bean.OutpatientDepartment;
import com.nobitastudio.materialdesign.bean.PictureVerificationCode;
import com.nobitastudio.materialdesign.bean.User;
import com.nobitastudio.materialdesign.bean.Visit;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.HttpUtil;
import com.nobitastudio.materialdesign.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    ActionBar actionBar;
    TextView registerChooseTextview;
    ImageView registerChooseImageView;
    Button confirmButton;
    TextView registerOutpatienDepartmentTextView;
    TextView registerDoctorNameTextView;
    TextView registerYearMonthDateTextView;
    TextView registerTimeSlotTextView;
    TextView registerAddressTextView;
    TextView registerCostTextView;
    EditText pictureVerificationCodeEditText;
    ImageView pictureVerificationCodeImageView;
    CardView networkCacheCardView;
    CardView errorCardView;
    TextView errorTextView;

    OutpatientDepartment selectedOutpatientDepartment;
    Doctor selectedDoctor;
    User user;
    Visit selectedVisit;
    PictureVerificationCode pictureVerificationCode;
    Gson gson;
    MedicalCard selectedMedicalCard;
    Diagnosis diagnosis;

    static final int REQUEST_CODE_REGISTERACTIVITY_TO_CHOOSEMEDICALCARDACTIVITY = 201;
    static final String DONT_INPUT_PICTURE_VERIFICATION_CODE = "请输入图片验证码";
    static final String PICTURE_VERIFICATION_CODE_INVALID = "图片验证码错误";
    static final String DONT_CHOOSE_MEDICALCARD = "请选择就诊人";
    static final String ACCESS_TIMEOUT = "网络连接超时";
    static final String CANT_REGISTER = "当前号源以挂完";

    private static int REGISTER_ACTIVITY_NO = 5002; // references registerActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    private void init() {

        initBaseData();

        //init widget
        toolbar = findViewById(R.id.activity_register_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(null);
        }

        registerChooseTextview = findViewById(R.id.activity_register_choose_textview);
        registerChooseImageView = findViewById(R.id.activity_register_choose_imageview);
        confirmButton = findViewById(R.id.activity_register_confirm_button);
        registerOutpatienDepartmentTextView = findViewById(R.id.activity_register_outpatientdepartment_textview);
        registerDoctorNameTextView = findViewById(R.id.activity_register_doctorname_textview);
        registerYearMonthDateTextView = findViewById(R.id.activity_register_yearmonthdate_textview);
        registerTimeSlotTextView = findViewById(R.id.activity_register_timeslot_textview);
        registerAddressTextView = findViewById(R.id.activity_register_address_textview);
        registerCostTextView = findViewById(R.id.activity_register_cost_textview);
        pictureVerificationCodeEditText = findViewById(R.id.activity_register_pictureverificationcode_edittext);
        pictureVerificationCodeImageView = findViewById(R.id.activity_register_pictrueverificationcode_imageview);
        networkCacheCardView = findViewById(R.id.activity_register_network_cache_cardview);
        errorCardView = findViewById(R.id.activity_register_error_cardview);
        errorTextView = findViewById(R.id.activity_register_error_textview);

        //init widget data
        initWidgetDate();

        //init lisetener
        registerChooseTextview.setOnClickListener(this);
        registerChooseImageView.setOnClickListener(this);
        confirmButton.setOnClickListener(this);
    }

    private void initBaseData() {

        selectedOutpatientDepartment = AppDataUtil.getSelectedOutpatientDepartment();
        selectedDoctor = AppDataUtil.getSelectedDoctor();
        user = AppDataUtil.getUser();
        selectedVisit = AppDataUtil.getSelectedVisit();
        gson = new Gson();

    }

    private void initWidgetDate() {
        registerOutpatienDepartmentTextView.setText(selectedOutpatientDepartment.getOutpatientDepartmentName());
        registerDoctorNameTextView.setText(selectedDoctor.getDoctorName());
        registerYearMonthDateTextView.setText(Utility.handleYear(selectedVisit.getYears()));
        registerTimeSlotTextView.setText(selectedVisit.getTimeSlot());
        registerAddressTextView.setText(selectedOutpatientDepartment.getDepartmentAddress());
        registerCostTextView.setText(selectedVisit.getCost().toString() + " 元");

        initPictureVerificationCode();
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
        if (viewId == R.id.activity_register_choose_textview || viewId == R.id.activity_register_choose_imageview) {
            // jump to choose medicalcard
            AppDataUtil.updateEntranceActivityNo(REGISTER_ACTIVITY_NO);
            Intent intent = new Intent(RegisterActivity.this, MyMedicalCardsActivity.class);
            startActivityForResult(intent, REQUEST_CODE_REGISTERACTIVITY_TO_CHOOSEMEDICALCARDACTIVITY);

        } else if (viewId == R.id.activity_register_confirm_button) {
            String pictureVerificationCodeText = pictureVerificationCodeEditText.getText().toString();
            String selectedMedicalCardOwnerName = registerChooseTextview.getText().toString();
            //jump to pay activity
            if (selectedMedicalCardOwnerName.equals("请选择")) {
                showErrorCardView(DONT_CHOOSE_MEDICALCARD);
            } else if (pictureVerificationCodeText.isEmpty()) {
                showErrorCardView(DONT_INPUT_PICTURE_VERIFICATION_CODE);
            } else {
                boolean pictureVerificationCodeIsRight = judgePictureVerificationCode(pictureVerificationCodeText);
                if (pictureVerificationCodeIsRight) {
                    //startRegister
                    showNetworkCacheCardView();
                    //send the registerdata to server,wait server
                    String requestAction = "startRegister";
                    String parameter = "&account=" + user.getUserAccount()
                            + "&medicalCardNo=" + selectedMedicalCard.getMedicalCardNo()
                            + "&visitNo=" + selectedVisit.getVisitNo();
                    HttpUtil.sendOkHttpRequest(requestAction, parameter, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            closeNetworkCacheCardView();
                            showErrorCardView(ACCESS_TIMEOUT);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String str = response.body().string();
                            diagnosis = gson.fromJson(str, Diagnosis.class);
                            closeNetworkCacheCardView();
                            if (diagnosis.getDiagnosisNo() == 0 || diagnosis.getRegistrationNo().isEmpty()) {
                                //register fail
                                showErrorCardView(CANT_REGISTER);
                            } else {
                                //enter payactivity,save data to global data.
                                AppDataUtil.setDiagnosis(diagnosis);
                                Intent intent = new Intent(RegisterActivity.this, OutpatientRegisterDetailsActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                } else {
                    showErrorCardView(PICTURE_VERIFICATION_CODE_INVALID);
                    // change the local pictureVerificationCode
                    if (pictureVerificationCode != null){ //the pictureVerificationCode must be null .else the data will be load two times.
                        initPictureVerificationCode();
                    }
                }
            }
        }
        //other click

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_REGISTERACTIVITY_TO_CHOOSEMEDICALCARDACTIVITY) {
            //get data from chooseMedicalCardActivity
            if (resultCode == RESULT_OK) {
                selectedMedicalCard = AppDataUtil.getSelectedMedicalCard();
                if (selectedMedicalCard != null) {
                    registerChooseTextview.setText(selectedMedicalCard.getOwnerName());
                }
            }
        }
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
