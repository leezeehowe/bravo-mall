package per.lee.bravo.bsonapi.exception.dao;

import lombok.Getter;
import lombok.Setter;
import per.lee.bravo.bsonapi.exception.BravoApiAbstractException;

public class EntityException extends BravoApiAbstractException {

    // 实体类
    @Setter
    @Getter
    private Class<?> entityCType;

    public EntityException() {
    }

    public EntityException(String message) {
        super(message);
    }

    public EntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityException(Throwable cause) {
        super(cause);
    }

    public EntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public EntityException(Class<?> entityCType) {
        this.entityCType = entityCType;
    }
}
