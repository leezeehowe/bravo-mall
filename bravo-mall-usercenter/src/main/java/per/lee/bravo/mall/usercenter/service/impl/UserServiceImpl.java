package per.lee.bravo.mall.usercenter.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import per.lee.bravo.mall.usercenter.constant.operationError.GlobalErrorEnum;
import per.lee.bravo.mall.usercenter.dto.Code2SessionResultDto;
import per.lee.bravo.mall.usercenter.dto.PostUserInfoDto;
import per.lee.bravo.mall.usercenter.entity.ExtensionWechatAccount;
import per.lee.bravo.mall.usercenter.redis.RedisService;
import per.lee.bravo.mall.usercenter.restful.protocol.BravoApiException;
import per.lee.bravo.mall.usercenter.service.*;
import per.lee.bravo.mall.usercenter.entity.FundamentalAccount;
import per.lee.bravo.mall.usercenter.vo.UserForAdminVo;
import per.lee.bravo.mall.usercenter.vo.WechatForAdminVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private WechatMiniProgramService wechatMiniProgramService;
    @Autowired
    private FundamentalAccountService fundamentalAccountService;
    @Autowired
    private ExtensionWechatAccountService extensionWechatAccountService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RedisService redisService;
    @Value("${redis.key.prefix.authCode}")
    private String smsCodePrefix;

    @Override
    public String signIn(String phoneNumber, String authCode) throws BravoApiException {
        String storedAuthCode = redisService.get(smsCodePrefix + phoneNumber);
        // 若无存储的验证码 或 存储的验证码和参数不符，登录失败
//        if(StrUtil.isEmptyOrUndefined(storedAuthCode) || !storedAuthCode.equals(authCode)) {
//            throw new BravoApiException(GlobalErrorEnum.LOGIN_FAIL, "验证码错误，请重新输入");
//        }
        // 尝试从数据库中根据手机号获取基础账号信息
        FundamentalAccount fundamentalAccount = fundamentalAccountService.getByPhone(phoneNumber);
        // 无基础账号，未注册
        if(fundamentalAccount == null) {
            // 因为验证码验证通过，所以直接注册
            fundamentalAccount = signUp(phoneNumber);
        }
        return tokenService.issueJwtAccessToken(fundamentalAccount.getUuid(), "bravo-mall-admin");
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public String signInByWechatWithCode(String code) throws BravoApiException {
        String accessToken, openId;

        FundamentalAccount fundamentalAccount;

        ExtensionWechatAccount latestWechatAccountInfo, originWechatAccountInfo;

        latestWechatAccountInfo = attachExtendAccountInfoOfWechatByCode(code);
        originWechatAccountInfo = extensionWechatAccountService.getByOpenId(latestWechatAccountInfo.getOpenid());

        // 初次登录，需要创建基本账号
        if(originWechatAccountInfo == null) {
            fundamentalAccount = createFundamentalAccountInfo();
            fundamentalAccountService.save(fundamentalAccount);
            latestWechatAccountInfo.setUserUuid(fundamentalAccount.getUuid());
            log.info("\n新微信用户登录\n uuid: {}\n openId: {}\n sessionKey: {}", fundamentalAccount.getUuid(), latestWechatAccountInfo.getOpenid(), latestWechatAccountInfo.getSessionKey());
        }
        // 该微信用户先前已登录过本平台， 无需创建基本账号，只需更新微信账号信息
        else {
            fundamentalAccount = fundamentalAccountService.getByUUID(originWechatAccountInfo.getUserUuid());
            log.info("\n微信用户登录\n uuid: {}\n openId: {}\n sessionKey: {}", fundamentalAccount.getUuid(), latestWechatAccountInfo.getOpenid(), latestWechatAccountInfo.getSessionKey());
        }

        // 更新微信账号信息
        extensionWechatAccountService.saveOrUpdate(latestWechatAccountInfo);

        // 生成token
        accessToken = tokenService.issueJwtAccessToken(fundamentalAccount.getUuid(), "bravo-mall-mini-program");
        return accessToken;
    }

    @Override
    public FundamentalAccount signUp(String phoneNumber) throws BravoApiException {
        FundamentalAccount newAccount = createFundamentalAccountInfo();
        newAccount.setPhoneNumber(phoneNumber);
        boolean saveSuccess = fundamentalAccountService.save(newAccount);
        // 保存到数据库失败
        if(!saveSuccess) {
            throw new BravoApiException(GlobalErrorEnum.CREATE_ENTITY_FAIL, "注册失败，请重试");
        }
        // 保存成功
        return fundamentalAccountService.getByUUID(newAccount.getUuid());
    }


    @Override
    public boolean saveOrUpdateAccountInfo(String uuid, PostUserInfoDto dto) {
        FundamentalAccount fundamentalAccount = new FundamentalAccount();
        fundamentalAccount.setUuid(uuid);
        fundamentalAccount.setGender(dto.getGender());
        fundamentalAccount.setAvatar(dto.getAvatarUrl());
        fundamentalAccount.setNickname(dto.getNickName());
        fundamentalAccount.setCountry(dto.getCountry());
        fundamentalAccount.setCity(dto.getCity());
        fundamentalAccount.setProvince(dto.getProvince());
        return fundamentalAccountService.saveOrUpdate(fundamentalAccount);
    }

    @Override
    public FundamentalAccount getFundamentalAccountInfo(String uuid) throws BravoApiException {
        QueryWrapper<FundamentalAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uuid", uuid).last("LIMIT 1");
        return Optional
                .ofNullable(fundamentalAccountService.getOne(queryWrapper, false))
                .orElseThrow(() -> new BravoApiException(GlobalErrorEnum.ILLEGAL_ARGUMENT, "不存在账号哦"));
    }

    @Override
    public IPage<UserForAdminVo> pageUserForAdminVo(Integer current, Integer size) {
        IPage<UserForAdminVo> userForAdminVoIPage = new Page<>(current, size);
        List<UserForAdminVo> userForAdminVoList = new ArrayList<>();
        IPage<FundamentalAccount> fundamentalAccountPage = new Page<>(current, size);
        fundamentalAccountPage = this.fundamentalAccountService.page(fundamentalAccountPage);
        List<ExtensionWechatAccount> extensionWechatAccountList = extensionWechatAccountService.list(fundamentalAccountPage.getRecords());
        int index = 0;
        for (FundamentalAccount fundamentalAccount : fundamentalAccountPage.getRecords()) {
            WechatForAdminVo wechatForAdminVo = null;
            // 微信账号信息
            if(index < extensionWechatAccountList.size()) {
                wechatForAdminVo = new WechatForAdminVo();
                ExtensionWechatAccount extensionWechatAccount = extensionWechatAccountList.get(index++);
                wechatForAdminVo.setOpenId(extensionWechatAccount.getOpenid());
                wechatForAdminVo.setUnionId(extensionWechatAccount.getUnionid());
            }

            // 基础账号信息
            UserForAdminVo userForAdminVo = new UserForAdminVo();
            BeanUtil.copyProperties(fundamentalAccount, userForAdminVo);
            userForAdminVo.setWechatAccount(wechatForAdminVo);
            userForAdminVoList.add(userForAdminVo);
        }
        userForAdminVoIPage.setRecords(userForAdminVoList);
        userForAdminVoIPage.setTotal(fundamentalAccountPage.getTotal());
        userForAdminVoIPage.setPages(fundamentalAccountPage.getPages());
        return userForAdminVoIPage;
    }

    @Override
    public UserForAdminVo search(String phone, String username, String uuid) throws BravoApiException {
        UserForAdminVo userForAdminVo = new UserForAdminVo();
        QueryWrapper<FundamentalAccount> queryWrapper = new QueryWrapper<>();
        if(!StrUtil.isEmptyOrUndefined(phone)) {
            queryWrapper.eq("phone_number", phone);
        }
        if(!StrUtil.isEmptyOrUndefined(username)) {
            queryWrapper.eq("username", username);

        }
        if(!StrUtil.isEmptyOrUndefined(uuid)) {
            queryWrapper.eq("uuid", uuid);
        }
        FundamentalAccount fundamentalAccount = Optional.ofNullable(fundamentalAccountService.getOne(queryWrapper)).orElseThrow(() -> new BravoApiException(GlobalErrorEnum.RECORD_ABSENT, "无该注册用户，请检查输入。"));
        BeanUtil.copyProperties(fundamentalAccount, userForAdminVo);
        return userForAdminVo;
    }

    /**
     * 生成基本账户信息
     *
     * @return 基本账户信息
     */
    private FundamentalAccount createFundamentalAccountInfo() {
        FundamentalAccount fundamentalEntity;
        fundamentalEntity = new FundamentalAccount();
        fundamentalEntity.setUuid(IdUtil.randomUUID());
        return fundamentalEntity;
    }

    /**
     * 通过微信提供的临时登录凭证code生成拓展-微信账户信息
     *
     * @param code 临时登录凭证
     * @return 微信账户信息
     */
    private ExtensionWechatAccount attachExtendAccountInfoOfWechatByCode(String code) throws BravoApiException {
        ExtensionWechatAccount wechatAccountInfo;
        Code2SessionResultDto code2SessionResultDto;
        code2SessionResultDto = wechatMiniProgramService.code2Session(code);
        // 调用微信官方code2Session接口失败，抛出异常
        if (!code2SessionResultDto.isSuccess()) {
            String errorCode = code2SessionResultDto.getErrcode().toString();
            String errorMsg = code2SessionResultDto.getErrmsg();
            throw new BravoApiException(GlobalErrorEnum.EXTERNAL_SERVER_ERROR, "调用微信官方服务失败，请重试");
        }
        // 调用成功，把微信返回的dto转成实体类
        wechatAccountInfo = new ExtensionWechatAccount();
        wechatAccountInfo.setOpenid(code2SessionResultDto.getOpenid());
        wechatAccountInfo.setSessionKey(code2SessionResultDto.getSession_key());
        wechatAccountInfo.setUnionid(code2SessionResultDto.getUnionid());
        return wechatAccountInfo;
    }
}
