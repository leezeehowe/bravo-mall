package per.lee.bravo.mall.usercenter.exception.internal;

import per.lee.bravo.mall.usercenter.constant.operationError.InternalOperationErrorEnum;
import per.lee.bravo.mall.usercenter.exception.base.OperationException;

public class AbsentUUIDException extends OperationException {
    public AbsentUUIDException(InternalOperationErrorEnum errorEnum) {
        super(errorEnum.getDes(), errorEnum.getCode());
    }
}
