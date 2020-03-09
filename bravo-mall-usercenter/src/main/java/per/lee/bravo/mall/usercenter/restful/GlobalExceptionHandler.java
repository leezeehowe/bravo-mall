package per.lee.bravo.mall.usercenter.restful;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import per.lee.bravo.mall.usercenter.constant.operationError.CommonOperationErrorEnum;
import per.lee.bravo.mall.usercenter.exception.base.ExternalPlatformException;
import per.lee.bravo.mall.usercenter.exception.base.OperationException;
import per.lee.bravo.mall.usercenter.restful.vo.RestfulBody;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ExternalPlatformException.class)
    @ResponseBody
    public RestfulBody handleExternalPlatformException(HttpServletRequest request, ExternalPlatformException e) {
        RestfulBody restfulBody = new RestfulBody();
        String customErrorCode = e.getCustomErrorCode();
        String errMsg = e.getMessage();
        restfulBody.setCustomCode(customErrorCode);
        restfulBody.setCause(errMsg);
        restfulBody.setSuccess(false);
        return restfulBody;
    }

    @ExceptionHandler(value = OperationException.class)
    @ResponseBody
    public RestfulBody handleOperationException(HttpServletRequest request, OperationException e) {
        RestfulBody restfulBody = new RestfulBody();
        String customErrorCode = e.getCustomErrorCode();
        String errMsg = e.getMessage();
        restfulBody.setCustomCode(customErrorCode);
        restfulBody.setCause(errMsg);
        return restfulBody;
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    public RestfulBody handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException e) {
        RestfulBody restfulBody = new RestfulBody();
        String errMsg = CommonOperationErrorEnum.ILLEGAL_ARGUMENT.getDes() + e.getMessage();
        restfulBody.setCustomCode(CommonOperationErrorEnum.ILLEGAL_ARGUMENT.getCode());
        restfulBody.setCause(errMsg);
        return restfulBody;
    }

    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public RestfulBody handleNullPointerException(HttpServletRequest request, NullPointerException ne) {
        RestfulBody restfulBody = new RestfulBody();
        String errMsg = CommonOperationErrorEnum.OPERATION_ERROR.getDes();
        restfulBody.setCause(errMsg);
        restfulBody.setCustomCode(CommonOperationErrorEnum.OPERATION_ERROR.getCode());
        return restfulBody;
    }

}
