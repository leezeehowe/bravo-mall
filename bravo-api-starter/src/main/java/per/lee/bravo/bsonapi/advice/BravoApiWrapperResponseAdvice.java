package per.lee.bravo.bsonapi.advice;

import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import per.lee.bravo.bsonapi.body.BravoApiBody;
import per.lee.bravo.bsonapi.strategy.context.GenerateBsonApiFDataFieldStrategyContext;


import java.util.Collection;

public class BravoApiWrapperResponseAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    GenerateBsonApiFDataFieldStrategyContext generateBsonApiFDataFieldStrategyContext;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public JSONObject beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 这里是全局异常拦截处理后的RestfulBody， 属于业务失败
        if(body instanceof BravoApiBody) {
            return ((BravoApiBody) body).toJSON();
        }
        // 这里是控制层返回的数据， 属于业务成功
        return BravoApiBody.success(generateBsonApiFDataFieldStrategyContext.generateDataField(body)).toJSON();
    }

    public static Class<?> get() {
        return Collection.class;
    }
}
