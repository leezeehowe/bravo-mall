package per.lee.bravo.mall.commodity.restful.protocol;

import org.springframework.http.HttpStatus;

public interface BravoApiEnumProtocol {

    String getCode();

    String getMessage();

    HttpStatus getStatus();

}
