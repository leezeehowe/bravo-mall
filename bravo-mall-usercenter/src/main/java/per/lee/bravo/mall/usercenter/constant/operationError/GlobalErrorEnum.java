package per.lee.bravo.mall.usercenter.constant.operationError;

import lombok.Setter;
import org.springframework.http.HttpStatus;
import per.lee.bravo.mall.usercenter.restful.protocol.BravoApiEnumProtocol;

public enum GlobalErrorEnum implements BravoApiEnumProtocol {

    OPERATION_ERROR("1", "未知异常 x_x", HttpStatus.INTERNAL_SERVER_ERROR),
    ILLEGAL_ARGUMENT("2", "参数不合法...", HttpStatus.BAD_REQUEST),
    EXTERNAL_SERVER_ERROR("3", "调用外部平台服务失败", HttpStatus.INTERNAL_SERVER_ERROR),
    LOGIN_FAIL("4", "登录失败", HttpStatus.FORBIDDEN),
    CREATE_ENTITY_FAIL("5", "创建记录失败", HttpStatus.INTERNAL_SERVER_ERROR),
    SEND_SMS_FAIL("6", "发送短信失败", HttpStatus.INTERNAL_SERVER_ERROR);

    @Setter
    private String code;
    @Setter
    private String message;
    @Setter
    private HttpStatus status;

    GlobalErrorEnum(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}
