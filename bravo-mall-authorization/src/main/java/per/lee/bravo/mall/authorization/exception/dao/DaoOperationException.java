package per.lee.bravo.mall.authorization.exception.dao;

import lombok.Getter;
import lombok.Setter;
import per.lee.bravo.mall.authorization.constant.typeEnum.DbOperation;
import per.lee.bravo.mall.authorization.entity.BaseEntity;
import per.lee.bravo.mall.authorization.exception.AuthorizationSystemException;

public class DaoOperationException extends AuthorizationSystemException {

    private static String message = "数据层操作失败";

    @Getter @Setter
    private DbOperation dbOperation;

    @Getter
    private String entityClassName;
    @Getter
    private String entitySimpleClassName;

    public <T extends BaseEntity> DaoOperationException(DbOperation dbOperation, Class<T> entityClass) {
        super(message);
        this.dbOperation = dbOperation;
        setEntityClassName(entityClass);
        setEntitySimpleClassName(entityClass);
    }

    public <T extends BaseEntity> DaoOperationException(DbOperation dbOperation, Class<T> entityClass, String message) {
        super(message);
        this.dbOperation = dbOperation;
        setEntityClassName(entityClass);
        setEntitySimpleClassName(entityClass);
    }

    public <T extends BaseEntity> void setEntityClassName(Class<T> entityClass) {
        this.entityClassName = entityClass.getName();
    }

    public <T extends BaseEntity> void setEntitySimpleClassName(Class<T> entityClass) {
        this.entitySimpleClassName = entityClass.getSimpleName();
    }
}
