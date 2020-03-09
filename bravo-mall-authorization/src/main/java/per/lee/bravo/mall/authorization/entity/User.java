package per.lee.bravo.mall.authorization.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 外部服务的用户id
     */
    private String externalId;

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

    public User(String externalId, Long createBy, Long updateBy) {
        this.externalId = externalId;
        this.createBy = createBy;
        this.updateBy = updateBy;
    }
}
