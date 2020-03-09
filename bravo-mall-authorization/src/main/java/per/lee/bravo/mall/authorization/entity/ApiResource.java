package per.lee.bravo.mall.authorization.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 接口资源表
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ApiResource extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资源url路径
     */
    private String url;

    /**
     * 接口状态，0->可用，1->不可用
     */
    private Integer status;

    /**
     * 接口所属服务名
     */
    private String belongTo;

    /**
     * 资源描述
     */
    private String description;

    /**
     * 创建该接口资源的账户id
     */
    private Long createBy;

    /**
     * 修改该角色的账号id，0->系统初始化
     */
    private Long updateBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后一次更新时间
     */
    private LocalDateTime updateTime;


}
