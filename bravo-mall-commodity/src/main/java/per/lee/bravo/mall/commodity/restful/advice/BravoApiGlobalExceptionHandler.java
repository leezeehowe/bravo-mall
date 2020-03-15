package per.lee.bravo.mall.commodity.restful.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import per.lee.bravo.mall.commodity.restful.BravoApiContext;
import per.lee.bravo.mall.commodity.restful.body.BravoApiRestfulBody;
import per.lee.bravo.mall.commodity.restful.protocol.BravoApiException;


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
            BravoApiException cus = new BravoApiException();
            cus.setDetail(_e.getMessage());
            cus.setCode(_e.getClass().getSimpleName());
            cus.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return context.fail(cus);
        }
    }


}
