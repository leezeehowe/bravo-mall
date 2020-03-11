package per.lee.bravo.bsonapi.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import per.lee.bravo.bsonapi.BravoErrorItemMetaData;
import per.lee.bravo.bsonapi.body.BravoApiBody;
import per.lee.bravo.bsonapi.body.errors.SourceItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
public class BravoApiGlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BravoApiBody handleIllegalDtoParameterException(HttpServletRequest request, HttpServletResponse response, Exception _e) throws Exception {
        BravoErrorItemMetaData<?> e;
        if(_e instanceof BravoErrorItemMetaData) {
            e = (BravoErrorItemMetaData<?>)_e;
            String id, code, title, detail;
            HttpStatus status;
            SourceItem source;
            id = e.getId();
            code = e.getCode();
            title = e.getTitle();
            detail = e.getDetail();
            status = Optional.ofNullable(e.getStatus()).orElse(HttpStatus.INTERNAL_SERVER_ERROR);
            source = e.getSource();
            response.setStatus(status.value());
            return BravoApiBody.fail(id, code, title, detail, status, source);
        }
        else {
            throw _e;
        }
    }

}
