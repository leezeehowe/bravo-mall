package per.lee.bravo.bsonapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import per.lee.bravo.mall.authorization.restful.body.BsonApiBody;
import per.lee.bravo.mall.authorization.restful.body.errors.SourceItem;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BsonApiBody handleIllegalDtoParameterException(HttpServletRequest request, Exception _e) {
        String id, code, title, detail;
        HttpStatus status;
        List<SourceItem> source;
        if(_e instanceof BsonApiException) {
            BsonApiException e;
            e = (BsonApiException) _e;
            id = e.getId();
            code = e.getCode();
            title = e.getTitle(false);
            detail = e.getDetail();
            status = e.getStatus();
            source = e.getSource();
        } else {
            id = _e.getClass().getSimpleName();
            code = "-1";
            title = "系统繁忙，请稍后再来啦";
            detail = _e.getMessage();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            source = null;
        }
        return BsonApiBody.fail(id, code, title, detail, status, source);
    }

}
