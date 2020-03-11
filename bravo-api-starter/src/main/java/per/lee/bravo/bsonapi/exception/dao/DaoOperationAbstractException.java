package per.lee.bravo.bsonapi.exception.dao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import per.lee.bravo.bsonapi.BravoErrorItemMetaData;
import per.lee.bravo.bsonapi.body.errors.SourceItem;
import per.lee.bravo.bsonapi.constant.enums.DbOperation;
import per.lee.bravo.bsonapi.exception.BravoApiAbstractException;


public class DaoOperationAbstractException extends BravoApiAbstractException implements BravoErrorItemMetaData<DaoOperationAbstractException> {

    // 数据库操作类型，增、删、改、查
    @Setter @Getter
    private DbOperation dbOperation;

    // 实体类
    @Setter @Getter
    private Class<?> entityCType;

    @Override
    public String getId() {
        return getClassSimpleName();
    }

    @Override
    public String getCode() {
        return getClassSimpleName();
    }

    @Override
    public String getTitle() {
        return "数据操作失败";
    }

    @Override
    public DaoOperationAbstractException withStatus(HttpStatus status) {
        setStatus(status);
        return this;
    }

    @Override
    public DaoOperationAbstractException withParameter(String parameter) {
        setSource(new SourceItem(null, parameter));
        return this;
    }

    @Override
    public DaoOperationAbstractException withDetail(String detail) {
        setDetail(detail);
        return this;
    }

    public DaoOperationAbstractException() {
    }

    public DaoOperationAbstractException(String message) {
        super(message);
    }

    public DaoOperationAbstractException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoOperationAbstractException(Throwable cause) {
        super(cause);
    }

    public DaoOperationAbstractException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DaoOperationAbstractException(DbOperation dbOperation, Class<?> entityCType) {
        this.dbOperation = dbOperation;
        this.entityCType = entityCType;
    }
}
