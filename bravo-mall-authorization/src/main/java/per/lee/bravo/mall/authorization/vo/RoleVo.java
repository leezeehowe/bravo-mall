package per.lee.bravo.mall.authorization.vo;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import per.lee.bravo.mall.authorization.restful.body.data.DataItem;
import per.lee.bravo.mall.authorization.restful.vo.BsonApiDataItem;

import java.util.Map;

@Data
@AllArgsConstructor
public class RoleVo implements BsonApiDataItem {

    private Long id;

    private String name;

    @Override
    public String getType() {
        return "role";
    }

    @Override
    public String getId() {
        return id.toString();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return JSONUtil.parseObj(this, true);
    }

    @Override
    public Map<String, DataItem> getRelationships() {
        return null;
    }
}
