package per.lee.bravo.mall.usercenter.vo;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import per.lee.bravo.mall.usercenter.constant.entityStatus.FundamentalAccountInfoCompletingStatusEnum;
import per.lee.bravo.mall.usercenter.constant.entityStatus.UserGenderEnum;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserForAdminVo{

    private Long id;


    /**
     * 用户唯一标识
     */
    private String uuid;

    /**
     * 用户名，可用于登录
     */
    private String username;

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
     * 位置：
     * 国-省-市-区
     */
    private String position;

    /**
     * 是否完善了信息, 0-是，1-否
     */
    private FundamentalAccountInfoCompletingStatusEnum isInfoCompleted;

    /**
     * 微信账号信息
     */
    private WechatForAdminVo wechatAccount;

    public String getPosition() {
        List<String> strings = new ArrayList<>();
        String country = getCountry();
        String province = getProvince();
        String city = getCity();
        if(!StrUtil.isEmptyOrUndefined(country))strings.add(country);
        if(!StrUtil.isEmptyOrUndefined(province))strings.add(province);
        if(!StrUtil.isEmptyOrUndefined(city))strings.add(city);
        return ArrayUtil.join(strings.toArray(), "-");
    }
}
