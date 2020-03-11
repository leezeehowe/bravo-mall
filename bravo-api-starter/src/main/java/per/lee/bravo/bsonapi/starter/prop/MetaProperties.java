package per.lee.bravo.bsonapi.starter.prop;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bson-api.meta")
public class MetaProperties {

    private String copyright;

    private String authors;

}
