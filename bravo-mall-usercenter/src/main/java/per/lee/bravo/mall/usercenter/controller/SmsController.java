package per.lee.bravo.mall.usercenter.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.lee.bravo.mall.usercenter.constant.operationError.GlobalErrorEnum;
import per.lee.bravo.mall.usercenter.redis.RedisService;
import per.lee.bravo.mall.usercenter.restful.body.BravoApiRestfulBody;
import per.lee.bravo.mall.usercenter.restful.protocol.BravoApiException;
import per.lee.bravo.mall.usercenter.service.SmsService;

@Slf4j
@RestController
@RequestMapping("/${api.version}/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;
    @Autowired
    private RedisService redisService;
    @Value("${redis.key.prefix.authCode}")
    private String smsCodePrefix;
    @Value("${redis.key.expire.authCode}")
    private Long expireSeconds;


    @PostMapping("/code")
    public void  sendAuthCode(@RequestBody JSONObject vo) throws BravoApiException {
        String phoneNumber = vo.getStr("phoneNumber");
        BravoApiRestfulBody responseBody = smsService.getAuthCode(phoneNumber);
        if(responseBody.getException() != null) {
            throw new BravoApiException(GlobalErrorEnum.SEND_SMS_FAIL, responseBody.getException().getMessage());
        }
        JSONObject data = JSONUtil.parseObj(responseBody.getResource());
        String smsCode = data.getStr("smsCode");
        redisService.set(smsCodePrefix + phoneNumber, smsCode);
        redisService.expire(smsCodePrefix + phoneNumber, expireSeconds);
        log.info("{} - {} - {} - 秒有效期", phoneNumber, smsCode, expireSeconds);
    }
}
