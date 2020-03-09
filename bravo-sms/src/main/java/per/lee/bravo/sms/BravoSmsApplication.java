package per.lee.bravo.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BravoSmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(BravoSmsApplication.class, args);
    }
}
