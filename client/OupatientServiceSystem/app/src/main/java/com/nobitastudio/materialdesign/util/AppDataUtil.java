package com.nobitastudio.materialdesign.util;

import android.support.v7.app.AppCompatActivity;

import com.nobitastudio.materialdesign.bean.*;

import java.util.ArrayList;
import java.util.List;

public class AppDataUtil {

    private static int MAIN_ACITIVITY_NO = 5001; // references mainActivity

    private static int REGISTER_ACTIVITY_NO = 5002; // references registerActivity

    private static int HOSPIALHOME_FRAGMENT_NO = 5003; // references HospitalHomeFragment

    private static int PERSONALCENTER_ACTIVITY_NO = 5005; // references registerActivity

    private static int entranceActivityNo = 5001; // reference entranceActivity is which activity,default main activity

    /**
     * normal global data.
     */

    private static User user;

    private static OutpatientDepartment selectedOutpatientDepartment;

    private static Doctor selectedDoctor;

    private static Visit selectedVisit;

    private static MedicalCard selectedMedicalCard;

    private static Diagnosis diagnosis;

    private static String registrationNo;

    private static Integer orderState;

    private static Integer doctorId;

    private static String enrollAccount;

    private static String updatePasswordAccount;

    private static HospitalActivity selectedHospitalActivity;

    private static HealthNews selectedHealthNews;

    private static String ownerAccount;

    private static MedicalCard waitForBindMedicalCard;

    private static String itemCostId;

    private static ClientDrugListData clientDrugListData;


    /**
     * list gloabal data
     */

    private static List<PaymentSimpleData> displayPaymentSimpleDataList = new ArrayList<>();

    private static List<AllPayment> allPayments = new ArrayList<>();

    private static List<SimpleHealthNews> simpleHealthNewsList = new ArrayList<>();

    private static List<AppCompatActivity> activities = new ArrayList<>();

    private static List<Visit> visits = new ArrayList<>();

    private static List<Visit> visitsDisplayed = new ArrayList<>();

    private static List<Visit> visitsAvailable = new ArrayList<>();

    private static List<ClientRegistration> clientRegistrations = new ArrayList<>();

    private static List<OutpatientDepartment> outpatientDepartments = new ArrayList<>();

    private static List<Doctor> doctors = new ArrayList<>();

    private static List<DoctorWhetherHasNumber> doctorWhetherHasNumbers = new ArrayList<>();

    private static List<ClientDoctor> clientDoctors = new ArrayList<>();

    private static List<HospitalActivity> hospitalActivityList = new ArrayList<>();

    private static List<HealthNews> healthNewsList;

    private static List<HealthNews> healthNewsHeadlinesList;//reference 健康头条

    private static List<HealthNews> healthNewsLectureList;//reference 医生讲座

    //getter and setter method


    public static ClientDrugListData getClientDrugListData() {
        return clientDrugListData;
    }

    public static void setClientDrugListData(ClientDrugListData clientDrugListData) {
        AppDataUtil.clientDrugListData = clientDrugListData;
    }

    public static String getItemCostId() {
        return itemCostId;
    }

    public static void setItemCostId(String itemCostId) {
        AppDataUtil.itemCostId = itemCostId;
    }

    public static List<PaymentSimpleData> getDisplayPaymentSimpleDataList() {
        return displayPaymentSimpleDataList;
    }

    public static void setDisplayPaymentSimpleDataList(List<PaymentSimpleData> displayPaymentSimpleDataList) {
        AppDataUtil.displayPaymentSimpleDataList = displayPaymentSimpleDataList;
    }

    public static List<AllPayment> getAllPayments() {
        return allPayments;
    }

    public static void setAllPayments(List<AllPayment> allPayments) {
        AppDataUtil.allPayments = allPayments;
    }

    public static List<SimpleHealthNews> getSimpleHealthNewsList() {
        return simpleHealthNewsList;
    }

    public static void setSimpleHealthNewsList(List<SimpleHealthNews> simpleHealthNewsList) {
        AppDataUtil.simpleHealthNewsList = simpleHealthNewsList;
    }

    public static MedicalCard getWaitForBindMedicalCard() {
        return waitForBindMedicalCard;
    }

    public static void setWaitForBindMedicalCard(MedicalCard waitForBindMedicalCard) {
        AppDataUtil.waitForBindMedicalCard = waitForBindMedicalCard;
    }

    public static String getOwnerAccount() {
        return ownerAccount;
    }

    public static void setOwnerAccount(String ownerAccount) {
        AppDataUtil.ownerAccount = ownerAccount;
    }

    public static List<HealthNews> getHealthNewsHeadlinesList() {
        return healthNewsHeadlinesList;
    }

    public static void setHealthNewsHeadlinesList(List<HealthNews> healthNewsHeadlinesList) {
        AppDataUtil.healthNewsHeadlinesList = healthNewsHeadlinesList;
    }

    public static List<HealthNews> getHealthNewsLectureList() {
        return healthNewsLectureList;
    }

    public static void setHealthNewsLectureList(List<HealthNews> healthNewsLectureList) {
        AppDataUtil.healthNewsLectureList = healthNewsLectureList;
    }

    public static HospitalActivity getSelectedHospitalActivity() {
        return selectedHospitalActivity;
    }

    public static void setSelectedHospitalActivity(HospitalActivity selectedHospitalActivity) {
        AppDataUtil.selectedHospitalActivity = selectedHospitalActivity;
    }

    public static HealthNews getSelectedHealthNews() {
        return selectedHealthNews;
    }

    public static void setSelectedHealthNews(HealthNews selectedHealthNews) {
        AppDataUtil.selectedHealthNews = selectedHealthNews;
    }

    public static List<HospitalActivity> getHospitalActivityList() {
        return hospitalActivityList;
    }

    public static void setHospitalActivityList(List<HospitalActivity> hospitalActivityList) {
        AppDataUtil.hospitalActivityList = hospitalActivityList;
    }

    public static List<HealthNews> getHealthNewsList() {
        return healthNewsList;
    }

    public static void setHealthNewsList(List<HealthNews> healthNewsList) {
        AppDataUtil.healthNewsList = healthNewsList;
    }

    public static String getUpdatePasswordAccount() {
        return updatePasswordAccount;
    }

    public static void setUpdatePasswordAccount(String updatePasswordAccount) {
        AppDataUtil.updatePasswordAccount = updatePasswordAccount;
    }

    public static List<AppCompatActivity> getActivities() {
        return activities;
    }

    public static String getEnrollAccount() {
        return enrollAccount;
    }

    public static void setEnrollAccount(String enrollAccount) {
        AppDataUtil.enrollAccount = enrollAccount;
    }

    public static Integer getDoctorId() {
        return doctorId;
    }

    public static void setDoctorId(Integer doctorId) {
        AppDataUtil.doctorId = doctorId;
    }

    public static List<ClientDoctor> getClientDoctors() {
        return clientDoctors;
    }

    public static void setClientDoctors(List<ClientDoctor> clientDoctors) {
        AppDataUtil.clientDoctors = clientDoctors;
    }

    public static String getRegistrationNo() {
        return registrationNo;
    }

    public static void setRegistrationNo(String registrationNo) {
        AppDataUtil.registrationNo = registrationNo;
    }

    public static Integer getOrderState() {
        return orderState;
    }

    public static void setOrderState(Integer orderState) {
        AppDataUtil.orderState = orderState;
    }

    public static List<Visit> getVisitsDisplayed() {
        return visitsDisplayed;
    }

    public static void setVisitsDisplayed(List<Visit> visitsDisplayed) {
        AppDataUtil.visitsDisplayed = visitsDisplayed;
    }

    public static List<Visit> getVisitsAvailable() {
        return visitsAvailable;
    }

    public static void setVisitsAvailable(List<Visit> visitsAvailable) {
        AppDataUtil.visitsAvailable = visitsAvailable;
    }

    public static List<DoctorWhetherHasNumber> getDoctorWhetherHasNumbers() {
        return doctorWhetherHasNumbers;
    }

    public static void setDoctorWhetherHasNumbers(List<DoctorWhetherHasNumber> doctorWhetherHasNumbers) {
        AppDataUtil.doctorWhetherHasNumbers = doctorWhetherHasNumbers;
    }

    public static List<Doctor> getDoctors() {
        return doctors;
    }

    public static void setDoctors(List<Doctor> doctors) {
        AppDataUtil.doctors = doctors;
    }

    public static List<OutpatientDepartment> getOutpatientDepartments() {
        return outpatientDepartments;
    }

    public static void setOutpatientDepartments(List<OutpatientDepartment> outpatientDepartments) {
        AppDataUtil.outpatientDepartments = outpatientDepartments;
    }

    public static List<ClientRegistration> getClientRegistrations() {
        return clientRegistrations;
    }

    public static void setClientRegistrations(List<ClientRegistration> clientRegistrations) {
        AppDataUtil.clientRegistrations = clientRegistrations;
    }

    public static List<Visit> getVisits() {
        return visits;
    }

    public static void setVisits(List<Visit> visits) {
        AppDataUtil.visits = visits;
    }

    public static List<MedicalCard> getMedicalCards() {
        return AppDataUtil.getUser().getMedicalCards();
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        AppDataUtil.user = user;
    }

    public static OutpatientDepartment getSelectedOutpatientDepartment() {
        return selectedOutpatientDepartment;
    }

    public static void setSelectedOutpatientDepartment(OutpatientDepartment selectedOutpatientDepartment) {
        AppDataUtil.selectedOutpatientDepartment = selectedOutpatientDepartment;
    }

    public static Doctor getSelectedDoctor() {
        return selectedDoctor;
    }

    public static void setSelectedDoctor(Doctor selectedDoctor) {
        AppDataUtil.selectedDoctor = selectedDoctor;
    }

    public static Visit getSelectedVisit() {
        return selectedVisit;
    }

    public static void setSelectedVisit(Visit selectedVisit) {
        AppDataUtil.selectedVisit = selectedVisit;
    }

    public static MedicalCard getSelectedMedicalCard() {
        return selectedMedicalCard;
    }

    public static void setSelectedMedicalCard(MedicalCard selectedMedicalCard) {
        AppDataUtil.selectedMedicalCard = selectedMedicalCard;
    }

    public static Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public static void setDiagnosis(Diagnosis diagnosis) {
        AppDataUtil.diagnosis = diagnosis;
    }


    //other method

    /**
     * close all activities
     */
    public static void finishAllActivities() {
        for (AppCompatActivity activity : getActivities()) {
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * close all activity but one
     * @param remainActivity
     */
    public static void finishAllactivitiesButOne(AppCompatActivity remainActivity) {
        for (AppCompatActivity activity : getActivities()) {
            if (activity != null && activity != remainActivity) {
                activity.finish();
            }
        }
    }


    /**
     * update entranceActivityNo to target activity
     *
     * @param targetActivityNo reference target activity no
     */
    public static void updateEntranceActivityNo(int targetActivityNo) {
        entranceActivityNo = targetActivityNo;
    }

    /**
     * 查看当前activity是否是从mainActivity进入下一个activity
     */
    public static boolean entranceActivityIsMainActivity() {
        if (entranceActivityNo == MAIN_ACITIVITY_NO) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查看当前activity是否是从personalCenterActivity进入下一个activity
     */
    public static boolean entranceActivityIsPersonalCenterActivity() {
        if (entranceActivityNo == PERSONALCENTER_ACTIVITY_NO) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查看是否是从registerActivity进入的
     */
    public static boolean entranceActivityIsRegisterActivity() {
        if (entranceActivityNo == REGISTER_ACTIVITY_NO) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean entranceActivityIsHospitalHomeFragment(){
        if (entranceActivityNo == HOSPIALHOME_FRAGMENT_NO){
            return true;
        } else {
            return false;
        }
    }




}
