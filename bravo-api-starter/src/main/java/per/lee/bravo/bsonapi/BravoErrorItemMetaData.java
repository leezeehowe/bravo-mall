package per.lee.bravo.bsonapi;

import org.springframework.http.HttpStatus;
import per.lee.bravo.bsonapi.body.errors.SourceItem;
import per.lee.bravo.bsonapi.exception.BravoApiAbstractException;

public interface BravoErrorItemMetaData<T extends BravoApiAbstractException> {


    String getId();

    String getCode();

    String getTitle();

    String getDetail();

    SourceItem getSource();

    HttpStatus getStatus();

    T withStatus(HttpStatus status);

    T withParameter(String parameter);

    T withDetail(String detail);

}
