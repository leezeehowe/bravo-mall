package per.lee.bravo.mall.authorization.service.impl;

import per.lee.bravo.bsonapi.constant.enums.DbOperation;
import per.lee.bravo.bsonapi.exception.dao.DaoOperationAbstractException;
import per.lee.bravo.mall.authorization.constant.statusEnum.Status;
import per.lee.bravo.mall.authorization.entity.Role;
import per.lee.bravo.mall.authorization.mapper.RoleMapper;
import per.lee.bravo.mall.authorization.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Override
    public void isRoleAvailable(Long roleId) throws DaoOperationAbstractException {
        Role role = Optional.ofNullable(getById(roleId))
                .orElseThrow(() -> new EntityNotFoundException(Role.class).withParameter("roleId"));
        if(Status.NONEFFECTIVE.equals(role.getStatus())) {
            throw new EntityNoneffectiveException(DbOperation.SELECT, Role.class).withParameter("roleId").withDetail("当前角色不可用哦");
        }
    }

}
