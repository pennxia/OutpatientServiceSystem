package cn.nobitastudio.component.controller;

import cn.nobitastudio.component.service.IOutpatientService;
import cn.nobitastudio.model.*;
import cn.nobitastudio.util.HttpSendJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@Scope("prototype")
public class OutpatientServiceSystemReadController {

    @Autowired
    private IOutpatientService iOutpatientService;

    /**
     * @param request  客户端连接服务端时自动创建，request中含有所有客户端发送的信息.
     * @param response 客户端连接服务端时自动创建，通过response向客户端传送信息
     */
    @RequestMapping(value = "login")
    public void login(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }

        LoginResult loginResult = new LoginResult();
        if (account != null && password != null) {
            LoginMessage loginMessage = new LoginMessage(account, password);
            Uuser uuser = iOutpatientService.isLogin(loginMessage); //uuser must isn't null , if don't find a uuser.it's attribute is null
            loginResult.setUuser(uuser);
            if (uuser.getAccount() != null) {
                //query hospitalActivity
                List<HospitalActivity> hospitalActivityList = iOutpatientService.getHospitalActivity();
                loginResult.setHospitalActivityList(hospitalActivityList);

                //query healthNews
                List<HealthNews> healthNewsList = iOutpatientService.getHealthNews();
                loginResult.setHealthNewsList(healthNewsList);

                //query simpleHealthNews
                List<SimpleHealthNews> simpleHealthNewsList = iOutpatientService.getSimpleHealthNews();
                loginResult.setSimpleHealthNewsList(simpleHealthNewsList);
            }
        }

        HttpSendJsonUtil.sendLoginResultsonToClient(response,loginResult,type);
    }

    /**
     * 查询所有的门诊科室
     *
     * @param response 客户端连接服务端时自动创建，通过response向客户端传送信息
     */
    @RequestMapping(value = "getAllDepartment")
    public void getAlldepartment(HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }
        List<Department> departments = iOutpatientService.getAllDepartment();
        HttpSendJsonUtil.sendDepartmentListJsonToClient(response, departments, type);
    }

    /**
     * 查询所有某科室的全部医生
     *
     * @param request  客户端连接服务端时自动创建，request中含有所有客户端发送的信息.
     * @param response 客户端连接服务端时自动创建，通过response向客户端传送信息
     */
    @RequestMapping(value = "getAllDoctorByDepartmentNo")
    public void getAllDcotorByDepartmentNo(HttpServletRequest request, HttpServletResponse response) {
        String departmentNoStr = request.getParameter("departmentNo");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }
        if (departmentNoStr != null) {
            List<Doctor> doctors = iOutpatientService.getAllDoctorByDepartmentNo(Integer.parseInt(departmentNoStr));
            HttpSendJsonUtil.sendDoctorListJsonToClient(response, doctors, type);
        }
    }

    /**
     * 通过医生id查询所有该名医生的全部出诊信息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "getAllVisitByDoctorId")
    public void getAllVisitByDoctorId(HttpServletRequest request, HttpServletResponse response) {
        String doctorId = request.getParameter("doctorId");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }
        if (doctorId != null) {
            List<Visit> visits = iOutpatientService.getAllVisitByDoctorId(Integer.valueOf(doctorId));
            HttpSendJsonUtil.sendVisitListJsonToClient(response, visits, type);
        }
    }

    /**
     * 根据医生id 查询该医生是否有号
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "queryDoctorWhetherHasNumber")
    public void queryDoctorWhetherHasNumber(HttpServletRequest request, HttpServletResponse response) {
        String doctorId = request.getParameter("doctorId");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }
        DoctorWhetherHasNumber doctorWhetherHasNumber = iOutpatientService.queryDoctorWhetherHasNumber(Integer.valueOf(doctorId));
        HttpSendJsonUtil.sendDoctorWhetherHaveNumberJsonToClient(response, doctorWhetherHasNumber, type);
    }

    /**
     * 通过用户的手机号，查询他收藏的医生
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "queryMyDoctorByAccount")
    public void queryMyDoctorByAccount(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter("account");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }

        List<MyDoctor> myDoctors = iOutpatientService.queryMyDoctorByAccount(Long.valueOf(account));
        HttpSendJsonUtil.sendMyDoctorJsonToClient(response, myDoctors, type);
    }

    /**
     * 通过用户的账号，查询他的全部订单（挂号单）
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "queryAllRegistrtionByAccount")
    public void queryAllRegistrtionByAccount(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter("account");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }

        List<Registration> registrations = iOutpatientService.queryAllRegistrtionByAccount(Long.valueOf(account));
        HttpSendJsonUtil.sendRegistrationJsonToClient(response, registrations, type);
    }

    @RequestMapping(value = "queryRegistrationFormByRegistrationNo")
    public void queryRegistrationFormByRegistrationNo(HttpServletRequest request, HttpServletResponse response) {
        String registrationNo = request.getParameter("registrationNo");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }

        RegistrationForm registrationForm = iOutpatientService.getRegistrationFormByRegistrationNo(registrationNo);
        HttpSendJsonUtil.sendRegistrationFormJsonToClient(response, registrationForm, type);
    }

    @RequestMapping(value = "queryAllClientRegistrationByAccount")
    public void queryAllClientRegistrationByAccount(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter("account");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }

        List<ClientRegistration> clientRegistrations = iOutpatientService.queryAllClientRegistrationByAccount(Long.valueOf(account));
        HttpSendJsonUtil.senClientRegistrationJsonToClient(response, clientRegistrations, type);
    }

    @RequestMapping(value = "queryAllClientDoctorByAccount")
    public void queryAllClientDoctorByAccount(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter("account");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }

        List<ClientDoctor> clientDoctors = iOutpatientService.queryAllClientDoctorByAccount(Long.valueOf(account));
        HttpSendJsonUtil.senClientDoctorJsonToClient(response, clientDoctors, type);
    }

    @RequestMapping(value = "whetherHaveRecord")
    public void whetherHaveRecord(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter("account");
        String queryType = request.getParameter("queryType"); // 1 references enroll  2 references update password.
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }

        boolean whetherHaveRecord = iOutpatientService.userWhetherCanEnroll(Long.valueOf(account));//if haven't query one ,return false,or true
        Message message = new Message(false, "初始化Message");
        if (whetherHaveRecord) {
            if (queryType.equals("1")) {
                // can't enroll
                message = new Message(false, "已被注册");
            } else if (queryType.equals("2")) {
                //update password
                message = new Message(true, "可以修改");
            }
        } else {
            if (queryType.equals("1")) {
                //enroll
                message = new Message(true, "可以注册");
            } else if (queryType.equals("2")) {
                //cen't update password
                message = new Message(false, "尚未注册");
            }

        }
        HttpSendJsonUtil.sendMessageJsonToClient(response, message, type);
    }

    @RequestMapping(value = "getAllHealthNews")
    public void getAllHealthNews(HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }

        List<HospitalActivity> healthNews = iOutpatientService.getHospitalActivity();
        HttpSendJsonUtil.sendHealthNewsListToClient(response, healthNews, type);
    }

    @RequestMapping(value = "whetherCreateMedicalCard")
    public void whetherCreateMedicalCard(HttpServletRequest request, HttpServletResponse response){
        String ownerAccount = request.getParameter("ownerAccount");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }

        Message message = iOutpatientService.whetherCreateMedicalCard(Long.valueOf(ownerAccount));
        HttpSendJsonUtil.sendMessageJsonToClient(response,message,type);
    }

    @RequestMapping(value = "medicalCardWhetherExist")
    public void medicalCardWhetherExist(HttpServletRequest request, HttpServletResponse response){
        String medicalCardNo = request.getParameter("medicalCardNo");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }

        MedicalCard medicalCard = iOutpatientService.getMedicalCardByMedicalCardNo(Long.valueOf(medicalCardNo));
        HttpSendJsonUtil.sendMedicalCardJsonToClient(response, medicalCard, type);
    }

    @RequestMapping(value = "getPictureVerificationCode")
    public void getPictureVerificationCode(HttpServletRequest request, HttpServletResponse response){
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }

        PictureVerificationCode pictureVerificationCode = iOutpatientService.getPictureVerificationCode();
        HttpSendJsonUtil.sendPictureVerificationCodeJsonToClient(response,pictureVerificationCode,type);
    }

    @RequestMapping(value = "getDepartmentIntroduction")
    public void getDepartmentIntroduction(HttpServletRequest request, HttpServletResponse response){
        String departmentNo = request.getParameter("departmentNo");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }

        DepartmentIntroduction departmentIntroduction = iOutpatientService.getDepartmentIntroduction(Integer.valueOf(departmentNo));
        HttpSendJsonUtil.sendDepartmentIntroductionJsonToClient(response,departmentIntroduction,type);
    }

    @RequestMapping(value = "getAllPayment")
    public void getAllPayment(HttpServletRequest request, HttpServletResponse response){
        String account = request.getParameter("account");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }

        AllPayment allPayment = iOutpatientService.getAllPayment(Long.valueOf(account));
        HttpSendJsonUtil.sendAllPaymentJsonToClient(response,allPayment,type);
    }

    @RequestMapping(value = "getDrugListDetails")
    public void getDrugListDetails(HttpServletRequest request, HttpServletResponse response){

        String drugCostId = request.getParameter("drugCostId");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }

        ClientDrugListData clientDrugListData = iOutpatientService.getDrugListDetails(drugCostId);
        HttpSendJsonUtil.sendClientDrugListDataJsonToClient(response,clientDrugListData,type);
    }

    @RequestMapping(value = "getOperationCostDetails")
    public void getOperationCostDetails(HttpServletRequest request, HttpServletResponse response){

        String operationCostId = request.getParameter("operationCostId");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }

        ClientOperationData clientOperationData = iOutpatientService.getOperationCostDetails(operationCostId);
        HttpSendJsonUtil.sendClientOperationDataJsonToClient(response,clientOperationData,type);
    }

    @RequestMapping(value = "getCheckInspectionDetails")
    public void getCheckInspectionDetails(HttpServletRequest request, HttpServletResponse response){

        String checkInspectionCostId = request.getParameter("checkInspectionCostId");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }

        ClientCheckInspectionData clientCheckInspectionData = iOutpatientService.getCheckInspectionDetails(checkInspectionCostId);
        HttpSendJsonUtil.sendClientCheckInspectionDataJsonToClient(response,clientCheckInspectionData,type);
    }

    /**
     * test method
     */
    @RequestMapping(value = "readTest")
    public void test(HttpServletRequest request, HttpServletResponse response) {

    }

}
