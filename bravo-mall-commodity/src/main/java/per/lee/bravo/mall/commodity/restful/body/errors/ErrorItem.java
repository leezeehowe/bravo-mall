package per.lee.bravo.mall.commodity.restful.body.errors;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorItem {

    // 应用特定的错误码，以字符串表示。
    private String code;
    // 适用于这个问题的HTTP状态码，使用字符串表示。
    private String status;
    // 问题描述
    private String message;
    // 针对该问题的高可读性解释
    private String cause;
}


