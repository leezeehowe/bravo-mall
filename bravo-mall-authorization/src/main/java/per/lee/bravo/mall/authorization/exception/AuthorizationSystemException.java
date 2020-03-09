package per.lee.bravo.mall.authorization.exception;

/**
 * 权限系统的运行时异常
 */
public class AuthorizationSystemException extends Exception {

    public AuthorizationSystemException() {
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
