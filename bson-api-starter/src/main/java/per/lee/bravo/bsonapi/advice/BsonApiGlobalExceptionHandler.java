package per.lee.bravo.bsonapi.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import per.lee.bravo.bsonapi.body.BsonApiBody;
import per.lee.bravo.bsonapi.body.errors.SourceItem;
import per.lee.bravo.bsonapi.exception.BsonApiException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
public class BsonApiGlobalExceptionHandler {

    @ExceptionHandler(value = BsonApiException.class)
    @ResponseBody
    public BsonApiBody handleIllegalDtoParameterException(HttpServletRequest request, HttpServletResponse response, BsonApiException e) {
        String id, code, title, detail;
        HttpStatus status;
        List<SourceItem> source;
        id = e.getId();
        code = e.getCode();
        title = e.getTitle(false);
        detail = e.getDetail();
        status = e.getStatus();
        source = e.getSource();
        response.setStatus(status.value());
        return BsonApiBody.fail(id, code, title, detail, status, source);
    }

}
