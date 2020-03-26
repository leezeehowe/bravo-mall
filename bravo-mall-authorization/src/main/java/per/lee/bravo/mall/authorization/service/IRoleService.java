package per.lee.bravo.mall.authorization.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import per.lee.bravo.mall.authorization.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import per.lee.bravo.mall.authorization.restful.protocol.BravoApiException;
import per.lee.bravo.mall.authorization.tree.po.PayloadNode;

import java.util.List;

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
    void isRoleAvailable(Long roleId) throws BravoApiException;

    /**
     * 获取当前角色树的层级
     * @return
     */
    Integer getDeepestLevel();

    /**
     * 创建角色
     * @param name 名称
     * @param description 描述
     * @param parId 上级角色id
     */
    void createRole(String name, String description, Long parId, Integer level) throws BravoApiException;

    /**
     * 根据名称获取角色
     * @param name 名称
     * @return
     */
    Role getByName(String name);

    List<PayloadNode<Role>> getAllRootTree();

}
