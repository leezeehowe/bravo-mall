package per.lee.bravo.mall.usercenter.constant.operationError;

import lombok.Setter;
import per.lee.bravo.mall.usercenter.constant.ExternalPlatformIdentityEnum;

public enum InternalOperationErrorEnum implements OperationErrorInterface{

    ACCESS_TOKEN_EXPIRED(8403, "accessToken已过期，请重新登录"),
    ABSENT_UUID(8404, "不存在该用户");

    @Setter
    private Integer errorCode;
    @Setter
    private String description;

    InternalOperationErrorEnum(Integer errorCode, String description) {
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
