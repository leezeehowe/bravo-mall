package per.lee.bravo.sms.bean;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import per.lee.bravo.sms.constant.AlibabaSendSmsException;
import per.lee.bravo.sms.exception.SendSmsException;

import java.util.Optional;

public class AlibabaSmsBean {

    private IAcsClient client;
    private CommonRequest request;

    public AlibabaSmsBean(DefaultProfile profile) {
        client = new DefaultAcsClient(profile);
        request = new CommonRequest();
    }

    public void doSendSms(String phoneNumbers, String templateCode, String signName, JSONObject templateParam) throws SendSmsException {
        String message = "\"发送短信：模板Code={} 签名={} 手机号={}\\n参数={}\\n响应={}\"";
        if(StrUtil.isEmptyOrUndefined(phoneNumbers)) {
            throw new SendSmsException(AlibabaSendSmsException.MISSING_PHONE_NUMBER.getMessage(), null).of(phoneNumbers, templateCode, signName, templateParam);
        }
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", templateParam.toString());
        try {
            CommonResponse response = client.getCommonResponse(request);
            JSONObject responseBody = JSONUtil.parseObj(response.getData());
            String code = responseBody.getStr("Code");
            if(!"OK".equals(code)) {
                throw new SendSmsException(AlibabaSendSmsException.of(code).getMessage(), responseBody).of(phoneNumbers, templateCode, signName, templateParam);
            }
        }
        catch (ClientException e) {
            throw new SendSmsException(e).of(phoneNumbers, templateCode, signName, templateParam);
        }
    }
}
