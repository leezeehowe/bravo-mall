package per.lee.bravo.mall.authorization.dto;

import lombok.Data;

@Data
public class PostRoleDto {

    private String name;

    private String description;

    private Long parId;

    private Integer level;

}
