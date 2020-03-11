package per.lee.bravo.bsonapi.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import per.lee.bravo.bsonapi.body.errors.SourceItem;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BravoApiAbstractException extends Exception{

    private String detail;

    private SourceItem source;

    private HttpStatus status;

    public String getClassSimpleName() {
        return this.getClass().getSimpleName();
    }

    public BravoApiAbstractException() {
    }

    public BravoApiAbstractException(String message) {
        super(message);
    }

    public BravoApiAbstractException(String message, Throwable cause) {
        super(message, cause);
    }

    public BravoApiAbstractException(Throwable cause) {
        super(cause);
    }

    public BravoApiAbstractException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
