package per.lee.bravo.sms.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import per.lee.bravo.sms.exception.SendSmsException;
import per.lee.bravo.sms.bean.AlibabaSmsBean;
import per.lee.bravo.sms.service.SmsService;

@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Value("${alibaba-cloud.sms-service.signName}")
    private String signName;

    @Value("${alibaba-cloud.sms-service.sendCodeTemplateCode}")
    private String sendCodeTemplate;

    @Autowired
    AlibabaSmsBean alibabaSmsBean;

    @Override
    public String sendAuthCodeToSpecifyPhone(String phoneNumber) throws SendSmsException {
        JSONObject templateParams = new JSONObject();
        // 生成验证码
        String code = RandomUtil.randomNumbers(6);
        // 装填模板参数
        templateParams.put("code", code);
        // 发送短信
        alibabaSmsBean.doSendSms(phoneNumber, sendCodeTemplate, signName, templateParams);
        // 日志
        log.info("成功发送验证码短信 - 手机 = {} | 验证码 = {}", phoneNumber, code);
        return code;
    }

}
