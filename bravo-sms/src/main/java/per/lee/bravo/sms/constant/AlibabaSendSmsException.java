package per.lee.bravo.sms.constant;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import per.lee.bravo.sms.restful.protocol.BravoApiEnumProtocol;
import per.lee.bravo.sms.restful.protocol.BravoApiException;

public enum AlibabaSendSmsException implements BravoApiEnumProtocol {

    FAIL("default", "发送短信失败", HttpStatus.INTERNAL_SERVER_ERROR),
    LIMITED("isv.BUSINESS_LIMIT_CONTROL", "获取验证码太频繁啦，请稍等一会再来吧！", HttpStatus.SERVICE_UNAVAILABLE),
    MISSING_PHONE_NUMBER("MISSING_PHONE_NUMBER", "告诉我手机号呢...", HttpStatus.BAD_REQUEST),
    ILLEGAL_PHONE_NUMBER("isv.MOBILE_NUMBER_ILLEGAL", "请给我正确的手机号呢...", HttpStatus.BAD_REQUEST);

    @Getter
    private String code;
    @Getter
    private String message;
    @Getter
    private HttpStatus httpStatus;

    AlibabaSendSmsException(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.httpStatus = status;
    }

    public static BravoApiException of(String code) {
        BravoApiException exception;
        for (AlibabaSendSmsException error : AlibabaSendSmsException.values()) {
            if(error.getCode().equals(code)) {
                return new BravoApiException(error, "");
            }
        }
        return new BravoApiException(AlibabaSendSmsException.FAIL, "未知原因，请重试");
    }

    @Override
    public HttpStatus getStatus() {
        return httpStatus;
    }
}
