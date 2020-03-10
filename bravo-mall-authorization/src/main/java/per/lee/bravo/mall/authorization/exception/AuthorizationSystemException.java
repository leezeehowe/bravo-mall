package per.lee.bravo.mall.authorization.exception;

import per.lee.bravo.mall.authorization.restful.BsonApiException;

/**
 * 权限系统的业务异常
 */
public class AuthorizationSystemException extends BsonApiException {

    private static String message = "权限系统业务操作异常";

    public AuthorizationSystemException() {
        super(message);
    }

    public AuthorizationSystemException(String message) {
        super(message);
    }

    public AuthorizationSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizationSystemException(Throwable cause) {
        super(cause);
    }

    public AuthorizationSystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
