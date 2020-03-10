package per.lee.bravo.mall.authorization.exception.dao;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import lombok.Getter;
import lombok.Setter;
import per.lee.bravo.mall.authorization.constant.typeEnum.DbOperation;
import per.lee.bravo.mall.authorization.entity.BaseEntity;

import java.util.Optional;


/**
 * 当数据库中不存在指定条件的记录时抛出此异常
 */
public class EntityNotFoundException extends DaoOperationException {

    @Getter @Setter
    private JSONObject condition;

    private static String message = "数据库中不存在符合条件的记录";

    public <T extends BaseEntity> EntityNotFoundException(Class<T> entityClass, JSONObject condition) {
        super(DbOperation.SELECT, entityClass, message);
        setCondition(condition);
    }

    public <T extends BaseEntity> EntityNotFoundException(Class<T> entityClass, String columnName, Object columnValue) {
        super(DbOperation.SELECT, entityClass, message);
        setCondition(new JSONObject().put(columnName, Optional.ofNullable(columnValue).orElse("")));
    }

    @Override
    public String getMessage() {
        return StrUtil.format("{} - entity={} | type={} | condition={}", super.getMessage(), getEntityClassName(), getDbOperation(), condition.toString());
    }

    @Override
    public String getLocalizedMessage() {
        return StrUtil.format("{} - entity={} | type={} | condition={}", super.getMessage(), getEntitySimpleClassName(), getDbOperation(), condition.toString());
    }
}
