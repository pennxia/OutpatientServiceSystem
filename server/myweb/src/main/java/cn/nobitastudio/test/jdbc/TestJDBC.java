package cn.nobitastudio.test.jdbc;

import cn.nobitastudio.component.dao.IOutpatientServiceDAO;
import cn.nobitastudio.model.LoginMessage;
import cn.nobitastudio.model.Uuser;
import org.springframework.beans.factory.annotation.Autowired;

public class TestJDBC {

    @Autowired
    static IOutpatientServiceDAO iOutpatientServiceDAO;

    public static void main(String[] args) {

        LoginMessage loginMessage = new LoginMessage("15709932234","a");
        Uuser uuser = iOutpatientServiceDAO.isLogin(loginMessage);
        System.out.println(uuser.toString());

    }

}
