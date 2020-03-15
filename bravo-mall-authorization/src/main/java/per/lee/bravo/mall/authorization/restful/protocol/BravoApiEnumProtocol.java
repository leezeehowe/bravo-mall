package per.lee.bravo.mall.authorization.restful.protocol;

import org.springframework.http.HttpStatus;

public interface BravoApiEnumProtocol {

    String getCode();

    String getMessage();

    HttpStatus getStatus();

}
