package per.lee.bravo.bsonapi.strategy.impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;
import per.lee.bravo.bsonapi.body.data.DataItem;
import per.lee.bravo.bsonapi.strategy.GenerateBsonApiDataFieldStrategy;
import per.lee.bravo.bsonapi.strategy.entry.GenerateBsonApiDataFieldStrategyEntryEnum;
import per.lee.bravo.bsonapi.vo.BsonApiDataItem;


import java.util.List;


@Component
public class SingleDataItemStrategyImpl implements GenerateBsonApiDataFieldStrategy {

    @Override
    public GenerateBsonApiDataFieldStrategyEntryEnum getEntry() {
        return GenerateBsonApiDataFieldStrategyEntryEnum.SINGLE;
    }

    @Override
    public <T> List<DataItem> execute(T o) {
        BsonApiDataItem data;
        String type;
        data = (BsonApiDataItem) o;
        type = StrUtil.emptyToDefault(data.getType(), data.getClass().getSimpleName());
        return List.of(new DataItem(type, data.getId(), data.getAttributes(), data.getRelationships()));
    }

}
