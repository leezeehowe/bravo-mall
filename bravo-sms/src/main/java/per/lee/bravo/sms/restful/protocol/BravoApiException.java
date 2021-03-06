package per.lee.bravo.sms.restful.protocol;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BravoApiException extends Exception{

    private String code;

    private String detail;

    private HttpStatus status;

    public BravoApiException(BravoApiEnumProtocol enumProtocol, String detail) {
        super(enumProtocol.getMessage());
        setDetail(detail);
        setCode(enumProtocol.getCode());
        setStatus(enumProtocol.getStatus());
    }

    public BravoApiException(String message, String code, String detail, HttpStatus status) {
        super(message);
        setCode(code);
        setDetail(detail);
        setStatus(status);
    }
}