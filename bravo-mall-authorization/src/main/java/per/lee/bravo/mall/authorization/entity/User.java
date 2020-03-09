package per.lee.bravo.mall.authorization.entity;

import java.time.LocalDateTime;

import per.lee.bravo.mall.authorization.constant.statusEnum.Status;
import per.lee.bravo.mall.authorization.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表，权限服务内部维护的用户表，为了和外部服务解耦
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户中心的用户账号uuid
     */
    private String userUuid;

    /**
     * 状态，0->有效，1->无效
     */
    private Status status;

    /**
     * 创建该颁发记录的账户id，0->系统自动操作
     */
    private Long createBy;

    /**
     * 修改该颁发记录的账户id，0->系统自动操作
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
