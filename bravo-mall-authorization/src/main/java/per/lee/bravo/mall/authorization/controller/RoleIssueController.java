package per.lee.bravo.mall.authorization.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.lee.bravo.mall.authorization.dto.PostRoleIssueDto;
import per.lee.bravo.mall.authorization.entity.User;
import per.lee.bravo.mall.authorization.exception.common.DaoOperationException;
import per.lee.bravo.mall.authorization.exception.role.NoneffectiveRoleException;

/**
 * <p>
 * 角色颁发表，即用户与角色之间的映射 前端控制器
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
@RestController
@RequestMapping("/role-issue")
public class RoleIssueController extends BaseController{

    /**
     * 颁发角色给指定的用户
     * @param dto 参数
     * @throws DaoOperationException 自动创建用户失败
     * @throws NoneffectiveRoleException 角色当前不可用
     */
    @PostMapping
    public void post(@RequestBody PostRoleIssueDto dto) throws DaoOperationException, NoneffectiveRoleException {
        // 颁发对象 - 角色id
        Long roleId;
        // 颁发对象 - 用户的外部id
        String externalUserId;
        externalUserId = dto.getIssuedUserExternalId();
        roleId = dto.getRoleId();
        roleIssueService.issueRole(externalUserId, roleId, true);
    }

}
