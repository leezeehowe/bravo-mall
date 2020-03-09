package per.lee.bravo.mall.usercenter.constant;


import lombok.Getter;
import lombok.Setter;

/**
 * 各类对于本系统外部第三方平台得标识
 * 如：
 *  微信小程序
 *  Github
 *  ...
 */
public enum ExternalPlatformIdentityEnum {

    WECHAT_MINI_PROGRAM("1", "微信小程序");

    @Getter @Setter
    private String code;
    @Getter @Setter
    private String name;

    ExternalPlatformIdentityEnum(String code, String name) {
        setCode(code);
        setName(name);
    }

}
