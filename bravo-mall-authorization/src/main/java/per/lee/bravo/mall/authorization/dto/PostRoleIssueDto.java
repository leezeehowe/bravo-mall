package per.lee.bravo.mall.authorization.dto;

import lombok.Data;

@Data
public class PostRoleIssueDto extends BaseDto{

    private Long roleId;

    private String issuedUserExternalId;

}
