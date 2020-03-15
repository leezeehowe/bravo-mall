package per.lee.bravo.mall.authorization.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PostRoleIssueDto extends BaseDto{

    private Long roleId;

    private String issuedUserExternalId;

}
