package per.lee.bravo.mall.usercenter.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import per.lee.bravo.mall.usercenter.constant.entityStatus.FundamentalAccountInfoCompletingStatusEnum;
import per.lee.bravo.mall.usercenter.constant.entityStatus.UserGenderEnum;

/**
 * user
 * @author lee
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "user_fundamental")
public class FundamentalAccountEntity extends BaseEntity implements Serializable {

    /**
     * 用户唯一标识
     */
    private String uuid;

    /**
     * 用户名，可用于登录
     */
    private String username;

    /**
     * 密码，md5加密
     */
    private String password;

    /**
     * 预留字段，或用来存储第三方系统如github的用户信息
     */
    @TableField(select = false)
    private String reserve;

    /**
     * 邮箱地址
     */
    private String emailAddress;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 昵称，仅支持中文、数字、大小写英文字母、下划线
     */
    private String nickname;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 性别，0-未知，1-男性，2-女性
     */
    private UserGenderEnum gender;

    /**
     * 所在国家
     */
    private String country;

    /**
     * 所在省份
     */
    private String province;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 是否完善了信息, 0-是，1-否
     */
    private FundamentalAccountInfoCompletingStatusEnum isInfoCompleted;

    public static final String UUID_COLUMN_NAME = "uuid";

    private static final long serialVersionUID = 1L;
}