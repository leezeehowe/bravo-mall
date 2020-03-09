package per.lee.bravo.sms.restful.vo;

import lombok.Data;

@Data
public class RestfulBody {

    // 业务是否成功
    private boolean isSuccess = false;

    //自定义返回数据
    private Object customData;

    // 自定义业务执行情况状态码
    private String customCode;

    // 错误信息
    private String cause;
}

