package per.lee.bravo.mall.usercenter.service;

import per.lee.bravo.mall.usercenter.dto.PostUserInfoDto;
import per.lee.bravo.mall.usercenter.entity.FundamentalAccount;
import per.lee.bravo.mall.usercenter.restful.protocol.BravoApiException;

public interface UserService {

    /**
     * 通过手机号码、验证码进行登录
     * @param phoneNumber 手机号码
     * @param authCode 手机验证码
     * @return token 登录凭证
     */
    String signIn(String phoneNumber, String authCode) throws BravoApiException;

    /**
     * 通过微信注册用户
     * @param code 微信临时登录凭证
     * @return token 登录凭证
     */
    String signInByWechatWithCode(String code) throws BravoApiException;

    /**
     * 通过手机号码进行注册
     */
    FundamentalAccount signUp(String phoneNumber) throws BravoApiException;

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
    FundamentalAccount getFundamentalAccountInfo(String uuid) throws BravoApiException;

}
