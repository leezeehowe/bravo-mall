package per.lee.bravo.mall.authorization.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostAuthorityDto {

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 父角色id
     */
    private Long parId;

    /**
     * 资源id列表
     */
    private List<Long> webpage;

    /**
     * api资源id列表
     */
    private List<Long> api;

}
