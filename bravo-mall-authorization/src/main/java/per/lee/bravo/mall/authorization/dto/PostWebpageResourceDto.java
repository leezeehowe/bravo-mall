package per.lee.bravo.mall.authorization.dto;

import lombok.Data;

@Data
public class PostWebpageResourceDto {

    private Integer type;

    private Integer level;

    private Long parId;

    private String name;

    private String text;

    private String description;

    private String path;

}
