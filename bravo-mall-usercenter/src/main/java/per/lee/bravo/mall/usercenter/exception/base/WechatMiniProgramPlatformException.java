package per.lee.bravo.mall.usercenter.exception.base;

import per.lee.bravo.mall.usercenter.constant.ExternalPlatformIdentityEnum;

/**
 * 微信小程序平台异常
 */
public class WechatMiniProgramPlatformException extends ExternalPlatformException {

    @Override
    public ExternalPlatformIdentityEnum getExternalPlatformIdentity() {
        return ExternalPlatformIdentityEnum.WECHAT_MINI_PROGRAM;
    }

    public WechatMiniProgramPlatformException() {
    }

    public WechatMiniProgramPlatformException(String message, String customErrorCode) {
        super(message, customErrorCode);
    }

    public WechatMiniProgramPlatformException(String message, Throwable cause, String customErrorCode) {
        super(message, cause, customErrorCode);
    }
}
