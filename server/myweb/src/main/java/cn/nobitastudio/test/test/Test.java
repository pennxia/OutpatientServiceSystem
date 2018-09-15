package cn.nobitastudio.test.test;


import cn.nobitastudio.util.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test {

    private static final int appid = 1400107331;

    private static final String appkey = "0e0b67106450077ff4a8cb3b0cf8a876";

    private static final int[] templateId = {147950, 147843, 147954, 147867, 147957, 147977}; //1.2 注册账号 3.4 找回密码 5.6 修改密码 每类第一个为文字少，第二个为文字多

    private static final String smsSign = "大雄咩咩";

    public static void main(String[] args) {

        Test2 test2 = new Test2();
        List<Test2> test2List = new ArrayList<>();
        test2List.add(test2);

        System.out.println(test2.hashCode()==test2List.get(0).hashCode());


    }



}
