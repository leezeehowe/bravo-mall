package per.lee.bravo.bsonapi.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import per.lee.bravo.mall.authorization.constant.illegalDtoParametereEnum.IllegalDtoParameterMeteData;

import java.util.List;

public class IllegalDtoParameterException extends AuthorizationSystemException {

    private static String message = "参数有误，请检查您的输入";

    @Getter
    private List<IllegalParameterDetail> illegalParameterDetailList;

    public IllegalDtoParameterException(String fieldName, Object fieldValue, String message) {
        super(message);
        this.illegalParameterDetailList = List.of(new IllegalParameterDetail(fieldName, fieldValue, message));
    }

    public IllegalDtoParameterException(IllegalDtoParameterMeteData illegalDtoParameterMeteData) {
        super(message);
        this.illegalParameterDetailList = List.of(new IllegalParameterDetail(illegalDtoParameterMeteData.getParameterName(),
                illegalDtoParameterMeteData.getParameterValue(), illegalDtoParameterMeteData.getMessage()));
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class IllegalParameterDetail {

    private String parameterName;

    private Object parameterValue;

    private String message;

}
