package per.lee.bravo.mall.authorization.constant.illegalDtoParametereEnum;

public enum UserIllegalParam implements IllegalDtoParameterMeteData {

    EXTERNAL_ID("用户外部ID");

    private String parameterName;

    private Object parameterValue;

    private String message = "不能为空";

    UserIllegalParam(String parameterName) {
        this.parameterName = parameterName;
    }

    public UserIllegalParam withValue(Object parameterValue) {
        this.parameterValue = parameterValue;
        return this;
    }


    public UserIllegalParam withValueAndMessage(Object parameterValue, String message) {
        this.parameterValue = parameterValue;
        this.message = message;
        return this;
    }


    @Override
    public String getParameterName() {
        return this.parameterName;
    }

    @Override
    public Object getParameterValue() {
        return this.parameterValue;
    }


    @Override
    public String getMessage() {
        return this.message;
    }
}
