package per.lee.bravo.mall.authorization.constant.illegalDtoParametereEnum;


public interface IllegalDtoParameterMeteData {

    /**
     * 获取dto里面不合法的参数的参数名
     * @return 不合法的参数名
     */
    String getParameterName();

    /**
     * 获取dto里面不合法的参数的参数值
     * @return 不合法的参数值
     */
    Object getParameterValue();

    /**
     * 附带的信息，将返回给用户
     * @return 附带的信息，将返回给用户
     */
    String getMessage();

}
