package per.lee.bravo.mall.commodity.restful.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import per.lee.bravo.mall.commodity.restful.BravoApiContext;
import per.lee.bravo.mall.commodity.restful.body.BravoApiRestfulBody;


import java.util.Collection;

@Slf4j
@RestControllerAdvice
public class BravoApiWrapperResponseAdvice implements ResponseBodyAdvice<Object> {

    private BravoApiContext context;

    public BravoApiWrapperResponseAdvice(BravoApiContext context) {
        this.context = context;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public BravoApiRestfulBody beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 这里是全局异常拦截处理后的RestfulBody， 属于业务失败
        if(body instanceof BravoApiRestfulBody) {
            BravoApiRestfulBody b = (BravoApiRestfulBody) body;
            response.setStatusCode(HttpStatus.valueOf(Integer.valueOf(b.getException().getStatus())));
            return b;
        }
        // 这里是控制层返回的数据， 属于业务成功
        return context.success(body);
    }

    public static Class<?> get() {
        return Collection.class;
    }
}
