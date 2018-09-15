package cn.nobitastudio.component.dao;

import cn.nobitastudio.model.*;
import cn.nobitastudio.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

@Repository
public class OutpatientServiceDAO implements IOutpatientServiceDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 查询是否存在该用户，若存在，这返回该用户信息
     *
     * @param message 登陆信息的封装对象
     * @return 如果根据登陆信息，确认为合法登陆信息，则返回登录信息对应账号的User信息，不合法则返回空;
     */
    @Override
    public Uuser isLogin(LoginMessage message) {
        String sql = "select * from uuser where account = ? and password = ?";
        RowMapper<Uuser> rowMapper = new BeanPropertyRowMapper<Uuser>(Uuser.class);
        List<Uuser> uusers = jdbcTemplate.query(sql, new Object[]{Long.valueOf(message.getUserAccount()), message.getUserPassword()}, rowMapper);
        if (uusers.size() > 0) {
            Uuser uuser = uusers.get(0);
            //get the user's medicalcards
            String sql2 = "select * from medicalcard where medicalcardno in (select medicalcardno from bind where account = ?)";
            RowMapper<MedicalCard> rowMapper2 = new BeanPropertyRowMapper<>(MedicalCard.class);
            List<MedicalCard> medicalCards = jdbcTemplate.query(sql2, new Object[]{uuser.getAccount()}, rowMapper2);
            uuser.setMedicalCards(medicalCards);

            //get the user's collection doctors
            List<MyDoctor> myDoctors = queryMyDoctorByAccount(Long.valueOf(message.getUserAccount()));
            uuser.setMyDoctors(myDoctors);
            return uuser;
        } else {
            return new Uuser();
        }

    }

    /**
     * 查询所有的科室
     *
     * @return 科室列表
     */
    @Override
    public List<Department> getAllDepartment() {
        String sql = "select * from department";
        RowMapper<Department> rowMapper = new BeanPropertyRowMapper<>(Department.class);
        List<Department> departments = jdbcTemplate.query(sql, rowMapper);
        return departments;
    }

    /**
     * 查询某个科室下的所有坐诊医生
     *
     * @param deparmentNo 需要查询科室的代号
     * @return 该科室下所有医生集合
     */
    @Override
    public List<Doctor> getAllDoctorByDepartmentNo(Integer deparmentNo) {
        String sql = "select * from doctor where departmentno = ?";
        RowMapper<Doctor> rowMapper = new BeanPropertyRowMapper<>(Doctor.class);
        List<Doctor> doctors = jdbcTemplate.query(sql, new Object[]{deparmentNo}, rowMapper);
        return doctors;
    }

    /**
     * 查询某位医生的全部坐诊安排
     *
     * @param doctorId 需要查询医生的id
     * @return 该医生的全部出诊安排
     */
    @Override
    public List<Visit> getAllVisitByDoctorId(Integer doctorId) {
        String sql = "select * from visit where doctorid = ?";
        RowMapper<Visit> rowMapper = new BeanPropertyRowMapper<>(Visit.class);
        List<Visit> visits = jdbcTemplate.query(sql, new Object[]{doctorId}, rowMapper);
        return visits;
    }

    /**
     * 查询某一用户下的全部就诊卡
     * 暂时未使用
     *
     * @param account 需要查询用户的账号
     * @return 该用户的全部绑定就诊卡
     */
    @Override
    public List<MedicalCard> getAllMedicalCardByAccount(Long account) {
        String sql = "select * from bind where account = ?";
        RowMapper<MedicalCard> rowMapper = new BeanPropertyRowMapper<>(MedicalCard.class);
        List<MedicalCard> medicalCards = jdbcTemplate.query(sql, new Object[]{account}, rowMapper);
        return medicalCards;
    }

    /**
     * 插入挂号记录
     *
     * @param uuser       挂号用户
     * @param medicalCard 就诊病人就诊卡
     * @param visit       确定预约的医生出诊安排
     * @return 插入是否成功
     */
    @Override
    public boolean insertIntoRegisration(Uuser uuser, MedicalCard medicalCard, Visit visit) {
        //根据挂号单的创建时间，生成挂号单的流水号.（12 位 出诊唯一号 + 年  月  日）
        BigInteger registrationNo;
        Long visitNo = visit.getVisitNo();
        String strvisitno = visitNo.toString();
        String registrationNoStr;
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String date = String.valueOf(calendar.get(Calendar.DATE));
        if (calendar.get(Calendar.MONTH) + 1 < 10) {
            registrationNoStr = strvisitno + year + "0" + month + date;
        } else {
            registrationNoStr = strvisitno + year + month + date;
        }

        registrationNo = new BigInteger(registrationNoStr);
        String sql = "insert into registration(registrationno,account,visitno,medicalcardno) values(?,?,?,?)";
        int result = jdbcTemplate.update(sql, new Object[]{registrationNo, uuser.getAccount(), visit.getVisitNo(), medicalCard.getMedicalCardNo()});
        String sql2 = "update visit set leftamount = leftamount - 1 where visitno = ?";
        int result2 = jdbcTemplate.update(sql2, new Object[]{visit.getVisitNo()});
        return result > 0 && result2 > 0;
    }

    /**
     * 通过出诊安排查询该次出诊安排的医生
     *
     * @param visit 本次出诊安排
     * @return 此出诊安排的医生
     */
    @Override
    public Doctor getDoctorByVisit(Visit visit) {
        String sql = "select * from doctor where doctorid = ?";
        RowMapper<Doctor> rowMapper = new BeanPropertyRowMapper<>(Doctor.class);
        List<Doctor> doctors = jdbcTemplate.query(sql, new Object[]{visit.getDoctorId()}, rowMapper);
        if (doctors.size() > 0) {
            return doctors.get(0);
        } else {
            return new Doctor();
        }
    }

    /**
     * 根据医生信息查询改名医生所属的科室
     *
     * @param doctor 需要查询的医生
     * @return 该名医生的门诊信息
     */
    @Override
    public Department getDepartmentByDoctor(Doctor doctor) {
        String sql = "select * from department where departmentno = ?";
        RowMapper<Department> rowMapper = new BeanPropertyRowMapper<>(Department.class);
        List<Department> departments = jdbcTemplate.query(sql, new Object[]{doctor.getDepartmentNo()}, rowMapper);
        if (departments.size() > 0) {
            return departments.get(0);
        } else {
            return new Department();
        }
    }

    /**
     * 插入一条挂号单信息
     *
     * @param registrationno
     * @param account
     * @param visitno
     * @param medicalcardno
     * @param diagnosisNo
     * @param createTime
     * @return
     */
    @Override
    public int insertRegistrationData(String registrationno, Long account, Long visitno, Long medicalcardno, Integer diagnosisNo, String createTime) {
        String sql = "insert into registration(registrationno,account,visitno,medicalcardno,diagnosisno,createtime) values(?,?,?,?,?,?)";
        int lines = jdbcTemplate.update(sql, new Object[]{registrationno, account, visitno, medicalcardno, diagnosisNo, createTime});
        return lines;
    }

    /**
     * 在插入挂号数据之后，应该将那条出诊对的剩余数量减1
     *
     * @param visitNo
     * @return
     */
    @Override
    public int updateAfterRegisterVisit(Long visitNo) {
        String sql = "update visit set leftamount = leftamount - 1 where visitno = ?";
        int lines = jdbcTemplate.update(sql, new Object[]{visitNo});
        return lines;
    }

    /**
     * 更新挂号单订单的支付状态 和 支付操作时间
     *
     * @param registrationNo
     * @return
     */
    @Override
    public int updateRegistrationPayStateAndTime(String registrationNo, Integer payState) {
        String payOrCancelTime = Utility.getCurrentTime();
        int lines = updateOrderState(registrationNo, Utility.getChargeItemTypeRegister(), payState, payOrCancelTime);
        return lines;
    }

    /**
     * 通过挂号单流水号，查询其支付状态，用于线程中的自动更改状态
     *
     * @param registrationNo
     * @return 查询挂号单的支付状态
     */
    @Override
    public int getRegistrationPayState(String registrationNo) {
        int payState = 100;// 100 point error，the registration has been deleted.
        OrderBind orderBind = queryOrderBind(registrationNo, Utility.getChargeItemTypeRegister());
        Orders orders = queryOrders(orderBind.getPaySerialNumber());
        if (orders.getOrderState() != null) {
            payState = orders.getOrderState();
        }
        return payState;
    }

    /**
     * 通过用户的手机号，查询他收藏的医生
     *
     * @param account 待查询的用户手机号
     */
    @Override
    public List<MyDoctor> queryMyDoctorByAccount(Long account) {
        //get the user's collection doctors
        String sql = "select * from mydoctor where account = ?";
        RowMapper<MyDoctor> rowMapper = new BeanPropertyRowMapper<>(MyDoctor.class);
        List<MyDoctor> myDoctors = jdbcTemplate.query(sql, new Object[]{account}, rowMapper);
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
    public int insertDoctorIntoMyDoctor(Long account, Long doctorId) {
        String sql = "insert into mydoctor(account,doctorid) values(?,?)";
        int lines = jdbcTemplate.update(sql, new Object[]{account, doctorId});
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
    public int deleteDoctorFromMydoctor(Long account, Long doctorId) {
        String sql = "delete from mydoctor where account = ? and doctorid = ?";
        int lines = jdbcTemplate.update(sql, new Object[]{account, doctorId});
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
        String sql = "select * from registration where account = ?";
        RowMapper<Registration> rowMapper = new BeanPropertyRowMapper<>(Registration.class);
        List<Registration> registrations = jdbcTemplate.query(sql, new Object[]{account}, rowMapper);
        return registrations;
    }

    /**
     * 通过挂号单号 查询完整挂号单信息
     *
     * @param registrationNo
     * @return
     */
    @Override
    public Registration queryRegistrationByRegistrationNo(String registrationNo) {
        String sql = "select * from registration where registrationno = ?";
        RowMapper<Registration> rowMapper = new BeanPropertyRowMapper<>(Registration.class);
        List<Registration> registrations = jdbcTemplate.query(sql, new Object[]{registrationNo}, rowMapper);
        if (registrations.size() == 0) {
            return new Registration();
        }
        return registrations.get(0);
    }

    /**
     * 通过visitNo，查询准确的一条医生出诊安排
     *
     * @param visitNo
     * @return
     */
    @Override
    public Visit queryVisitByVisitNo(Long visitNo) {
        String sql = "select * from visit where visitno = ?";
        RowMapper<Visit> rowMapper = new BeanPropertyRowMapper<>(Visit.class);
        List<Visit> visits = jdbcTemplate.query(sql, new Object[]{visitNo}, rowMapper);
        if (visits.size() == 0) {
            return new Visit();
        }
        return visits.get(0);
    }

    /**
     * 通过医生id 查询完整医生信息
     *
     * @param DoctorId
     * @return
     */
    @Override
    public Doctor queryDoctorByDoctorId(Integer DoctorId) {
        String sql = "select * from doctor where doctorid = ?";
        RowMapper<Doctor> rowMapper = new BeanPropertyRowMapper<>(Doctor.class);
        List<Doctor> doctors = jdbcTemplate.query(sql, new Object[]{DoctorId}, rowMapper);
        if (doctors.size() == 0) {
            return new Doctor();
        }
        return doctors.get(0);
    }

    /**
     * 通过科室号 查询完整科室信息
     *
     * @param departmentNo
     * @return
     */
    @Override
    public Department queryDepartmentByDepartmentNo(Integer departmentNo) {
        String sql = "select * from department where departmentno = ?";
        RowMapper<Department> rowMapper = new BeanPropertyRowMapper<>(Department.class);
        List<Department> departments = jdbcTemplate.query(sql, new Object[]{departmentNo}, rowMapper);
        if (departments.size() == 0) {
            return new Department();
        }
        return departments.get(0);
    }

    /**
     * 根据就诊卡号，查询就诊卡
     *
     * @param medicalCardNo
     * @return
     */
    @Override
    public MedicalCard queryMedicalCardByMedicalCardNo(Long medicalCardNo) {
        String sql = "select * from medicalcard where medicalcardno = ?";
        RowMapper<MedicalCard> rowMapper = new BeanPropertyRowMapper<>(MedicalCard.class);
        List<MedicalCard> medicalCards = jdbcTemplate.query(sql, new Object[]{medicalCardNo}, rowMapper);
        if (medicalCards.size() == 0) {
            return new MedicalCard();
        }
        return medicalCards.get(0);
    }

    /**
     * 添加注册用户
     *
     * @param enrollMessage
     * @return
     */
    @Override
    public int addUser(EnrollMessage enrollMessage) {
        String sql = "insert into uuser(account,password,username)";
        int lines = jdbcTemplate.update(sql, new Object[]{Long.valueOf(enrollMessage.getAccount()), enrollMessage.getPassword(), enrollMessage.getUserName()});
        return lines;
    }

    /**
     * 根据用户手机号查询此用户是否存在
     *
     * @param account 代注册的手机号
     * @return 查询的数量，0 或 1
     */
    @Override
    public int queryUserByAccount(Long account) {
        String sql = "select * from uuser where account = ?";
        RowMapper<Uuser> rowMapper = new BeanPropertyRowMapper<Uuser>(Uuser.class);
        List<Uuser> uusers = jdbcTemplate.query(sql, new Object[]{account}, rowMapper);
        return uusers.size();
    }

    /**
     * 将用户的请求验证码记录保存在UserVerificationCode 表中
     *
     * @param userVerificationCode
     * @return
     */
    @Override
    public int addUserVerificationCode(UserVerificationCode userVerificationCode) {
        String sql = "insert into userverificationcode(account,verificationcode,verificationcodestate,verificationcodesendtime,sendresult) values(?,?,?,?,?)";
        int lines = jdbcTemplate.update(sql, new Object[]{userVerificationCode.getAccount(), userVerificationCode.getVerificationCode(),
                userVerificationCode.getVerificationCodeState(), userVerificationCode.getVerificationCodeSendTime(),
                userVerificationCode.getSendResult()});
        return lines;
    }

    /**
     * 查询某次验证码
     *
     * @param userVerificationCode
     * @return
     */
    @Override
    public UserVerificationCode queryUserVerificationCode(UserVerificationCode userVerificationCode) {
        String sql;
        RowMapper<UserVerificationCode> rowMapper = new BeanPropertyRowMapper<UserVerificationCode>(UserVerificationCode.class);
        List<UserVerificationCode> userVerificationCodes;
        if (userVerificationCode.getVerificationCodeSendTime() == null || userVerificationCode.getVerificationCodeSendTime().isEmpty()) {
            //user confrim verification code
            sql = "select * from UserVerificationCode where account = ? and verificationCode = ?";
            userVerificationCodes = jdbcTemplate.query(sql, new Object[]{userVerificationCode.getAccount(), userVerificationCode.getVerificationCode()}, rowMapper);
            if (userVerificationCodes.size() > 0) {
                //the user input right verification
                sql = "select * from UserVerificationCode where account = ? and verificationCode = ? and verificationcodestate = 1";
                userVerificationCodes = jdbcTemplate.query(sql, new Object[]{userVerificationCode.getAccount(), userVerificationCode.getVerificationCode()}, rowMapper);
                if (!(userVerificationCodes.size() > 0)) {
                    //return a userVerificationCode it's state sign is 2
                    return new UserVerificationCode(userVerificationCode.getAccount(),
                            userVerificationCode.getVerificationCode(),
                            2,
                            "",
                            "success");
                }
            }
        } else {
            //query the verification code state.
            sql = "select * from UserVerificationCode where account = ? and verificationCode = ? and verificationCodeSendTime = ?";
            userVerificationCodes = jdbcTemplate.query(sql, new Object[]{userVerificationCode.getAccount(),
                    userVerificationCode.getVerificationCode(),
                    userVerificationCode.getVerificationCodeSendTime()}, rowMapper);
        }

        if (userVerificationCodes.size() == 0) {
            return new UserVerificationCode();
        }
        return userVerificationCodes.get(0);
    }

    /**
     * 更新某个用户的验证码，更改状态位置 使其无效
     *
     * @param userVerificationCode
     */
    @Override
    public void updateAllUserverificationCode(UserVerificationCode userVerificationCode) {
        String sql = "update UserverificationCode set verificationCodeState = 2 where account = ? and verificationCodeState = 1";
        jdbcTemplate.update(sql, new Object[]{userVerificationCode.getAccount()});
    }

    /**
     * 更新用户的验证码，使其invalid
     *
     * @param userVerificationCode
     */
    @Override
    public void updateUserificationCode(UserVerificationCode userVerificationCode) {
        String sql = "update UserVerificationCode set VerificationCodeState = 2 where account = ? and verificationcode = ? and verificationcodesendtime = ?";
        jdbcTemplate.update(sql, new Object[]{userVerificationCode.getAccount(),
                userVerificationCode.getVerificationCode(),
                userVerificationCode.getVerificationCodeSendTime()});
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
    public int addUuser(Long account, String password, String userName) {
        String sql = "insert into uuser(account,password,username) values(?,?,?)";
        int lines = jdbcTemplate.update(sql, new Object[]{account, password, userName});
        return lines;
    }

    /**
     * 更新用户密码
     *
     * @param account     待更新密码的账户
     * @param newPassword 新密码
     * @return 更新的行数
     */
    @Override
    public int updateUuser(Long account, String newPassword) {
        String sql = "update uuser set password = ? where account = ?";
        int lines = jdbcTemplate.update(sql, new Object[]{newPassword, account});
        return lines;
    }

    /**
     * test method
     *
     * @param account
     * @return
     */
    @Override
    public Uuser test(Long account) {
        String sql = "select * from uuser where account = ?";
        RowMapper<Uuser> rowMapper = new BeanPropertyRowMapper<Uuser>(Uuser.class);
        List<Uuser> uusers = jdbcTemplate.query(sql, new Object[]{account}, rowMapper);
        if (uusers.size() == 0) {
            return new Uuser();
        }
        return uusers.get(0);
    }

    /**
     * 查询所有的医院活动
     *
     * @return
     */
    @Override
    public List<HospitalActivity> queryHealthActivity() {
        String sql = "select * from hospitalActivity";
        RowMapper<HospitalActivity> rowMapper = new BeanPropertyRowMapper<>(HospitalActivity.class);
        List<HospitalActivity> hospitalActivityList = jdbcTemplate.query(sql, rowMapper);
        return hospitalActivityList;
    }

    /**
     * 查询所有的健康新闻
     *
     * @return
     */
    @Override
    public List<HealthNews> queryHealthNews() {
        String sql = "select * from healthnews";
        RowMapper<HealthNews> rowMapper = new BeanPropertyRowMapper<>(HealthNews.class);
        List<HealthNews> healthNewsList = jdbcTemplate.query(sql, rowMapper);
        return healthNewsList;
    }

    /**
     * 通过诊疗卡的拥有者电话，查询他所办理的全部诊疗卡
     *
     * @param ownerAccount
     * @return
     */
    @Override
    public List<MedicalCard> queryMedicalCardByOwnerAccount(Long ownerAccount) {
        String sql = "select * from medicalcard where ownerAccount = ?";
        RowMapper<MedicalCard> rowMapper = new BeanPropertyRowMapper<>(MedicalCard.class);
        List<MedicalCard> medicalCards = jdbcTemplate.query(sql, new Object[]{ownerAccount}, rowMapper);
        return medicalCards;
    }

    /**
     * 用户办理一张诊疗卡
     *
     * @param medicalCard 待办理诊疗卡消息
     * @return 插入是否成功
     */
    @Override
    public boolean insertMedicalCard(MedicalCard medicalCard) {
        String sql = "insert into medicalCard(medicalCardNo, ownerName, ownerIdCard, ownerSex, ownerAddress, ownerAccount) values(?,?,?,?,?,?)";
        int lines = jdbcTemplate.update(sql, new Object[]{medicalCard.getMedicalCardNo(), medicalCard.getOwnerName(), medicalCard.getOwnerIdCard(), medicalCard.getOwnerSex(), medicalCard.getOwnerAddress(), medicalCard.getOwnerAccount()});
        return lines > 0;
    }

    /**
     * 插入用户绑定诊疗卡数据
     *
     * @param account
     * @param medicalCardNo
     * @return
     */
    @Override
    public boolean insertBind(Long account, Long medicalCardNo) {
        String sql = "insert into bind(account,medicalcardNo) values(?,?)";
        int lines = jdbcTemplate.update(sql, new Object[]{account, medicalCardNo});
        return lines > 0;
    }

    /**
     * 查询图片类验证码
     *
     * @return
     */
    @Override
    public PictureVerificationCode queryPictureVerificationCode() {
        String pictureVerificationCodeId = Utility.getRandom(1, 100);
        String sql = "select * from pictureVerificationCode where pictureVerificationCodeId = ?";
        RowMapper<PictureVerificationCode> rowMapper = new BeanPropertyRowMapper<>(PictureVerificationCode.class);
        List<PictureVerificationCode> pictureVerificationCodes = jdbcTemplate.query(sql, new Object[]{pictureVerificationCodeId}, rowMapper);
        if (pictureVerificationCodes.size() == 0) {
            queryPictureVerificationCode();
        }
        return pictureVerificationCodes.get(0);
    }

    /**
     * 查询简要健康新闻，用于healthNewsActivity中pager的显示
     *
     * @return
     */
    @Override
    public List<SimpleHealthNews> querySimpleHealthNews() {
        String sql = "select * from simpleHealthNews";
        RowMapper<SimpleHealthNews> rowMapper = new BeanPropertyRowMapper<>(SimpleHealthNews.class);
        List<SimpleHealthNews> simpleHealthNewsList = jdbcTemplate.query(sql, rowMapper);
        return simpleHealthNewsList;
    }

    /**
     * 查询 OutpatientIntroduction数据
     *
     * @param departmentNo
     * @return
     */
    @Override
    public DepartmentIntroduction queryDepartmentIntroduction(Integer departmentNo) {
        String sql = "select * from departmentIntroduction where departmentNo = ?";
        RowMapper<DepartmentIntroduction> rowMapper = new BeanPropertyRowMapper<>(DepartmentIntroduction.class);
        List<DepartmentIntroduction> outpatientIntroductions = jdbcTemplate.query(sql, new Object[]{departmentNo}, rowMapper);
        if (outpatientIntroductions.size() == 0) {
            return new DepartmentIntroduction();
        }
        return outpatientIntroductions.get(0);
    }

    /**
     * 查询指定account 的药品缴费信息
     *
     * @param account
     * @return
     */
    @Override
    public List<DrugCost> queryDrugCostsByAccount(Long account) {
        String sql = "select * from drugCost where account = ?";
        RowMapper<DrugCost> rowMapper = new BeanPropertyRowMapper<>(DrugCost.class);
        List<DrugCost> drugCosts = jdbcTemplate.query(sql, new Object[]{account}, rowMapper);

        return drugCosts;
    }

    /**
     * 查询指定account 的手术缴费信息
     *
     * @param account
     * @return
     */
    @Override
    public List<OperationCost> queryOperationCostsByAccount(Long account) {
        String sql = "select * from OperationCost where account = ?";
        RowMapper<OperationCost> rowMapper = new BeanPropertyRowMapper<>(OperationCost.class);
        List<OperationCost> operationCosts = jdbcTemplate.query(sql, new Object[]{account}, rowMapper);

        return operationCosts;
    }

    /**
     * 查询指定account 的检查检验缴费信息
     *
     * @param account
     * @return
     */
    @Override
    public List<CheckInspectionCost> queryCheckInspectionCostsByAccount(Long account) {
        String sql = "select * from CheckInspectionCost where account = ?";
        RowMapper<CheckInspectionCost> rowMapper = new BeanPropertyRowMapper<>(CheckInspectionCost.class);
        List<CheckInspectionCost> checkInspectionCosts = jdbcTemplate.query(sql, new Object[]{account}, rowMapper);

        return checkInspectionCosts;
    }

    /**
     * 通过 drugListId 得到所有的药品单详情
     *
     * @param drugListId
     * @return
     */
    @Override
    public List<DrugList> queryDrugListByDrugListId(String drugListId) {
        String sql = "select * from drugList where drugListId = ?";
        RowMapper<DrugList> rowMapper = new BeanPropertyRowMapper<>(DrugList.class);
        List<DrugList> drugLists = jdbcTemplate.query(sql, new Object[]{drugListId}, rowMapper);
        return drugLists;
    }

    /**
     * 通过 drugId 查询其详情.
     *
     * @param drugId
     * @return
     */
    @Override
    public Drug queryDrugDetailsByDrugId(String drugId) {
        String sql = "select * from drug where drugId = ?";
        RowMapper<Drug> rowMapper = new BeanPropertyRowMapper<>(Drug.class);
        List<Drug> drugs = jdbcTemplate.query(sql, new Object[]{drugId}, rowMapper);
        if (drugs.size() == 0) {
            return new Drug();
        }
        return drugs.get(0);
    }

    /**
     * 通过 operationItemId 来得到operationItem 相应的operationItem详细信息
     *
     * @param operationItemId
     * @return
     */
    @Override
    public OperationItem queryOperationItemByOperationItemId(String operationItemId) {
        String sql = "select * from operationItem where operationItemId = ?";
        RowMapper<OperationItem> rowMapper = new BeanPropertyRowMapper<>(OperationItem.class);
        List<OperationItem> operationItems = jdbcTemplate.query(sql, new Object[]{operationItemId}, rowMapper);

        if (operationItems.size() == 0) {
            return new OperationItem();
        }
        return operationItems.get(0);
    }

    /**
     * 通过 OperationCostId 查询手术单的大概详情
     *
     * @param operationCostId
     * @return
     */
    @Override
    public OperationCost queryOperationCostByOperationCostId(String operationCostId) {
        String sql = "select * from operationCost where operationCostId = ?";
        RowMapper<OperationCost> rowMapper = new BeanPropertyRowMapper<>(OperationCost.class);
        List<OperationCost> operationCosts = jdbcTemplate.query(sql, new Object[]{operationCostId}, rowMapper);

        if (operationCosts.size() == 0) {
            return new OperationCost();
        }
        return operationCosts.get(0);
    }

    /**
     * 通过 checkInspectionCostId 查询 checkInspectionCost数据
     *
     * @param checkInspectionCostId
     * @return
     */
    @Override
    public CheckInspectionCost queryCheckInspectionCostByCheckInspectionCostId(String checkInspectionCostId) {

        String sql = "select * from CheckInspectionCost where checkInspectionCostId = ?";
        RowMapper<CheckInspectionCost> rowMapper = new BeanPropertyRowMapper<>(CheckInspectionCost.class);
        List<CheckInspectionCost> checkInspectionCosts = jdbcTemplate.query(sql, new Object[]{checkInspectionCostId}, rowMapper);

        if (checkInspectionCosts.size() == 0) {
            return new CheckInspectionCost();
        }
        return checkInspectionCosts.get(0);
    }

    /**
     * 通过 checkInspectionItemId 查询 checkInspectionItem
     *
     * @param checkInspectionItemId
     * @return
     */
    @Override
    public CheckInspectionItem queryCheckInspectionItemByCheckInspectionItemId(String checkInspectionItemId) {

        String sql = "select * from CheckInspectionItem where CheckInspectionItemId = ?";
        RowMapper<CheckInspectionItem> rowMapper = new BeanPropertyRowMapper<>(CheckInspectionItem.class);
        List<CheckInspectionItem> checkInspectionItems = jdbcTemplate.query(sql, new Object[]{checkInspectionItemId}, rowMapper);

        if (checkInspectionItems.size() == 0) {
            return new CheckInspectionItem();
        }
        return checkInspectionItems.get(0);
    }

    /**
     * 通过 drugCostId 来得到DrugCost
     *
     * @param drugCostId
     * @return
     */
    @Override
    public DrugCost queryDrugCostByDrugCostId(String drugCostId) {
        String sql = "select * from drugCost where drugCostId = ?";
        RowMapper<DrugCost> rowMapper = new BeanPropertyRowMapper<>(DrugCost.class);
        List<DrugCost> drugCosts = jdbcTemplate.query(sql, new Object[]{drugCostId}, rowMapper);

        if (drugCosts.size() == 0) {
            return new DrugCost();
        }
        return drugCosts.get(0);
    }

    /**
     * 通过 chargeItemId 和 chargeItemType 查找 OrderBind 实例,后续使用其 orderId 来查找Orders实例
     *
     * @param chargeItemId
     * @param chargeItemType
     * @return
     */
    @Override
    public OrderBind queryOrderBind(String chargeItemId, Integer chargeItemType) {
        String sql = "select * from OrderBind where chargeItemId = ? and chargeItemType = ?";
        RowMapper<OrderBind> rowMapper = new BeanPropertyRowMapper<>(OrderBind.class);
        List<OrderBind> orderBinds = jdbcTemplate.query(sql, new Object[]{chargeItemId, chargeItemType}, rowMapper);

        if (orderBinds.size() == 0) {
            return new OrderBind();
        }
        return orderBinds.get(0);
    }

    /**
     * 通过 orderId 来查询 Orders
     *
     * @param paySerialNumber
     * @return
     */
    @Override
    public Orders queryOrders(String paySerialNumber) {
        String sql = "select * from Orders where paySerialNumber = ?";
        RowMapper<Orders> rowMapper = new BeanPropertyRowMapper<>(Orders.class);
        List<Orders> ordersList = jdbcTemplate.query(sql, new Object[]{paySerialNumber}, rowMapper);

        if (ordersList.size() == 0) {
            return new Orders();
        }
        return ordersList.get(0);
    }

    /**
     * 通过 doctorNoteId 查询 DoctorNote
     *
     * @param doctorNoteId
     * @return
     */
    @Override
    public DoctorNote queryDoctorNoteByDoctorNoteId(String doctorNoteId) {
        String sql = "select * from DoctorNote where doctorNoteId = ?";
        RowMapper<DoctorNote> rowMapper = new BeanPropertyRowMapper<>(DoctorNote.class);
        List<DoctorNote> doctorNotes = jdbcTemplate.query(sql, new Object[]{doctorNoteId}, rowMapper);

        if (doctorNotes.size() == 0) {
            return new DoctorNote();
        }
        return doctorNotes.get(0);
    }

    /**
     * 插入订单信息
     *
     * @param orders
     * @return
     */
    @Override
    public int insertOrder(Orders orders) {
        String sql = "insert into orders(ordername,orderstate,ordercreatetime,paymethod,payserialnumber,allprice,payorcanceltime) values(?,?,?,?,?,?,?)";
        int lines = jdbcTemplate.update(sql, new Object[]{orders.getOrderName(), orders.getOrderState(), orders.getOrderCreateTime(), orders.getPayMethod(), orders.getPaySerialNumber(), orders.getAllPrice(), orders.getPayOrCancelTime()});
        return lines;
    }

    /**
     * 插入 订单绑定 数据
     * 这个方法必须在 调用了 insertOrder 之后才能调用，因为 payserialnumber 参考 Orders 的 payserialnumber
     *
     * @param orderBind
     * @return
     */
    @Override
    public int insertOrderBind(OrderBind orderBind) {
        String sql = "insert into orderbind(payserialnumber,chargeItemType,chargeItemId) values(?,?,?)";
        int lines = jdbcTemplate.update(sql, new Object[]{orderBind.getPaySerialNumber(), orderBind.getChargeItemType(), orderBind.getChargeItemId()});
        return lines;
    }

    /**
     * 根据收费单单号来对应的订单支付状态，支付时间等
     *
     * @param chargeItemId
     * @param chargeItemType
     * @param orderState
     * @param payOrCancelTime
     * @return
     */
    @Override
    public int updateOrderState(String chargeItemId, Integer chargeItemType, Integer orderState, String payOrCancelTime) {
        OrderBind orderBind = queryOrderBind(chargeItemId, chargeItemType);
        int lines = updateOrders(orderBind.getPaySerialNumber(), orderState, payOrCancelTime);
        return lines;
    }

    /**
     * 修改指定订单状态
     *
     * @param paySerialNumber
     * @param orderState
     * @param payOrCancelTime
     * @return
     */
    @Override
    public int updateOrders(String paySerialNumber, Integer orderState, String payOrCancelTime) {
        String sql = "update orders set orderstate = ?, payorcanceltime = ? where payserialnumber = ?";
        int lines = jdbcTemplate.update(sql, new Object[]{orderState, payOrCancelTime, paySerialNumber});
        return lines;
    }

    /**
     * 删除 诊疗卡绑定
     *
     * @param account
     * @param medicalCardNo
     * @return
     */
    @Override
    public int deleteBind(Long account, Long medicalCardNo) {
        String sql = "delete bind where account = ? and medicalCardNo = ?";
        int lines = jdbcTemplate.update(sql,new Object[]{account,medicalCardNo});
        return lines;
    }

}
