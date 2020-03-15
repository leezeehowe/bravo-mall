package per.lee.bravo.sms.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import per.lee.bravo.sms.restful.protocol.BravoApiEnumProtocol;

@Getter
public enum OperationErrorEnum implements BravoApiEnumProtocol {
    ILLEGAL_PARAMETER("1", "参数不合法，请重新输入", HttpStatus.BAD_REQUEST);

    private String code;

    private String message;

    private HttpStatus status;

    OperationErrorEnum(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

}
