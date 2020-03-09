package per.lee.bravo.mall.usercenter.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import per.lee.bravo.mall.usercenter.service.SmsService;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Override
    public String sendAuthCode(String phoneNumber) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("phoneNumber", phoneNumber);
        ServiceInstance smsServiceInstance = loadBalancerClient.choose("bravo-sms");
        String sendSmsApiUrl = StrUtil.format("http://{}:{}/sms", smsServiceInstance.getHost(), smsServiceInstance.getPort());
        String responseJsonStr = HttpUtil.createPost(sendSmsApiUrl).body(requestBody.toString()).contentType(ContentType.JSON.toString()).execute().body();
        JSONObject responseJson = JSONUtil.parseObj(responseJsonStr);
        return responseJson.getStr("authCode");
    }

}
