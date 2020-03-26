package per.lee.bravo.mall.commodity.constant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import per.lee.bravo.mall.commodity.restful.protocol.BravoApiEnumProtocol;

@Getter
public enum OperationErrorEnum implements BravoApiEnumProtocol {

    OPERATION_ERROR("1", "未知异常 x_x", HttpStatus.INTERNAL_SERVER_ERROR),
    ILLEGAL_ARGUMENT("2", "参数不合法...", HttpStatus.BAD_REQUEST),
    EXTERNAL_SERVER_ERROR("3", "调用外部平台服务失败", HttpStatus.INTERNAL_SERVER_ERROR),
    CREATE_ENTITY_FAIL("4", "创建记录失败", HttpStatus.INTERNAL_SERVER_ERROR),
    ABSENT_ENTITY_ERROR("5", "不存在该记录", HttpStatus.NOT_FOUND),
    NONEFFECTIVE_ENTITY_ERROR("6", "当前不可用", HttpStatus.FORBIDDEN),
    CONFLICT_ENTITY_ERROR("7", "已经存在该记录", HttpStatus.CONFLICT);

    @Setter
    private String code;
    @Setter
    private String message;
    @Setter
    private HttpStatus status;

    OperationErrorEnum(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }


}
