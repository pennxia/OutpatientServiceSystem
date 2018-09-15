package cn.nobitastudio.component.dao;

import cn.nobitastudio.model.*;

import java.util.List;

public interface IOutpatientServiceDAO {

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


    /**
     * 查询某一用户下的全部就诊卡
     *
     * @param account 需要查询用户的账号
     * @return 该用户的全部绑定就诊卡
     */
    List<MedicalCard> getAllMedicalCardByAccount(Long account);

    /**
     * 插入挂号记录
     *
     * @param uuser       挂号用户
     * @param medicalCard 就诊病人就诊卡
     * @param visit       确定预约的医生出诊安排
     * @return 插入的行数
     */
    boolean insertIntoRegisration(Uuser uuser, MedicalCard medicalCard, Visit visit);

    /**
     * 通过出诊安排查询该次出诊安排的医生
     *
     * @param visit 本次出诊安排
     * @return 此出诊安排的医生
     */
    Doctor getDoctorByVisit(Visit visit);

    /**
     * 根据医生信息查询改名医生所属的科室
     *
     * @param doctor 需要查询的医生
     * @return 该名医生的门诊信息
     */
    Department getDepartmentByDoctor(Doctor doctor);

    /**
     * 插入一条挂号单信息
     * @param registrationno
     * @param account
     * @param visitno
     * @param medicalcardno
     * @param diagnosisNo
     * @param createTime
     * @return
     */
    int insertRegistrationData(String registrationno, Long account, Long visitno, Long medicalcardno, Integer diagnosisNo, String createTime);

    /**
     * 在插入挂号数据之后，应该将那条出诊对的剩余数量减1
     *
     * @param visitNo
     * @return
     */
    int updateAfterRegisterVisit(Long visitNo);

    /**
     * 根据挂号单号更改其支付状态 和 修改状态时间
     *
     * @param registrationNo 需要更更改状态的挂号单号
     * @param payState       更改为哪个状态 0：待支付 1：支付完成 2：已取消
     * @return
     */
    int updateRegistrationPayStateAndTime(String registrationNo, Integer payState);

    /**
     * 通过挂号单流水号，查询其支付状态，用于线程中的自动更改状态
     *
     * @param registrationNo
     * @return
     */
    int getRegistrationPayState(String registrationNo);

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
    int insertDoctorIntoMyDoctor(Long account, Long doctorId);

    /**
     * 将某一个医生从用户的医生列表中删除
     *
     * @param account  用户的账号
     * @param doctorId 待收藏的医生id
     * @return
     */
    int deleteDoctorFromMydoctor(Long account, Long doctorId);

    /**
     * 通过用户的账号，查询他的全部订单（挂号单）
     *
     * @param account 待查找用户的账号
     * @return
     */
    List<Registration> queryAllRegistrtionByAccount(Long account);


    /**
     * 通过挂号单号 查询完整挂号单信息
     *
     * @param registrationNo
     * @return
     */
    Registration queryRegistrationByRegistrationNo(String registrationNo);

    /**
     * 通过visitNo，查询准确的一条医生出诊安排
     *
     * @param visitNo
     * @return
     */
    Visit queryVisitByVisitNo(Long visitNo);


    /**
     * 通过医生id 查询完整医生信息
     *
     * @param DoctorId
     * @return
     */
    Doctor queryDoctorByDoctorId(Integer DoctorId);

    /**
     * 通过科室号 查询完整科室信息
     *
     * @param departmentNo
     * @return
     */
    Department queryDepartmentByDepartmentNo(Integer departmentNo);

    /**
     * 根据就诊卡号，查询就诊卡
     *
     * @param medicalCardNo
     * @return
     */
    MedicalCard queryMedicalCardByMedicalCardNo(Long medicalCardNo);

    /**
     * 添加注册用户
     *
     * @return
     */
    int addUser(EnrollMessage enrollMessage);

    /**
     * 根据用户手机号查询此用户是否存在
     *
     * @param account 代注册的手机号
     * @return 查询的数量，0 或 1
     */
    int queryUserByAccount(Long account);

    /**
     * 将用户的请求验证码记录保存在UserVerificationCode 表中
     *
     * @param userVerificationCode
     * @return
     */
    int addUserVerificationCode(UserVerificationCode userVerificationCode);

    /**
     * 查询某次验证码
     *
     * @param userVerificationCode
     * @return
     */
    UserVerificationCode queryUserVerificationCode(UserVerificationCode userVerificationCode);

    /**
     * 更新某个用户的验证码，更改状态位置 使其无效
     *
     * @param userVerificationCode
     */
    void updateAllUserverificationCode(UserVerificationCode userVerificationCode);

    /**
     * 更新用户的验证码，使其invalid
     *
     * @param userVerificationCode
     */
    void updateUserificationCode(UserVerificationCode userVerificationCode);

    /**
     * 用户通过验证码操作，进入填写资料注册注册用户阶段
     *
     * @param account
     * @param password
     * @param userName
     * @return
     */
    int addUuser(Long account, String password, String userName);

    /**
     * 更新用户密码
     *
     * @param account     待更新密码的账户
     * @param newPassword 新密码
     * @return 更新的行数
     */
    int updateUuser(Long account, String newPassword);

    /**
     * test method
     *
     * @param account
     * @return
     */
    Uuser test(Long account);

    /**
     * 查询所有的医院活动
     *
     * @return
     */
    List<HospitalActivity> queryHealthActivity();

    /**
     * 查询所有的健康新闻
     *
     * @return
     */
    List<HealthNews> queryHealthNews();

    /**
     * 通过诊疗卡的拥有者电话，查询他所办理的全部诊疗卡
     *
     * @param ownerAccount
     * @return
     */
    List<MedicalCard> queryMedicalCardByOwnerAccount(Long ownerAccount);

    /**
     * 用户办理一张诊疗卡
     *
     * @param medicalCard 待办理诊疗卡消息
     * @return 插入是否成功
     */
    boolean insertMedicalCard(MedicalCard medicalCard);

    /**
     * 插入用户绑定诊疗卡数据
     *
     * @param account
     * @param medicalCardNo
     * @return
     */
    boolean insertBind(Long account, Long medicalCardNo);

    /**
     * 查询图片类验证码
     *
     * @return
     */
    PictureVerificationCode queryPictureVerificationCode();

    /**
     * 查询简要健康新闻，用于healthNewsActivity中pager的显示
     *
     * @return
     */
    List<SimpleHealthNews> querySimpleHealthNews();

    /**
     * 查询getDepartmentIntroduction
     *
     * @return
     */
    DepartmentIntroduction queryDepartmentIntroduction(Integer departmentNo);

    /**
     * 查询指定account 的药品缴费信息
     *
     * @param account
     * @return
     */
    List<DrugCost> queryDrugCostsByAccount(Long account);

    /**
     * 查询指定account 的手术缴费信息
     *
     * @param account
     * @return
     */
    List<OperationCost> queryOperationCostsByAccount(Long account);

    /**
     * 查询指定account 的检查检验缴费信息
     *
     * @param account
     * @return
     */
    List<CheckInspectionCost> queryCheckInspectionCostsByAccount(Long account);

    /**
     * 通过 drugListId 得到所有的药品单详情
     *
     * @param drugListId
     * @return
     */
    List<DrugList> queryDrugListByDrugListId(String drugListId);

    /**
     * 通过 drugId 查询其详情.
     *
     * @param drugId
     * @return
     */
    Drug queryDrugDetailsByDrugId(String drugId);

    /**
     * 通过 OperationCostId 查询手术单的大概详情
     *
     * @param operationCostId
     * @return
     */
    OperationCost queryOperationCostByOperationCostId(String operationCostId);

    /**
     * 通过 operationItemId 来得到operationItem 相应的operationItem详细信息
     *
     * @param operationItemId
     * @return
     */
    OperationItem queryOperationItemByOperationItemId(String operationItemId);

    /**
     * 通过 checkInspectionCostId 查询 checkInspectionCost数据
     *
     * @param checkInspectionCostId
     * @return
     */
    CheckInspectionCost queryCheckInspectionCostByCheckInspectionCostId(String checkInspectionCostId);

    /**
     * 通过 checkInspectionItemId 查询 checkInspectionItem
     *
     * @param checkInspectionItemId
     * @return
     */
    CheckInspectionItem queryCheckInspectionItemByCheckInspectionItemId(String checkInspectionItemId);

    /**
     * 通过 drugCostId 来得到DrugCost
     *
     * @param drugCostId
     * @return
     */
    DrugCost queryDrugCostByDrugCostId(String drugCostId);

    /**
     * 通过 chargeItemId 和 chargeItemType 查找 OrderBind 实例,后续使用其 orderId 来查找Orders实例
     *
     * @param chargeItemId
     * @param chargeItemType
     * @return
     */
    OrderBind queryOrderBind(String chargeItemId, Integer chargeItemType);

    /**
     * 通过 orderId 来查询 Orders
     *
     * @param paySerialNumber
     * @return
     */
    Orders queryOrders(String paySerialNumber);

    /**
     * 通过 doctorNoteId 查询 DoctorNote
     *
     * @param doctorNoteId
     * @return
     */
    DoctorNote queryDoctorNoteByDoctorNoteId(String doctorNoteId);

    /**
     * 插入订单信息
     *
     * @param orders
     * @return
     */
    int insertOrder(Orders orders);

    /**
     * 插入 订单绑定 数据
     *
     * @param orderBind
     * @return
     */
    int insertOrderBind(OrderBind orderBind);

    /**
     * 通过 先查询order
     * @param chargeItemId
     * @param chargeItemType
     * @param orderState
     * @param payOrCancelTime
     * @return
     */
    int updateOrderState(String chargeItemId, Integer chargeItemType, Integer orderState, String payOrCancelTime);

    /**
     * 修改指定订单状态
     * @param paySerialNumber
     * @param orderState
     * @param payOrCancelTime
     * @return
     */
    int updateOrders(String paySerialNumber, Integer orderState, String payOrCancelTime);

    /**
     * 删除 诊疗卡绑定
     * @param account
     * @param medicalCardNo
     * @return
     */
    int deleteBind(Long account,Long medicalCardNo);
}
