package per.lee.bravo.mall.authorization.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import per.lee.bravo.mall.authorization.constant.operationError.OperationErrorEnum;
import per.lee.bravo.mall.authorization.constant.statusEnum.Status;
import per.lee.bravo.mall.authorization.entity.RoleIssue;
import per.lee.bravo.mall.authorization.entity.User;
import per.lee.bravo.mall.authorization.mapper.RoleIssueMapper;
import per.lee.bravo.mall.authorization.restful.protocol.BravoApiException;
import per.lee.bravo.mall.authorization.service.IRoleIssueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import per.lee.bravo.mall.authorization.service.IRoleService;
import per.lee.bravo.mall.authorization.service.IUserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色颁发表，即用户与角色之间的映射 服务实现类
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
@Service
public class RoleIssueServiceImpl extends ServiceImpl<RoleIssueMapper, RoleIssue> implements IRoleIssueService {

    @Autowired
    IRoleIssueService roleIssueService;
    @Autowired
    IRoleService roleService;
    @Autowired
    IUserService userService;

    @Override
    public RoleIssue getOne(Long userId, Long roleId, Status status) {
        QueryWrapper<RoleIssue> queryWrapper = new QueryWrapper<RoleIssue>()
                .eq("user_id", userId)
                .eq("role_id", roleId)
                .eq("status", status.getCode());
        return getOne(queryWrapper);
    }

    @Override
    public List<RoleIssue> listRoleIssuesOfSpecifiedUser(User user, Status status) {
        QueryWrapper<RoleIssue> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId()).eq("status", status.getCode());
        return list(queryWrapper);
    }

    @Override
    public void issueRole(String externalUserId, Long roleId, boolean createCreateUserIfAbsent) throws BravoApiException {
        RoleIssue roleIssue;
        User internalUser, requestingUser;
        // 检查颁发对象 - 角色 当前是否可用（是否存在 or 是否停用）
        roleService.isRoleAvailable(roleId);
        // 获取当前http请求的用户
        requestingUser = userService.getRequestingUser(true);
        // 获取颁发对象 - 用户
        internalUser = userService.getOne(externalUserId, createCreateUserIfAbsent);
        // 生产颁发记录实体对象
        roleIssue = new RoleIssue();
        // 颁发对象 - 用户id
        roleIssue.setUserId(internalUser.getId());
        // 颁发对象 - 角色id
        roleIssue.setRoleId(roleId);
        // 颁发人id
        roleIssue.setCreateBy(requestingUser.getId());
        roleIssue.setUpdateBy(requestingUser.getId());
        // 若当前已经有有效的颁发记录则无需新建
        if(getOne(internalUser.getId(), roleId, Status.EFFECTIVE) == null) {
            save(roleIssue);
        } else {
            throw new BravoApiException(OperationErrorEnum.CONFLICT_ENTITY_ERROR, StrUtil.format("用户[ID={}]已持有角色[ID={}]，请勿重复颁发", internalUser.getId(), roleId));
        }
    }

    @Override
    public Map<String, List<String>> batchIssueRole(String externalUserId, String[] roleIdArray, boolean createCreateUserIfAbsent) {
        Map<String, List<String>> map = new HashMap<>();
        List<String> successfulMessageList = new ArrayList<>();
        List<String> failMessageList = new ArrayList<>();
        for (String s : roleIdArray) {
            Long roleId = Long.valueOf(s);
            try {
                issueRole(externalUserId, roleId, createCreateUserIfAbsent);
                successfulMessageList.add(StrUtil.format("成功向用户颁发角色[ID={}]", roleId));
            } catch (BravoApiException e) {
                failMessageList.add(e.getDetail());
            }
        }
        map.put("successfulIssued", successfulMessageList);
        map.put("failIssued", failMessageList);
        return map;
    }
}
