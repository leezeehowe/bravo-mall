package per.lee.bravo.mall.commodity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 类目表
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Category {

    private static final long serialVersionUID = 1L;

    /**
     * 上级类目的id， 0表示无上级类目即一级类目
     */
    private Long parId;

    /**
     * 类目名称
     */
    private String name;

    /**
     * 标识自己所在的层次，即自己属于第几级类目。0->1级类目，1->2级类目
     */
    private Integer level;

    /**
     * 该类目下的商品数量，等于该类目下的所有子树的类目节点的该值之和
     */
    private Integer productCount;

    /**
     * 该类目的商品单位，如服装类目该值则为 件
     */
    private String productUnit;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 图片
     */
    private String icon;

    /**
     * 关键字, 逗号分割
     */
    private String keywords;

    /**
     * 描述
     */
    private String description;

    /**
     * 是否显示在导航栏；0->不显示； 1->显示
     */
    private Integer navStatus;

    /**
     * 显示状态：0->不显示；1->显示
     */
    private Integer showStatus;

    /**
     * 主键，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后一次更新时间
     */
    private LocalDateTime updateTime;


}
