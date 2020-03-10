package per.lee.bravo.mall.authorization.restful.body.errors;

import lombok.Data;

@Data
public class SourceItem {
    // 指向请求参数中的引起错误的资源
    private String pointer;
    // 引起错误的资源的参数
    private String parameter;
}
