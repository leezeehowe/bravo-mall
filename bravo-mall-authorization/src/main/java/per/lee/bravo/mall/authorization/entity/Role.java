package per.lee.bravo.mall.authorization.entity;

import java.time.LocalDateTime;

import per.lee.bravo.mall.authorization.constant.statusEnum.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import per.lee.bravo.mall.authorization.tree.po.TreeNode;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Role extends BaseEntity implements TreeNode {

    private static final long serialVersionUID = 1L;

    /**
     * 上级角色id，0->一级角色
     */
    private Long parId;

    /**
     * 所在角色树层次，0->一级角色，1->二级角色
     */
    private Integer level;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色状态，0->有效的，1->无效的
     */
    private Status status;

    /**
     * 子角色数量
     */
    private Integer subCount;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 创建该角色的账号id，0->系统初始化
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

    public Role(Long parId, Integer level, String name, String description) {
        this.parId = parId;
        this.name = name;
        this.level = level;
        this.description = description;
    }

    @Override
    public void setSubCount(int subCount) {
        this.subCount = subCount;
    }
}
