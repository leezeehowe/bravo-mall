package per.lee.bravo.mall.usercenter.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import per.lee.bravo.mall.usercenter.bean.WechatMiniProgramConfig;
import per.lee.bravo.mall.usercenter.constant.operationError.WechatMiniProgramPlatformOperationErrorEnum;
import per.lee.bravo.mall.usercenter.dto.Code2SessionResultDto;
import per.lee.bravo.mall.usercenter.exception.wechat.Code2SessionApiException;
import per.lee.bravo.mall.usercenter.service.WechatMiniProgramService;

import java.util.Optional;

@Service
public class WechatMiniProgramServiceImpl implements WechatMiniProgramService {

    @Autowired
    WechatMiniProgramConfig wechatMiniProgramConfig;

    @Override
    public Code2SessionResultDto code2Session(String js_code) throws Code2SessionApiException {
        // appId
        String appId;
        // secret
        String secret;
        // code2Session接口的请求地址
        String code2SessionRequestUrl;
        // code2Session接口的响应体字符串
        String code2SessionResponseStr;
        // 返回结果封装
        Code2SessionResultDto code2SessionResultDto;
        appId = wechatMiniProgramConfig.getAppId();
        secret = wechatMiniProgramConfig.getSecret();
        code2SessionRequestUrl = StrUtil.format("https://api.weixin.qq.com/sns/jscode2session?appid={}&secret={}&js_code={}&grant_type=authorization_code", appId, secret, js_code);
        // 向微信发送请求，5s超时
        return Optional
                .ofNullable(HttpUtil.get(code2SessionRequestUrl, 5000))
                .map(s -> JSONUtil.toBean(s, Code2SessionResultDto.class))
                .orElseThrow(() -> Code2SessionApiException.of(Code2SessionApiException.class, WechatMiniProgramPlatformOperationErrorEnum.DEFAULT_ERROR));
    }
}
