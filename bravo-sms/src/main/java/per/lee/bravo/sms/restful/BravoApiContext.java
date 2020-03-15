package per.lee.bravo.sms.restful;

import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import per.lee.bravo.sms.restful.body.BravoApiRestfulBody;
import per.lee.bravo.sms.restful.body.errors.ErrorItem;
import per.lee.bravo.sms.restful.body.meta.Meta;
import per.lee.bravo.sms.restful.protocol.BravoApiException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


@Component
public class BravoApiContext {

    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${api.version}")
    private String apiVersion;
    @Value("${api.authors}")
    private List<String> authors;

    private HttpServletRequest request;

    public BravoApiContext(HttpServletRequest request) {
        this.request = request;
    }

    public BravoApiRestfulBody generateRestfulBody() {
        BravoApiRestfulBody restfulBody;
        Meta meta;
        String server, server_time, request_id;
        server = applicationName;
        server_time = DateUtil.now();
        request_id = request.getHeader("requestId");
        meta = new Meta(applicationName, authors, apiVersion);
        restfulBody = new BravoApiRestfulBody(request_id, server, server_time, meta);
        return restfulBody;
    }

    public BravoApiRestfulBody success(Object data) {
        BravoApiRestfulBody restfulBody = generateRestfulBody();
        restfulBody.setResource(data);
        return restfulBody;
    }

    public BravoApiRestfulBody fail(BravoApiException exception) {
        BravoApiRestfulBody restfulBody = generateRestfulBody();
        ErrorItem errorItem;
        HttpStatus status;
        String code, message, cause;
        code = exception.getCode();
        message = exception.getMessage();
        cause = exception.getDetail();
        status = Optional.ofNullable(exception.getStatus()).orElse(HttpStatus.INTERNAL_SERVER_ERROR);
        errorItem = new ErrorItem(code, String.valueOf(status.value()), message, cause);
        restfulBody.setException(errorItem);
        return restfulBody;
    }
}