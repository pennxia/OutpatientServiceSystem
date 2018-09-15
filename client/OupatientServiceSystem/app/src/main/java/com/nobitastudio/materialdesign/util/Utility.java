package com.nobitastudio.materialdesign.util;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.nobitastudio.materialdesign.bean.Doctor;
import com.nobitastudio.materialdesign.bean.MyDoctor;
import com.nobitastudio.materialdesign.bean.YearMonthDate;

import java.util.Calendar;

public class Utility {


    static final String DOCTOR_IMAGE_PACKAGE = "http://www.nobitastudio.cn/myweb/pictures/doctor_img/doctor_image";
    static final String HEALTH_NEWS_ICON_PACKAGE = "http://www.nobitastudio.cn/myweb/pictures/healthnews_icon_img/";
    static final String HOSPITAL_ACTIVITY_ICON_PACKAGE = "http://www.nobitastudio.cn/myweb/pictures/hospitalactivity_icon_img/";
    static final String SIMPLE_HEALTH_NEWS_ICON_PACKAGE = "http://www.nobitastudio.cn/myweb/pictures/simplehealthnews_icon_img/";

    //挂号单(8001），药品单（8002），检查检验单（8003），手术单（8004）
    public static Integer CHARGE_ITEM_TYPE_REGISTER = 8001;

    public static Integer CHARGE_ITEM_TYPE_DRUGCOST = 8002;

    public static Integer CHARGE_ITEM_TYPE_CHECKINSPECTIONCOST = 8003;

    public static Integer CHARGE_ITEM_TYPE_OPERATIONCOST = 8004;

    public static void showToastShort(final AppCompatActivity activity, final String string) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, string, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void showToastLong(final AppCompatActivity activity, final String string) {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, string, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static String getDoctorImageRequestAddress(Integer doctorId) {
        int no = doctorId % 3 + 1;
        return DOCTOR_IMAGE_PACKAGE + no + ".png";
    }

    public static String getHospitalActivityIconAddress(String hospitalActivityIconName) {
        return HOSPITAL_ACTIVITY_ICON_PACKAGE + hospitalActivityIconName + ".png";
    }

    public static String getHealthNewsIconAddress(String healthNewsIconName) {
        return HEALTH_NEWS_ICON_PACKAGE + healthNewsIconName + ".png";
    }

    public static String getSimpleHealthNewsIconAddress(String simpleHealthNewsIconName) {
        return SIMPLE_HEALTH_NEWS_ICON_PACKAGE + simpleHealthNewsIconName + ".png";
    }

    public static String handleHealthNewsPublishTime(String healthNewsPublishTime) {

        //20180711224920
        //01234567890123
        Integer year = Integer.valueOf(healthNewsPublishTime.substring(0, 4));
        Integer month = Integer.valueOf(healthNewsPublishTime.substring(4, 6));
        Integer day = Integer.valueOf(healthNewsPublishTime.substring(6, 8));

        Calendar calendar = Calendar.getInstance();
        Integer currentYear = calendar.get(Calendar.YEAR);
        Integer currentMonth = calendar.get(Calendar.MONTH) + 1;
        Integer currentDay = calendar.get(Calendar.DATE);

        String publishTime = "未知";
        if ((year - currentYear) > 2) {
            publishTime = "两年前";
        } else if (((year - currentYear) == 2) && (month < currentMonth || month.equals(currentMonth))) {
            publishTime = "两年前";
        } else if (((year - currentYear) == 2) && month > currentMonth) {
            publishTime = "一年前";
        } else if (((year - currentYear) == 1) && (month < currentMonth || month.equals(currentMonth))) {
            publishTime = "一年前";
        } else if (((year - currentYear) == 1) && month > currentMonth) {
            publishTime = 12 - month + currentMonth + "月前";
        } else if ((year.equals(currentYear)) && month < currentMonth) {
            publishTime = currentMonth - month + "月前";
        } else if ((year.equals(currentYear)) && (month.equals(currentMonth)) && (day < currentDay)) {
            publishTime = currentDay - day + "天前";
        } else if ((year.equals(currentYear)) && (month.equals(currentMonth)) && (day.equals(currentDay))) {
            publishTime = "刚刚";
        }

        return publishTime;
    }


    public static String handleYear(YearMonthDate yearMonthDate) {
        //handle complex data to simple data
        String year = String.valueOf(yearMonthDate.getYear() + 1900);
        int intMonth = yearMonthDate.getMonth() + 1;
        String strMonth;
        if (intMonth < 10) {
            strMonth = "0" + String.valueOf(intMonth);
        } else {
            strMonth = String.valueOf(intMonth);
        }
        String date = String.valueOf(yearMonthDate.getDate());
        return year + "-" + strMonth + "-" + date;
    }

    public static void showSnackbarshort(final AppCompatActivity activity, final View view, final String prompt, final String buttonText, int selectedDoctor) {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(view, prompt, Snackbar.LENGTH_SHORT)
                        .setAction(buttonText, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
            }
        });
    }

    /**
     * @return 年月日
     */
    public static String getSimpleCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1 > 9 ? calendar.get(Calendar.MONTH) + 1 : "0" + (calendar.get(Calendar.MONTH) + 1));
        String date = String.valueOf(calendar.get(Calendar.DATE) > 9 ? calendar.get(Calendar.DATE) : "0" + calendar.get(Calendar.DATE));
        String currenTime = year + month + date;
        return currenTime;
    }

    /**
     * @return 年月日时分秒
     */
    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1 > 9 ? calendar.get(Calendar.MONTH) + 1 : "0" + (calendar.get(Calendar.MONTH) + 1));
        String date = String.valueOf(calendar.get(Calendar.DATE) > 9 ? calendar.get(Calendar.DATE) : "0" + calendar.get(Calendar.DATE));
        String hours = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY) > 9 ? calendar.get(Calendar.HOUR_OF_DAY) : "0" + calendar.get(Calendar.HOUR_OF_DAY));
        String minutes = String.valueOf(calendar.get(Calendar.MINUTE) > 9 ? calendar.get(Calendar.MINUTE) : "0" + calendar.get(Calendar.MINUTE));
        String second = String.valueOf(calendar.get(Calendar.SECOND) > 9 ? calendar.get(Calendar.SECOND) : "0" + calendar.get(Calendar.SECOND));
        String currenTime = year + month + date + hours + minutes + second;
        return currenTime;
    }

    /**
     * 将14位的纯字符串时间，转换为 年 - 月 - 日 时：分：秒
     *
     * @param strTime14
     * @return
     */
    public static String handleStrDate(String strTime14) {
        String year = strTime14.substring(0, 4);
        String month = strTime14.substring(4, 6);
        String date = strTime14.substring(6, 8);
        String hour = strTime14.substring(8, 10);
        String minute = strTime14.substring(10, 12);
        String second = strTime14.substring(12);
        String time = year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
        return time;
    }

    public static String getStartTime(String strTime14) {
        String year = strTime14.substring(0, 4);
        String month = strTime14.substring(4, 6);
        String date = strTime14.substring(6, 8);
        String hour = strTime14.substring(8, 10);
        String time = year + "-" + month + "-" + date + " " + hour + ":00:00";
        return time;
    }

    /**
     * 将 integer 类型的 orderState 转换为 需要显示的值（0:待支付  1：已支付  2：已取消)
     *
     * @param orderState
     * @return
     */
    public static String handelIntegerOrderStateToStr(Integer orderState) {

        String strOrderState = "异常";
        if (orderState.equals(0)) {
            strOrderState = "待支付";
        } else if (orderState.equals(1)) {
            strOrderState = "已支付";
        } else if (orderState.equals(2)) {
            strOrderState = "已取消";
        }
        return strOrderState;
    }


}
