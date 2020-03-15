package per.lee.bravo.mall.usercenter.service;

import cn.hutool.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import per.lee.bravo.mall.usercenter.restful.body.BravoApiRestfulBody;

@FeignClient(value = "bravo-sms")
public interface SmsService {

    /**
     * 发送验证码到指定手机号码
     * @param phoneNumber 手机号
     * @return 验证码 string
     */
    @RequestMapping(method = RequestMethod.GET, value="api/v1/sms/code")
    BravoApiRestfulBody getAuthCode(@RequestParam String phoneNumber);

}
