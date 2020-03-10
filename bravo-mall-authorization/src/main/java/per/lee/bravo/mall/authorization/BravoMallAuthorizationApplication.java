package per.lee.bravo.mall.authorization;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
@MapperScan("per.lee.bravo.mall.authorization.mapper")
public class BravoMallAuthorizationApplication {
    public static void main(String[] args) {
        SpringApplication.run(BravoMallAuthorizationApplication.class, args);
    }
}
