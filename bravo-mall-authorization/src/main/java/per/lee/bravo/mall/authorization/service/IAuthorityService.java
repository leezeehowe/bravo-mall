package per.lee.bravo.mall.authorization.service;

import per.lee.bravo.mall.authorization.constant.statusEnum.Status;
import per.lee.bravo.mall.authorization.entity.Authority;
import com.baomidou.mybatisplus.extension.service.IService;
import per.lee.bravo.mall.authorization.entity.Role;
import per.lee.bravo.mall.authorization.restful.protocol.BravoApiException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 权限表，由角色和资源之间的映射 服务类
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
public interface IAuthorityService extends IService<Authority> {

    /**
     * 颁发资源给指定角色
     * @param roleId 角色id
     * @param resourceType 资源类型，0-API 1-网页
     * @param resourceIdList 资源id列表
     */
    Map<String, List<String>> issueAuthority(Long roleId, int resourceType, List<Long> resourceIdList);

    /**
     * 删除指定角色的权限
     * @param roleId 角色id
     * @param resourceType 资源类型，0-API 1-网页
     * @param resourceIdList 资源id列表
     */
    void deleteAuthority(Long roleId, int resourceType, List<Long> resourceIdList);

    /**
     * 根据以下参数获取授权记录
     * @param roleId 角色id
     * @param resourceId 资源id
     * @param resourceType 资源类型
     * @return 授权记录
     */
    Authority getOne(Long roleId, Long resourceId, int resourceType);

    /**
     * 获取指定角色拥有的前端路由权限(即菜单)
     * @return 路由权限列表
     */
    List<Authority> getRouterAuthorityOfSpecifiedRole(Long roleId);

    /**
     * 获取指定角色拥有的api权限
     * @param roleId 角色id
     * @return 权限列表
     */
    List<Authority> getApiAuthorityOfSpecifiedRole(Long roleId);


    /**
     * 获取当前请求用户的路由权限名
     * @return
     */
    Set<String> getRouterAuthority() throws BravoApiException;


}
