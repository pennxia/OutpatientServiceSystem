package cn.nobitastudio.util;

import cn.nobitastudio.model.UserVerificationCode;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

import java.io.IOException;

public class SendMessageUtil {

    private static final int appid = 1400107331;

    private static final String appkey = "0e0b67106450077ff4a8cb3b0cf8a876";

    private static final int[] templateId = {147950,147954,147957,156224,156422};//0.注册账号 1.找回密码 2.修改密码 3.办理诊疗卡 4.绑定诊疗卡

    private static final String smsSign = "大雄咩咩";

    public static UserVerificationCode sendVerificationCodeToUser(String account, Integer messageType, String[] params){

        String verificationCode = params[0];
        SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
        SmsSingleSenderResult result;  // 签名参数未提供或者为空时，会使用默认签名发送短信
        String sendResult = "fail";
        try {
            result = ssender.sendWithParam("86", account, templateId[messageType], params, smsSign, "", "");
            if (result.result == 0){
                //send success
                sendResult = "success";
            }else {
                //send fail
                sendResult = "fail";
            }
        } catch (HTTPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new UserVerificationCode(Long.valueOf(account), verificationCode, 1, Utility.getCurrentTime(),sendResult);
    }

}
