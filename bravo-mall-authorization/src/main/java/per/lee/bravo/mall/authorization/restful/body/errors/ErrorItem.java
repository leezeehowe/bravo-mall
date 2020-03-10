package per.lee.bravo.mall.authorization.restful.body.errors;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ErrorItem {
    // 特定问题的唯一标识符。
    private String id;
    // 适用于这个问题的HTTP状态码，使用字符串表示。
    private String status;
    // 应用特定的错误码，以字符串表示。
    private String code;
    // 简短的，可读性高的问题总结。除了国际化本地化处理之外，不同场景下，相同的问题，值是不应该变动的。
    private String title;
    // 针对该问题的高可读性解释。与title相同,这个值可以被本地化。
    private String detail;
    // 包括错误资源的引用的对象
    private List<SourceItem> source;
}


