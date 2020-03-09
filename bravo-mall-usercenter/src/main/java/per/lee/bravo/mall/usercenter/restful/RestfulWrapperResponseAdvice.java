package per.lee.bravo.mall.usercenter.restful;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import per.lee.bravo.mall.usercenter.restful.vo.RestfulBody;

@RestControllerAdvice
public class RestfulWrapperResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        RestfulBody restfulBody;
        // 这里是全局异常拦截处理后的RestfulBody， 属于业务失败
        if(body.getClass() == RestfulBody.class) {
            restfulBody = (RestfulBody) body;
        }
        // 这里是控制层返回的数据， 属于业务成功
        else {
            restfulBody = new RestfulBody();
            restfulBody.setCustomData(body);
            restfulBody.setCustomCode("0");
            restfulBody.setSuccess(true);
        }
        return restfulBody;
    }
}
