package com.nobitastudio.materialdesign.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.bean.HealthNews;
import com.nobitastudio.materialdesign.bean.HospitalActivity;
import com.nobitastudio.materialdesign.bean.LoginMessage;
import com.nobitastudio.materialdesign.bean.LoginResult;
import com.nobitastudio.materialdesign.bean.SimpleHealthNews;
import com.nobitastudio.materialdesign.bean.User;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    ActionBar actionBar;
    EditText userAccountEditText;
    EditText userPasswordEditText;
    Button loginButon;
    TextView forgetPasswrodTextview;
    TextView registerTexview;
    CardView networkCacheCardView;
    CardView errorCardView;
    TextView errorTextView;

    LoginMessage loginMessage;
    String account;
    String userPassword;
    boolean autoLogin;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    boolean loginInfIsRight;

    Gson gson;
    User user;
    LoginResult loginResult;
    List<HospitalActivity> hospitalActivityList;
    List<SimpleHealthNews> simpleHealthNewsList;
    List<HealthNews> healthNewsList;
    List<HealthNews> healthNewsHeadlinesList;
    List<HealthNews> healthNewsLectureList;

    static final int PHONE_NUM_LENGTH = 11;
    static final String PASSWORD_LENGTH_LESS = "密码长度太短";
    static final String PASSWORD_LENGTH_MORE = "密码长度过长";
    static final String ACCOUNT_IS_LESS = "请输入正确手机号";
    static final String DONT_INPUT_ACCOUNT = "请输入手机号";
    static final String DONT_INPUT_PASSWORD = "请输入密码";
    static final String ACCOUNT_OR_PAASSWORD_RONG = "账号或密码错误";
    static final String ACCESS_TIMEOUT = "网络连接超时";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

    }

    private void init() {

        initBaseData();

        //init widget
        toolbar = findViewById(R.id.activity_login_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(null);
        }
        userAccountEditText = findViewById(R.id.activity_login_account_edittext);
        userPasswordEditText = findViewById(R.id.activity_login_password_edittext);
        loginButon = findViewById(R.id.activity_login_login_button);
        forgetPasswrodTextview = findViewById(R.id.activity_login_forgetpassword_textview);
        registerTexview = findViewById(R.id.activity_login_enroll_textview);
        networkCacheCardView = findViewById(R.id.activity_login_networkcache_cardview);
        errorCardView = findViewById(R.id.activity_login_error_cardview);
        errorTextView = findViewById(R.id.activity_login_error_textview);

        //init listener;
        loginButon.setOnClickListener(this);
        forgetPasswrodTextview.setOnClickListener(this);
        registerTexview.setOnClickListener(this);

        //get preferences data.
        account = preferences.getString("account", "");
        userPassword = preferences.getString("userPassword", "");
        autoLogin = preferences.getBoolean("autoLogin", false);
        if (!account.isEmpty() && !userPassword.isEmpty()) {
            userAccountEditText.setText(account);
            userPasswordEditText.setText(userPassword);
            if (autoLogin) {
                showNetworkCacheCardView();
                loginMessage = new LoginMessage(account, userPassword);
                sendLoginInfToServer(loginMessage);
            }
        }

    }

    private void initBaseData() {
        gson = new Gson();
        AppDataUtil.getActivities().add(this);
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
        if (viewId == R.id.activity_login_login_button) {
            userClickLogin();
        } else if (viewId == R.id.activity_login_forgetpassword_textview) {
            //forget passowrd
            enterForgetPasswordOneActivity();
        } else if (viewId == R.id.activity_login_enroll_textview) {
            //enroll
            enterEnrollOneActivity();
        }
    }

    private void enterEnrollOneActivity() {
        Intent intent = new Intent(this, EnrollOneActivity.class);
        startActivity(intent);
    }

    private void enterForgetPasswordOneActivity() {
        Intent intent = new Intent(this, ForgetPasswordOneActivity.class);
        startActivity(intent);
    }

    private void userClickLogin() {
        account = userAccountEditText.getText().toString();
        userPassword = userPasswordEditText.getText().toString();
        if (account == null || account.length() == 0) {
            showErrorCardView(DONT_INPUT_ACCOUNT);
        } else if (userPassword == null || userPassword.length() == 0) {
            showErrorCardView(DONT_INPUT_PASSWORD);
        } else if (account.length() != PHONE_NUM_LENGTH) {
            showErrorCardView(ACCOUNT_IS_LESS);
        } else if (userPassword.length() < 6 || userPassword.length() < 6) {
            showErrorCardView(PASSWORD_LENGTH_LESS);
        } else if (userPassword.length() > 16 || userPassword.length() > 16) {
            showErrorCardView(PASSWORD_LENGTH_MORE);
        } else {
            showNetworkCacheCardView();
            //give the user information to the server,get necessary information,if right go to mainactivity,otherwise,ack user to reinput
            loginMessage = new LoginMessage(account, userPassword);
            sendLoginInfToServer(loginMessage);
        }

    }

    protected void sendLoginInfToServer(final LoginMessage loginMessage) {
        //give the user inf to server and get result from server that wehether can login
        //there only test,set loginInfIsRight true
        //if true;save date,and loginInfIsRight = true,otherwise,userInfIsRight = false.show alert inf
        // SimpleUserdata contains user simple data,then server judge the user data ,reture true or false then the app give right action
        String requestAction = "login";
        String parameter = "&account=" + loginMessage.getAccount() + "&password=" + loginMessage.getPassword();
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
                if (user.getUserPassword().isEmpty() || user.getUserAccount().equals("0")) {
                    loginInfIsRight = false;
                } else {
                    loginInfIsRight = true;
                }
                //if itsn't auto login ,let the thread wait
                if (!autoLogin) {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                loginResult();
            }

        });

    }

    private void loginResult() {
        if (loginInfIsRight) {
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

            //save simpleHealthNewsActivity
            simpleHealthNewsList = loginResult.getSimpleHealthNewsList();
            AppDataUtil.setSimpleHealthNewsList(simpleHealthNewsList);

            //storage user account password to sharedPreferences.
            editor.putString("account", account);
            editor.putString("userPassword", userPassword);
            editor.putBoolean("autoLogin", true);
            editor.apply();

            closeNetworkCacheCardView();

            //login in the mainActivity,come back to mainactivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            closeNetworkCacheCardView();
            // below mast run on main thread,because of deal UI
            showErrorCardView(ACCOUNT_OR_PAASSWORD_RONG);
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

}
