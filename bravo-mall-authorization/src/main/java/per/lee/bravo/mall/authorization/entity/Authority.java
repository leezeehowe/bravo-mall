package per.lee.bravo.mall.authorization.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限表，由角色和资源之间的映射
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Authority extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 资源id，只能是父级角色拥有的资源id
     */
    private Long resourceId;

    /**
     * 资源类型，0->接口资源，1->页面资源
     */
    private Integer resourceType;

    /**
     * 类型，0->拥有该权限，1->无该权限
     */
    private Integer type;

    /**
     * 创建该权限的账户id，0->系统初始化
     */
    private Long createBy;

    /**
     * 修改该权限的账户id，0->系统初始化
     */
    private Long updateBy;

    /**
     * 资源描述
     */
    private String description;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后一次更新时间
     */
    private LocalDateTime updateTime;


}
