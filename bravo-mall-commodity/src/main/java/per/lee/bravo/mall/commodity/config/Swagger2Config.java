package per.lee.bravo.mall.commodity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class Swagger2Config {

    public static final String basePackage = "per.lee.bravo.mall.commodity.*";
    public static final String name = "leezeehowe";
    public static final String url = "";
    public static final String email = "";

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("bravo-mall-commodity")   //标题
                .description("Restful-API-Doc") //描述
                .termsOfServiceUrl("https://www.cnblogs.com/viyoung") //这里配置的是服务网站，我写的是我的博客园站点~欢迎关注~
                .contact(new Contact(name, url, email)) // 三个参数依次是姓名，个人网站，邮箱
                .version("1.0") //版本
                .build();
    }
}
