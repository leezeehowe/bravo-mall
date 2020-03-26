package per.lee.bravo.mall.authorization.entity;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import per.lee.bravo.mall.authorization.tree.po.TreeNode;

/**
 * <p>
 * 接口资源表
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ApiResource extends BaseEntity implements TreeNode {

    private static final long serialVersionUID = 1L;

    /**
     * 资源url路径
     */
    private String url;

    /**
     * 资源名
     */
    private String name;

    /**
     * 接口状态，0->可用，1->不可用
     */
    private Integer status;

    /**
     * 资源所属服务名
     */
    private String belongTo;

    /**
     * 资源描述
     */
    private String description;

    /**
     * 创建该资源的账户id, 0->系统初始化
     */
    private Long createBy;

    /**
     * 修改该角色的账号id，0->系统初始化
     */
    private Long updateBy;

    /**
     * 资源版本
     */
    private String version;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后一次更新时间
     */
    private LocalDateTime updateTime;

    private Long parId;

    private Integer subCount;

    private Integer level;

    @Override
    public void setSubCount(int subCount) {
        this.subCount = subCount;
    }
}
