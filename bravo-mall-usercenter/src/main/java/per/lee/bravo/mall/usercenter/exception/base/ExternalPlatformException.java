package per.lee.bravo.mall.usercenter.exception.base;

import per.lee.bravo.mall.usercenter.constant.ExternalPlatformIdentityEnum;

public abstract class ExternalPlatformException extends OperationException {

    /**
     * 获取第三方平台身份
     * @return 第三方平台身份标识枚举类
     */
    public abstract ExternalPlatformIdentityEnum getExternalPlatformIdentity();

    public ExternalPlatformException() {
    }

    public ExternalPlatformException(String message, String customErrorCode) {
        super(message, customErrorCode);
    }

    public ExternalPlatformException(String message, Throwable cause, String customErrorCode) {
        super(message, cause, customErrorCode);
    }

    @Override
    public String getCustomErrorCode() {
        return getExternalPlatformIdentity().getCode() + "-" + super.getCustomErrorCode();
    }
}
