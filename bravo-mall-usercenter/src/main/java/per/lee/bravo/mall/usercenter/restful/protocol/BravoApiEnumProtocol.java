package per.lee.bravo.mall.usercenter.restful.protocol;

import org.springframework.http.HttpStatus;

public interface BravoApiEnumProtocol {

    String getCode();

    String getMessage();

    HttpStatus getStatus();

}
