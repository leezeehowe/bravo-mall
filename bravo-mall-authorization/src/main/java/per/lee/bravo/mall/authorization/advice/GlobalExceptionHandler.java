package per.lee.bravo.mall.authorization.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import per.lee.bravo.bsonapi.advice.BsonApiGlobalExceptionHandler;
import per.lee.bravo.bsonapi.body.BsonApiBody;
import per.lee.bravo.bsonapi.body.errors.SourceItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends BsonApiGlobalExceptionHandler {

    @ExceptionHandler(value = HttpMessageConversionException.class)
    @ResponseBody
    public BsonApiBody handleIllegalDtoParameterException(HttpServletRequest request, HttpServletResponse response, HttpMessageConversionException _e) {
        String id, code, title, detail;
        HttpStatus status;
        List<SourceItem> source;
        id = _e.getClass().getSimpleName();
        code = "-1";
        title = "请求参数不能为空哦";
        detail = _e.getMessage();
        status = HttpStatus.BAD_REQUEST;
        source = null;
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return BsonApiBody.fail(id, code, title, detail, status, source);
    }
}
