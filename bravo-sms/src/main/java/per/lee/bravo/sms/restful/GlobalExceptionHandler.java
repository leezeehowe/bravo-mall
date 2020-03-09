package per.lee.bravo.sms.restful;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import per.lee.bravo.sms.exception.SendSmsException;
import per.lee.bravo.sms.restful.vo.RestfulBody;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SendSmsException.class)
    @ResponseBody
    public RestfulBody handleSendSmsException(SendSmsException e) {
        log.error("\n" + e.getClass().getName() + "-发送短信失败\n" +  JSONUtil.toJsonPrettyStr(e));
        RestfulBody restfulBody = new RestfulBody();
        restfulBody.setCustomCode("-1");
        restfulBody.setCause(e.getMessage());
        restfulBody.setSuccess(false);
        return restfulBody;
    }

}
