package per.lee.bravo.mall.usercenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("per.lee.bravo.mall.usercenter.mapper")
public class BravoMallUserCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(BravoMallUserCenterApplication.class, args);
    }
}
