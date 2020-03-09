package per.lee.bravo.mall.usercenter.service;


import per.lee.bravo.mall.usercenter.dto.Code2SessionResultDto;
import per.lee.bravo.mall.usercenter.exception.wechat.Code2SessionApiException;

public interface WechatMiniProgramService {

    /**
     * 通过微信下发的临时登录凭证Code向微信服务器请求登录
     * @param js_code 临时登录凭证
     * @return Code2SessionResult
     */
    Code2SessionResultDto code2Session(String js_code) throws Code2SessionApiException;
}
