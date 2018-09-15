package cn.nobitastudio.component.service;

import cn.nobitastudio.model.*;

import java.util.List;

public interface IOutpatientService {

    /**
     * 查询是否存在该用户，若存在，这返回该用户信息
     *
     * @param message 登陆信息的封装对象
     * @return 如果根据登陆信息，确认为合法登陆信息，则返回登录信息对应账号的User信息，不合法则返回空;
     */
    Uuser isLogin(LoginMessage message);

    /**
     * 查询所有的科室
     *
     * @return 科室列表
     */
    List<Department> getAllDepartment();

    /**
     * 查询某个科室下的所有坐诊医生
     *
     * @param deparmentNo 需要查询科室的代号
     * @return 该科室下所有医生集合
     */
    List<Doctor> getAllDoctorByDepartmentNo(Integer deparmentNo);

    /**
     * 查询某位医生的全部坐诊安排
     *
     * @param doctorId 需要查询医生的id
     * @return 该医生的全部出诊安排
     */
    List<Visit> getAllVisitByDoctorId(Integer doctorId);

    /*    *//**
     * 通过用户的账号，就诊卡号，出诊号，生成一个挂号单
     * @param uuser 挂号用户
     * @param medicalCard 就诊病人的就诊卡
     * @param visit 所选择的医生特定出诊
     * @return
     *//*
    RegisterForm getRegistrationForm(Uuser uuser,MedicalCard medicalCard,Visit visit);*/

    /**
     * 通过医生的id查询该名医生的出诊安排内，是否有号可以挂
     *
     * @param doctorId
     * @return
     */
    DoctorWhetherHasNumber queryDoctorWhetherHasNumber(Integer doctorId);

    /**
     * 插入一条挂号单信息
     *
     * @param account       用户账号
     * @param visitno       医生的出诊安排号
     * @param medicalcardno 就诊病人的就诊卡号
     * @return
     */
    Diagnosis startRegistration(Long account, Long visitno, Long medicalcardno);


    /**
     * 根据挂号单号，取消挂号记录。并且将取消本次就诊的就诊卡取消就诊次数+1,达到5次便冻结
     *
     * @param registrationNo 待取消挂号单的流水号
     */
    boolean cancelRegistration(String registrationNo);

    /**
     * 根据挂号单号，取消挂号记录。并且将取消本次就诊的就诊卡取消就诊次数+1,达到5次便冻结
     *
     * @param registrationNo 待取消挂号单的流水号
     */
    boolean confirmRegistration(String registrationNo);

    /**
     * 通过用户的手机号，查询他收藏的医生
     *
     * @param account 待查询的用户手机号
     */
    List<MyDoctor> queryMyDoctorByAccount(Long account);

    /**
     * 将某一个医生插入到他的医生列表中
     *
     * @param account  用户的账号
     * @param doctorId 待收藏的医生id
     * @return
     */
    int addDoctorToMyDoctor(Long account, Long doctorId);

    /**
     * 将某一个医生从用户的医生列表中删除
     *
     * @param account  用户的账号
     * @param doctorId 待收藏的医生id
     * @return
     */
    int removeDoctorFromMydoctor(Long account, Long doctorId);

    /**
     * 通过用户的账号，查询他的全部订单（挂号单）
     *
     * @param account 待查找用户的账号
     * @return
     */
    List<Registration> queryAllRegistrtionByAccount(Long account);

    /**
     * 通过挂号单号，得到简单的挂号单表
     *
     * @param registrationNo
     * @return
     */
    RegistrationForm getRegistrationFormByRegistrationNo(String registrationNo);

    /**
     * 通过用户账号查询其所有的挂号单，和普通的通过账号查询挂号单相比，这个是发送给客户端的数据，并不需要所有的挂号单记录，仅仅需要部分数据即可，且需要查询其每个挂号单对应的价钱
     *
     * @param account
     * @return
     */
    List<ClientRegistration> queryAllClientRegistrationByAccount(Long account);

    /**
     * 通过用户的账号，查询所有他的收藏医生信息，其中包含两个对象，医生和他的科室
     *
     * @param account
     * @return
     */
    List<ClientDoctor> queryAllClientDoctorByAccount(Long account);

    /**
     * 通过用户输入的账号 判断是否该账号已被注册
     *
     * @param account 待注册账号
     * @return 若能够注册返回true 否则 返回false
     */
    boolean userWhetherCanEnroll(Long account);

    /**
     * 将用户的请求验证码记录保存在UserVerificationCode 表中
     *
     * @param userVerificationCode
     * @return
     */
    void addUserVerificationCode(UserVerificationCode userVerificationCode);

    /**
     * 根据UserVerificationCodeState 查询它此刻的状态
     *
     * @param userVerificationCode
     * @return
     */
    int queryUserVerificationCodeState(UserVerificationCode userVerificationCode);

    /**
     * 更新用户的验证码，使其invalid
     *
     * @param userVerificationCode
     */
    void updateUserverificationCode(UserVerificationCode userVerificationCode);

    /**
     * 用户从客户端提交了验证码
     *
     * @param accout
     * @param userVerificationCode
     * @return
     */
    Message userConfirmVerificationCode(Long accout, String userVerificationCode);

    /**
     * 用户通过验证码操作，进入填写资料注册注册用户阶段
     *
     * @param account
     * @param password
     * @param userName
     * @return
     */
    Uuser userEnrollSuccess(Long account, String password, String userName);

    /**
     * 用户更新密码
     *
     * @param account
     * @param newPassword
     * @return
     */
    Uuser userChangePassword(Long account, String newPassword);

    /**
     * test method
     */
    Uuser test(Long account);

    /**
     * 得到所有的医院活动
     *
     * @return
     */
    List<HospitalActivity> getHospitalActivity();

    /**
     * 得到所有健康新闻
     *
     * @return
     */
    List<HealthNews> getHealthNews();

    /**
     * 查询该手机号是否能办理诊疗卡,每个手机号限办五个.
     *
     * @param ownerAccount
     * @return
     */
    Message whetherCreateMedicalCard(Long ownerAccount);


    /**
     * 用户办理诊疗卡
     *
     * @param medicalCard
     * @return
     */
    boolean userCreateMedicalCard(MedicalCard medicalCard);

    /**
     * 用户绑定诊疗卡
     *
     * @param account
     * @param medicalCardNo
     * @return
     */
    boolean userBindMedicalCard(Long account, Long medicalCardNo);

    /**
     * 通过medicalCardNo 查询其medicalcard对象
     *
     * @param medicalCardNo
     * @return
     */
    MedicalCard getMedicalCardByMedicalCardNo(Long medicalCardNo);

    /**
     * 得到图片类验证码
     *
     * @return
     */
    PictureVerificationCode getPictureVerificationCode();

    /**
     * 得到简要健康新闻，用于healthNewsActivity中pager的显示
     *
     * @return
     */
    List<SimpleHealthNews> getSimpleHealthNews();

    /**
     * 得到departmentNo对应的DepartmentIntroduction
     *
     * @param departmentNo
     * @return
     */
    DepartmentIntroduction getDepartmentIntroduction(Integer departmentNo);

    /**
     * 通过该用户的account 得到所以其各种缴费信息，及其每类缴费各类收费项目
     *
     * @param account
     * @return
     */
    AllPayment getAllPayment(Long account);

    /**
     * 得到客户端需要的处方单详情
     *
     * @param drugCostId
     * @return
     */
    ClientDrugListData getDrugListDetails(String drugCostId);

    /**
     * 通过 手术单号 得到相应的手术单信息
     * @param operationCostId
     * @return
     */
    ClientOperationData getOperationCostDetails(String operationCostId);

    /**
     * 通过 checkInspectionCostId 查询其检查客户端需要的检验单的数据
     * @param checkInspectionCostId
     * @return
     */
    ClientCheckInspectionData getCheckInspectionDetails(String checkInspectionCostId);

    /**
     * 用户支付完成，确认订单
     * @param paySerialNumber
     * @return
     */
    Message confirmOrder(String paySerialNumber);

    /**
     * 用户接触绑定诊疗卡
     * @param account
     * @param medicalCardNo
     * @return
     */
    Message userUnbindMedicalCard(Long account,Long medicalCardNo);
}
