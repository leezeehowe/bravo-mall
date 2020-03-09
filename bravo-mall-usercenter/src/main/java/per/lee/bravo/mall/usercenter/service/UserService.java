package per.lee.bravo.mall.usercenter.service;

import per.lee.bravo.mall.usercenter.dto.PostUserInfoDto;
import per.lee.bravo.mall.usercenter.entity.ExtensionWechatAccountEntity;
import per.lee.bravo.mall.usercenter.entity.FundamentalAccountEntity;
import per.lee.bravo.mall.usercenter.exception.internal.AbsentUUIDException;
import per.lee.bravo.mall.usercenter.exception.wechat.Code2SessionApiException;

public interface UserService{

    /**
     * 通过手机号码、验证码、密码进行注册
     * @param phoneNumber 手机号码
     * @param authCode 手机验证码
     * @param password 密码
     * @return 用户信息
     */
    FundamentalAccountEntity signUp(String phoneNumber, String authCode, String password);

    /**
     * 通过微信注册用户
     * @param code 微信临时登录凭证
     * @return token
     */
    String signUpByWechatWithCode(String code) throws Code2SessionApiException;


    /**
     * 保存或更新基本账户信息
     * @param uuid 账户uuid
     * @param dto 基本账户信息DTO
     * @return 是否成功
     */
    boolean saveOrUpdateAccountInfo(String uuid, PostUserInfoDto dto);

    /**
     * 获取基本账户信息
     * @param uuid 账户uuid
     * @return 基本用户信息
     */
    FundamentalAccountEntity getFundamentalAccountInfo(String uuid) throws AbsentUUIDException;

}
