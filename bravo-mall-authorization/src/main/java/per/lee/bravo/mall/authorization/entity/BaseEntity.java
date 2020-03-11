package per.lee.bravo.mall.authorization.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;

public class BaseEntity {

    @Getter @TableId(type = IdType.AUTO)
    private Long id;

}
