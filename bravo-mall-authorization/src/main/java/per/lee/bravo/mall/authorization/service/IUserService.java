package per.lee.bravo.mall.authorization.service;

import per.lee.bravo.bsonapi.exception.dao.DaoOperationAbstractException;
import per.lee.bravo.bsonapi.exception.dto.IllegalDtoParameterAbstractException;
import per.lee.bravo.mall.authorization.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * <p>
 * 用户表，权限服务内部维护的用户表，为了和外部服务解耦 服务类
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
public interface IUserService extends IService<User> {

    /**
     * 获取此次http请求的用户外部id
     * @return 用户外部id
     */
    String getRequestingUserExternalId();

    /**
     * 获取此次http请求的用户实体类
     * @param createIfAbsent 是否自动创建用户当系统当前不存在该用户
     * @return 用户实体类
     * @throws DaoOperationAbstractException 自动创建用户失败
     */
    User getRequestingUser(boolean createIfAbsent) throws DaoOperationAbstractException, IllegalDtoParameterAbstractException;

    /**
     * 外部用户到系统内用户的映射
     * @param externalId 外部用户id
     * @param createIfAbsent 是否自动创建用户当系统当前不存在该用户
     * @return 用户实体类
     * @throws EntityNotFoundException 当 createIfAbsent = false，且系统当前缺少该条映射时，抛出此异常。
     * @throws DaoOperationAbstractException 自动创建用户失败
     */
    User getOne(String externalId, boolean createIfAbsent) throws IllegalDtoParameterAbstractException, DaoOperationAbstractException;

}
