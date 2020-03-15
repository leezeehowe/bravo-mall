package per.lee.bravo.mall.authorization.controller;


import org.springframework.web.bind.annotation.*;
import per.lee.bravo.mall.authorization.dto.PostRoleIssueDto;

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
     */
    @PostMapping
    public void post(@RequestBody PostRoleIssueDto dto) throws Throwable {
        // 颁发对象 - 角色id
        Long roleId;
        // 颁发对象 - 用户的外部id
        String externalUserId;
        externalUserId = dto.getIssuedUserExternalId();
        roleId = dto.getRoleId();
        roleIssueService.issueRole(externalUserId, roleId, true);
    }

}
