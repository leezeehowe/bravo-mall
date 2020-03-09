package per.lee.bravo.mall.usercenter.exception.wechat;

import per.lee.bravo.mall.usercenter.exception.base.WechatMiniProgramPlatformException;

public class Code2SessionApiException extends WechatMiniProgramPlatformException {

    public Code2SessionApiException() {
    }

    public Code2SessionApiException(String message, String customErrorCode) {
        super(message, customErrorCode);
        setCustomErrorCode(customErrorCode);
    }

    public Code2SessionApiException(String message, Throwable cause, String customErrorCode) {
        super(message, cause, customErrorCode);
    }

}
