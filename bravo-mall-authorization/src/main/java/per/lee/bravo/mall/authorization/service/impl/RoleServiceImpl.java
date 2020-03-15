package per.lee.bravo.mall.authorization.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import per.lee.bravo.mall.authorization.constant.operationError.OperationErrorEnum;
import per.lee.bravo.mall.authorization.constant.statusEnum.Status;
import per.lee.bravo.mall.authorization.entity.Role;
import per.lee.bravo.mall.authorization.mapper.RoleMapper;
import per.lee.bravo.mall.authorization.restful.protocol.BravoApiException;
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

    final
    RoleMapper roleMapper;

    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public void isRoleAvailable(Long roleId) throws BravoApiException {
        Role role = Optional.ofNullable(getById(roleId))
                .orElseThrow(() -> new BravoApiException(OperationErrorEnum.ABSENT_ENTITY_ERROR, "不存在该角色，请联系管理员"));
        if(Status.NONEFFECTIVE.equals(role.getStatus())) {
            throw new BravoApiException(OperationErrorEnum.NONEFFECTIVE_ENTITY_ERROR, "当前角色不可用，请联系管理员");
        }
    }

    @Override
    public Integer getDeepestLevel() {
        return roleMapper.selectDeepestLevel();
    }

}
