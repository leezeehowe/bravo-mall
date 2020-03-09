package per.lee.bravo.sms.dto;

import cn.hutool.json.JSONObject;
import lombok.Data;

@Data
public class SendSmsDto {

    private String phoneNumbers;

    private String templateCode;

    private String signName;

    private JSONObject templateParams;

}
