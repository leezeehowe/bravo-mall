package per.lee.bravo.sms.restful.protocol;

import org.springframework.http.HttpStatus;

public interface BravoApiEnumProtocol {

    String getCode();

    String getMessage();

    HttpStatus getStatus();

}
