package per.lee.bravo.sms.config;

import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import per.lee.bravo.sms.bean.AlibabaSmsBean;

@Configuration
public class BeanConfig {

    @Value("${alibaba-cloud.sms-service.accessKeyId}")
    private String ACCESS_KEY_ID;
    @Value("${alibaba-cloud.sms-service.accessKeySecret}")
    private String ACCESS_KEY_SECRET;

    @Bean(name = "alibabaSmsBean")
    public AlibabaSmsBean alibabaSmsBean() {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        return new AlibabaSmsBean(profile);
    }

}
