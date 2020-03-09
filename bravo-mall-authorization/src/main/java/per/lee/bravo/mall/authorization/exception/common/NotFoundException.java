package per.lee.bravo.mall.authorization.exception.common;


import cn.hutool.json.JSONObject;
import lombok.Getter;
import lombok.Setter;
import per.lee.bravo.mall.authorization.constant.typeEnum.DbOperation;
import per.lee.bravo.mall.authorization.entity.BaseEntity;


/**
 * 当数据库中不存在指定的主键是抛出该异常
 */
public class NotFoundException extends DaoOperationException {

    @Getter @Setter
    private JSONObject condition;


    public <T extends BaseEntity> NotFoundException(Class<T> entityClass, JSONObject condition) {
        super(DbOperation.SELECT, entityClass);
        setCondition(condition);
    }

    public <T extends BaseEntity> NotFoundException(Class<T> entityClass, String columnName, String columnValue) {
        super(DbOperation.SELECT, entityClass);
        setCondition(new JSONObject().put(columnName, columnValue));
    }
}
