package per.lee.bravo.mall.authorization.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import per.lee.bravo.mall.authorization.tree.po.TreeNode;

/**
 * <p>
 * 网页资源表
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WebpageResource extends BaseEntity implements Serializable, TreeNode {

    private static final long serialVersionUID = 1L;

    /**
     * 上级id，0->一级菜单
     */
    private Long parId;

    /**
     * 资源名
     */
    private String name;

    /**
     * 页面文字内容
     */
    private String text;

    /**
     * URL
     */
    private String path;

    /**
     * 类型，0->菜单，1->菜单项，2->超链接，3->按钮
     */
    private Integer type;

    /**
     * 资源描述
     */
    private String description;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 拥有子资源
     */
    private Integer subCount;

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

    public void setSubCount(Integer subCount) {
        this.subCount = subCount;
    }
}
