package per.lee.bravo.mall.usercenter.constant.operationError;

import lombok.Setter;
import per.lee.bravo.mall.usercenter.constant.ExternalPlatformIdentityEnum;

public enum CommonOperationErrorEnum implements OperationErrorInterface {

    OPERATION_ERROR(8848, "未知异常 x_x"),
    ILLEGAL_ARGUMENT(8849, "参数不合法...");

    @Setter
    private Integer errorCode;
    @Setter
    private String description;

    CommonOperationErrorEnum(Integer errorCode, String description) {
        this.errorCode = errorCode;
        this.description = description;
    }


    @Override
    public ExternalPlatformIdentityEnum getPlatformIdentity() {
        return null;
    }

    @Override
    public String getCode() {
        return this.errorCode.toString();
    }

    @Override
    public String getDes() {
        return this.description;
    }
}
