package per.lee.bravo.bsonapi.exception;

import lombok.Setter;
import org.springframework.http.HttpStatus;
import per.lee.bravo.bsonapi.body.errors.SourceItem;

import java.util.List;


public class BsonApiException extends Exception {

    @Setter
    private String code;
    @Setter
    private String detail;
    @Setter
    private List<SourceItem> source;
    @Setter
    private HttpStatus status;


    public BsonApiException() {
    }

    public BsonApiException(String message) {
        super(message);
    }

    public BsonApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public BsonApiException(Throwable cause) {
        super(cause);
    }

    public BsonApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public final String getId() {
        return this.getClass().getSimpleName();
    }

    public final String getTitle(boolean localize) {
        return !localize ? getMessage() : getLocalizedMessage();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return this.code;
    }

    public String getDetail() {
        return this.detail;
    }

    public List<SourceItem> getSource() {
        return this.source;
    }

}
