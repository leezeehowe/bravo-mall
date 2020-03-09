package per.lee.bravo.sms.controller;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.lee.bravo.sms.dto.SendSmsDto;
import per.lee.bravo.sms.exception.SendSmsException;
import per.lee.bravo.sms.service.SmsService;

@RestController
@RequestMapping("/sms")
@Slf4j
public class SmsController {

    @Autowired
    SmsService smsService;

    @PostMapping("/code")
    public JSONObject sendSms(@RequestBody SendSmsDto dto) throws SendSmsException {
        String code = smsService.sendAuthCodeToSpecifyPhone(dto.getPhoneNumbers());
        return new JSONObject().put("code", code);
    }

}
