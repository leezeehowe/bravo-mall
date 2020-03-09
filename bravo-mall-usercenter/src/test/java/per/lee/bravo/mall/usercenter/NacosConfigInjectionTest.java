package per.lee.bravo.mall.usercenter;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import per.lee.bravo.mall.usercenter.bean.WechatMiniProgramConfig;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class NacosConfigInjectionTest {

    @Autowired
    private WechatMiniProgramConfig wechatMiniProgramConfig;

    /**
     * 测试从Nacos上能否正确读取到小程序的appId和secret
     */
    @Test
    public void testWechatMiniProgramConfigInjection() {
        String appId, secret;
        appId = wechatMiniProgramConfig.getAppId();
        secret = wechatMiniProgramConfig.getSecret();
        log.debug("appId: {}", appId);
        log.debug("secret: {}", secret);
        Assert.assertNotNull("未从Nacos获取到小程序appId！", appId);
        Assert.assertNotNull("未从Nacos获取到小程序secret！", secret);
    }

}
