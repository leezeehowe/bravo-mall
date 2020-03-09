package per.lee.bravo.mall.usercenter.constant.operationError;

import lombok.Setter;
import per.lee.bravo.mall.usercenter.constant.ExternalPlatformIdentityEnum;

public enum WechatMiniProgramPlatformOperationErrorEnum implements OperationErrorInterface {

    DEFAULT_ERROR(4000, "微信小程序平台异常"),
    CODE_INVALID(40163, "code无效，请重试");


    @Setter
    private Integer errorCode;
    @Setter
    private String description;

    WechatMiniProgramPlatformOperationErrorEnum(int code, String description) {
        setErrorCode(code);
        setDescription(description);
    }

    @Override
    public ExternalPlatformIdentityEnum getPlatformIdentity() {
        return ExternalPlatformIdentityEnum.WECHAT_MINI_PROGRAM;
    }

    @Override
    public String getCode() {
        return getPlatformIdentity().getCode() + errorCode;
    }

    @Override
    public String getDes() {
        return this.description;
    }


}
