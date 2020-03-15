package per.lee.bravo.mall.authorization.service;

import per.lee.bravo.mall.authorization.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import per.lee.bravo.mall.authorization.restful.protocol.BravoApiException;


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
     */
    User getRequestingUser(boolean createIfAbsent) throws BravoApiException;

    /**
     * 外部用户到系统内用户的映射
     * @param externalId 外部用户id
     * @param createIfAbsent 是否自动创建用户当系统当前不存在该用户
     * @return 用户实体类
     */
    User getOne(String externalId, boolean createIfAbsent) throws BravoApiException;

}
