package per.lee.bravo.mall.usercenter.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import per.lee.bravo.bsonapi.exception.dao.DaoOperationAbstractException;
import per.lee.bravo.mall.usercenter.constant.operationError.InternalOperationErrorEnum;
import per.lee.bravo.mall.usercenter.dto.Code2SessionResultDto;
import per.lee.bravo.mall.usercenter.dto.PostUserInfoDto;
import per.lee.bravo.mall.usercenter.entity.ExtensionWechatAccountEntity;
import per.lee.bravo.mall.usercenter.exception.wechat.Code2SessionApiException;
import per.lee.bravo.mall.usercenter.service.*;
import per.lee.bravo.mall.usercenter.entity.FundamentalAccountEntity;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private WechatMiniProgramService wechatMiniProgramService;
    @Autowired
    private FundamentalAccountEntityService fundamentalAccountEntityService;
    @Autowired
    private ExtensionWechatAccountEntityService extensionWechatAccountEntityService;
    @Autowired
    private TokenService tokenService;

    @Override
    public FundamentalAccountEntity signUp(String phoneNumber, String authCode, String password) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public String signUpByWechatWithCode(String code) throws Code2SessionApiException {
        String accessToken, openId;

        FundamentalAccountEntity fundamentalAccount;

        ExtensionWechatAccountEntity latestWechatAccountInfo, originWechatAccountInfo;

        latestWechatAccountInfo = attachExtendAccountInfoOfWechatByCode(code);
        originWechatAccountInfo = extensionWechatAccountEntityService.getByOpenId(latestWechatAccountInfo.getOpenid());

        // 初次登录，需要创建基本账号
        if(originWechatAccountInfo == null) {
            fundamentalAccount = createFundamentalAccountInfo();
            fundamentalAccountEntityService.save(fundamentalAccount);
            latestWechatAccountInfo.setUserUuid(fundamentalAccount.getUuid());
            log.info("\n新微信用户登录\n uuid: {}\n openId: {}\n sessionKey: {}", fundamentalAccount.getUuid(), latestWechatAccountInfo.getOpenid(), latestWechatAccountInfo.getSessionKey());
        }
        // 该微信用户先前已登录过本平台， 无需创建基本账号，只需更新微信账号信息
        else {
            fundamentalAccount = fundamentalAccountEntityService.getByUUID(originWechatAccountInfo.getUserUuid());
            log.info("\n微信用户登录\n uuid: {}\n openId: {}\n sessionKey: {}", fundamentalAccount.getUuid(), latestWechatAccountInfo.getOpenid(), latestWechatAccountInfo.getSessionKey());
        }

        // 更新微信账号信息
        extensionWechatAccountEntityService.saveOrUpdate(latestWechatAccountInfo);

        // 生成token
        accessToken = tokenService.issueJwtAccessToken(fundamentalAccount.getUuid(), "bravo-mall-mini-program");
        return accessToken;
    }


    @Override
    public boolean saveOrUpdateAccountInfo(String uuid, PostUserInfoDto dto) {
        FundamentalAccountEntity fundamentalAccountEntity = new FundamentalAccountEntity();
        fundamentalAccountEntity.setUuid(uuid);
        fundamentalAccountEntity.setGender(dto.getGender());
        fundamentalAccountEntity.setAvatar(dto.getAvatarUrl());
        fundamentalAccountEntity.setNickname(dto.getNickName());
        fundamentalAccountEntity.setCountry(dto.getCountry());
        fundamentalAccountEntity.setCity(dto.getCity());
        fundamentalAccountEntity.setProvince(dto.getProvince());
        return fundamentalAccountEntityService.saveOrUpdate(fundamentalAccountEntity);
    }

    @Override
    public FundamentalAccountEntity getFundamentalAccountInfo(String uuid) throws DaoOperationAbstractException {
        QueryWrapper<FundamentalAccountEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uuid", uuid).last("LIMIT 1");
        return Optional
                .ofNullable(fundamentalAccountEntityService.getOne(queryWrapper, false))
                .orElseThrow(() -> new EntityNotFoundException(FundamentalAccountEntity.class)
                        .withDetail(InternalOperationErrorEnum.ABSENT_UUID.getDes()));
    }

    /**
     * 生成基本账户信息
     *
     * @return 基本账户信息
     */
    private FundamentalAccountEntity createFundamentalAccountInfo() {
        FundamentalAccountEntity fundamentalEntity;
        fundamentalEntity = new FundamentalAccountEntity();
        fundamentalEntity.setUuid(IdUtil.randomUUID());
        return fundamentalEntity;
    }

    /**
     * 通过微信提供的临时登录凭证code生成拓展-微信账户信息
     *
     * @param code 临时登录凭证
     * @return 微信账户信息
     */
    private ExtensionWechatAccountEntity attachExtendAccountInfoOfWechatByCode(String code) throws Code2SessionApiException {
        ExtensionWechatAccountEntity wechatAccountInfo;
        Code2SessionResultDto code2SessionResultDto;
        code2SessionResultDto = wechatMiniProgramService.code2Session(code);
        // 调用微信官方code2Session接口失败，抛出异常
        if (!code2SessionResultDto.isSuccess()) {
            String errorCode = code2SessionResultDto.getErrcode().toString();
            String errorMsg = code2SessionResultDto.getErrmsg();
            throw new Code2SessionApiException(errorMsg, errorCode);
        }
        // 调用成功，把微信返回的dto转成实体类
        wechatAccountInfo = new ExtensionWechatAccountEntity();
        wechatAccountInfo.setOpenid(code2SessionResultDto.getOpenid());
        wechatAccountInfo.setSessionKey(code2SessionResultDto.getSession_key());
        wechatAccountInfo.setUnionid(code2SessionResultDto.getUnionid());
        return wechatAccountInfo;
    }
}
