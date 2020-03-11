package per.lee.bravo.mall.authorization.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import per.lee.bravo.bsonapi.advice.BravoApiGlobalExceptionHandler;
import per.lee.bravo.bsonapi.body.BravoApiBody;
import per.lee.bravo.bsonapi.body.errors.SourceItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends BravoApiGlobalExceptionHandler {

    @ExceptionHandler(value = HttpMessageConversionException.class)
    @ResponseBody
    public BravoApiBody handleIllegalDtoParameterException(HttpServletRequest request, HttpServletResponse response, HttpMessageConversionException _e) {
        String id, code, title, detail;
        HttpStatus status;
        SourceItem source;
        id = _e.getClass().getSimpleName();
        code = "-1";
        title = "请求参数不能为空哦";
        detail = _e.getMessage();
        status = HttpStatus.BAD_REQUEST;
        source = null;
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return BravoApiBody.fail(id, code, title, detail, status, source);
    }
}
