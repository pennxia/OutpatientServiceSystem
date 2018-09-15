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
import android.widget.TextView;

import com.google.gson.Gson;
import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.bean.HealthNews;
import com.nobitastudio.materialdesign.bean.HospitalActivity;
import com.nobitastudio.materialdesign.bean.LoginResult;
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

public class ForgetPasswordThreeActivity extends AppCompatActivity implements View.OnClickListener {


    Toolbar toolbar;
    ActionBar actionBar;
    EditText setPasswordEditText;
    EditText confrimPasswordEditText;
    Button updatePasswordButton;
    CardView networkCacheCardView;
    CardView errorCardView;
    TextView errorTextView;

    String updatePasswordAccount;
    Gson gson;
    User user;
    LoginResult loginResult;
    List<HospitalActivity> hospitalActivityList;
    List<HealthNews> healthNewsList;
    List<HealthNews> healthNewsHeadlinesList;
    List<HealthNews> healthNewsLectureList;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    static final String DONT_INPUT_PASSWORD = "请输入密码";
    static final String PASSWORD_DONT_MUTCH = "两次密码不一致";
    static final String PASSWORD_IS_ILLEGAL = "密码含有非法字符";
    static final String PASSWORD_LENGTH_LESS = "密码长度太短";
    static final String PASSWORD_LENGTH_MORE = "密码长度过长";
    static final String UPDATE_PASSWORD_FAIL = "修改失败，请重试";
    static final String ACCESS_TIMEOUT = "网络连接超时";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_three);

        init();
    }

    private void init() {

        initBaseData();

        //init widget
        toolbar = findViewById(R.id.activity_forgetpasswordthree_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(null);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setPasswordEditText = findViewById(R.id.activity_forgetpasswordthree_set_password_edittext);
        confrimPasswordEditText = findViewById(R.id.activity_forgetpasswordthree_confrim_password_edittext);
        updatePasswordButton = findViewById(R.id.activity_forgetpasswordthree_enroll_button);
        networkCacheCardView = findViewById(R.id.activity_forgetpasswordthree_network_cache_cardview);
        errorCardView = findViewById(R.id.activity_forgetpasswordthree_error_cardview);
        errorTextView = findViewById(R.id.activity_forgetpasswordthree_error_textview);

        //init listener
        updatePasswordButton.setOnClickListener(this);
    }

    private void initBaseData() {
        updatePasswordAccount = AppDataUtil.getUpdatePasswordAccount();
        gson = new Gson();
        preferences = getSharedPreferences("loginMessage", MODE_PRIVATE);
        editor = preferences.edit();

        healthNewsHeadlinesList = new ArrayList<>();
        //save healthNewsOneList
        AppDataUtil.setHealthNewsHeadlinesList(healthNewsHeadlinesList);
        healthNewsLectureList = new ArrayList<>();
        //save healthNewsTwoList
        AppDataUtil.setHealthNewsLectureList(healthNewsLectureList);
    }

    @Override
    public void onClick(View v) {

        int viewId = v.getId();
        if (viewId == R.id.activity_forgetpasswordthree_enroll_button) {
            //user enroll，
            String setPassword = setPasswordEditText.getText().toString();
            String confirmPassword = confrimPasswordEditText.getText().toString();
            if (setPassword == null || confirmPassword == null || setPassword.isEmpty() || confirmPassword.isEmpty()) {
                showErrorCardView(DONT_INPUT_PASSWORD);
            } else if (!(setPassword.equals(confirmPassword))) {
                showErrorCardView(PASSWORD_DONT_MUTCH);
            } else if (passwordIsIlleegal(setPassword) || passwordIsIlleegal(confirmPassword)) {
                showErrorCardView(PASSWORD_IS_ILLEGAL);
            } else if (setPassword.length() < 6 || confirmPassword.length() < 6) {
                showErrorCardView(PASSWORD_LENGTH_LESS);
            } else if (setPassword.length() > 16 || confirmPassword.length() > 16) {
                showErrorCardView(PASSWORD_LENGTH_MORE);
            } else {
                //enroll
                showNetworkCacheCardView();
                String requestAction = "userChangePassword";
                String parameter = "&account=" + updatePasswordAccount + "&newPassword=" + setPassword;
                HttpUtil.sendOkHttpRequest(requestAction, parameter, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        closeNetworkCacheCardView();
                        showErrorCardView(ACCESS_TIMEOUT);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        loginResult = gson.fromJson(response.body().string(), LoginResult.class);
                        user = loginResult.getUser();
                        closeNetworkCacheCardView();
                        if (user.getUserPassword().isEmpty() || user.getUserAccount().equals("0")) {
                            showErrorCardView(UPDATE_PASSWORD_FAIL);
                        } else {
                            //save user data to global data
                            AppDataUtil.setUser(user);
                            //get healthnews
                            healthNewsList = loginResult.getHealthNewsList();
                            AppDataUtil.setHealthNewsList(healthNewsList);
                            if (healthNewsList.size() > 0) {
                                for (HealthNews healthNews : healthNewsList) {
                                    if (healthNews.getHealthNewsClass() == 1) {
                                        healthNewsHeadlinesList.add(healthNews);
                                    } else {
                                        healthNewsLectureList.add(healthNews);
                                    }
                                }
                            }

                            //save hospitalActivity
                            hospitalActivityList = loginResult.getHospitalActivityList();
                            AppDataUtil.setHospitalActivityList(hospitalActivityList);

                            editor.putString("account", user.getUserAccount());
                            editor.putString("userPassword", user.getUserPassword());
                            editor.putBoolean("autoLogin", true);
                            editor.apply();
                            Utility.showToastLong(ForgetPasswordThreeActivity.this,"密码已修改成功~");
                            Intent intent = new Intent(ForgetPasswordThreeActivity.this, MainActivity.class);
                            startActivity(intent);
                            ForgetPasswordThreeActivity.this.finish();
                            //close login activity
                            AppDataUtil.finishAllActivities();
                        }
                    }
                });

            }

        }

    }

    /**
     * 判断输入的密码是否含有非法字符
     *
     * @param enrollAccount
     * @return
     */
    private boolean passwordIsIlleegal(String enrollAccount) {
        for (int i = 0; i < enrollAccount.length(); i++) {
            char c = enrollAccount.charAt(i);
            if (!charIsDigitOrLetter(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符是否是在 a ~ z A ~ Z 0 - 9 之间
     *
     * @param c
     * @return
     */
    boolean charIsDigitOrLetter(char c) {
        if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') || ('0' <= c && c <= '9')) {
            return true;
        } else {
            return false;
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
        } else {
            finish();
        }
        return true;
    }
}
