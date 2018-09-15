package cn.nobitastudio.component.service;

import cn.nobitastudio.component.dao.IOutpatientServiceDAO;
import cn.nobitastudio.model.*;
import cn.nobitastudio.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OutpatientService implements IOutpatientService {

    private static final String ORDERNAME_REGISTER = "挂号订单";

    @Autowired
    IOutpatientServiceDAO iOutpatientServiceDAO;

    /**
     * 查询是否存在该用户，若存在，这返回该用户信息
     *
     * @param message 登陆信息的封装对象
     * @return 如果根据登陆信息，确认为合法登陆信息，则返回登录信息对应账号的User信息，不合法则返回空;
     */
    @Override
    public Uuser isLogin(LoginMessage message) {
        return iOutpatientServiceDAO.isLogin(message);
    }

    /**
     * 查询所有的科室
     *
     * @return 科室列表
     */
    @Override
    public List<Department> getAllDepartment() {
        return iOutpatientServiceDAO.getAllDepartment();
    }

    /**
     * 查询某个科室下的所有坐诊医生
     *
     * @param deparmentNo 需要查询科室的代号
     * @return 该科室下所有医生集合
     */
    @Override
    public List<Doctor> getAllDoctorByDepartmentNo(Integer deparmentNo) {
        return iOutpatientServiceDAO.getAllDoctorByDepartmentNo(deparmentNo);
    }

    /**
     * 查询某位医生的全部坐诊安排
     *
     * @param doctorId 需要查询医生的id
     * @return 该医生的全部出诊安排
     */
    @Override
    public List<Visit> getAllVisitByDoctorId(Integer doctorId) {
        return iOutpatientServiceDAO.getAllVisitByDoctorId(doctorId);
    }

    /**
     * 通过医生的id查询该名医生的出诊安排内，是否有号可以挂
     *
     * @param doctorId
     * @return
     */
    @Override
    public DoctorWhetherHasNumber queryDoctorWhetherHasNumber(Integer doctorId) {
        DoctorWhetherHasNumber doctorWhetherHasNumber = new DoctorWhetherHasNumber(doctorId);
        List<Visit> visits = iOutpatientServiceDAO.getAllVisitByDoctorId(doctorId);
        Integer leftAmount = null;
        for (int i = 0; i < visits.size(); i++) {
            leftAmount = visits.get(i).getLeftAmount();
            if (leftAmount.intValue() != 0) {
                doctorWhetherHasNumber.setDoctorhasNumber(true);
                break;
            }
            doctorWhetherHasNumber.setDoctorhasNumber(false);
        }

        return doctorWhetherHasNumber;
    }

    /**
     * 插入一条挂号单信息
     *
     * @param account       用户账号
     * @param visitno       医生的出诊安排号
     * @param medicalcardno 就诊病人的就诊卡号
     * @return
     */
    @Override
    public Diagnosis startRegistration(Long account, Long visitno, Long medicalcardno) {

        Visit visit = iOutpatientServiceDAO.queryVisitByVisitNo(visitno);
        Integer leftAmount = visit.getLeftAmount();
        Integer amount = visit.getAmount();
        Diagnosis diagnosis;
        Integer diagnosisNo;
        final String registrationNo;
        if (leftAmount > 0) {
            // can register
            String createTime = Utility.getCurrentTime();
            diagnosisNo = amount - leftAmount + 1;
            registrationNo = Utility.getRegistrationId();
            int lines = iOutpatientServiceDAO.insertRegistrationData(registrationNo, account, visitno, medicalcardno, diagnosisNo, createTime);
            int lines2 = iOutpatientServiceDAO.updateAfterRegisterVisit(visitno);
            //add a thread to auto cancel register after 30s if user don't do operation
            if (lines > 0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //30s later set the register cancel pay
                            Thread.sleep(1800000);
                            if (iOutpatientServiceDAO.getRegistrationPayState(registrationNo) == 0) {
                                iOutpatientServiceDAO.updateRegistrationPayStateAndTime(registrationNo, new Integer(2));
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            diagnosis = new Diagnosis(diagnosisNo, registrationNo);
            if (!(lines > 0) || !(lines2 > 0)) {
                diagnosis = new Diagnosis(0, "0");
            } else {
                // register success
                //add orders data and orderBind data(orderId,orderType,chargeId);
                //insert ordersdata
                String orderName = ORDERNAME_REGISTER;
                Integer orderState = 0;
                String orderCreateTime = Utility.getCurrentTime();
                String payMethod = "待支付";
                String paySerialNumber = Utility.getRegisterSerialNumber();
                //query visit by visitNo
                Double allPrice = Double.valueOf(visit.getCost().toString());
                String payOrCancelTime = createTime;
                //String orderId, String orderName, Integer orderState, String orderCreateTime, String payMethod, String paySerialNumber, Double allPrice, String payOrCancelTime
                Orders orders = new Orders(orderName, orderState, orderCreateTime, payMethod, paySerialNumber, allPrice, payOrCancelTime);
                int lines3 = iOutpatientServiceDAO.insertOrder(orders);

                //insert orderbind data
                Integer chargeItemType = 8001;
                String chargeItemId = registrationNo;  //need to update
                OrderBind orderBind = new OrderBind(paySerialNumber, chargeItemType, chargeItemId);
                int lines4 = iOutpatientServiceDAO.insertOrderBind(orderBind);

                if (!(lines3 > 0) || !(lines4 > 0)) {
                    diagnosis = new Diagnosis(0, "0");
                }
            }
        } else {
            //can't register
            diagnosis = new Diagnosis(0, "0");
        }

        return diagnosis;
    }

    /**
     * 根据挂号单号，取消挂号记录。并且将取消本次就诊的就诊卡取消就诊次数+1,达到5次便冻结
     *
     * @param registrationNo 待取消挂号单的流水号
     */
    @Override
    public boolean cancelRegistration(String registrationNo) {
        int lines = iOutpatientServiceDAO.updateRegistrationPayStateAndTime(registrationNo, new Integer(2));
        return lines > 0;
    }

    /**
     * 根据挂号单号，确认挂号记录。
     *
     * @param registrationNo 待确认挂号单的流水号
     */
    @Override
    public boolean confirmRegistration(String registrationNo) {
        int lines = iOutpatientServiceDAO.updateRegistrationPayStateAndTime(registrationNo, new Integer(1));
        return lines > 0;
    }

    /**
     * 通过用户的手机号，查询他收藏的医生
     *
     * @param account 待查询的用户手机号
     */
    @Override
    public List<MyDoctor> queryMyDoctorByAccount(Long account) {
        List<MyDoctor> myDoctors = iOutpatientServiceDAO.queryMyDoctorByAccount(account);
        return myDoctors;
    }

    /**
     * 将某一个医生插入到他的医生列表中
     *
     * @param account  用户的账号
     * @param doctorId 待收藏的医生id
     * @return
     */
    @Override
    public int addDoctorToMyDoctor(Long account, Long doctorId) {
        int lines = iOutpatientServiceDAO.insertDoctorIntoMyDoctor(account, doctorId);
        return lines;
    }

    /**
     * 将某一个医生从用户的医生列表中删除
     *
     * @param account  用户的账号
     * @param doctorId 待收藏的医生id
     * @return
     */
    @Override
    public int removeDoctorFromMydoctor(Long account, Long doctorId) {
        int lines = iOutpatientServiceDAO.deleteDoctorFromMydoctor(account, doctorId);
        return lines;
    }

    /**
     * 通过用户的账号，查询他的全部订单（挂号单）
     *
     * @param account 待查找用户的账号
     * @return
     */
    @Override
    public List<Registration> queryAllRegistrtionByAccount(Long account) {
        return iOutpatientServiceDAO.queryAllRegistrtionByAccount(account);
    }

    /**
     * 通过挂号单号，得到简单的挂号单表
     *
     * @param registrationNo
     * @return
     */
    @Override
    public RegistrationForm getRegistrationFormByRegistrationNo(String registrationNo) {
        Registration registration = iOutpatientServiceDAO.queryRegistrationByRegistrationNo(registrationNo);
        Visit visit = iOutpatientServiceDAO.queryVisitByVisitNo(registration.getVisitNo());
        Doctor doctor = iOutpatientServiceDAO.queryDoctorByDoctorId(visit.getDoctorId());
        Department department = iOutpatientServiceDAO.queryDepartmentByDepartmentNo(doctor.getDepartmentNo());
        RegistrationForm registrationForm = new RegistrationForm();
        MedicalCard medicalCard = iOutpatientServiceDAO.queryMedicalCardByMedicalCardNo(registration.getMedicalCardNo());
        if (registration.getRegistrationNo() != null && visit.getVisitNo() != null && doctor.getDoctorId() != null && department.getDepartmentNo() != null) {
            registrationForm.setOutpatientName(department.getDepartmentName());
            registrationForm.setDoctorName(doctor.getDoctorName());
            registrationForm.setMedicalCardOwnerName(medicalCard.getOwnerName());
            registrationForm.setYearMonthDate(visit.getYears());
            registrationForm.setTimeSlot(visit.getTimeSlot());
            registrationForm.setDiagnosisNo(registration.getDiagnosisNo().toString());
            registrationForm.setCost(visit.getCost().toString());
            registrationForm.setAddress(department.getDepartmentAddress());
            registrationForm.setAppointSource("门诊服务系统App");
        }

        return registrationForm;
    }

    /**
     * 通过用户账号查询其所有的挂号单，和普通的通过账号查询挂号单相比，这个是发送给客户端的数据，并不需要所有的挂号单记录，仅仅需要部分数据即可，且需要查询其每个挂号单对应的价钱
     *
     * @param account
     * @return
     */
    @Override
    public List<ClientRegistration> queryAllClientRegistrationByAccount(Long account) {

        List<Registration> registrations = iOutpatientServiceDAO.queryAllRegistrtionByAccount(account);
        List<ClientRegistration> clientRegistrations = new ArrayList<>();
        if (registrations.size() > 0) {
            for (int i = 0; i < registrations.size(); i++) {
                Registration registration = registrations.get(i);
                String registrationNo = registration.getRegistrationNo();
                OrderBind orderBind = iOutpatientServiceDAO.queryOrderBind(registrationNo, Utility.getChargeItemTypeRegister());
                Orders orders = iOutpatientServiceDAO.queryOrders(orderBind.getPaySerialNumber());
                Integer orderState = orders.getOrderState();  // 0 references don't pay. 1 references have pay. 2 references haveCancel pay
                String payOrCancelTime = orders.getPayOrCancelTime();
                Visit visit = iOutpatientServiceDAO.queryVisitByVisitNo(registration.getVisitNo());
                Float cost = visit.getCost();
                ClientRegistration clientRegistration = new ClientRegistration(registrationNo, cost, orderState, payOrCancelTime);
                clientRegistrations.add(clientRegistration);
            }
        }
        return clientRegistrations;
    }

    /**
     * 通过用户的账号，查询所有他的收藏医生信息，其中包含两个对象，医生和他的科室
     *
     * @param account
     * @return
     */
    @Override
    public List<ClientDoctor> queryAllClientDoctorByAccount(Long account) {
        List<ClientDoctor> clientDoctors = new ArrayList<>();
        List<MyDoctor> myDoctors = queryMyDoctorByAccount(account);
        if (myDoctors.size() > 0) {
            //query
            for (MyDoctor myDoctor : myDoctors) {
                Doctor doctor = iOutpatientServiceDAO.queryDoctorByDoctorId(myDoctor.getDoctorId());
                Department department = iOutpatientServiceDAO.queryDepartmentByDepartmentNo(doctor.getDepartmentNo());
                String hospitalName = "石河子大学附属医院";
                ClientDoctor clientDoctor = new ClientDoctor(doctor, department, hospitalName);
                clientDoctors.add(clientDoctor);
            }
        }
        return clientDoctors;
    }

    /**
     * 通过用户输入的账号 判断是否该账号已被注册
     *
     * @param account 待注册账号
     * @return 若能够注册返回true 否则 返回false
     */
    @Override
    public boolean userWhetherCanEnroll(Long account) {
        return iOutpatientServiceDAO.queryUserByAccount(account) == 1;
    }

    /**
     * 将用户的请求验证码记录保存在UserVerificationCode 表中
     *
     * @param userVerificationCode
     * @return
     */
    @Override
    public void addUserVerificationCode(UserVerificationCode userVerificationCode) {
        //first let all the user's verifacation invalid.
        iOutpatientServiceDAO.updateAllUserverificationCode(userVerificationCode);
        //create a new effictive verification code.
        int lines = iOutpatientServiceDAO.addUserVerificationCode(userVerificationCode);
        if (!(lines > 0)) {
            //add fails there should record it.
        }
    }

    /**
     * 根据UserVerificationCodeState 查询它此刻的状态
     *
     * @param userVerificationCode
     * @return
     */
    @Override
    public int queryUserVerificationCodeState(UserVerificationCode userVerificationCode) {
        UserVerificationCode userVerificationCode1 = iOutpatientServiceDAO.queryUserVerificationCode(userVerificationCode);
        if (userVerificationCode1.getVerificationCodeState() != null) {
            return userVerificationCode1.getVerificationCodeState();
        }
        return 100;  //100 reference error
    }

    /**
     * 更新用户的验证码，使其invalid
     *
     * @param userVerificationCode
     */
    @Override
    public void updateUserverificationCode(UserVerificationCode userVerificationCode) {
        iOutpatientServiceDAO.updateUserificationCode(userVerificationCode);
    }

    /**
     * 用户从客户端提交了验证码
     *
     * @param accout
     * @param verificationCode
     * @return
     */
    @Override
    public Message userConfirmVerificationCode(Long accout, String verificationCode) {
        UserVerificationCode userVerificationCode = new UserVerificationCode();
        userVerificationCode.setAccount(accout);
        userVerificationCode.setVerificationCode(verificationCode);
        UserVerificationCode userVerificationCode1 = iOutpatientServiceDAO.queryUserVerificationCode(userVerificationCode);
        Message message = new Message(false, "验证码错误");
        if (userVerificationCode1.getVerificationCode() == null) {
            //user input an rong verification code
            message = new Message(false, "验证码错误");
        } else if (userVerificationCode1.getVerificationCodeState().equals(new Integer(2))) {
            //this verification has been invalid
            message = new Message(false, "验证码已失效");
        } else if (userVerificationCode1.getVerificationCodeState().equals(new Integer(1))) {
            //this verification is right
            message = new Message(true, "验证码正确");
        }

        if (message.isSuccess()) {
            //the user has validate this verifacation ,let it invalid
            iOutpatientServiceDAO.updateUserificationCode(userVerificationCode1);
        }

        return message;
    }

    /**
     * 用户通过验证码操作，进入填写资料注册注册用户阶段
     *
     * @param account
     * @param password
     * @param userName
     * @return
     */
    @Override
    public Uuser userEnrollSuccess(Long account, String password, String userName) {
        iOutpatientServiceDAO.addUuser(account, password, userName);
        return isLogin(new LoginMessage(account.toString(), password));
    }

    /**
     * 用户更新密码
     *
     * @param account     更新密码的账户
     * @param newPassword
     * @return
     */
    @Override
    public Uuser userChangePassword(Long account, String newPassword) {
        iOutpatientServiceDAO.updateUuser(account, newPassword);
        return isLogin(new LoginMessage(account.toString(), newPassword));
    }

    /**
     * 得到所有的医院活动
     *
     * @return
     */
    @Override
    public List<HospitalActivity> getHospitalActivity() {
        return iOutpatientServiceDAO.queryHealthActivity();
    }

    /**
     * 得到所有健康新闻
     *
     * @return
     */
    @Override
    public List<HealthNews> getHealthNews() {
        return iOutpatientServiceDAO.queryHealthNews();
    }

    /**
     * 查询该手机号是否能办理诊疗卡,每个手机号限办五个.
     *
     * @param ownerAccount 诊疗卡拥有者的电话号码
     * @return
     */
    @Override
    public Message whetherCreateMedicalCard(Long ownerAccount) {
        List<MedicalCard> medicalCards = iOutpatientServiceDAO.queryMedicalCardByOwnerAccount(ownerAccount);
        Message message;
        if (medicalCards.size() < 5) {
            message = new Message(true, "可以办理");
        } else {
            message = new Message(false, "办理诊疗卡数量以上限");
        }

        return message;
    }

    /**
     * 用户办理诊疗卡
     *
     * @param medicalCard
     * @return
     */
    @Override
    public boolean userCreateMedicalCard(MedicalCard medicalCard) {
        return iOutpatientServiceDAO.insertMedicalCard(medicalCard);
    }

    /**
     * 用户绑定诊疗卡
     *
     * @param account
     * @param medicalCardNo
     * @return
     */
    @Override
    public boolean userBindMedicalCard(Long account, Long medicalCardNo) {
        return iOutpatientServiceDAO.insertBind(account, medicalCardNo);
    }

    /**
     * 通过medicalCardNo 查询其medicalcard对象
     *
     * @param medicalCardNo
     * @return
     */
    @Override
    public MedicalCard getMedicalCardByMedicalCardNo(Long medicalCardNo) {
        return iOutpatientServiceDAO.queryMedicalCardByMedicalCardNo(medicalCardNo);
    }

    /**
     * 得到图片类验证码
     *
     * @return
     */
    @Override
    public PictureVerificationCode getPictureVerificationCode() {
        return iOutpatientServiceDAO.queryPictureVerificationCode();
    }

    /**
     * 得到简要健康新闻，用于healthNewsActivity中pager的显示
     *
     * @return
     */
    @Override
    public List<SimpleHealthNews> getSimpleHealthNews() {
        return iOutpatientServiceDAO.querySimpleHealthNews();
    }

    /**
     * 得到departmentNo对应的DepartmentIntroduction
     *
     * @param departmentNo
     * @return
     */
    @Override
    public DepartmentIntroduction getDepartmentIntroduction(Integer departmentNo) {
        return iOutpatientServiceDAO.queryDepartmentIntroduction(departmentNo);
    }

    /**
     * 通过该用户的account 得到所以其各种缴费信息，及其每类缴费各类收费项目
     *
     * @param account
     * @return
     */
    @Override
    public AllPayment getAllPayment(Long account) {

        AllPayment allPayment = new AllPayment();
        List<ClientRegistrationCost> clientRegistrationCosts = new ArrayList<>();
        List<ClientDrugCost> clientDrugCosts = new ArrayList<>();
        List<ClientOperationCost> clientOperationCosts = new ArrayList<>();
        List<ClientCheckInspectionCost> clientCheckInspectionCosts = new ArrayList<>();

        // query registration orders
        List<Registration> registrations = iOutpatientServiceDAO.queryAllRegistrtionByAccount(account);
        for (Registration registration : registrations) {
            Orders orders = iOutpatientServiceDAO.queryOrders(iOutpatientServiceDAO.queryOrderBind(registration.getRegistrationNo(), Utility.getChargeItemTypeRegister()).getPaySerialNumber());
            ClientRegistrationCost clientRegistrationCost = new ClientRegistrationCost(registration, orders);
            clientRegistrationCosts.add(clientRegistrationCost);
        }
        allPayment.setClientRegistrationCosts(clientRegistrationCosts);

        // query drugCost orders
        List<DrugCost> drugCosts = iOutpatientServiceDAO.queryDrugCostsByAccount(account);
        for (DrugCost drugCost : drugCosts) {
            Orders orders = iOutpatientServiceDAO.queryOrders(iOutpatientServiceDAO.queryOrderBind(drugCost.getDrugCostId(), Utility.getChargeItemTypeDrugcost()).getPaySerialNumber());
            clientDrugCosts.add(new ClientDrugCost(drugCost, orders));
        }
        allPayment.setClientDrugCosts(clientDrugCosts);

        // query operationCost orders
        List<OperationCost> operationCosts = iOutpatientServiceDAO.queryOperationCostsByAccount(account);
        for (OperationCost operationCost : operationCosts) {
            Orders orders = iOutpatientServiceDAO.queryOrders(iOutpatientServiceDAO.queryOrderBind(operationCost.getOperationCostId(), Utility.getChargeItemTypeOperationcost()).getPaySerialNumber());
            clientOperationCosts.add(new ClientOperationCost(operationCost, orders));
        }
        allPayment.setClientOperationCosts(clientOperationCosts);

        //query CheckInspectionCost orders
        List<CheckInspectionCost> checkInspectionCosts = iOutpatientServiceDAO.queryCheckInspectionCostsByAccount(account);
        for (CheckInspectionCost checkInspectionCost : checkInspectionCosts) {
            Orders orders = iOutpatientServiceDAO.queryOrders(iOutpatientServiceDAO.queryOrderBind(checkInspectionCost.getCheckInspectionCostId(), Utility.getChargeItemTypeCheckinspectioncost()).getPaySerialNumber());
            clientCheckInspectionCosts.add(new ClientCheckInspectionCost(checkInspectionCost, orders));
        }
        allPayment.setClientCheckInspectionCosts(clientCheckInspectionCosts);

        return allPayment;
    }

    /**
     * 得到客户端需要的处方单详情
     *
     * @param drugCostId
     * @return
     */
    @Override
    public ClientDrugListData getDrugListDetails(String drugCostId) {

        ClientDrugListData clientDrugListData = new ClientDrugListData();
        Integer doctorId = 0;

        // get drugCost by drugCostId
        DrugCost drugCost = iOutpatientServiceDAO.queryDrugCostByDrugCostId(drugCostId);
        String drugListId = drugCost.getDrugListId();

        //getDrugList and drugListId
        List<DrugList> drugLists = iOutpatientServiceDAO.queryDrugListByDrugListId(drugListId);

        //get need data to init clientDrugListData
        if (drugLists.size() > 0) {
            List<ClientDrugList> clientDrugLists = new ArrayList<>();
            for (DrugList drugList : drugLists) {

                // get drug details by drugId.
                String drugId = drugList.getDrugId();
                Drug drug = iOutpatientServiceDAO.queryDrugDetailsByDrugId(drugId);

                String drugName = drug.getDrugName();
                Integer purchaseNumber = drugList.getDrugPurchaseQuantity();
                Double price = drug.getDrugPrice();
                Double allPrice = drugList.getAllPrice();
                if (doctorId.equals(0)) {
                    doctorId = drugList.getDoctorId();
                }
                ClientDrugList clientDrugList = new ClientDrugList(drugName, purchaseNumber, price, allPrice);
                clientDrugLists.add(clientDrugList);
            }
            clientDrugListData.setClientDrugLists(clientDrugLists);
        } else {
            clientDrugListData.setClientDrugLists(new ArrayList<ClientDrugList>());
        }

        //get patientName by medicalCardNo;
        Long medicalCardNo = drugCost.getMedicalCardNo();
        MedicalCard medicalCard = iOutpatientServiceDAO.queryMedicalCardByMedicalCardNo(medicalCardNo);
        if (medicalCard.getOwnerName() != null) {
            String patientName = medicalCard.getOwnerName();
            clientDrugListData.setPatientName(patientName);
            clientDrugListData.setMedicalCardNo(medicalCardNo);
        }

        //get diagnosisDoctorName by doctorId
        if (!doctorId.equals(0)) {
            Doctor doctor = iOutpatientServiceDAO.queryDoctorByDoctorId(doctorId);
            if (doctor.getDoctorName() != null) {
                String diagnosisDoctorName = doctor.getDoctorName();
                clientDrugListData.setDiagnosisDoctorName(diagnosisDoctorName);
            }
        }

        //query orders
        OrderBind orderBind = iOutpatientServiceDAO.queryOrderBind(drugCostId, Utility.getChargeItemTypeDrugcost());
        Orders orders = iOutpatientServiceDAO.queryOrders(orderBind.getPaySerialNumber());
        clientDrugListData.setOrders(orders);

        //query doctorNote
        DoctorNote doctorNote = iOutpatientServiceDAO.queryDoctorNoteByDoctorNoteId(drugCost.getDoctorNoteId());
        clientDrugListData.setDoctorNote(doctorNote);

        return clientDrugListData;
    }

    /**
     * 通过 手术单号 得到相应的手术单信息
     *
     * @param operationCostId
     * @return
     */
    @Override
    public ClientOperationData getOperationCostDetails(String operationCostId) {

        // query operationCost
        OperationCost operationCost = iOutpatientServiceDAO.queryOperationCostByOperationCostId(operationCostId);
        String operationItemId = operationCost.getOperationItemId();
        Integer purchaseNumber = operationCost.getPurchaseNumber();
        String operationRoom = operationCost.getOperationRoom();
        String startTime = operationCost.getStartTime();

        // query operationItem details
        OperationItem operationItem = iOutpatientServiceDAO.queryOperationItemByOperationItemId(operationItemId);
        String operationName = operationItem.getOperationItemName();
        Double price = operationItem.getPrice();

        //query patientName
        Long medicalCardNo = operationCost.getMedicalCardNo();
        MedicalCard medicalCard = iOutpatientServiceDAO.queryMedicalCardByMedicalCardNo(medicalCardNo);
        String patientName = medicalCard.getOwnerName();

        //query orders
        OrderBind orderBind = iOutpatientServiceDAO.queryOrderBind(operationCostId, Utility.getChargeItemTypeOperationcost());
        Orders orders = iOutpatientServiceDAO.queryOrders(orderBind.getPaySerialNumber());

        //query doctorNote
        DoctorNote doctorNote = iOutpatientServiceDAO.queryDoctorNoteByDoctorNoteId(operationCost.getDoctorNoteId());

        ClientOperationData clientOperationData = new ClientOperationData(operationName, patientName, price, purchaseNumber, startTime, operationRoom, medicalCardNo, orders, doctorNote);
        return clientOperationData;
    }

    /**
     * 通过 checkInspectionCostId 查询其检查客户端需要的检验单的数据
     *
     * @param checkInspectionCostId
     * @return
     */
    @Override
    public ClientCheckInspectionData getCheckInspectionDetails(String checkInspectionCostId) {

        // query checkInspectionCost
        CheckInspectionCost checkInspectionCost = iOutpatientServiceDAO.queryCheckInspectionCostByCheckInspectionCostId(checkInspectionCostId);
        Integer purchaseNumber = checkInspectionCost.getPurchaseNumber();
        String checkTime = checkInspectionCost.getCheckTime();
        String checkInspectionRoom = checkInspectionCost.getCheckRoom();

        // query checkInspectionItem
        String checkInspectionItemId = checkInspectionCost.getCheckInspectionItemId();
        CheckInspectionItem checkInspectionItem = iOutpatientServiceDAO.queryCheckInspectionItemByCheckInspectionItemId(checkInspectionItemId);
        String checkInspectionItemName = checkInspectionItem.getCheckInspectionItemName();
        Double price = checkInspectionItem.getPrice();

        // query patientName
        Long medicalCardNo = checkInspectionCost.getMedicalCardNo();
        MedicalCard medicalCard = iOutpatientServiceDAO.queryMedicalCardByMedicalCardNo(medicalCardNo);
        String patientName = medicalCard.getOwnerName();

        //query orders
        OrderBind orderBind = iOutpatientServiceDAO.queryOrderBind(checkInspectionCostId, Utility.getChargeItemTypeCheckinspectioncost());
        Orders orders = iOutpatientServiceDAO.queryOrders(orderBind.getPaySerialNumber());

        //query doctorNote
        DoctorNote doctorNote = iOutpatientServiceDAO.queryDoctorNoteByDoctorNoteId(checkInspectionCost.getDoctorNoteId());

        ClientCheckInspectionData clientCheckInspectionData = new ClientCheckInspectionData(checkInspectionItemName, patientName, price, purchaseNumber, checkTime, checkInspectionRoom, medicalCardNo, orders, doctorNote);
        return clientCheckInspectionData;
    }

    /**
     * 用户支付完成，确认订单
     *
     * @param paySerialNumber
     * @return
     */
    @Override
    public Message confirmOrder(String paySerialNumber) {
        int lines = iOutpatientServiceDAO.updateOrders(paySerialNumber, 1, Utility.getCurrentTime());
        Message message;
        if (lines == 1) {
            message = new Message(true, "支付状态更新成功");
        } else {
            message = new Message(false, "支付状态更新失败");
        }
        return message;
    }

    /**
     * 用户接触绑定诊疗卡
     *
     * @param account
     * @param medicalCardNo
     * @return
     */
    @Override
    public Message userUnbindMedicalCard(Long account, Long medicalCardNo) {
        int lines = iOutpatientServiceDAO.deleteBind(account, medicalCardNo);
        Message message;
        if (lines == 1) {
            message = new Message(true, "解绑成功");
        } else {
            message = new Message(false,"解绑失败");
        }
        return message;
    }

    /**
     * test method
     *
     * @param account
     */
    @Override
    public Uuser test(Long account) {
        return iOutpatientServiceDAO.test(account);
    }

}
