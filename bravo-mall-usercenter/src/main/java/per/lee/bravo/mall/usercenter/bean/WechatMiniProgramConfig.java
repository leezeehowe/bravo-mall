package per.lee.bravo.mall.usercenter.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class WechatMiniProgramConfig {

    @Value("${wechat.miniProgram.appId}")
    private String appId;

    @Value("${wechat.miniProgram.secret}")
    private String secret;


    public String getAppId() {
        return appId;
    }

    public String getSecret() {
        return secret;
    }
}
