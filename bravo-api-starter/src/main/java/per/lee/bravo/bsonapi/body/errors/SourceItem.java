package per.lee.bravo.bsonapi.body.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SourceItem {
    // 指向请求参数中的引起错误的资源
    private String pointer;
    // 引起错误的资源的参数
    private String parameter;
}
