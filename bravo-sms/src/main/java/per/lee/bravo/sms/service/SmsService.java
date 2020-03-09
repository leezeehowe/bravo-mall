package per.lee.bravo.sms.service;

import per.lee.bravo.sms.exception.SendSmsException;

public interface SmsService {

    /**
     * 发送验证码到指定的手机号
     * @param phoneNumber 接受验证码的手机号码
     * @return 验证码
     */
    String sendAuthCodeToSpecifyPhone(String phoneNumber) throws SendSmsException;

}
