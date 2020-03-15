package per.lee.bravo.mall.usercenter.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * user_wechat
 * @author lee
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "user_wechat")
public class ExtensionWechatAccount extends BaseEntity implements Serializable{

    /**
     * 平台用户的UUID
     */
    private String userUuid;

    /**
     * 微信用户unionId
     */
    private String unionid;

    /**
     * 微信小程序用户openId
     */
    private String openid;

    /**
     * 微信小程序用户secret
     */
    private String sessionKey;

    private static final long serialVersionUID = 1L;

    public static final String OPENID_COLUMN_NAME= "openid";
}