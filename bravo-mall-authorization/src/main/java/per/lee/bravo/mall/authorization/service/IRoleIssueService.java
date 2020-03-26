package per.lee.bravo.mall.authorization.service;

import per.lee.bravo.mall.authorization.constant.statusEnum.Status;
import per.lee.bravo.mall.authorization.entity.RoleIssue;
import com.baomidou.mybatisplus.extension.service.IService;
import per.lee.bravo.mall.authorization.entity.User;
import per.lee.bravo.mall.authorization.restful.protocol.BravoApiException;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色颁发表，即用户与角色之间的映射 服务类
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
public interface IRoleIssueService extends IService<RoleIssue> {

    /**
     * 颁发一个角色给指定的用户
     * @param externalUserId 外部用户id
     * @param roleId 角色id
     * @param createCreateUserIfAbsent 是否自动内外部用户映射当系统当前无该外部用户记录
     */
    void issueRole(String externalUserId, Long roleId, boolean createCreateUserIfAbsent) throws BravoApiException;

    /**
     * 批量颁发角色给指定用户
     * @param externalUserId 指定的用户UUID
     * @param roleIdArray 要颁发的角色ID字符串数组
     * @param createCreateUserIfAbsent 是否自动创建请求用户若不存在
     * @return 颁发成功的消息提示 and 颁发失败的消息提示
     */
    Map<String, List<String>> batchIssueRole(String externalUserId, String[] roleIdArray, boolean createCreateUserIfAbsent);

    /**
     * 获取颁发记录
     * @param userId 用户id
     * @param roleId 角色id
     * @param status 颁发记录是否有效
     * @return 角色颁发记录
     */
    RoleIssue getOne(Long userId, Long roleId, Status status);

    /**
     * 获取一个用户的角色颁发记录
     * @param user
     * @return
     */
    List<RoleIssue> listRoleIssuesOfSpecifiedUser(User user, Status status);


}
