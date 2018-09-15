package com.nobitastudio.materialdesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nobitastudio.materialdesign.bean.ClientRegistration;
import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.bean.User;
import com.nobitastudio.materialdesign.fragment.AboutFragment;
import com.nobitastudio.materialdesign.fragment.HospitalHomeFragment;
import com.nobitastudio.materialdesign.fragment.MyAppointmentFragment;
import com.nobitastudio.materialdesign.fragment.MessageCenterFragment;
import com.nobitastudio.materialdesign.fragment.SettingFragment;
import com.nobitastudio.materialdesign.fragment.TestFragment;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.HttpUtil;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    HospitalHomeFragment hospitalHomeFragment;
    MyAppointmentFragment myAppointmentFragment;
    MessageCenterFragment MessageCenterFragment;
    SettingFragment settingFragment;
    AboutFragment aboutFragment;
    TestFragment testFragment;
    Fragment currentFragment;
    Toolbar toolbar;
    CircleImageView toolbarCircleImageView;
    CircleImageView navCircleImageView;
    TextView toolbarTextView;
    TextView navTextView;
    DrawerLayout drawer;
    NavigationView navigationView;
    CardView networkCacheCardView;
    CardView errorCardView;
    TextView errorTextView;

    FragmentTransaction fragmentTransaction;

    User user;
    Gson gson;

    static final int HOSPITAL_HOME_FRAGMENT_NO = 1001;
    static final int MY_APPOINTMENT_FRAGMENT_NO = 1002;
    static final int MESSAGE_CENTER_NO = 1003;
    static final int SETTING_FRAGMENT_NO = 1005;
    static final int ABOUT_FRAGMENT_NO = 1006;

    static final String ACCESS_TIMEOUT = "网络连接超时";
    static final String DONT_FIND_REGISTER_RECORD = "未查找您的挂号记录";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    /**
     * init all module,including all fragment and widgets of all fragment,show UI at first entering;
     */
    private void init() {

        initBaseData();

        //init fragment
        hospitalHomeFragment = new HospitalHomeFragment();
        myAppointmentFragment = new MyAppointmentFragment();
        MessageCenterFragment = new MessageCenterFragment();
        settingFragment = new SettingFragment();
        aboutFragment = new AboutFragment();
        testFragment = new TestFragment();

        //init fragmentTransaction
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.activity_main_fragment_container, hospitalHomeFragment, HospitalHomeFragment.class.getName());
        fragmentTransaction.add(R.id.activity_main_fragment_container, myAppointmentFragment, MyAppointmentFragment.class.getName());
        fragmentTransaction.add(R.id.activity_main_fragment_container, MessageCenterFragment, MessageCenterFragment.class.getName());
        fragmentTransaction.add(R.id.activity_main_fragment_container, settingFragment, HospitalHomeFragment.class.getName());
        fragmentTransaction.add(R.id.activity_main_fragment_container, aboutFragment, HospitalHomeFragment.class.getName());
        fragmentTransaction.add(R.id.activity_main_fragment_container, testFragment, HospitalHomeFragment.class.getName());
        fragmentTransaction.hide(hospitalHomeFragment);
        fragmentTransaction.hide(myAppointmentFragment);
        fragmentTransaction.hide(MessageCenterFragment);
        fragmentTransaction.hide(settingFragment);
        fragmentTransaction.hide(aboutFragment);
        fragmentTransaction.hide(testFragment);
        fragmentTransaction.commit();

        //init widgets
        drawer = findViewById(R.id.activity_main_drawerlayout);
        navigationView = findViewById(R.id.nav_view);
        //navigationView.setItemIconTintList(null);     //使导航栏的图标变为彩色
        toolbar = findViewById(R.id.activity_main_toolbar);
        toolbarCircleImageView = findViewById(R.id.circle_imageview_fragment);
        toolbarTextView = findViewById(R.id.activity_main_toolbar_textview);
        View nav_hearderView = navigationView.getHeaderView(0);
        navCircleImageView = nav_hearderView.findViewById(R.id.nav_circle_imageView);
        navTextView = nav_hearderView.findViewById(R.id.nav_textView1);
        networkCacheCardView = findViewById(R.id.activity_main_network_cache_cardview);
        errorCardView = findViewById(R.id.activity_main_error_cardview);
        errorTextView = findViewById(R.id.activity_main_error_textview);

        //init all listner
        navigationView.setNavigationItemSelectedListener(this);
        toolbarCircleImageView.setOnClickListener(this);
        navCircleImageView.setOnClickListener(this);

        //display the hospital home
        showFragment(HOSPITAL_HOME_FRAGMENT_NO);

        initWidgetData();

    }

    private void initBaseData() {
        gson = new Gson();
        user = AppDataUtil.getUser();
    }

    @Override
    public void onClick(View v) {

        int viewId = v.getId();
        if (viewId == R.id.circle_imageview_fragment) {
            drawer.openDrawer(GravityCompat.START);

        } else if (viewId == R.id.nav_circle_imageView) {
            //jump to personal center activity
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(this, PersonalCenterActivity.class);
            startActivity(intent);
        }
    }

    /**
     * this method must run after init widget.
     */
    private void initWidgetData() {
        navTextView.setText(AppDataUtil.getUser().getUserName());
    }

    private void showFragment(int fragmentNo) {

        //reinit fragmentTransaction,previous fragmentTransaction has commited,can't redo other operation
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (fragmentNo == HOSPITAL_HOME_FRAGMENT_NO) {
            //display HospitalHomeFragment
            if (currentFragment != null) {
                fragmentTransaction.hide(currentFragment);
            }
            toolbarTextView.setText("医院主页");
            fragmentTransaction.show(hospitalHomeFragment).commit();
            currentFragment = hospitalHomeFragment;

        } else if (fragmentNo == MY_APPOINTMENT_FRAGMENT_NO) {

            //display MyAppointmentFragment
            showNetworkCacheCardView();
            if (currentFragment != null) {
                getSupportFragmentManager().beginTransaction().hide(currentFragment).commit();
            }
            AppDataUtil.getClientRegistrations().clear();
            notifyMyAppointmentAdapter();
            toolbarTextView.setText("我的预约");
            String requestAction = "queryAllClientRegistrationByAccount";
            String parameter = "&account=" + AppDataUtil.getUser().getUserAccount();
            HttpUtil.sendOkHttpRequest(requestAction, parameter, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    closeNetworkCacheCardView();
                    showErrorCardView(ACCESS_TIMEOUT);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    List<ClientRegistration> clientRegistrations = gson.fromJson(response.body().string(), new TypeToken<List<ClientRegistration>>() {
                    }.getType());
                    closeNetworkCacheCardView();
                    if (clientRegistrations.size() > 0){
                        AppDataUtil.getClientRegistrations().addAll(clientRegistrations);
                        notifyMyAppointmentAdapter();
                    }else {
                        //the user don't register
                        showErrorCardView(DONT_FIND_REGISTER_RECORD);
                    }
                }
            });

            getSupportFragmentManager().beginTransaction().show(myAppointmentFragment).commit();
            currentFragment = myAppointmentFragment;

        } else if (fragmentNo == MESSAGE_CENTER_NO) {


            //show network cache

            //display MessageCenterFragment
            if (currentFragment != null) {
                fragmentTransaction.hide(currentFragment);
            }
            toolbarTextView.setText("信息中心");
            fragmentTransaction.show(MessageCenterFragment).commit();
            currentFragment = MessageCenterFragment;

        } else if (fragmentNo == SETTING_FRAGMENT_NO) {
            if (currentFragment != null) {
                fragmentTransaction.hide(currentFragment);
            }
            toolbarTextView.setText("设置");
            fragmentTransaction.show(settingFragment).commit();
            currentFragment = settingFragment;

        } else if (fragmentNo == ABOUT_FRAGMENT_NO) {
            if (currentFragment != null) {
                fragmentTransaction.hide(currentFragment);
            }
            toolbarTextView.setText("关于");
            fragmentTransaction.show(aboutFragment).commit();
            currentFragment = aboutFragment;

        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.activity_main_drawerlayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_hospital_home) {
            showFragment(HOSPITAL_HOME_FRAGMENT_NO);

        } else if (id == R.id.nav_qr_code) {
            showFragment(MY_APPOINTMENT_FRAGMENT_NO);

        } else if (id == R.id.nav_message_center) {
            showFragment(MESSAGE_CENTER_NO);

        } else if (id == R.id.nav_personal_center) {
            Intent intent = new Intent(MainActivity.this,PersonalCenterActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_help) {
            showFragment(SETTING_FRAGMENT_NO);

        } else if (id == R.id.nav_about) {
            showFragment(ABOUT_FRAGMENT_NO);
        } else {
            //save all resource and date,throw exception then exit;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void notifyMyAppointmentAdapter() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myAppointmentFragment.getAdapter().notifyDataSetChanged();
            }
        });
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

    public void showErrorCardView(final String alterInf) {
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
