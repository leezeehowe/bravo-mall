package per.lee.bravo.mall.authorization.exception.common;

import lombok.Getter;
import lombok.Setter;
import per.lee.bravo.mall.authorization.constant.typeEnum.DbOperation;
import per.lee.bravo.mall.authorization.entity.BaseEntity;
import per.lee.bravo.mall.authorization.exception.AuthorizationSystemException;

public class DaoOperationException extends AuthorizationSystemException {

    @Getter @Setter
    private DbOperation dbOperation;

    @Getter
    private String entityClassName;

    public <T extends BaseEntity> DaoOperationException(DbOperation dbOperation, Class<T> entityClass) {
        this.dbOperation = dbOperation;
        setEntityClassName(entityClass);
    }

    public <T extends BaseEntity> void setEntityClassName(Class<T> entityClass) {
        this.entityClassName = entityClass.getName();
    }
}
