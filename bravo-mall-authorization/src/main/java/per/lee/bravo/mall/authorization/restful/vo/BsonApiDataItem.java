package per.lee.bravo.mall.authorization.restful.vo;


import per.lee.bravo.mall.authorization.restful.body.data.DataItem;

import java.util.Map;

public interface BsonApiDataItem {

    String getType();

    String getId();

    Map<String, Object> getAttributes();

    Map<String, DataItem> getRelationships();

}
