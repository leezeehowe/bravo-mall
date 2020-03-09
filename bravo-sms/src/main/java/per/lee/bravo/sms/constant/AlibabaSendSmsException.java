package per.lee.bravo.sms.constant;


import lombok.Getter;

public enum AlibabaSendSmsException {

    FAIL("default", "发送短信失败"),
    LIMITED("isv.BUSINESS_LIMIT_CONTROL", "获取验证码太频繁啦，请稍等一会再来吧！"),
    MISSING_PHONE_NUMBER("MISSING_PHONE_NUMBER", "告诉我手机号呢...");

    @Getter
    private String code;
    @Getter
    private String message;

    AlibabaSendSmsException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static AlibabaSendSmsException of(String code) {
        for (AlibabaSendSmsException sendSmsException : AlibabaSendSmsException.values()) {
            if(sendSmsException.getCode().equals(code)) {
                return sendSmsException;
            }
        }
        return FAIL;
    }
}
