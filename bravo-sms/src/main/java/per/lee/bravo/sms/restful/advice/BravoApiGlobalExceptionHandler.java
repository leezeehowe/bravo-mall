package per.lee.bravo.sms.restful.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import per.lee.bravo.sms.restful.BravoApiContext;
import per.lee.bravo.sms.restful.body.BravoApiRestfulBody;
import per.lee.bravo.sms.restful.protocol.BravoApiException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class BravoApiGlobalExceptionHandler {

    private BravoApiContext context;

    public BravoApiGlobalExceptionHandler(BravoApiContext context) {
        this.context = context;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BravoApiRestfulBody handleIllegalDtoParameterException(HttpServletRequest request, HttpServletResponse response, Exception _e) throws Exception {
        if(_e instanceof BravoApiException) {
            BravoApiException e = (BravoApiException)_e;
            return context.fail(e);
        }
        else {
            throw _e;
        }
    }

}
