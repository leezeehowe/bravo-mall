package per.lee.bravo.mall.authorization.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import per.lee.bravo.mall.authorization.constant.operationError.OperationErrorEnum;
import per.lee.bravo.mall.authorization.constant.statusEnum.Status;
import per.lee.bravo.mall.authorization.constant.typeEnum.ResourceType;
import per.lee.bravo.mall.authorization.entity.*;
import per.lee.bravo.mall.authorization.mapper.AuthorityMapper;
import per.lee.bravo.mall.authorization.restful.protocol.BravoApiException;
import per.lee.bravo.mall.authorization.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限表，由角色和资源之间的映射 服务实现类
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements IAuthorityService {

    @Autowired
    IUserService userService;
    @Autowired
    IWebpageResourceService webpageResourceService;
    @Autowired
    IAuthorityService authorityService;
    @Autowired
    IRoleService roleService;
    @Autowired
    IRoleIssueService roleIssueService;

    @Transactional(rollbackFor = {Exception.class})
    public Map<String, List<String>> issueAuthority(Long roleId, int resourceType, List<Long> resourceList) {
        List<String> successMessageList = new ArrayList<>();
        List<String> failMessageList = new ArrayList<>();
        String resourceTypeName = resourceType == 0 ? "API资源" : "网页资源";
        String successMessage = "成功授权{}[ID={}]给角色[ID={}]";
        String failMessage = "角色[ID={}]当前已经拥有该{}[ID={}]";
        try {
            User user = userService.getRequestingUser(true);
            List<Long> distinctResourceIdList = resourceList.stream().distinct().collect(Collectors.toList());
            // 过滤掉已经存在的授权记录
            Iterator<Long> iterator = distinctResourceIdList.iterator();
            while (iterator.hasNext()) {
                Long resourceId = iterator.next();
                // 已经有该条授权记录
                if (getOne(roleId, resourceId, resourceType) != null) {
                    iterator.remove();
                    failMessageList.add(StrUtil.format(failMessage, roleId, resourceTypeName, resourceId));
                } else {
                    successMessageList.add(StrUtil.format(successMessage, resourceTypeName, resourceId, roleId));
                }
            }
            List<Authority> authorityList = distinctResourceIdList.stream().map(resourceId -> {
                Authority authority = new Authority();
                authority.setRoleId(roleId);
                // 资源类型
                authority.setResourceType(resourceType);
                // 拥有该资源
                authority.setType(0);
                authority.setResourceId(resourceId);
                authority.setCreateBy(user.getId());
                authority.setUpdateBy(user.getId());
                return authority;
            }).collect(Collectors.toList());
            saveBatch(authorityList, authorityList.size());
            return Map.of("successfulIssue", successMessageList, "failIssue", failMessageList);
        } catch (Exception e) {
            failMessageList.clear();
            failMessageList.add("全部授权失败，请重试, 原因：" + e.getMessage());
            return Map.of("failIssue", failMessageList);
        }
    }

    @Override
    public void deleteAuthority(Long roleId, int resourceType, List<Long> resourceIdList) {
        QueryWrapper<Authority> queryWrapper = new QueryWrapper<>();
        resourceIdList.forEach((resourceId) -> {
            queryWrapper
                    .eq("role_id", roleId)
                    .eq("resource_id", resourceId)
                    .eq("resource_type", resourceType)
                    .or();
        });
        this.remove(queryWrapper);
    }

    @Override
    public Authority getOne(Long roleId, Long resourceId, int resourceType) {
        QueryWrapper<Authority> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("role_id", roleId)
                .eq("resource_id", resourceId)
                .eq("resource_type", resourceType);
        return getOne(queryWrapper);
    }

    @Override
    public List<Authority> getRouterAuthorityOfSpecifiedRole(Long roleId) {
        QueryWrapper<Authority> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("resource_type", ResourceType.MENU.getCode()).eq("type", 0).eq("role_id", roleId);
        return list(queryWrapper);
    }

    @Override
    public List<Authority> getApiAuthorityOfSpecifiedRole(Long roleId) {
        QueryWrapper<Authority> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("resource_type", ResourceType.API.getCode()).eq("type", 0).eq("role_id", roleId);
        return list(queryWrapper);
    }

    @Override
    public Set<String> getRouterAuthority() throws BravoApiException {
        // 路由权限
        Set<String> routersSet;
        // 获取正在请求的用户
        User requestingUser = userService.getRequestingUser(true);
        // 获取其有效的角色颁发记录
        List<RoleIssue> roleIssueList = roleIssueService.listRoleIssuesOfSpecifiedUser(requestingUser, Status.EFFECTIVE);
        // 遍历角色颁发记录
        return roleIssueList.stream().map(roleIssue -> {
            Long roleId = roleIssue.getRoleId();
            List<Authority> authorityList = authorityService.getRouterAuthorityOfSpecifiedRole(roleId);
            return webpageResourceService.getWebpageOfAuthority(authorityList).stream().map(WebpageResource::getName).collect(Collectors.toList());
        }).flatMap(Collection::stream).collect(Collectors.toSet());
    }
}
