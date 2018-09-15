package com.nobitastudio.materialdesign.activity;

import android.content.Intent;
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
import android.widget.TextView;

import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.adapter.MyMedicalCardsActivityRecycleViewAdapter;
import com.nobitastudio.materialdesign.bean.MedicalCard;
import com.nobitastudio.materialdesign.util.AppDataUtil;

import java.util.List;

public class MyMedicalCardsActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    ActionBar actionBar;
    RecyclerView recyclerView;
    CardView errorCardView;
    TextView errorTextView;
    Button createMedicalCardButton;
    Button bindMedicalCardButton;
    TextView toolbarTextView;

    MyMedicalCardsActivityRecycleViewAdapter adapter;

    List<MedicalCard> medicalCards;
    int myMedicalCardSize;

    static final String ACCESS_TIMEOUT = "网络连接超时";
    static final String MEDICALCARD_NO_BIND = "您还未绑定诊疗卡";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_medical_cards);

        init();
    }

    private void init() {

        initBaseData();

        //init widget
        toolbar = findViewById(R.id.activity_my_medicalcard_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
        errorCardView = findViewById(R.id.activity_my_medicalcard_error_cardview);
        errorTextView = findViewById(R.id.activity_my_medicalcard_error_textview);
        recyclerView = findViewById(R.id.activity_my_medicalcard_recycleview);
        createMedicalCardButton = findViewById(R.id.activity_my_medicalcard_createmedicalcard_button);
        bindMedicalCardButton = findViewById(R.id.activity_my_medicalcard_bindmedicalcard_button);
        toolbarTextView = findViewById(R.id.activity_my_medicalcard_toolbaractivity_my_medicalcard_toolbar_textview);
        if (AppDataUtil.entranceActivityIsMainActivity() || AppDataUtil.entranceActivityIsPersonalCenterActivity()) {
            toolbarTextView.setText("我的诊疗卡");
        } else if (AppDataUtil.entranceActivityIsRegisterActivity()){
            toolbarTextView.setText("选取就诊人");
        }

        //judget user whether bind medicalcard
        if (medicalCards.size() == 0) {
            showErrorCardView(MEDICALCARD_NO_BIND);
        }
        //init adapter
        adapter = new MyMedicalCardsActivityRecycleViewAdapter(this, medicalCards);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        //init listener
        createMedicalCardButton.setOnClickListener(this);
        bindMedicalCardButton.setOnClickListener(this);
    }

    private void initBaseData() {
        medicalCards = AppDataUtil.getMedicalCards();
        myMedicalCardSize = medicalCards.size();
    }

    @Override
    public void onClick(View v) {

        int viewId = v.getId();
        if (viewId == R.id.activity_my_medicalcard_createmedicalcard_button) {
            //create medicalcard
            Intent intent = new Intent(this, CreateMedicalCardOneActivity.class);
            startActivity(intent);

        } else if (viewId == R.id.activity_my_medicalcard_bindmedicalcard_button) {
            //bind medicalcard
            Intent intent = new Intent(this,BindMedicalCardOneActivity.class);
            startActivity(intent);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else {
            //chooce other menuitem;
            finish();
        }
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (myMedicalCardSize != AppDataUtil.getMedicalCards().size()) {
            //refresh adapter
            myMedicalCardSize = AppDataUtil.getMedicalCards().size();
            adapter.notifyDataSetChanged();
        }
    }

}
