package per.lee.bravo.bsonapi.exception.dto;

import org.springframework.http.HttpStatus;
import per.lee.bravo.bsonapi.BravoErrorItemMetaData;
import per.lee.bravo.bsonapi.body.errors.SourceItem;
import per.lee.bravo.bsonapi.exception.BravoApiAbstractException;


public class IllegalDtoParameterAbstractException extends BravoApiAbstractException implements BravoErrorItemMetaData<IllegalDtoParameterAbstractException> {


    @Override
    public String getId() {
        return getClassSimpleName();
    }

    @Override
    public String getCode() {
        return getClassSimpleName();
    }

    @Override
    public String getTitle() {
        return "参数不合法";
    }

    @Override
    public IllegalDtoParameterAbstractException withStatus(HttpStatus status) {
        setStatus(status);
        return this;
    }

    @Override
    public IllegalDtoParameterAbstractException withParameter(String parameter) {
        setSource(new SourceItem(null, parameter));
        return this;
    }

    @Override
    public IllegalDtoParameterAbstractException withDetail(String detail) {
        setDetail(detail);
        return this;
    }
}