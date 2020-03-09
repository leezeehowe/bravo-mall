package per.lee.bravo.mall.authorization.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 网页资源表
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WebpageResource extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 上级id，0->一级菜单
     */
    private String parId;

    /**
     * 名称
     */
    private String name;

    /**
     * URL
     */
    private String url;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 类型，0->菜单，1->菜单项，2->超链接，3->按钮
     */
    private Integer type;

    /**
     * 资源描述
     */
    private String description;

    /**
     * 创建页面资源的账户id，0->系统初始化
     */
    private Long createBy;

    /**
     * 修改该页面资源的账号id，0->系统初始化
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
