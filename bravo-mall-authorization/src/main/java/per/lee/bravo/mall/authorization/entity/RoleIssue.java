package per.lee.bravo.mall.authorization.entity;

import java.time.LocalDateTime;

import per.lee.bravo.mall.authorization.constant.statusEnum.Status;
import per.lee.bravo.mall.authorization.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色颁发表，即用户与角色之间的映射
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class RoleIssue extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 状态，0->有效，1->无效
     */
    private Status status;

    /**
     * 创建该颁发记录的账户id，0->系统初始化
     */
    private Long createBy;

    /**
     * 修改该颁发记录的账户id，0->系统初始化
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
