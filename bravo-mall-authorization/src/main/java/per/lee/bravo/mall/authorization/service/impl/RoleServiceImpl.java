package per.lee.bravo.mall.authorization.service.impl;

import per.lee.bravo.mall.authorization.constant.statusEnum.Status;
import per.lee.bravo.mall.authorization.entity.Role;
import per.lee.bravo.mall.authorization.exception.common.NotFoundException;
import per.lee.bravo.mall.authorization.exception.role.NoneffectiveRoleException;
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
    public void isRoleAvailable(Long roleId) throws NoneffectiveRoleException, NotFoundException {
        Role role = Optional.of(getById(roleId))
                .orElseThrow(NotFoundException::new);
        if(Status.NONEFFECTIVE.equals(role.getStatus())) {
            throw new NoneffectiveRoleException();
        }
    }

}
