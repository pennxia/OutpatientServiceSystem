package cn.nobitastudio.component.controller;

import cn.nobitastudio.component.service.IOutpatientService;
import cn.nobitastudio.model.*;
import cn.nobitastudio.util.HttpSendJsonUtil;
import cn.nobitastudio.util.SendMessageUtil;
import cn.nobitastudio.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class OutpatientServiceSystemWriteController {

    @Autowired
    private IOutpatientService iOutpatientService;

    /**
     * 挂号正式开始
     *
     * @param request
     * @param response
     */
    @RequestMapping("startRegister")
    public synchronized void startRegister(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter("account");
        String medicalCardNo = request.getParameter("medicalCardNo");
        String visitNo = request.getParameter("visitNo");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }

        Diagnosis diagnosis = iOutpatientService.startRegistration(Long.valueOf(account), Long.valueOf(visitNo), Long.valueOf(medicalCardNo));
        HttpSendJsonUtil.sendDiagnosisNoJsonToClient(response, diagnosis, type);
    }

    /**
     * 取消本次挂号
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "cancelRegistration")
    public synchronized void cancelRegistration(HttpServletRequest request, final HttpServletResponse response) {
        final String registrationNo = request.getParameter("registrationNo");
        String needResponse = request.getParameter("needResponse");
        String type = request.getParameter("type");
        boolean sendResponse = false;
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }
        if (needResponse == null || needResponse.isEmpty() || needResponse.equals("yes")) {
            sendResponse = true;
        } else if (needResponse.equals("no")) {
            sendResponse = false;
        }

        boolean success = iOutpatientService.cancelRegistration(registrationNo);
        Message message;
        if (sendResponse) {
            if (success) {
                message = new Message(true, "取消成功");
            } else {
                message = new Message(false, "取消失败");
            }
            HttpSendJsonUtil.sendMessageJsonToClient(response, message, type);
        }

    }

    /**
     * 确认本次挂号（在支付成功后，调用该方法）
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "confirmRegistration")
    public synchronized void confirmRegistration(HttpServletRequest request, final HttpServletResponse response) {
        final String registrationNo = request.getParameter("registrationNo");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }
        Message message;
        boolean success = iOutpatientService.confirmRegistration(registrationNo);
        if (success) {
            message = new Message(true, "确认成功");
        } else {
            message = new Message(false, "确认失败");
        }
        HttpSendJsonUtil.sendMessageJsonToClient(response, message, type);
    }

    /**
     * 将某一个医生插入到他的医生列表中
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "addDoctorToMyDoctor")
    public synchronized void addDoctorToMyDoctor(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter("account");
        String doctorId = request.getParameter("doctorId");
        int lines = iOutpatientService.addDoctorToMyDoctor(Long.valueOf(account), Long.valueOf(doctorId));
    }

    /**
     * 将某一个医生从用户的医生列表移除
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "removeDoctorFromMyDoctor")
    public synchronized void removeDoctorFromMyDoctor(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter("account");
        String doctorId = request.getParameter("doctorId");

        int lines = iOutpatientService.removeDoctorFromMydoctor(Long.valueOf(account), Long.valueOf(doctorId));
        if (!(lines > 0)) {
            // update the data fail.record it.there i don't finish it.
        }
    }

    @RequestMapping(value = "getVerificationCode")
    public void userGetVerificationCode(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter("account");
        String messageType = request.getParameter("messageType"); //需要发送的验证码类型
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }
        String[] params;
        String verificationCode = Utility.getRandom(100000, 999999);
        Message message;

        //by messagetype to init params params[0] is verificationcode. other is defined by myself.
        if (messageType.equals("4")) {
            // bind medical card params are verification code,medicalcardno,ownername
            String medicalCardNo = request.getParameter("medicalCardNo");
/*            //because the params item's max lenth is 12,but the medicalcardno length is 16.so ,let it sparate.
            //spatate it to 年月日，时分秒，序号
            String medicalCardNoOne = medicalCardNo.substring(0, 8);
            String medicalCardNoTwo = medicalCardNo.substring(8, 14);
            String medicalCardNoThree = medicalCardNo.substring(14);
            params = new String[]{verificationCode, medicalCardNoOne, medicalCardNoTwo, medicalCardNoThree, ownerName};*/

            //the limited don't exit
            String ownerName = request.getParameter("ownerName");
            params = new String[]{verificationCode, medicalCardNo, ownerName};

        } else {
            params = new String[]{verificationCode};
        }

        final UserVerificationCode userVerificationCode = SendMessageUtil.sendVerificationCodeToUser(account, Integer.valueOf(messageType), params);
        if (userVerificationCode.getSendResult().equals("success")) {
            // save the verification code.
            iOutpatientService.addUserVerificationCode(userVerificationCode);
            message = new Message(true, "发送成功");
            //set a thread to change the verification if user do nothing after 2 minutes
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(120000);
                        //first query this verification code whether is effctive. if it's not ,need'n do anything.
                        int userVerificationCodeState = iOutpatientService.queryUserVerificationCodeState(userVerificationCode);
                        if (userVerificationCodeState == 1) {
                            //the user don't use the verification code. change the userverification code state sign.let it invalid
                            iOutpatientService.updateUserverificationCode(userVerificationCode);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } else {
            //send fail.send the fail data to client
            message = new Message(false, "发送失败");
        }
        HttpSendJsonUtil.sendMessageJsonToClient(response, message, type);
    }

    @RequestMapping(value = "userConfirmVerificationCode")
    public synchronized void userConfirmVerificationCode(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter("account");
        String verificationCode = request.getParameter("verificationCode");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }

        Message message = iOutpatientService.userConfirmVerificationCode(Long.valueOf(account), verificationCode);
        HttpSendJsonUtil.sendMessageJsonToClient(response, message, type);
    }

    @RequestMapping(value = "userEnroll")
    public synchronized void userEnroll(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String userName = request.getParameter("userName");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }
        LoginResult loginResult = new LoginResult();
        Uuser uuser = iOutpatientService.userEnrollSuccess(Long.valueOf(account), password, userName);
        loginResult.setUuser(uuser);
        if (uuser.getAccount() != null || uuser.getPassword() != null || !(uuser.getPassword().isEmpty())) {
            //query hospitalActivity
            List<HospitalActivity> hospitalActivityList = iOutpatientService.getHospitalActivity();
            loginResult.setHospitalActivityList(hospitalActivityList);
            //query healthNews
            List<HealthNews> healthNewsList = iOutpatientService.getHealthNews();
            loginResult.setHealthNewsList(healthNewsList);
        }

        HttpSendJsonUtil.sendLoginResultsonToClient(response, loginResult, type);
    }

    @RequestMapping(value = "userChangePassword")
    public synchronized void userChangePassword(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter("account");
        String newPassword = request.getParameter("newPassword");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }

        LoginResult loginResult = new LoginResult();
        Uuser uuser = iOutpatientService.userChangePassword(Long.valueOf(account), newPassword);
        loginResult.setUuser(uuser);
        if (uuser.getAccount() != null || !(uuser.getPassword() != null) || !(uuser.getPassword().isEmpty())) {
            //query hospitalActivity
            List<HospitalActivity> hospitalActivityList = iOutpatientService.getHospitalActivity();
            loginResult.setHospitalActivityList(hospitalActivityList);
            //query healthNews
            List<HealthNews> healthNewsList = iOutpatientService.getHealthNews();
            loginResult.setHealthNewsList(healthNewsList);
        }

        HttpSendJsonUtil.sendLoginResultsonToClient(response, loginResult, type);

    }

    @RequestMapping(value = "userCreateMedicalCard")
    public synchronized void userCreateMedicalCard(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter("account");
        String ownerAccount = request.getParameter("ownerAccount");
        String ownerName = request.getParameter("ownerName");
        String ownerSex = request.getParameter("ownerSex");
        String ownerIdCard = request.getParameter("ownerIdCard");
        String ownerAddress = request.getParameter("ownerAddress");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }
        Long medicalCardNo = Utility.getMedicalcardNo();
        MedicalCard medicalCard = new MedicalCard(medicalCardNo, ownerName, ownerIdCard, ownerSex, ownerAddress, Long.valueOf(ownerAccount));
        boolean isSuccess = iOutpatientService.userCreateMedicalCard(medicalCard);
        if (isSuccess) {
            //bind the account and medicalcard
            boolean bindSuccess = iOutpatientService.userBindMedicalCard(Long.valueOf(account), medicalCard.getMedicalCardNo());
            if (!bindSuccess) {
                //wait for deal with.
            }
        } else {
            medicalCard = new MedicalCard();
        }
        HttpSendJsonUtil.sendMedicalCardJsonToClient(response, medicalCard, type);
    }

    @RequestMapping(value = "userBindMedicalCard")
    public synchronized void userBindMedicalCard(HttpServletRequest request, HttpServletResponse response) {

        String ownerAccount = request.getParameter("ownerAccount");
        String verificationCode = request.getParameter("verificationCode");
        String account = request.getParameter("account");
        String medicalCardNo = request.getParameter("medicalCardNo");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }
        //judget the verificationcode.
        Message message = iOutpatientService.userConfirmVerificationCode(Long.valueOf(ownerAccount), verificationCode);
        if (message.isSuccess()) {
            //verificationcode ,bind the accout and medicalcardNo.
            boolean bindSuccess = iOutpatientService.userBindMedicalCard(Long.valueOf(account), Long.valueOf(medicalCardNo));
            if (bindSuccess) {
                message = new Message(true, "绑定成功");
            } else {
                message = new Message(false, "绑定失败");
            }
        }

        HttpSendJsonUtil.sendMessageJsonToClient(response, message, type);
    }

    @RequestMapping(value = "confirmOrder")
    public synchronized void confirmOrders(HttpServletRequest request, HttpServletResponse response) {
        String paySerialNumber = request.getParameter("paySerialNumber");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }

        Message message = iOutpatientService.confirmOrder(paySerialNumber);
        HttpSendJsonUtil.sendMessageJsonToClient(response, message, type);
    }

    @RequestMapping(value = "userUnbindMedicalCard")
    public synchronized void userUnbindMedicalCard(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter("account");
        String medicalCardNo = request.getParameter("medicalCardNo");
        String type = request.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "chrome";
        }

        Message message = iOutpatientService.userUnbindMedicalCard(Long.valueOf(account), Long.valueOf(medicalCardNo));
        HttpSendJsonUtil.sendMessageJsonToClient(response, message, type);
    }

    @RequestMapping(value = "writeTest")
    public synchronized void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String str = Utility.getRegisterSerialNumber();
        response.getWriter().print(str);
        System.out.println(str);
    }


}
