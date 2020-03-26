package per.lee.bravo.mall.authorization.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import per.lee.bravo.mall.authorization.constant.operationError.OperationErrorEnum;
import per.lee.bravo.mall.authorization.constant.statusEnum.Status;
import per.lee.bravo.mall.authorization.entity.Role;
import per.lee.bravo.mall.authorization.entity.User;
import per.lee.bravo.mall.authorization.mapper.RoleMapper;
import per.lee.bravo.mall.authorization.mapper.TreeMapper;
import per.lee.bravo.mall.authorization.restful.protocol.BravoApiException;
import per.lee.bravo.mall.authorization.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import per.lee.bravo.mall.authorization.service.IUserService;
import per.lee.bravo.mall.authorization.tree.po.PayloadNode;
import per.lee.bravo.mall.authorization.tree.service.ITreeService;

import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private IUserService userService;
    @Autowired
    private ITreeService<Role> treeService;

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
        return treeService.getDeepestLevel("role");
    }

    @Override
    @Transactional
    public void createRole(String name, String description, Long parId, Integer level) throws BravoApiException {
        User user = userService.getRequestingUser(true);
        Role role = getByName(name);
        if(role != null) {
            throw new BravoApiException(OperationErrorEnum.CONFLICT_ENTITY_ERROR, StrUtil.format("已经存在名为[{}]的角色。", name));
        }
        role = new Role(parId, level, name, description);
        role.setCreateBy(user.getId());
        role.setUpdateBy(user.getId());
        try{
            save(role);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new BravoApiException(OperationErrorEnum.CREATE_ENTITY_FAIL, "请检查参数是否有误！");
        }
    }

    @Override
    public Role getByName(String name) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        return getOne(queryWrapper);
    }

    @Override
    public List<PayloadNode<Role>> getAllRootTree() {
        List<PayloadNode<Role>> rootNodeTree = new ArrayList<>();
        // 查出所有parId为0的树根节点
        List<Role> rootNode = treeService.getAllRootNode(this);
        rootNode.forEach(role -> {
            rootNodeTree.add(treeService.getSubTreeOf(role, this));
        });
        return rootNodeTree;
    }

}
