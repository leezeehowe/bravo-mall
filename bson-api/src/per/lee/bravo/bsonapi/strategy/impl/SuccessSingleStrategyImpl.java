package per.lee.bravo.bsonapi.strategy.impl;

import org.springframework.stereotype.Component;
import per.lee.bravo.bsonapi.body.data.DataItem;
import per.lee.bravo.bsonapi.strategy.GenerateBsonApiBodyStrategy;
import per.lee.bravo.bsonapi.strategy.entry.GenerateBsonApiBodyEntryEnum;
import per.lee.bravo.bsonapi.vo.BsonApiDataItem;


import java.util.List;


@Component
public class SuccessSingleStrategyImpl implements GenerateBsonApiBodyStrategy {

    @Override
    public GenerateBsonApiBodyEntryEnum getEntry() {
        return GenerateBsonApiBodyEntryEnum.SINGLE;
    }

    @Override
    public <T> List<DataItem> execute(T o) {
        BsonApiDataItem data;
        String type;
        data = (BsonApiDataItem) o;
        type = data.getClass().getSimpleName();
        return List.of(new DataItem(type, data.getId(), data.getAttributes(), data.getRelationships()));
    }

}
