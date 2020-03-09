package per.lee.bravo.sms.exception;

import cn.hutool.json.JSONObject;
import lombok.Data;

import java.util.Map;

@Data
public class SendSmsException extends Exception {

    private String phoneNumbers;

    private String templateCode;

    private String signName;

    private JSONObject templateParams;

    private Map<String, Object> responseData;

    public SendSmsException(String message, Map<String, Object> customData) {
        super(message);
        setResponseData(customData);
    }

    public SendSmsException(Throwable cause) {
        super(cause);
    }

    public SendSmsException of(String phoneNumbers, String templateCode, String signName, JSONObject templateParams) {
        setPhoneNumbers(phoneNumbers);
        setTemplateCode(templateCode);
        setSignName(signName);
        setTemplateParams(templateParams);
        return this;
    }
}
