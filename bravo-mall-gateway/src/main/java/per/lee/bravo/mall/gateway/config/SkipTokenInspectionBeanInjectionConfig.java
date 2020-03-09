package per.lee.bravo.mall.gateway.config;

import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import per.lee.bravo.mall.gateway.bean.SkipTokenInspectionUrlBean;

@Configuration
public class SkipTokenInspectionBeanInjectionConfig {

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String CONFIG_SERVER_ADDRESS;

    @Bean
    public SkipTokenInspectionUrlBean skipTokenInspectionUrlBean() throws NacosException {
        return new SkipTokenInspectionUrlBean(CONFIG_SERVER_ADDRESS);
    }

}
