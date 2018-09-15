package cn.nobitastudio.util;

import cn.nobitastudio.model.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class HttpSendJsonUtil {

    private static final String CHARACTERENCODING_ANDROID_UTF_8 = "UTF-8";
    private static final String CHARACTERENCODING_CHROME_GBK = "GBK";

    private static final String ANDROID_TYPE = "android";
    private static final String CHROME_TYPE = "GBK";

    public static void sendUserJsonToClient(HttpServletResponse response, Uuser uuser, String type) {
        if (type.equals(ANDROID_TYPE)) {
            response.setCharacterEncoding(CHARACTERENCODING_ANDROID_UTF_8);
        } else {
            response.setCharacterEncoding(CHARACTERENCODING_CHROME_GBK);
        }

        JSONObject jsonObject = JSONObject.fromObject(uuser);
        try {
            System.out.println(jsonObject.getString("userName") == null);
            response.getWriter().print(jsonObject.toString());
        } catch (IOException e) {
            System.out.println("sendUserJsonToClient exception");
            e.printStackTrace();

        }
    }

    public static void sendDepartmentListJsonToClient(HttpServletResponse response, List<Department> departments, String type) {
        if (type.equals(ANDROID_TYPE)) {
            response.setCharacterEncoding(CHARACTERENCODING_ANDROID_UTF_8);
        } else {
            response.setCharacterEncoding(CHARACTERENCODING_CHROME_GBK);
        }
        JSONArray jsonArray = JSONArray.fromObject(departments);
        try {
            response.getWriter().print(jsonArray.toString());
        } catch (IOException e) {
            System.out.println("sendDepartmentListJsonToClient exception");
            e.printStackTrace();

        }
    }

    public static void sendDoctorListJsonToClient(HttpServletResponse response, List<Doctor> doctors, String type) {
        if (type.equals(ANDROID_TYPE)) {
            response.setCharacterEncoding(CHARACTERENCODING_ANDROID_UTF_8);
        } else {
            response.setCharacterEncoding(CHARACTERENCODING_CHROME_GBK);
        }
        JSONArray jsonArray = JSONArray.fromObject(doctors);
        try {
            response.getWriter().print(jsonArray.toString());
        } catch (IOException e) {
            System.out.println("sendDoctorListJsonToClient exception");
            e.printStackTrace();

        }
    }

    public static void sendVisitListJsonToClient(HttpServletResponse response, List<Visit> visits, String type) {
        if (type.equals(ANDROID_TYPE)) {
            response.setCharacterEncoding(CHARACTERENCODING_ANDROID_UTF_8);
        } else {
            response.setCharacterEncoding(CHARACTERENCODING_CHROME_GBK);
        }
        JSONArray jsonArray = JSONArray.fromObject(visits);
        try {
            response.getWriter().print(jsonArray.toString());
        } catch (IOException e) {
            System.out.println("sendVisitListJsonToClient exception");
            e.printStackTrace();

        }
    }

    public static void sendDoctorWhetherHaveNumberJsonToClient(HttpServletResponse response, DoctorWhetherHasNumber doctorWhetherHasNumber, String type) {
        if (type.equals(ANDROID_TYPE)) {
            response.setCharacterEncoding(CHARACTERENCODING_ANDROID_UTF_8);
        } else {
            response.setCharacterEncoding(CHARACTERENCODING_CHROME_GBK);
        }
        JSONObject jsonObject = JSONObject.fromObject(doctorWhetherHasNumber);
        try {
            response.getWriter().print(jsonObject.toString());
        } catch (IOException e) {
            System.out.println("sendDoctorWhetherHaveNumberJsonToClient exception");
            e.printStackTrace();

        }
    }

    public static void sendDiagnosisNoJsonToClient(HttpServletResponse response, Diagnosis diagnosisNo, String type) {
        if (type.equals(ANDROID_TYPE)) {
            response.setCharacterEncoding(CHARACTERENCODING_ANDROID_UTF_8);
        } else {
            response.setCharacterEncoding(CHARACTERENCODING_CHROME_GBK);
        }
        JSONObject jsonObject = JSONObject.fromObject(diagnosisNo);
        try {
            response.getWriter().print(jsonObject.toString());
        } catch (IOException e) {
            System.out.println("sendDiagnosisNoJsonToClient exception");
            e.printStackTrace();

        }
    }

    public static void sendMyDoctorJsonToClient(HttpServletResponse response, List<MyDoctor> myDoctors, String type) {
        if (type.equals(ANDROID_TYPE)) {
            response.setCharacterEncoding(CHARACTERENCODING_ANDROID_UTF_8);
        } else {
            response.setCharacterEncoding(CHARACTERENCODING_CHROME_GBK);
        }
        JSONArray jsonArray = JSONArray.fromObject(myDoctors);
        try {
            response.getWriter().print(jsonArray.toString());
        } catch (IOException e) {
            System.out.println("sendMyDoctorJsonToClient exception");
            e.printStackTrace();

        }
    }

    public static void sendRegistrationJsonToClient(HttpServletResponse response, List<Registration> registrations, String type) {
        if (type.equals(ANDROID_TYPE)) {
            response.setCharacterEncoding(CHARACTERENCODING_ANDROID_UTF_8);
        } else {
            response.setCharacterEncoding(CHARACTERENCODING_CHROME_GBK);
        }
        JSONArray jsonArray = JSONArray.fromObject(registrations);
        try {
            response.getWriter().print(jsonArray.toString());
        } catch (IOException e) {
            System.out.println("sendRegistrationJsonToClient exception");
            e.printStackTrace();

        }
    }

    public static void senClientRegistrationJsonToClient(HttpServletResponse response, List<ClientRegistration> clientRegistrations, String type) {
        if (type.equals(ANDROID_TYPE)) {
            response.setCharacterEncoding(CHARACTERENCODING_ANDROID_UTF_8);
        } else {
            response.setCharacterEncoding(CHARACTERENCODING_CHROME_GBK);
        }
        JSONArray jsonArray = JSONArray.fromObject(clientRegistrations);
        try {
            response.getWriter().print(jsonArray.toString());
        } catch (IOException e) {
            System.out.println("sendRegistrationJsonToClient exception");
            e.printStackTrace();

        }
    }

    public static void sendRegistrationFormJsonToClient(HttpServletResponse response, RegistrationForm registrationForm, String type) {
        if (type.equals(ANDROID_TYPE)) {
            response.setCharacterEncoding(CHARACTERENCODING_ANDROID_UTF_8);
        } else {
            response.setCharacterEncoding(CHARACTERENCODING_CHROME_GBK);
        }
        JSONObject jsonObject = JSONObject.fromObject(registrationForm);
        try {
            response.getWriter().print(jsonObject.toString());
        } catch (IOException e) {
            System.out.println("sendRegistrationFormJsonToClient exception");
            e.printStackTrace();

        }
    }

    public static void senClientDoctorJsonToClient(HttpServletResponse response, List<ClientDoctor> clientDoctors, String type) {
        if (type.equals(ANDROID_TYPE)) {
            response.setCharacterEncoding(CHARACTERENCODING_ANDROID_UTF_8);
        } else {
            response.setCharacterEncoding(CHARACTERENCODING_CHROME_GBK);
        }
        JSONArray jsonArray = JSONArray.fromObject(clientDoctors);
        try {
            response.getWriter().print(jsonArray.toString());
        } catch (IOException e) {
            System.out.println("senClientDoctorJsonToClient exception");
            e.printStackTrace();

        }
    }

    public static void sendMessageJsonToClient(HttpServletResponse response, Message message, String type) {
        if (type.equals(ANDROID_TYPE)) {
            response.setCharacterEncoding(CHARACTERENCODING_ANDROID_UTF_8);
        } else {
            response.setCharacterEncoding(CHARACTERENCODING_CHROME_GBK);
        }
        JSONObject jsonObject = JSONObject.fromObject(message);
        try {
            response.getWriter().print(jsonObject.toString());
        } catch (IOException e) {
            System.out.println("sendMessageJsonToClient exception");
            e.printStackTrace();

        }
    }

    public static void sendHealthNewsListToClient(HttpServletResponse response, List<HospitalActivity> healthNews, String type) {
        if (type.equals(ANDROID_TYPE)) {
            response.setCharacterEncoding(CHARACTERENCODING_ANDROID_UTF_8);
        } else {
            response.setCharacterEncoding(CHARACTERENCODING_CHROME_GBK);
        }
        JSONArray jsonArray = JSONArray.fromObject(healthNews);
        try {
            response.getWriter().print(jsonArray.toString());
        } catch (IOException e) {
            System.out.println("sendHealthNewsListToClient exception");
            e.printStackTrace();

        }
    }

    public static void sendLoginResultsonToClient(HttpServletResponse response, LoginResult loginResult, String type) {
        if (type.equals(ANDROID_TYPE)) {
            response.setCharacterEncoding(CHARACTERENCODING_ANDROID_UTF_8);
        } else {
            response.setCharacterEncoding(CHARACTERENCODING_CHROME_GBK);
        }
        JSONObject jsonObject = JSONObject.fromObject(loginResult);
        try {
            response.getWriter().print(jsonObject.toString());
        } catch (IOException e) {
            System.out.println("sendLoginResultsonToClient exception");
            e.printStackTrace();

        }
    }

    public static void sendMedicalCardJsonToClient(HttpServletResponse response, MedicalCard medicalCard, String type) {
        if (type.equals(ANDROID_TYPE)) {
            response.setCharacterEncoding(CHARACTERENCODING_ANDROID_UTF_8);
        } else {
            response.setCharacterEncoding(CHARACTERENCODING_CHROME_GBK);
        }
        JSONObject jsonObject = JSONObject.fromObject(medicalCard);
        try {
            response.getWriter().print(jsonObject.toString());
        } catch (IOException e) {
            System.out.println("sendMedicalCardJsonToClient exception");
            e.printStackTrace();

        }
    }

    public static void sendPictureVerificationCodeJsonToClient(HttpServletResponse response, PictureVerificationCode pictureVerificationCode, String type) {
        if (type.equals(ANDROID_TYPE)) {
            response.setCharacterEncoding(CHARACTERENCODING_ANDROID_UTF_8);
        } else {
            response.setCharacterEncoding(CHARACTERENCODING_CHROME_GBK);
        }
        JSONObject jsonObject = JSONObject.fromObject(pictureVerificationCode);
        try {
            response.getWriter().print(jsonObject.toString());
        } catch (IOException e) {
            System.out.println("sendPictureVerificationCodeJsonToClient exception");
            e.printStackTrace();

        }
    }

    public static void sendDepartmentIntroductionJsonToClient(HttpServletResponse response, DepartmentIntroduction outpatientIntroduction, String type) {
        if (type.equals(ANDROID_TYPE)) {
            response.setCharacterEncoding(CHARACTERENCODING_ANDROID_UTF_8);
        } else {
            response.setCharacterEncoding(CHARACTERENCODING_CHROME_GBK);
        }
        JSONObject jsonObject = JSONObject.fromObject(outpatientIntroduction);
        try {
            response.getWriter().print(jsonObject.toString());
        } catch (IOException e) {
            System.out.println("sendOutpatientIntroductionJsonToClient exception");
            e.printStackTrace();

        }
    }

    public static void sendAllPaymentJsonToClient(HttpServletResponse response, AllPayment allPayment, String type) {
        if (type.equals(ANDROID_TYPE)) {
            response.setCharacterEncoding(CHARACTERENCODING_ANDROID_UTF_8);
        } else {
            response.setCharacterEncoding(CHARACTERENCODING_CHROME_GBK);
        }
        JSONObject jsonObject = JSONObject.fromObject(allPayment);
        try {
            response.getWriter().print(jsonObject.toString());
        } catch (IOException e) {
            System.out.println("sendAllPaymentJsonToClient exception");
            e.printStackTrace();

        }
    }

    public static void sendClientDrugListDataJsonToClient(HttpServletResponse response, ClientDrugListData clientDrugListData, String type) {
        if (type.equals(ANDROID_TYPE)) {
            response.setCharacterEncoding(CHARACTERENCODING_ANDROID_UTF_8);
        } else {
            response.setCharacterEncoding(CHARACTERENCODING_CHROME_GBK);
        }
        JSONObject jsonObject = JSONObject.fromObject(clientDrugListData);
        try {
            response.getWriter().print(jsonObject.toString());
        } catch (IOException e) {
            System.out.println("sendClientDrugListDataJsonToClient exception");
            e.printStackTrace();

        }
    }

    public static void sendClientOperationDataJsonToClient(HttpServletResponse response, ClientOperationData clientOperationData, String type) {
        if (type.equals(ANDROID_TYPE)) {
            response.setCharacterEncoding(CHARACTERENCODING_ANDROID_UTF_8);
        } else {
            response.setCharacterEncoding(CHARACTERENCODING_CHROME_GBK);
        }
        JSONObject jsonObject = JSONObject.fromObject(clientOperationData);
        try {
            response.getWriter().print(jsonObject.toString());
        } catch (IOException e) {
            System.out.println("sendClientOperationDataJsonToClient exception");
            e.printStackTrace();

        }
    }

    public static void sendClientCheckInspectionDataJsonToClient(HttpServletResponse response, ClientCheckInspectionData clientCheckInspectionData, String type) {
        if (type.equals(ANDROID_TYPE)) {
            response.setCharacterEncoding(CHARACTERENCODING_ANDROID_UTF_8);
        } else {
            response.setCharacterEncoding(CHARACTERENCODING_CHROME_GBK);
        }
        JSONObject jsonObject = JSONObject.fromObject(clientCheckInspectionData);
        try {
            response.getWriter().print(jsonObject.toString());
        } catch (IOException e) {
            System.out.println("sendClientCheckInspectionDataJsonToClient exception");
            e.printStackTrace();

        }
    }
}
