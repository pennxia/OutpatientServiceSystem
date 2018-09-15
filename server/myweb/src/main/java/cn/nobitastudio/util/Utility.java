package cn.nobitastudio.util;

import cn.nobitastudio.model.Uuser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Calendar;

public class Utility {

    public static String medicalcardSavedCurrentTime = "19000101000000";

    public static Integer savedMedicalCardNoTwo = 1;
    //挂号单(8001），药品单（8002），检查检验单（8003），手术单（8004）
    private static Integer CHARGE_ITEM_TYPE_REGISTER = 8001;

    private static Integer CHARGE_ITEM_TYPE_DRUGCOST = 8002;

    private static Integer CHARGE_ITEM_TYPE_CHECKINSPECTIONCOST = 8003;

    private static Integer CHARGE_ITEM_TYPE_OPERATIONCOST = 8004;

    // 订单 自增序列
    private static Integer orderNo = 1;

    private static String orderSavedCurrentTime = "19000101"; // 年月日

    private static Integer registraionNo = 1;

    private static String registrationSavedCurrentTime = "19000101"; //初始时间

    // method
    public static Integer getChargeItemTypeRegister() {
        return CHARGE_ITEM_TYPE_REGISTER;
    }

    public static Integer getChargeItemTypeDrugcost() {
        return CHARGE_ITEM_TYPE_DRUGCOST;
    }

    public static Integer getChargeItemTypeCheckinspectioncost() {
        return CHARGE_ITEM_TYPE_CHECKINSPECTIONCOST;
    }

    public static Integer getChargeItemTypeOperationcost() {
        return CHARGE_ITEM_TYPE_OPERATIONCOST;
    }

    public static void setChargeItemTypeOperationcost(Integer chargeItemTypeOperationcost) {
        CHARGE_ITEM_TYPE_OPERATIONCOST = chargeItemTypeOperationcost;
    }

    public static JSONObject handleUuserToJsonObject(Uuser uuser) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuser", JSONObject.fromObject(uuser));
        return jsonObject;
    }

    public static Uuser handleJsonObjectToUuser(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("uuser");
        String account = jsonArray.getJSONObject(0).getString("account");
        String password = jsonArray.getJSONObject(1).getString("password");
        String username = jsonArray.getJSONObject(2).getString("username");

        Uuser uuser = new Uuser(Long.valueOf(account), password, username);
        return uuser;
    }

    /**
     * @param begin
     * @param end
     * @return return begin - end random string
     */
    public static String getRandom(int begin, int end) {
        int random = begin + (int) (Math.random() * (end - begin));
        return String.valueOf(random);
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
        String currentTime = year + month + date + hours + minutes + second;
        return currentTime;
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
     * @return 时分秒
     */
    public static String getSimpleCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        String hours = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY) > 9 ? calendar.get(Calendar.HOUR_OF_DAY) : "0" + calendar.get(Calendar.HOUR_OF_DAY));
        String minutes = String.valueOf(calendar.get(Calendar.MINUTE) > 9 ? calendar.get(Calendar.MINUTE) : "0" + calendar.get(Calendar.MINUTE));
        String second = String.valueOf(calendar.get(Calendar.SECOND) > 9 ? calendar.get(Calendar.SECOND) : "0" + calendar.get(Calendar.SECOND));
        return hours + minutes + second;
    }

    /**
     * 得到全局唯一的挂号单单号
     *
     * @return
     */
    public static String getRegistrationId() {
        String signature = "12";
        String currentTime = getSimpleCurrentTime();  //年月日
        String currentDate = getSimpleCurrentDate();  //时分秒
        String strRegistrationNo;
        if (currentTime.equals(registrationSavedCurrentTime)) {
            strRegistrationNo = handleIntOrderNo(registraionNo);
            registraionNo++;
        } else {
            registrationSavedCurrentTime = currentTime;
            registraionNo = 1;
            strRegistrationNo = handleIntOrderNo(orderNo);
            registraionNo = 2;
        }
        String registrationNo = signature + currentTime + currentDate + strRegistrationNo;
        return registrationNo;
    }

    /**
     * 在创建诊疗卡的时，得到该诊疗卡的medicalCardNO;
     * 支持多线程并发执行 产生不同且规律的数值.
     *
     * @return
     */
    public static Long getMedicalcardNo() {
        String medicalCardNoOne = getCurrentTime();
        String medicalCardNoTwo;
        if (medicalCardNoOne.equals(medicalcardSavedCurrentTime)) {
            medicalCardNoTwo = savedMedicalCardNoTwo > 9 ? savedMedicalCardNoTwo.toString() : "0" + savedMedicalCardNoTwo;
            savedMedicalCardNoTwo++;
        } else {
            medicalcardSavedCurrentTime = medicalCardNoOne;
            medicalCardNoTwo = "01";
            savedMedicalCardNoTwo = 2;
        }
        return Long.valueOf(medicalCardNoOne + medicalCardNoTwo);
    }

    /**
     * 将14位的纯字符串时间，转换为 年 - 月 - 日 时：分：秒
     *
     * @param payOrCancelTime
     * @return
     */
    public static String handleStrDate(String payOrCancelTime) {
        String year = payOrCancelTime.substring(0, 4);
        String month = payOrCancelTime.substring(4, 6);
        String date = payOrCancelTime.substring(6, 8);
        String hour = payOrCancelTime.substring(8, 10);
        String minute = payOrCancelTime.substring(10, 12);
        String second = payOrCancelTime.substring(12);
        String time = year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
        return time;
    }

    /**
     * 获得挂号单的流水号,以天为单位，天数发生改变时，自动将自增序列从 1 开始计数，最大为 99999999
     *
     * @return
     */
    public static String getRegisterSerialNumber() {

        String signature = "52";
        String orderType = "8001";
        String payMethod = "81";
        String currentTime = getSimpleCurrentTime();  //年月日
        String currentDate = getSimpleCurrentDate();  //时分秒
        String strOrderNo;
        String currentTimeAll = currentTime + currentDate;
        if (currentTime.equals(orderSavedCurrentTime)) {
            strOrderNo = handleIntOrderNo(orderNo);
            orderNo++;
        } else {
            orderSavedCurrentTime = currentTime;
            orderNo = 1;
            strOrderNo = handleIntOrderNo(orderNo);
            orderNo++;
        }
        String registerSerialNumber = signature + orderType + payMethod + currentTimeAll + strOrderNo;
        return registerSerialNumber;
    }

    /**
     * 得到 药品单流水号，实现原理同getRegisterSerialNumber（）
     *
     * @return
     */
    public static String getDrugCostSerialNumber() {
        return null;
    }

    /**
     * 得到 手术单流水号，实现原理同getRegisterSerialNumber（）
     *
     * @return
     */
    public static String getOperationCostSerialNumber() {
        return null;
    }

    /**
     * 得到 检查检验单流水号，实现原理同getRegisterSerialNumber（）
     *
     * @return
     */
    public static String getCheckInspectionCostSerialNumber() {
        return null;
    }

    /**
     * 将 Integer 类型的 orderNo 转换为位数合适的 string 类型显示
     *
     * @param orderNo
     * @return
     */
    public static String handleIntOrderNo(Integer orderNo) {

        String strOrderNo = orderNo.toString();
        if (strOrderNo.length() == 1) {
            // 1~ 9
            strOrderNo = "0000000" + strOrderNo;
        } else if (strOrderNo.length() == 2) {
            // 10 ~ 99
            strOrderNo = "000000" + strOrderNo;
        } else if (strOrderNo.length() == 3) {
            // 100 ~ 999
            strOrderNo = "00000" + strOrderNo;
        } else if (strOrderNo.length() == 4) {
            // 1000 ~ 9999
            strOrderNo = "0000" + strOrderNo;
        } else if (strOrderNo.length() == 5) {
            // 10000 ~ 99999
            strOrderNo = "000" + strOrderNo;
        } else if (strOrderNo.length() == 6) {
            // 100000 ~ 999999
            strOrderNo = "00" + strOrderNo;
        } else if (strOrderNo.length() == 7) {
            // 1000000 ~ 9999999
            strOrderNo = "0" + strOrderNo;
        } else if (strOrderNo.length() == 8) {
            // 10000000 ~ 99999999. do nothing
        }
        return strOrderNo;
    }

}
