package per.lee.bravo.mall.authorization.service;

import per.lee.bravo.bsonapi.exception.dao.DaoOperationAbstractException;
import per.lee.bravo.mall.authorization.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
public interface IRoleService extends IService<Role> {

    /**
     * 该角色是否可使用
     * @param roleId 角色id
     */
    void isRoleAvailable(Long roleId) throws DaoOperationAbstractException;

}
