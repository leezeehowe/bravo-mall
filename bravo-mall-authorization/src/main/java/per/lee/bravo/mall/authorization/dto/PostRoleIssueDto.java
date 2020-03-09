package per.lee.bravo.mall.authorization.dto;

import lombok.Data;

@Data
public class PostRoleIssueDto {

    private Long roleId;

    private String issuedUserExternalId;

}
