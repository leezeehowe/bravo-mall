package per.lee.bravo.mall.commodity;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("per.lee.bravo.mall.commodity.mapper")
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class BravoMallCommodityApplication {
    public static void main(String[] args) {
        SpringApplication.run(BravoMallCommodityApplication.class, args);
    }
}
