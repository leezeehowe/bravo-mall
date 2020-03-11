package per.lee.bravo.bsonapi.body.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataItem {
    // 描述资源的类型，如 article、user、commodity
    private String type;
    // 该资源的唯一标识
    private String id;
    // 该资源的属性，除去id
    private Map<String, Object> attributes;
    // 与该资源相关的资源
    private Map<String, DataItem> relationships;
}
