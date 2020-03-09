package per.lee.bravo.mall.usercenter.exception.base;

import lombok.Getter;
import lombok.Setter;
import per.lee.bravo.mall.usercenter.constant.operationError.InternalOperationErrorEnum;
import per.lee.bravo.mall.usercenter.constant.operationError.OperationErrorInterface;

import java.lang.reflect.InvocationTargetException;

/**
 * 系统业务操作异常
 */
public class OperationException extends Exception {

    /**
     * 自定义业务异常错误码
     */
    @Getter @Setter
    private String customErrorCode;

    public OperationException() {}

    public OperationException(String message, String customErrorCode) {
        super(message);
        this.customErrorCode = customErrorCode;
    }

    public OperationException(String message, Throwable cause, String customErrorCode) {
        super(message, cause);
        this.customErrorCode = customErrorCode;
    }

    public static OperationException of(InternalOperationErrorEnum operationErrorEnum) {
        return new OperationException(operationErrorEnum.getDes(), operationErrorEnum.getCode());
    }

    public static <T extends OperationException> T of(Class<T> tClass, OperationErrorInterface errorInterface) {
        try {
            return OperationException.ofIns(tClass, errorInterface.getDes(), errorInterface.getCode());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            return (T) new OperationException(errorInterface.getDes(), errorInterface.getCode());
        }
    }

    public static <T extends OperationException> T ofIns(Class<T> tClass, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?>[] classes = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            classes[i] = params[i].getClass();
        }
        return tClass.getConstructor(classes).newInstance(params);
    }

}
