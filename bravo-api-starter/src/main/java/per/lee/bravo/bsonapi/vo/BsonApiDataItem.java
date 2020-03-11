package per.lee.bravo.bsonapi.vo;



import per.lee.bravo.bsonapi.body.data.DataItem;

import java.util.Map;

public interface BsonApiDataItem {

    String getType();

    String getId();

    Map<String, Object> getAttributes();

    Map<String, DataItem> getRelationships();

}