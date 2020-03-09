package per.lee.bravo.mall.usercenter.controller;

import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.lee.bravo.mall.usercenter.service.SmsService;

@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @PostMapping("/authCode")
    public String sendAuthCode(@RequestBody JSONObject vo) {
        String phoneNumber = vo.getStr("phoneNumber");
        return smsService.sendAuthCode(phoneNumber);
    }
}
