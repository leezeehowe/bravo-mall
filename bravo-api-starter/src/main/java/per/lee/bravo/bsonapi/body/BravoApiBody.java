package per.lee.bravo.bsonapi.body;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import per.lee.bravo.bsonapi.body.data.DataItem;
import per.lee.bravo.bsonapi.body.errors.ErrorItem;
import per.lee.bravo.bsonapi.body.errors.SourceItem;
import per.lee.bravo.bsonapi.body.meta.Meta;

import java.util.List;

@Data
@NoArgsConstructor
public class BravoApiBody {

    // 资源列表
    private List<DataItem> data;
    // 错误列表
    private List<ErrorItem> errors;
    // 元信息
    private Meta meta = new Meta();

    public BravoApiBody(List<DataItem> data) {
        this.data = data;
    }

    public JSONObject toJSON() {
        return JSONUtil.parseObj(this, true);
    }

    public static BravoApiBody success(List<DataItem> data) {
        return new BravoApiBody(data);
    }

    public static BravoApiBody fail(String id, String code, String title, String detail, HttpStatus httpStatus, SourceItem source) {
        BravoApiBody bravoApiBody = new BravoApiBody();
        ErrorItem errorItem = new ErrorItem(id, String.valueOf(httpStatus.value()), code, title, detail, source);
        bravoApiBody.setErrors(List.of(errorItem));
        return bravoApiBody;
    }


}