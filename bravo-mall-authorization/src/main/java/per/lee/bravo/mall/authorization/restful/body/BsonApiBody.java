package per.lee.bravo.mall.authorization.restful.body;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import per.lee.bravo.mall.authorization.restful.body.data.DataItem;
import per.lee.bravo.mall.authorization.restful.body.errors.ErrorItem;
import per.lee.bravo.mall.authorization.restful.body.errors.SourceItem;
import per.lee.bravo.mall.authorization.restful.body.meta.Meta;

import java.util.List;

@Data
@NoArgsConstructor
public class BsonApiBody {

    // 资源列表
    private List<DataItem> data;
    // 错误列表
    private List<ErrorItem> errors;
    // 元信息
    private Meta meta = new Meta();

    public BsonApiBody(List<DataItem> data) {
        this.data = data;
    }

    public JSONObject toJSON() {
        return JSONUtil.parseObj(this, true);
    }

    public static BsonApiBody success(List<DataItem> data) {
        return new BsonApiBody(data);
    }

    public static BsonApiBody fail(String id, String code, String title, String detail, HttpStatus httpStatus, List<SourceItem> source) {
        BsonApiBody bsonApiBody = new BsonApiBody();
        ErrorItem errorItem = new ErrorItem(id, String.valueOf(httpStatus.value()), code, title, detail, source);
        bsonApiBody.setErrors(List.of(errorItem));
        return bsonApiBody;
    }


}