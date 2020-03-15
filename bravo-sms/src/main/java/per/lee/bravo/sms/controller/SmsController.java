package per.lee.bravo.sms.controller;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import per.lee.bravo.sms.restful.protocol.BravoApiException;
import per.lee.bravo.sms.service.SmsService;

@RestController
@RequestMapping("/api/${api.version}/sms")
@Slf4j
public class SmsController {

    @Autowired
    SmsService smsService;

    @GetMapping("/code")
    public JSONObject sendSms(String phoneNumber) throws BravoApiException {
        String code = smsService.sendAuthCodeToSpecifyPhone(phoneNumber);
        return new JSONObject().put("smsCode", code);
    }

}
