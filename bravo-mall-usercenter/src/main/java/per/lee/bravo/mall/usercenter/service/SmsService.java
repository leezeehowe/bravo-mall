package per.lee.bravo.mall.usercenter.service;

public interface SmsService {
    /**
     * 发送验证码到指定手机号码
     * @param phoneNumber 手机号
     * @return 验证码 string
     */
    String sendAuthCode(String phoneNumber);
}
