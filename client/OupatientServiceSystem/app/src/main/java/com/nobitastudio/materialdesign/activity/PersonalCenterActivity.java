package com.nobitastudio.materialdesign.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.bean.User;
import com.nobitastudio.materialdesign.util.AppDataUtil;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalCenterActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    ActionBar actionBar;
    LinearLayout myOrderLinearLayout;
    LinearLayout myMedicalCardsLinearLayout;
    LinearLayout myDoctorsLinearLayout;
    LinearLayout myElectronicCaseLinearLayout;
    LinearLayout settingLinearLayout;
    TextView usernameTextview;
    CircleImageView userCircleImageView;

    User user;

    private static int PERSONALCENTER_ACTIVITY_NO = 5005; // references registerActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);

        init();
    }

    private void init() {

        initBaseData();

        //init widget
        toolbar = findViewById(R.id.activity_personal_center_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        myOrderLinearLayout = findViewById(R.id.activity_personal_center_myorder_linearlayout);
        myMedicalCardsLinearLayout = findViewById(R.id.activity_personal_center_mymedicalcards_linearlayout);
        myDoctorsLinearLayout = findViewById(R.id.activity_personal_center_mydoctors_linearlayout);
        myElectronicCaseLinearLayout = findViewById(R.id.activity_personal_center_myelectroniccase_linearlayout);
        settingLinearLayout = findViewById(R.id.activity_personal_center_setting_linearlayout);
        usernameTextview = findViewById(R.id.activity_personal_center_username_textview);
        userCircleImageView = findViewById(R.id.activity_personal_center_user_imageView);

        //init listener
        myOrderLinearLayout.setOnClickListener(this);
        myMedicalCardsLinearLayout.setOnClickListener(this);
        myDoctorsLinearLayout.setOnClickListener(this);
        myElectronicCaseLinearLayout.setOnClickListener(this);
        settingLinearLayout.setOnClickListener(this);
        userCircleImageView.setOnClickListener(this);

        //init widget data
        usernameTextview.setText(user.getUserName());

    }

    private void initBaseData() {
        user = AppDataUtil.getUser();

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        Intent intent;
        if (viewId == R.id.activity_personal_center_myorder_linearlayout){
            intent = new Intent(this,MyAppointmentActivity.class);
        }else if (viewId == R.id.activity_personal_center_mymedicalcards_linearlayout){
            AppDataUtil.updateEntranceActivityNo(PERSONALCENTER_ACTIVITY_NO);
            intent = new Intent(this,MyMedicalCardsActivity.class);
        }else if (viewId == R.id.activity_personal_center_mydoctors_linearlayout){
            intent = new Intent(this,MyDoctorActivity.class);
        }else if (viewId == R.id.activity_personal_center_myelectroniccase_linearlayout){
            intent = new Intent(this,MyElectronicCaseActivity.class);
        }else if (viewId == R.id.activity_personal_center_setting_linearlayout){
            intent = new Intent(this,SettingActivity.class);
        }else if (viewId == R.id.activity_personal_center_user_imageView){
            // let the user to change head image
            intent = new Intent(this,PersonalCenterActivity.class);
        }else {
            intent = new Intent(this,PersonalCenterActivity.class);
        }

        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == android.R.id.home){
            finish();
        }else {
            finish();
        }
        return true;
    }


}
