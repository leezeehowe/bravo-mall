package per.lee.bravo.bsonapi;

import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import per.lee.bravo.bsonapi.body.BsonApiBody;
import per.lee.bravo.bsonapi.strategy.context.GenerateBsonApiBodyStrategyContext;


import java.util.Collection;

@RestControllerAdvice
public class RestfulWrapperResponseAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    GenerateBsonApiBodyStrategyContext generateBsonApiBodyStrategyContext;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public JSONObject beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 这里是全局异常拦截处理后的RestfulBody， 属于业务失败
        if(body instanceof BsonApiBody) {
            return ((BsonApiBody) body).toJSON();
        }
        // 这里是控制层返回的数据， 属于业务成功
        return BsonApiBody.success(generateBsonApiBodyStrategyContext.doSuccess(body)).toJSON();
    }

    public static Class<?> get() {
        return Collection.class;
    }
}
