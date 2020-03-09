package per.lee.bravo.mall.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BravoAuthorizationApplication {
    public static void main(String[] args) {
        SpringApplication.run(BravoAuthorizationApplication.class, args);
    }
}
