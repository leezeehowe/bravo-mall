package per.lee.bravo.mall.authorization.advice;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import per.lee.bravo.bsonapi.advice.BravoApiWrapperResponseAdvice;

@RestControllerAdvice
public class ResponseBodyAdvice extends BravoApiWrapperResponseAdvice {
}
