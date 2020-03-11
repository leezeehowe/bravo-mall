package per.lee.bravo.bsonapi.exception.dao;

import lombok.Getter;
import lombok.Setter;
import per.lee.bravo.bsonapi.constant.typeenum.DbOperation;
import per.lee.bravo.bsonapi.exception.BsonApiException;

public class DaoOperationException extends BsonApiException {

    private static String message = "数据层操作失败";

    @Getter @Setter
    private DbOperation dbOperation;

    @Getter
    private String entityClassName;
    @Getter
    private String entitySimpleClassName;

    public <T> DaoOperationException(DbOperation dbOperation, Class<T> entityClass) {
        super(message);
        this.dbOperation = dbOperation;
        setEntityClassName(entityClass);
        setEntitySimpleClassName(entityClass);
    }

    public <T> DaoOperationException(DbOperation dbOperation, Class<T> entityClass, String message) {
        super(message);
        this.dbOperation = dbOperation;
        setEntityClassName(entityClass);
        setEntitySimpleClassName(entityClass);
    }

    public <T> void setEntityClassName(Class<T> entityClass) {
        this.entityClassName = entityClass.getName();
    }

    public <T> void setEntitySimpleClassName(Class<T> entityClass) {
        this.entitySimpleClassName = entityClass.getSimpleName();
    }
}
