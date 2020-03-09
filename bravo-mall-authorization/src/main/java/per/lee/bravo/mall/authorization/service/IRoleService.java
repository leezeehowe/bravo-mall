package per.lee.bravo.mall.authorization.service;

import per.lee.bravo.mall.authorization.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import per.lee.bravo.mall.authorization.exception.common.NotFoundException;
import per.lee.bravo.mall.authorization.exception.role.NoneffectiveRoleException;

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
     * @throws NoneffectiveRoleException 角色当前不可用
     * @throws NotFoundException  无该角色
     */
    void isRoleAvailable(Long roleId) throws NoneffectiveRoleException, NotFoundException;

}
